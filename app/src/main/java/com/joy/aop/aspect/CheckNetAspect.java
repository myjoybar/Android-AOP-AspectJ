package com.joy.aop.aspect;

import android.content.Context;

import com.joy.aop.MainActivity;
import com.joy.aop.utils.ContextUtils;
import com.joy.aop.annotation.CheckNet;
import com.joybar.library.common.log.L;
import com.joybar.library.common.net.NetworkUtils;
import com.joybar.library.common.wiget.ToastUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by joybar on 14/04/2018.
 */

@Aspect
public class CheckNetAspect {

	private static final String TAG = MainActivity.TAG;


	@Pointcut("execution(@com.joy.aop.annotation.CheckNet  * *(..))")
	public void executionCheckNet() {
	}

	@Around("executionCheckNet()")
	public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		CheckNet checkLogin = signature.getMethod().getAnnotation(CheckNet.class);
		if (checkLogin != null) {
			Context context = ContextUtils.getContext(joinPoint.getThis()) ;

			boolean isNetAvailable = NetworkUtils.isAvailable(context);
			if(isNetAvailable){
				L.i(TAG, "checkNet: 有网络");
				return joinPoint.proceed();
			}else{
				L.i(TAG, "checkNet: 没有网络");
				boolean isShowTips = checkLogin.isShowTips();
				if(isShowTips){
					ToastUtil.showLong(context,"当前网络异常，请检查网络连接");
				}
				return null;
			}

		}
		return joinPoint.proceed();
	}
}
