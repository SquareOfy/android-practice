package com.scsa.andr.memo4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.scsa.andr.memo4.db.DBHelper;


public class SMSReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSReceiver_SCSA";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive 호출됨 ::" + Thread.currentThread().getName());

        Bundle bundle = intent.getExtras();
        Object[] messages = (Object[]) bundle.get("pdus");
        SmsMessage[] smsMessages = new SmsMessage[messages.length];
        StringBuilder fullMessage = new StringBuilder();
        String sender = null;

        for (int i = 0; i < messages.length; i++) {
            smsMessages[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
            if (sender == null) {
                sender = smsMessages[i].getDisplayOriginatingAddress();
            }
            fullMessage.append(smsMessages[i].getDisplayMessageBody());
        }

        String body = fullMessage.toString();


        Log.d(TAG, "발신자: " + sender + ", 메시지 내용: " + body);
        saveMemoFromSMS(context, sender, body);
    }



    private void saveMemoFromSMS(Context context, String sender, String body){
        DBHelper dbHelper = new DBHelper(context, "mymemo.db", null, 1);
        String title = "[SMS]"+body;
        String memoContent = "[발신자] "+sender +"\n [내용]\n"+body;
        dbHelper.insert(new MemoDto(title, memoContent));
        Log.d(TAG, "SMS 메모로 저장 완료 "+ body);

        Intent updateIntent = new Intent("com.scsa.andr.memo4.UPDATE_MEMOS");
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);
        dbHelper.close();
    }
}
