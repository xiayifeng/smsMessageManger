package com.xyf.SmsMessageManager;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import com.xyf.SmsMessageManager.utils.LogUtils;
import com.xyf.SmsMessageManager.sms.SMS;
import com.xyf.SmsMessageManager.sms.SmsHandler;
import com.xyf.SmsMessageManager.sms.SmsObserver;

/**
 * Created by shxiayf on 2015/7/22.
 */
public class SmsService extends Service {

    private static final String Tag = SmsService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ContentObserver mObserver;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.w(Tag,"sms service is running ...");
        ContentResolver resolver = getContentResolver();

        Handler handler = new SmsHandler(this);

        mObserver = new SmsObserver(resolver, handler);

        resolver.registerContentObserver(SMS.SMS_URI, true, mObserver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        this.getContentResolver().unregisterContentObserver(mObserver);

        super.onDestroy();

        Intent intent = new Intent(this,SmsService.class);
        startService(intent);
    }
}
