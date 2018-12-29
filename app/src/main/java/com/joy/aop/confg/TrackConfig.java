package com.joy.aop.confg;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.joy.aop.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by joybar on 2018/12/24.
 */

public class TrackConfig {
	/**
	 * [{
	 * "className": "com.joy.aop.MainActivity",
	 * "resourceEntryNames": ["btn_getname", "btn_permission"],
	 * "methodNames": ["businessTrack1"]
	 * }]
	 */

	private static Map<String, String> viewTagHashMap = new HashMap<>();
	private static Map<String, String> methodHashMap = new HashMap<>();
	public static final String UNDER_LINE = "_";

	public static void main(String[] args) {
		List<TrackConfigData> trackConfigDataList = getTrackConfigData();
		System.out.println(Arrays.toString(trackConfigDataList.toArray()));
	}


	private static List<TrackConfigData> getTrackConfigData() {
		String jsonStr = "[{\"className\":\"com.joy.aop.MainActivity\",\"resourceEntryNames\":[\"btn_getname\",\"btn_permission\",\"btn_click\"]," +
				"\"methodNames\":[\"businessTrack1\"]}]";
		List<TrackConfigData> trackConfigDataList = getObjectList(jsonStr, TrackConfigData.class);
		return trackConfigDataList;
	}

	public static void intConfig() {
		if (viewTagHashMap.size() != 0 || methodHashMap.size() != 0) {
			return;
		}
		List<TrackConfigData> trackConfigDataList = getTrackConfigData();
		int size = trackConfigDataList.size();
		Log.i(MainActivity.TAG, Arrays.toString(trackConfigDataList.toArray()));
		for (int i = 0; i < size; i++) {
			TrackConfigData trackConfigData = trackConfigDataList.get(i);
			String className = trackConfigData.className;
			List<String> resourceEntryNames = trackConfigData.resourceEntryNames;
			List<String> methodNames = trackConfigData.methodNames;
			int resourceEntryNamesSize = resourceEntryNames.size();
			int methodNamesSize = methodNames.size();
			for (int k = 0; k < resourceEntryNamesSize; k++) {
				String resourceEntryName = resourceEntryNames.get(k);
				String viewTagName = className + UNDER_LINE + resourceEntryName;
				viewTagHashMap.put(viewTagName, viewTagName);
			}

			for (int j = 0; j < methodNamesSize; j++) {
				String methodName = methodNames.get(j);
				String methodTagName = className + UNDER_LINE + methodName;
				methodHashMap.put(methodTagName, methodTagName);
			}
		}

	}

	public static Map<String, String> getViewTagHashMap() {
		return viewTagHashMap;

	}

	public static Map<String, String> getMethodHashMap() {
		return methodHashMap;

	}

	public static class TrackConfigData {
		public String className;
		public List<String> resourceEntryNames;
		public List<String> methodNames;

		@Override
		public String toString() {
			return "TrackConfigData{" + "className='" + className + '\'' + ", resourceEntryNames=" + Arrays.toString(resourceEntryNames.toArray()) + ", " +
					"methodNames=" +
					Arrays.toString(methodNames.toArray()) + '}';
		}
	}


	public static <T> List<T> getObjectList(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
			for (JsonElement jsonElement : arry) {
				list.add(gson.fromJson(jsonElement, cls));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

}
