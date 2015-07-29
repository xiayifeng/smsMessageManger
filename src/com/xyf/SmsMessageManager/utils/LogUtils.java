package com.xyf.SmsMessageManager.utils;

import android.util.Log;

/**
 * Created by shxiayf on 2015/7/22.
 */
public class LogUtils {

    private static boolean debug = true;

    public static void w(String tag,String msg){
        if (debug) {
            Log.d(tag,msg);
        }
    }

}
