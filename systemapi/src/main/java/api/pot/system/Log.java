package api.pot.system;

import android.content.Context;
import android.widget.Toast;

public class Log {
    public static void d(Context context, String msg){
        try {
            ///---Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void s(Context context, String msg){
        try {
            ////---Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void x(Context context, String msg){
        try {
            ///Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void a(Context context, String msg){
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void r(Context context, String msg){
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }
}
