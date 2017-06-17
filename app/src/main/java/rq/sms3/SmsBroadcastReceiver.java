package rq.sms3;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.android.internal.telephony.GsmAlphabet;

import java.text.DateFormat;
import java.util.Date;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    DB_Controller db;
    public static final String SMS_BUNDLE = "pdus";
    byte[] b;
    String dataa="";
    GsmAlphabet nwet;
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        //db=new DB_Controller(this);
        SmsMessage[] smsMessage;
        if (intentExtras != null)
        {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            smsMessage=new SmsMessage[sms !=null ? sms.length : 0];
            for (int i = 0; i < smsMessage.length; ++i) {
                //   String format = intentExtras.getString("3gpp");
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) sms[i], "3gpp");
                b=smsMessage[i].getUserData();
                //    String smsBody = smsMessage[i].getMessageBody().toString();
                String address = smsMessage[i].getOriginatingAddress();
                int count3;
                if(b[0]<0)
                {
                    count3=b[0]+256;
                }
                else
                {
                    count3=b[0];
                }
                dataa=nwet.gsm7BitPackedToString(b,2,count3,0,b[1],b[1]);
                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += dataa ;
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                smsMessageStr+="DATE:"+currentDateTimeString;
                Toast.makeText(context,dataa,Toast.LENGTH_SHORT).show();
            }

            MainActivity inst = MainActivity.instance();
            inst.updateInbox(smsMessageStr);
            //if(smsMessageStr!=null)
        }
    }
}