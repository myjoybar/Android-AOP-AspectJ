package com.joy.aop.Utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.joy.aop.R;
import com.joybar.library.common.app.ScreenUtils;

/**
 * Created by joybar on 2018/12/24.
 */

public class DialogTestUtils {

	public static void showDialog(Activity activity){
		View view = LayoutInflater.from(activity).inflate(R.layout.custom_dialog,null,false);
		final AlertDialog dialog = new AlertDialog.Builder(activity).setView(view).create();
		Button button = view.findViewById(R.id.btn_click);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
		dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(activity)/4*3), ScreenUtils.getScreenHeight(activity)/4*3);
	}
}
