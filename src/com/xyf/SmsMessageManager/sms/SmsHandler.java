package com.xyf.SmsMessageManager.sms;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.text.TextUtils;
import com.xyf.SmsMessageManager.R;
import com.xyf.SmsMessageManager.model.MessageItem;
import com.xyf.SmsMessageManager.utils.LogUtils;


/**
 * Created by shxiayf on 2015/7/22.
 */
public class SmsHandler extends Handler {

    private static final String Tag = SmsHandler.class.getSimpleName();

    private Context mContext;
    public SmsHandler(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        MessageItem item = (MessageItem) msg.obj;

        if (item == null) {
            return;
        }

        LogUtils.w(Tag,"------------------------------------------");

        LogUtils.w(Tag, String.format("id(%d)threaid(%d)phone(%s)body(%s)", item.getId(), item.getThreadid(),item.getPhone(), item.getBody()));

        LogUtils.w(Tag, Build.VERSION.SDK_INT + "");
        if (Build.VERSION.SDK_INT >= 19){

        }else {
            ContentValues values = new ContentValues();
            values.put("read", "1");

            int result = mContext.getContentResolver().update(
                    Uri.parse("content://sms/inbox"), values, " _id=?",
                    new String[] { "" + item.getId() });

            LogUtils.w(Tag,String.format("deleteResult(%d)",result));
        }

//        String speedbody = mContext.getString(R.string.receivernumber)  + item.getPhone() + mContext.getString(R.string.recevermessage) + (TextUtils.isEmpty(item.getBody())?mContext.getString(R.string.empty):item.getBody());


//        int result = mContext.getContentResolver().update(SMS.CONTENT_URI,values,SMS._ID +"= ? and "+SMS.TYPE + "= ?",new String[]{String.valueOf(item.getId()),String.valueOf(SMS.MESSAGE_TYPE_INBOX)});
//        Uri uri = ContentUris.withAppendedId(SMS.CONTENT_URI,item.getId());
//        LogUtils.w(Tag,uri.toString());

//        int result = mContext.getContentResolver().update(uri,values,null,null);
//        LogUtils.w(Tag,String.format("updateResult(%d)",result));
//        int result = mContext.getContentResolver().delete(uri,null,null);
    }
}



