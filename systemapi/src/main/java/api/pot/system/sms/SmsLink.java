package api.pot.system.sms;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SmsLink {
    public Message message;
    public String root;
    public String target;
    public List<SLParam> params;

    public SmsLink(Message message, Context context) {
        this.message = message;
        String msg = message.getBody();
        root = msg.substring(XSms.prefixLn(), msg.indexOf("/", XSms.prefixLn()));
        target = msg.substring(msg.indexOf("/", 10/*XSms.prefixLn()*/),
                msg.indexOf("?")!=-1?msg.indexOf("?"):msg.length());
        params = new ArrayList<>();
        String p = msg.substring(msg.indexOf("?")+1);
        if(msg.indexOf("?")!=-1 && p.indexOf("=")!=-1){
            try {
                while (true){
                    if(p.indexOf("=")!=-1){
                        params.add(new SLParam(p.substring(0, p.indexOf("=")), p.substring(p.indexOf("=")+1,
                                p.indexOf("&")!=-1?p.indexOf("&"):p.length())));
                        if(0<=p.indexOf("&") && p.indexOf("&")<p.length()) p = p.substring(p.indexOf("&")+1);
                        else break;
                    }else break;
                }
            }catch (Exception e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public String toString() {
        String ret = "";
        ret+="root: "+root+"\n";
        ret+="target: "+target+"\n";
        for (SLParam param : params)
            ret+=param.label+" => "+param.value+"\n";
        return ret;
    }

    public boolean checkForAPI(String target) {
        String t1 = this.target, t2 = target;
        t1 = (!this.target.startsWith("/")?"/":"") + t1;
        t1 = t1 + (!this.target.endsWith("/")?"/":"");
        t2 = (!target.startsWith("/")?"/":"") + t2;
        t2 = t2 + (!target.endsWith("/")?"/":"");
        return t1.equals(t2);
    }

    public boolean checkParamsAvailable(String... labels) {
        boolean checker;
        if (labels!=null){
            for (String label : labels){
                label = label.toLowerCase();
                checker = false;
                for(SLParam param : params){
                    if(label.equals(param.label)){
                        checker = true;
                        break;
                    }
                }
                if(!checker) return false;
            }
        }
        return true;
    }

    public String getParam(String label) {
        label = label.toLowerCase();
        for(SLParam param : params){
            if(label.equals(param.label)){
                return param.value;
            }
        }
        return null;
    }
}
