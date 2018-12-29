package com.joy.aop.aspect;

import android.os.Build;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Pair;

import com.joy.aop.MainActivity;
import com.joy.aop.Utils.GATestUtils;
import com.joy.aop.Utils.MarkViewUtils;
import com.joy.aop.Utils.Strings;
import com.joy.aop.annotation.MethodTrack;
import com.joy.aop.confg.TrackConfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by joybar on 14/04/2018.
 */

@Aspect
public class MethodTrackAspect {
	private static final String TAG = MainActivity.TAG;

	private static final String POINTCUT_METHOD = "execution(@com.joy.aop.annotation.MethodTrack  * *(..))";


	@Pointcut(POINTCUT_METHOD)
	public void executionMethodTrack() {
	}

	@Around("executionMethodTrack()")
	public Object sendAnalyticsData(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		MethodTrack methodTrack = signature.getMethod().getAnnotation(MethodTrack.class);
		boolean isTrackParameter = false;
		Object result = null;
		if (methodTrack != null) {
			CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
			Class<?> cls = codeSignature.getDeclaringType();
			String methodName = codeSignature.getName();
			String className = getFullName(cls);
			String methodTagName = className + TrackConfig.UNDER_LINE + methodName;
			boolean isTrackMethod = MarkViewUtils.isTrackMethod(methodTagName);
			//Log.e(MainActivity.TAG, "methodTagName = " + methodTagName + ",isTrackMethod=" + isTrackMethod);
			if (isTrackMethod) {
				isTrackParameter = methodTrack.isTrackParameter();
				String properties = methodTrack.properties();
				Pair<String, String> enterMethodPair = enterMethodInfo(joinPoint);
				String tag = enterMethodPair.first;
				String msg = enterMethodPair.second;
				if (!TextUtils.isEmpty(properties)) {
					msg = msg + properties;
				}
				GATestUtils.send(tag, msg);
				result = joinPoint.proceed();
				Pair<String, String> exitMethodPair = exitMethodInfo(joinPoint, result);
				String exitTag = exitMethodPair.first;
				String exitMsg = exitMethodPair.second;
				if (!TextUtils.isEmpty(properties)) {
					exitMsg = exitMsg + properties;
				}
				if (!TextUtils.isEmpty(exitMsg)) {
					GATestUtils.send(exitTag, exitMsg);
				}
			} else {
				result = joinPoint.proceed();
			}

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
		builder.append(methodName).append('(');
		for (int i = 0; i < parameterValues.length; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(parameterNames[i]).append('=');
			builder.append(Strings.toString(parameterValues[i]));
		}
		builder.append(')');

		String className = asTag(cls);
		//Log.d(TAG, "className=" + className);
		Pair pair = new Pair(className, builder.toString());
		return pair;

	}


	private static Pair<String, String> exitMethodInfo(JoinPoint joinPoint, Object result) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			Trace.endSection();
		}

		Signature signature = joinPoint.getSignature();

		Class<?> cls = signature.getDeclaringType();
		String methodName = signature.getName();
		boolean hasReturnType = signature instanceof MethodSignature && ((MethodSignature) signature).getReturnType() != void.class;

		StringBuilder builder = new StringBuilder("\u21E0 ").append(methodName);

		if (hasReturnType) {
			builder.append(" = ");
			builder.append(Strings.toString(result));
		}

		//Log.d(asTag(cls), builder.toString());
		Pair pair = new Pair(asTag(cls), builder.toString());
		return pair;
	}

	private static String asTag(Class<?> cls) {
		if (cls.isAnonymousClass()) {
			return asTag(cls.getEnclosingClass());
		}
		return cls.getSimpleName();
	}

	private static String getFullName(Class<?> cls) {
		if (cls.isAnonymousClass()) {
			return asTag(cls.getEnclosingClass());
		}
		return cls.getName();
	}
}
