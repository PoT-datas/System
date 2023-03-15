package api.pot.system;

import android.content.Context;
import android.content.SharedPreferences;

public class XPreferences {

    private Context context;
    private String keyApp;

    private static XPreferences xPreferences;

    public XPreferences(Context context, String keyApp) {
        this.context = context;
        this.keyApp = keyApp;
    }

    public static XPreferences with(Context ctx, String kApp){
        if(xPreferences==null) xPreferences = new XPreferences(ctx, kApp);
        return xPreferences;
    }

    public void setPref(String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(keyApp, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getPref(String key){
        return getPref(key, null);
    }

    public String getPref(String key, String defaultVal){
        SharedPreferences pref = context.getSharedPreferences(keyApp, 0);
        String cont = pref.getString(key, defaultVal);
        return cont;
    }
}
