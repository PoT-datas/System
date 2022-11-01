package api.pot.system.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.pot.system.XCast;

public class Discussion {
    private String key;
    private String userA, userB;
    private List<Message> messages = new ArrayList<>();;
    private String date;
    private String lastSms;

    public Discussion(String key) {
        this.key = key;
        messages = new ArrayList<>();
    }

    public Discussion(Message message) {
        notify(message);
        messages = new ArrayList<>();
    }

    public Discussion addMsg(Message message){
        messages.add(message);
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void notify(Message message) {
        this.key = message.getKey();
        this.date = message.getDate();
        this.lastSms = message.getBody();
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

    public String getLastSms() {
        return lastSms;
    }

    public boolean contains(Message message) {
        return getKey().equals(message.getKey());
    }

    public List<Message> getMessages() {
        return messages;
    }
}
