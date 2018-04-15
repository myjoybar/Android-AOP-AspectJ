package com.joy.aop.aspect;

import android.app.Activity;

import com.joy.aop.MainActivity;
import com.joy.aop.annotation.CheckPermission;
import com.joybar.library.common.log.L;
import com.joybar.library.permission.PermissionGuide;
import com.joybar.library.permission.PermissionRequestActivity;
import com.joybar.library.permission.interf.IPermission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by joybar on 14/04/2018.
 */

@Aspect
public class CheckPermissionAspect {
    private static final String TAG = MainActivity.TAG;
    private static final String BEFORE_TAG = "[BEFORE]: ";
    private static final String END_TAG = "[END]: ";

    private static final String POINTCUT_METHOD = "execution(@com.joy.aop.annotation.CheckPermission  * " +
            "" + "" + "" + "" + "*(..))";

    @Pointcut(POINTCUT_METHOD)
    public void executionCheckPermission() {
    }

    @Around("executionCheckPermission()")
    public Object checkPermission(final ProceedingJoinPoint joinPoint) throws Throwable {
        L.e(TAG,"start  checkPermission");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckPermission checkPermission = signature.getMethod().getAnnotation(CheckPermission.class);

        if (checkPermission != null) {
            String[] permissions = checkPermission.permissions();
            final int requestCode = checkPermission.requestCode();
            if (joinPoint.getThis() instanceof Activity) {
                final Activity activity = (Activity) joinPoint.getThis();
               // PermissionManager.getInstance().requestPermissions(activity,permissions, requestCode);
                PermissionRequestActivity.permissionRequest(activity, permissions, requestCode, new IPermission() {

                    @Override
                    public void permissionGranted() {
                        L.e(TAG,"获取权限成功");
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                    @Override
                    public void permissionDenied(int requestCode, String[] permissions) {
                        L.e(TAG,"拒绝权限，requestCode="+requestCode);
                        PermissionGuide.showTipsDialog(activity,permissions);
                    }

                    @Override
                    public void permissionCanceled(int requestCode) {
                        L.e(TAG,"取消权限，requestCode="+requestCode);

                    }
                });
            }else{
                L.e(TAG,"权限上下文必须是Activity");
            }

        }
       // Object  result = joinPoint.proceed();
        return null ;
    }




}
