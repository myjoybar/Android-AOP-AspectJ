package com.joy.aop.aspect;

import android.util.Log;

import com.joy.aop.MainActivity;
import com.joy.aop.utils.JoinPointUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by joybar on 15/04/2018.
 */

@Aspect
public class ActivityLifeCycle {

    private static final String TAG = MainActivity.TAG;

//    @Before("execution(* android.app.Activity.on**(..))")
    @Before("execution(* com.joy.aop.BaseActivity.on**(..))")
    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
//        String key = joinPoint.getSignature().toString();
//        Log.d(TAG, "onActivityMethodBefore: " + key+"\n"+joinPoint.getThis());
//        String fullClassName = joinPoint.getThis().toString();
        String simpleClassName = JoinPointUtils.getSimpleName(joinPoint);
        String methodInfo = JoinPointUtils.getParameterNamesAndParameterValues(joinPoint);
        Log.d(TAG, "onActivityMethodBefore-----"+ simpleClassName + "：" + methodInfo);
    }

//    @After("execution(* android.app.Activity.on*(android.os.Bundle))")
    @After("execution(* com.joy.aop.BaseActivity.on*(android.os.Bundle))")
    public void onActivityMethodAfter(JoinPoint joinPoint) throws Throwable {
//        String simpleClassName = JoinPointUtils.getSimpleName(joinPoint);
//        String methodInfo = JoinPointUtils.getParameterNamesAndParameterValues(joinPoint);
//        Log.d(TAG, "onActivityMethodAfter"+simpleClassName + "：" + methodInfo);

        String key = joinPoint.getSignature().toString();
        Log.d(TAG, "onActivityMethodAfter: " + key);
    }


  //  @Around("execution(* android.app.Activity.on*(android.os.Bundle))")
 // @Before("execution(* *..Activity+.*(..)) ||execution(* *..Fragment+.*(..))")
//    @Around("execution(* android.app.Activity.on*(..))")
//    public void onActivityMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        String simpleClassName = JoinPointUtils.getSimpleName(joinPoint);
//        String methodInfo = JoinPointUtils.getParameterNamesAndParameterValues(joinPoint);
//        L.d(TAG, simpleClassName + "：" + methodInfo);
//        joinPoint.proceed();
//        L.d(TAG, simpleClassName + "：" + methodInfo);
//    }
//
//
//
//    @Around("execution(* android.app.Fragment.on**(..))")
//    public void onFragmentMethodBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        String simpleClassName = JoinPointUtils.getSimpleName(joinPoint);
//        String methodInfo = JoinPointUtils.getParameterNamesAndParameterValues(joinPoint);
//        L.d(TAG, simpleClassName + "：" + methodInfo);
//        joinPoint.proceed();
//        L.d(TAG, simpleClassName + "：" + methodInfo);
//    }
}
