package api.pot.system.sms;

import java.util.List;

public interface SmsListener {
    void onDiscussionFound(Discussion discussion);
    void onReceivedLink(SmsLink link);
    void onReceivedSms(Message message);
    void onMsgListReady(List<Message> messages);
}
