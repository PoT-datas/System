package api.pot.system.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.telephony.SmsManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String address = null;
    private String displayName = null;
    private String threadId = null;
    private String date = null;
    private String body = null;
    private String type = null;

    public Message(boolean inbox) {
        if(inbox) type = "inbox";
        else type = "sent";
    }

    public Message() {}

    public Message(String address, String body) {
        this.address = address;
        this.body = body;
    }

    public Message(String link) {
        if (!XSms.linkValide(link)) return;
        this.address = link.substring(10, link.indexOf('/', 10));
        if (!XSms.phoneValide(address)) {
            address = null;
            return;
        }
        this.body = link;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getDate() {
        try {
            Date d = new Date(Long.valueOf(date));
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd \uD83D\uDD66 HH:mm").format(d);
            return formattedDate;
        }catch (Exception e){
            return date;
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return address;
    }

    public boolean isInbox() {
        return type!=null && type.equals("inbox");
    }

    @Override
    public String toString() {
        return "Address : "+address+"\n"+
                "body : "+body;
    }

    public void sendSms() {
        String scAddress = null;

        PendingIntent sentIntent = null, deliveryIntent = null;

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage
                (address, scAddress, body,
                        sentIntent, deliveryIntent);
    }

    public boolean sendSms(Context context) {
        return SimUtil.sendSMS(context,0,address,null,
                body,null,null);
    }
}
