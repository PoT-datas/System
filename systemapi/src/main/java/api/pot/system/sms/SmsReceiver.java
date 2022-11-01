package api.pot.system.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {


    /*private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    Log.e("Message Content : ", " == " + messages[i].getMessageBody());
                    Log.e("Message Content Body : ", " == " + messages[i].getDisplayMessageBody());
                    Log.e("Message recieved From", " == " + messages[0].getOriginatingAddress());
                }
            }
        }
    }*/

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String smsBody = null;
            String address = null;
            Message message = null;
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            if(address!=null) message = new Message(address, smsBody);

            //this will update the UI with message
            XSms inst = XSms.instance(context);
            if(inst.smsListener!=null && message!=null) {
                if(XSms.linkValide(message.getBody())) inst.smsListener.onReceivedLink(new SmsLink(message, context));
                else if(message.getBody().length()==2) {
                    message.setBody("smslink://"+message.getAddress()+"/api/data?id="+message.getBody());
                    inst.smsListener.onReceivedLink(new SmsLink(message, context));
                }
                else inst.smsListener.onReceivedSms(message);
            }
        }
    }


}