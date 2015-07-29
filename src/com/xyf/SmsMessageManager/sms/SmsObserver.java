package com.xyf.SmsMessageManager.sms;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.xyf.SmsMessageManager.model.MessageItem;
import com.xyf.SmsMessageManager.utils.LogUtils;


/**
 * Created by shxiayf on 2015/7/22.
 */
public class SmsObserver extends ContentObserver {

    private static final String Tag = SmsObserver.class.getSimpleName();

    private static final String[] PROJECTION = new String[]

            {

                    SMS._ID,//0

                    SMS.TYPE,//1

                    SMS.ADDRESS,//2

                    SMS.BODY,//3

                    SMS.DATE,//4

                    SMS.THREAD_ID,//5

                    SMS.READ,//6

                    SMS.PROTOCOL//7

            };

    private static final String SELECTION =

//            SMS._ID + " > %s" +

//      " and " + SMS.PROTOCOL + " = null" +

//      " or " + SMS.PROTOCOL + " = " + SMS.PROTOCOL_SMS + ")" +

                    "(" + SMS.TYPE + " = " + SMS.MESSAGE_TYPE_INBOX + ")" +
                    "and " +SMS.READ + " = 0";

//                    " or " + SMS.TYPE + " = " + SMS.MESSAGE_TYPE_SENT + ")";


    private static final int COLUMN_INDEX_ID = 0;

    private static final int COLUMN_INDEX_TYPE = 1;

    private static final int COLUMN_INDEX_PHONE = 2;

    private static final int COLUMN_INDEX_BODY = 3;

    private static final int COLUMN_INDEX_PROTOCOL = 7;

    private static final int COLUMN_INDEX_THREAD_ID = 5;

    private ContentResolver mResolver;
    private Handler mHandler;

    public SmsObserver(ContentResolver resolver, Handler handler) {
        super(handler);
        this.mResolver = resolver;
        this.mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        LogUtils.w(Tag,"on observer change");
        super.onChange(selfChange);

        Cursor cursor = mResolver.query(SMS.SMS_URI, PROJECTION,
                SELECTION, null, SMS.DATE + " desc");

        long thread_id;
        int id, type, protocol;

        String phone, body;

        Message message;

        MessageItem item = null;

        if (cursor.moveToFirst()) {
            id = cursor.getInt(COLUMN_INDEX_ID);

            type = cursor.getInt(COLUMN_INDEX_TYPE);

            phone = cursor.getString(COLUMN_INDEX_PHONE);

            body = cursor.getString(COLUMN_INDEX_BODY);

            protocol = cursor.getInt(COLUMN_INDEX_PROTOCOL);

            thread_id =  cursor.getLong(COLUMN_INDEX_THREAD_ID);

            String log = String.format("id(%d)type(%d)phone(%s)body(%s)protocol(%d)thread_id(%d)",id,type,phone,body,protocol,thread_id);
            LogUtils.w(Tag,log);

//            if (protocol == SMS.PROTOCOL_SMS && body != null && body.startsWith(SMS.FILTER))
            if (protocol == SMS.PROTOCOL_SMS)
            {
                item = new MessageItem();

                item.setId(id);

                item.setThreadid(thread_id);

                item.setType(type);

                item.setPhone(phone);

                item.setBody(body);

                item.setProtocol(protocol);


                message = new Message();

                message.obj = item;

                mHandler.sendMessage(message);
            }
        }

//        while (cursor.moveToNext())
//        {
//
//            id = cursor.getInt(COLUMN_INDEX_ID);
//
//            type = cursor.getInt(COLUMN_INDEX_TYPE);
//
//            phone = cursor.getString(COLUMN_INDEX_PHONE);
//
//            body = cursor.getString(COLUMN_INDEX_BODY);
//
//            protocol = cursor.getInt(COLUMN_INDEX_PROTOCOL);
//
//            thread_id =  cursor.getLong(COLUMN_INDEX_THREAD_ID);
//
//            String log = String.format("id(%d)type(%d)phone(%s)body(%s)protocol(%d)thread_id(%d)",id,type,phone,body,protocol,thread_id);
//            LogUtils.w(Tag,log);
//
////            if (protocol == SMS.PROTOCOL_SMS && body != null && body.startsWith(SMS.FILTER))
//            if (protocol == SMS.PROTOCOL_SMS && body != null)
//            {
//                    item = new MessageItem();
//
//                    item.setId(id);
//
//                    item.setThreadid(thread_id);
//
//                    item.setType(type);
//
//                    item.setPhone(phone);
//
//                    item.setBody(body);
//
//                    item.setProtocol(protocol);
//
//            }
//        }

        cursor.close();

    }
}
