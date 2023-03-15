package api.pot.system;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

public class XDisplay {
    private static XDisplay xDisplay;
    private static Context context;

    public XDisplay(Context context) {
        this.context = context;
    }

    public static XDisplay with(Context context){
        if(xDisplay==null) xDisplay = new XDisplay(context);
        return xDisplay;
    }

    public static XDisplay keepOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return xDisplay;
    }
}
