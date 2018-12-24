package com.joy.aop.Utils;

import android.util.Log;

/**
 * Created by joybar on 2018/12/24.
 */

public class GATestUtils {

	public static void send(String catagory, String action) {
		Log.e("MainActivity", "统计的数据：catagory = " + catagory + "action = " + action);
	}
}
