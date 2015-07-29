package com.xyf.SmsMessageManager.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.xyf.SmsMessageManager.ui.MessageActivity;

/**
 * Created by shxiayf on 2015/7/29.
 */
public class NotifcationUtils {

    public static Notification getNotification(Context mContext,int icon,String title,String content) {
        Notification nf = new Notification(icon,title,System.currentTimeMillis());
        nf.flags = Notification.FLAG_AUTO_CANCEL;
        nf.defaults = Notification.DEFAULT_SOUND;

        Intent i = new Intent(mContext, MessageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pi = PendingIntent.getActivity(mContext,100,i,PendingIntent.FLAG_UPDATE_CURRENT);
        nf.setLatestEventInfo(mContext,title,content,pi);

        return nf;
    }

    public static void showNotification(Context mContext,int id,Notification nf) {
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id,nf);
    }

}
