package com.joy.aop.Utils;

import android.view.View;
import android.view.ViewGroup;

import com.joy.aop.confg.TrackConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joybar on 2018/12/24.
 */

public class MarkViewUtils {

	static Map<String, String> ViewClassHashMap = new HashMap();
	static final String UNDER_LINE = "_";
	static final int NO_ID = 0xffffffff;

	public static void traversalAndMarkView(Class<?> classZ, ViewGroup rootView) {
		String className = classZ.getName();
		if (ViewClassHashMap.containsKey(className)) {
			return;
		}
		ViewClassHashMap.put(className, className);
		for (int i = 0; i < rootView.getChildCount(); i++) {
			View childVg = rootView.getChildAt(i);
			if (childVg.getId() == NO_ID) {
				continue;
			}
			markView(classZ, childVg);
			if (childVg instanceof ViewGroup) {
				traversalAndMarkView(classZ, (ViewGroup) childVg);
			}
		}
	}

	private static void markView(Class<?> classZ, View view) {
		String tagName = classZ.getName() + UNDER_LINE + getIdName(view);
		view.setTag(tagName);

	}

	private static String getIdName(View view) {

		if (view.getId() == NO_ID) {
			return "no-id";
		} else {
			return view.getResources().getResourceEntryName(view.getId());
			// return view.getResources().getResourceName(view.getId());
		}
	}

	public static boolean isTrackView(View view) {
		String tag = (String) view.getTag();
		if (TrackConfig.getViewTagHashMap().containsKey(tag)) {
			return true;
		}
		return false;

	}
	public static boolean isTrackMethod(String methodTagName) {
		if (TrackConfig.getMethodHashMap().containsKey(methodTagName)) {
			return true;
		}
		return false;

	}

}
