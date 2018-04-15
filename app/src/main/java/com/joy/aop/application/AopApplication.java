package com.joy.aop.application;

import android.app.Application;

import com.joybar.library.common.log.L;
import com.joybar.library.common.log.LogLevel;

/**
 * Created by joybar on 14/04/2018.
 */

public class AopApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLog();

    }

    private void initLog() {
        L.setLogEnable(true);
        L.setLogLevel(LogLevel.TYPE_VERBOSE);
        L.setStackTrace(7);
        L.setLineIndicatorEnable(false);
    }

}
