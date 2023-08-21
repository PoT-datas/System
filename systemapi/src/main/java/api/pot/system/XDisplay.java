package api.pot.system;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
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

    public static float[] getSize(Context context) {
        return getSize((Activity) context);
    }

    public static float[] getSize(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        float[] size = {displayMetrics.widthPixels, displayMetrics.heightPixels+48*displayMetrics.density};
        return size;
    }

    public static float getDensity(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static void setOrientationPortrait(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static void setOrientationLandscape(Activity activity){
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
