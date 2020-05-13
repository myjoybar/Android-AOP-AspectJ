package com.joy.aop.aspect;

import android.util.Log;
import android.view.View;

import com.joy.aop.MainActivity;
import com.joy.aop.utils.GATestUtils;
import com.joy.aop.utils.MarkViewUtils;

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
		boolean isTrackView = MarkViewUtils.isTrackView(view);
		Log.i(MainActivity.TAG, "this view can be track = "+isTrackView);
		if (isTrackView) {
			GATestUtils.send(view.getTag().toString(), "is clicked");
		}
		joinPoint.proceed();
	}
//
//	@Before("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
//	public void onActivityMethodAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		Log.d(TAG, "start tracker view's click action");
//		if (null != joinPoint) {
//			Object[] parameterValues = joinPoint.getArgs();
//			if (null != parameterValues && parameterValues.length != 0) {
//				View view = (View) parameterValues[0];
//				if (null != view && !TextUtils.isEmpty(view.getTag().toString())) {
//					IGTracker.getInstance().sentEvent(CLICK, view.getTag().toString());
//				}
//			}
//		}
//
//	}

//=================
//
//	@Before("execution(* android.view.View.OnClickListener.onClick(android.view.View))")
//	public void onActivityMethodAround(JoinPoint joinPoint) throws Throwable {
//		Log.d(TAG, "start tracker view's click action");
//		if (null != joinPoint) {
//			Object[] parameterValues = joinPoint.getArgs();
//			if (null != parameterValues && parameterValues.length != 0) {
//				View view = (View) parameterValues[0];
//				String tagName = "";
//				if (null != view && TextUtils.isEmpty(tagName = getViewTag(view))) Log.d(TAG, "MethodTrack click  success");
//				IGTracker.getInstance().sentEvent(CLICK, tagName);
//			}
//		}
//	}
//
//
//	public static String getViewTag(View view) {
//		String className = view.getContext().getClass().getName();
//		String idName = getIdName(view);
//		String tagName = className + UNDER_LINE + idName;
//		return tagName;
//	}
//
//
//	private static String getIdName(View view) {
//		if (view.getId() == NO_ID) {
//			return "no-id";
//		} else {
//			return view.getResources().getResourceEntryName(view.getId());
//			// return view.getResources().getResourceName(view.getId());
//		}
//	}
//

}
