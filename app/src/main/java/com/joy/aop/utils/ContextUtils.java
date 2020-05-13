package com.joy.aop.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

/**
 * Created by joybar on 14/04/2018.
 */

public class ContextUtils {

    public static Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Application) {
            return (Application) object;
        } else if (object instanceof Fragment) {
            android.app.Fragment fragment = (android.app.Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof Fragment) {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
