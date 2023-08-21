package api.pot.system;

import android.content.Context;
import android.widget.Toast;

public class Log {

    public static void i(String tag, String msg){
        try {
            android.util.Log.i(tag, msg);
        }catch (Exception e){ }
    }

    public static void r(Context context, String msg){
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }



    public static void a(Context context, String msg){
        try {
            ///Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void b(Context context, String msg){
        try {
            ////Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }catch (Exception e){ }
    }

    public static void c(Context context, String msg){
        try {
            ////---Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }catch (Exception e){ }
    }

    public static void d(Context context, String msg){
        try {
            ///---Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void e(Context context, String msg){
        try {
            ///Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void f(Context context, String msg){
        try {
            ///---Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void g(Context context, String msg){
        try {
            ///Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void h(Context context, String msg){
        try {
            ///Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void i(Context context, String msg){
        try {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void s(Context context, String msg){
        try {
            ////---Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }

    public static void x(Context context, String msg){
        try {
            ////---Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }catch (Exception e){ }
    }
}
