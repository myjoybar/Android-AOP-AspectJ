package com.joy.aop.aspect;

import android.view.View;

import com.joy.aop.MainActivity;
import com.joy.aop.Utils.GATestUtils;
import com.joy.aop.Utils.MarkViewUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by joybar on 15/04/2018.
 */

@Aspect
public class ViewClickTrackAspect {

    private static final String TAG = MainActivity.TAG;

    @Around("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
    public void onActivityMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] parameterValues = joinPoint.getArgs();
        View view = (View) parameterValues[0];
        if(MarkViewUtils.isTrackView(view)){
            GATestUtils.send(view.getTag().toString(),"is clicked");
        }
        joinPoint.proceed();

    }



}
