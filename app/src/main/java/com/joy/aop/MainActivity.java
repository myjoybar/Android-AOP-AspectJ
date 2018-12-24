package com.joy.aop;

import android.Manifest;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.joy.aop.Utils.MarkViewUtils;
import com.joy.aop.annotation.CheckLogin;
import com.joy.aop.annotation.CheckNet;
import com.joy.aop.annotation.CheckPermission;
import com.joy.aop.annotation.LogTrace;
import com.joy.aop.annotation.MethodTrack;
import com.joybar.library.common.log.LogLevel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


	public static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MarkViewUtils.traversalAndMarkView(getClass(), (ViewGroup) ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content))
				.getChildAt(0));
		findViewById(R.id.btn_login).setOnClickListener(this);
		findViewById(R.id.btn_net).setOnClickListener(this);
		findViewById(R.id.btn_permission).setOnClickListener(this);
		findViewById(R.id.btn_getname).setOnClickListener(this);
		findViewById(R.id.btn_send_ga).setOnClickListener(this);


	}


	@LogTrace(traceSpendTime = false)
	@CheckLogin
	private void loginCheck() {
		Log.i(TAG, "已经登陆，执行登陆后的逻辑");
	}

	@CheckNet(isShowTips = true)
	private void netCheck() {
		Log.i(TAG, "网络已经连接，执行逻辑");
	}

	@CheckPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
			.CAMERA}, requestCode = 1)
	private void permissionCheck() {

		Log.i(TAG, "已经检查权限，执行授予权限后的逻辑");

//        //需要请求的权限
//        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
//        PermissionManager.getInstance().requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE}, 1);
//        //  PermissionManager.getInstance().requestPermissions(this,permissions, 1);
	}

	@LogTrace(level = LogLevel.TYPE_INFO, traceSpendTime = true)
	public String printLog(String first, String last) {
		Log.i(TAG, "已经PrintLog，执行PrintLog后的逻辑");
		SystemClock.sleep(15); // Don't ever really do this!
		return first + " " + last;
	}

	@MethodTrack()
	public String businessTrack1(String arg1, String arg2) {
		Log.i(TAG, "businessTrack1");
		return arg1 + arg2;
	}

	@MethodTrack()
	public void businessTrack2() {
		Log.i(TAG, "businessTrack2");
	}

	// @CheckLogin
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_login:
				loginCheck();
				break;
			case R.id.btn_net:
				netCheck();
				break;
			case R.id.btn_permission:
				permissionCheck();
				break;
			case R.id.btn_getname:
				printLog("Jake", "Wharton");
				break;
			case R.id.btn_send_ga:
				businessTrack1("I am ", "Joy");
				businessTrack2();
				break;
			default:
				break;
		}

	}
}
