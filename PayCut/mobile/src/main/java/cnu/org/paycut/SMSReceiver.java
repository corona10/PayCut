package cnu.org.paycut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.List;

import io.realm.Realm;

public class SMSReceiver extends BroadcastReceiver {

  public SMSReceiver() {
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
      StringBuilder sms = new StringBuilder();
      Bundle bundle = intent.getExtras();
      Realm realm = Realm.getInstance(context);
      if (bundle != null) {
        Object[] pdusObj = (Object[]) bundle.get("pdus");

        SmsMessage[] messages = new SmsMessage[pdusObj.length];
        for (int i = 0; i < pdusObj.length; i++) {
          messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
        }

        for (SmsMessage smsMessage : messages) {
          sms.append(smsMessage.getMessageBody());
          String phonenumber = smsMessage.getOriginatingAddress();
          String sms_contents = smsMessage.getMessageBody();

          Log.d("sms phonenumber1:", phonenumber);
          Log.d("sms contents1: ", sms_contents);
          SmsLog smsLog = SmsParser.parse(smsMessage);
          if(smsLog != null) {
            realm.beginTransaction();
            SmsLog realmlog = realm.copyToRealm(smsLog);
            realm.commitTransaction();
            List<SmsLog> logList = realm.where(SmsLog.class).findAll();
            Log.d("realm size", String.valueOf(logList.size()));

            int total_price = 0;
            for(int idx = 0; idx < logList.size(); idx++)
            {
              total_price += logList.get(idx).getPrice();
            }
            Log.d("total price",String.valueOf(total_price));
          }

        }
      }
    }
  }
}
