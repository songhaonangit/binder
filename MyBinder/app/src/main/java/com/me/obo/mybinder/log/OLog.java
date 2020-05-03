package com.me.obo.mybinder.log;

import android.util.Log;

/**
 * Created by obo on 2017/9/3.
 * Email:obo1993@gmail.com
 */

public class OLog {
    public static void i(String tag, String message) {
        Log.i(tag, message);
    }

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }
}
