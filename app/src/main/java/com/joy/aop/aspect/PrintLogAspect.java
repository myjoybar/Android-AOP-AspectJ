package com.joy.aop.aspect;

import android.os.Build;
import android.os.Looper;
import android.os.Trace;
import android.util.Pair;

import com.joy.aop.MainActivity;
import com.joy.aop.Utils.Strings;
import com.joy.aop.annotation.LogTrace;
import com.joybar.library.common.log.L;
import com.joybar.library.common.log.LogLevel;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.concurrent.TimeUnit;

/**
 * Created by joybar on 14/04/2018.
 */

@Aspect
public class PrintLogAspect {
    private static final String TAG = MainActivity.TAG;
    private static final String BEFORE_TAG = "[BEFORE]: ";
    private static final String END_TAG = "[END]: ";

    private static final String POINTCUT_METHOD = "execution(@com.joy.aop.annotation.LogTrace  * " +
            "" + "" + "" + "" + "" + "" + "" + "" + "*(..))";

    @Pointcut(POINTCUT_METHOD)
    public void executionPrintLog() {
    }

    @Around("executionPrintLog()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LogTrace printLog = signature.getMethod().getAnnotation(LogTrace.class);
        int logLevel = LogLevel.TYPE_VERBOSE;
        boolean traceSpendTime = false;
        if (printLog != null) {
            logLevel = printLog.level();
            traceSpendTime = printLog.traceSpendTime();
            Pair<String, String> enterMethodPair = enterMethodInfo(joinPoint);
            String tag = enterMethodPair.first;
            String msg = enterMethodPair.second;
            if (logLevel == LogLevel.TYPE_VERBOSE) {
                L.v(tag, msg);
            } else if (logLevel == LogLevel.TYPE_DEBUG) {
                L.d(tag, msg);
            } else if (logLevel == LogLevel.TYPE_INFO) {
                L.i(tag, msg);
            } else if (logLevel == LogLevel.TYPE_WARN) {
                L.w(tag, msg);
            } else if (logLevel == LogLevel.TYPE_ERROR) {
                L.e(tag, msg);
            }

        }
        Object result = null;
        Pair<String, String> exitMethodPair = null;
        if (traceSpendTime) {
            long startNanos = System.nanoTime();
            result = joinPoint.proceed();
            long stopNanos = System.nanoTime();
            long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);
            exitMethodPair = exitMethodInfo(joinPoint, result, lengthMillis);
        } else {
            result = joinPoint.proceed();
            exitMethodPair = exitMethodInfo(joinPoint, result, 0);
        }


        String tag = exitMethodPair.first;
        String msg = exitMethodPair.second;
        if (logLevel == LogLevel.TYPE_VERBOSE) {
            L.v(tag, msg);
        } else if (logLevel == LogLevel.TYPE_DEBUG) {
            L.d(tag, msg);
        } else if (logLevel == LogLevel.TYPE_INFO) {
            L.i(tag, msg);
        } else if (logLevel == LogLevel.TYPE_WARN) {
            L.w(tag, msg);
        } else if (logLevel == LogLevel.TYPE_ERROR) {
            L.e(tag, msg);
        }
        return result;
    }

    private Pair<String, String> enterMethodInfo(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        Class<?> cls = codeSignature.getDeclaringType();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();

        StringBuilder builder = new StringBuilder("\u21E2 ");
        builder.append(BEFORE_TAG + methodName).append('(');
        for (int i = 0; i < parameterValues.length; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(parameterNames[i]).append('=');
            builder.append(Strings.toString(parameterValues[i]));
        }
        builder.append(')');

        if (Looper.myLooper() != Looper.getMainLooper()) {
            builder.append(" [Thread:\"").append(Thread.currentThread().getName()).append("\"]");
        }
        String className = asTag(cls);
        //  Log.d(TAG, "className=" + className);
        Pair pair = new Pair(className, builder.toString());
        return pair;

    }


    private static Pair<String, String> exitMethodInfo(JoinPoint joinPoint, Object result, long
            lengthMillis) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        }

        Signature signature = joinPoint.getSignature();

        Class<?> cls = signature.getDeclaringType();
        String methodName = signature.getName();
        boolean hasReturnType = signature instanceof MethodSignature && ((MethodSignature)
                signature).getReturnType() != void.class;

        StringBuilder builder = new StringBuilder("\u21E0 ").append(END_TAG + methodName);
        if (0 != lengthMillis) {
            builder.append(" [").append(lengthMillis).append("ms]");
        }
        if (hasReturnType) {
            builder.append(" = ");
            builder.append(Strings.toString(result));
        }

        // Log.d(asTag(cls), builder.toString());
        Pair pair = new Pair(asTag(cls), builder.toString());
        return pair;
    }

    private static String asTag(Class<?> cls) {
        if (cls.isAnonymousClass()) {
            return asTag(cls.getEnclosingClass());
        }
        return cls.getSimpleName();
    }
}
