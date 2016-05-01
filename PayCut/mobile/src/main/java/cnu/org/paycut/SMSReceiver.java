package cnu.org.paycut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

    public SMSReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
       if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
       {
           StringBuilder sms = new StringBuilder();
           Bundle bundle = intent.getExtras();
           if (bundle != null) {
               Object[] pdusObj = (Object[])bundle.get("pdus");

               SmsMessage[] messages = new SmsMessage[pdusObj.length];
               for (int i = 0; i < pdusObj.length; i++) {
                   messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
               }

               for (SmsMessage smsMessage : messages) {
                   sms.append(smsMessage.getMessageBody());
                   String phonenumber = smsMessage.getOriginatingAddress();
                   String sms_contents = smsMessage.getMessageBody();
                   Log.d("sms phonenumber", phonenumber);
                   Log.d("sms contents", sms_contents);
               }
           }
       }
    }
}
