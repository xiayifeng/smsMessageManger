package com.xyf.SmsMessageManager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * Created by shxiayf on 2015/7/22.
 */
public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isServiceRunning(context,SmsService.class.getName())) {
            Intent serviceIntent = new Intent(context,SmsService.class);
            context.startService(serviceIntent);
        }
    }

    public boolean isServiceRunning(Context mContext,String servciename) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> servicelist = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (servicelist.size() > 0) {
            return false;
        }
        for (int i=0; i<servicelist.size(); i++) {
            if (servicelist.get(i).service.getClassName().equals(servciename) == true) {
                isRunning = true;
                break;
            }
        }

        return isRunning;
    }

}
