package api.pot.system;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Spinner;

import java.util.Locale;

public class Xpeaker {

    private static Xpeaker xpeaker;

    private static Context context;
    public static Xpeaker with(Context context){
        if(xpeaker==null){
            xpeaker = new Xpeaker();
            xpeaker.context = context;
            xpeaker.initSpeaker(context);
        }
        return xpeaker;
    }

    private  static TextToSpeech tts;
    public void initSpeaker(final Context context) {
        tts=new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    if(!setLanguage(Locale.FRENCH)){
                        if(!setLanguage(Locale.CANADA_FRENCH)){
                            if(!setLanguage(Locale.FRANCE)) {
                                if(!setLanguage(Locale.ENGLISH));
                            }
                        }
                    }
                    ///convertTextToSpeech("Bienvenu dans Travel GPS!");
                }else Log.g(context, "Initilization Failed!");
            }
        });
    }

    private boolean setLanguage(Locale lang) {
        int result = tts.setLanguage(lang);
        return !(result==TextToSpeech.LANG_MISSING_DATA ||
                result==TextToSpeech.LANG_NOT_SUPPORTED);
    }

    public void convertTextToSpeech(String text) {
        try {
            if(text==null||"".equals(text))
            {
                text = "Content not available";
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }else
                tts.speak(text, TextToSpeech.QUEUE_ADD, null);
        }catch (Exception e){}
    }
}
