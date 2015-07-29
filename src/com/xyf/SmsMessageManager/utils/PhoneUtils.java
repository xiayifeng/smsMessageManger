package com.xyf.SmsMessageManager.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by shxiayf on 2015/7/29.
 */
public class PhoneUtils {

    public static boolean isNetWorkAvailble(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] infos = cm.getAllNetworkInfo();
        for (NetworkInfo tmp : infos) {
            if (tmp.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasPermission(Context mContext,String permission) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo pInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] permissions = pInfo.requestedPermissions;
            for (String tmp : permissions) {
                if (permission.equals(tmp)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean canReadSms(Context mContext) {
        return hasPermission(mContext, Manifest.permission.READ_SMS);
    }

    public static boolean canWriteSms(Context mContext) {
        return hasPermission(mContext,Manifest.permission.WRITE_SMS);
    }

    public  static boolean canAccessNetWork(Context mContext) {
        return hasPermission(mContext,Manifest.permission.ACCESS_NETWORK_STATE);
    }

}
