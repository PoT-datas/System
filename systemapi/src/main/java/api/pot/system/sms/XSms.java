package api.pot.system.sms;

import android.app.PendingIntent;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import api.pot.system.XApp;

public class XSms {
    private HashMap<String, Discussion> discussions = new HashMap<>();
    private Discussion discussion;
    
    private static XSms sms;
    private Context context;
    public SmsListener smsListener;

    public XSms(Context context) {
        this.context = context;
    }

    public static XSms with(Context context) {
        if(sms==null) sms = new XSms(context);
        return sms;
    }

    public static String sent(){
        return "smslink://";
    }

    public static String prefix(){
        return "smslink://";
    }

    public static int prefixLn(){
        return prefix().length();
    }

    public static boolean linkValide(String msg) {
        if(msg==null || msg.length()<18) {
            return false;
        }
        msg = msg.toLowerCase();
        if(!msg.endsWith("/")) msg+="/";
        if(msg.indexOf(' ')!=-1) {
            return false;
        }
        if(!msg.startsWith("smslink://")) {
            return false;
        }
        if(!phoneValide(msg.substring(10, msg.indexOf('/', 10)))) {
            return false;
        }
        return true;
    }

    public static boolean phoneValide(String phone) {
        if(phone==null || phone.length()<8) return false;
        return true;
    }

    public XSms listener(SmsListener smsListener) {
        this.smsListener = smsListener;
        return this;
    }

    private int count = 0;

    public static XSms instance(Context context) {
        if(sms==null) sms = new XSms(context);
        return sms;
    }

    public void getDiscutions(int count, SmsListener smsListener){
        //this.smsListener = smsListener;
        this.count = count;

        TaskDiscution task = new TaskDiscution(smsListener);
        task.execute("");
    }

    private void setDisc(Message message, SmsListener listener) {
        if(discussions==null) discussions = new HashMap<>();

        Discussion discussion = discussions.get(message.getKey());
        if(discussion!=null) discussion.notify(message);
        else {
            discussions.put(message.getKey(), new Discussion(message));
            if(listener!=null) listener.onDiscussionFound(new Discussion(message));
        }

    }

    public XSms into(Discussion discussion) {
        this.discussion = discussion;
        return this;
    }

    public void getMessages(int count, SmsListener smsListener) {
        if(discussion==null) return;
        //this.smsListener = smsListener;
        this.count = count;

        TaskMessage task = new TaskMessage(smsListener);
        task.execute("");
    }


    public boolean sendSms(final int simID, final Message message) {
        XApp.runThread(new Runnable() {
            @Override
            public void run() {
                SimUtil.sendSMS(context, simID, message.getAddress(), message.getBody());
            }
        });
        return true;
    }

    public class TaskMessage extends AsyncTask<String, Void, String > {
        private SmsListener listener;

        public TaskMessage(SmsListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... strings) {
            Message message;
            Uri uriSMSURI = Uri.parse("content://sms/");
            Cursor c = context.getContentResolver().query(uriSMSURI, null, null, null, null);

            while (c != null && c.moveToNext()) {
                message = new Message();
                message.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                if(discussion.contains(message)){
                    message.setDisplayName(c.getString(c.getColumnIndexOrThrow("_id")));
                    message.setBody(c.getString(c.getColumnIndexOrThrow("body")));
                    message.setThreadId(c.getString(c.getColumnIndex("read")));
                    message.setDate(c.getString(c.getColumnIndexOrThrow("date")));
                    if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                        message.setType("inbox");
                    } else {
                        message.setType("sent");
                    }
                    discussion.addMsg(message);
                }
                if(discussion.getMessages().size()>=count) return null;
            }

            if (c != null) {
                c.close();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String lists) {
            List<Message> messages = new ArrayList<>();
            messages.addAll(discussion.getMessages());
            discussion.getMessages().clear();
            if(listener!=null) listener.onMsgListReady(messages);
        }
    }


    public class TaskDiscution extends AsyncTask<String, Void, String > {
        private SmsListener listener;

        public TaskDiscution(SmsListener listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... strings) {
            Message message;
            Uri uriSMSURI = Uri.parse("content://sms/");
            Cursor c = context.getContentResolver().query(uriSMSURI, null, null, null, null);

            //Toast.makeText(context, ""+c.getCount(), Toast.LENGTH_SHORT).show();

            while (c != null && c.moveToNext()) {
                message = new Message();
                message.setDisplayName(c.getString(c.getColumnIndexOrThrow("_id")));
                message.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                message.setBody(c.getString(c.getColumnIndexOrThrow("body")));
                message.setThreadId(c.getString(c.getColumnIndex("read")));
                message.setDate(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    message.setType("inbox");
                } else {
                    message.setType("sent");
                }

                setDisc(message, listener);

                if(discussions.size()>=count) return null;
            }

            if (c != null) {
                c.close();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String lists) {
            //if(smsListener!=null) smsListener.on
            discussions.clear();
        }
    }





}
