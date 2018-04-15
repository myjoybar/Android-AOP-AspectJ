package com.joy.aop.aspect;

import android.content.Context;
import android.widget.Toast;

import com.joy.aop.MainActivity;
import com.joy.aop.Utils.ContextUtils;
import com.joy.aop.annotation.CheckLogin;
import com.joybar.library.common.log.L;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by joybar on 14/04/2018.
 */

@Aspect
public class CheckLoginAspect {
    private static final String TAG = MainActivity.TAG;


    @Pointcut("execution(@com.joy.aop.annotation.CheckLogin  * *(..))")
    public void executionCheckLogin() {
    }

    @Around("executionCheckLogin()")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckLogin checkLogin = signature.getMethod().getAnnotation(CheckLogin.class);
        if (checkLogin != null) {
            Context context = ContextUtils.getContext(joinPoint.getThis()) ;
            java.util.Random random=new java.util.Random();
            int result=random.nextInt(2)+1;// 返回[0,2)
            if (result==1) {
                L.i(TAG, "checkLogin: 登录成功 ");
                Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();;
                return joinPoint.proceed();
            } else {
                L.i(TAG, "checkLogin: 请登录");
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        return joinPoint.proceed();
    }
}
