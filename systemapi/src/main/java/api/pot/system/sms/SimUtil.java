package api.pot.system.sms;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class SimUtil {

    public static boolean sendSMS(Context ctx, int simID, String address, String body) {
        return sendSMS(ctx, simID, address, null, body, null, null);
    }

    public static boolean sendSMS(Context ctx, int simID, String toNum, String centerNum, String smsText, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        String name;

        try {

            Method method = Class.forName("android.telephony.SubscriptionManager").getDeclaredMethod("getSubId", int.class);
            method.setAccessible(true);
            int[] param = (int[]) method.invoke(null, new Integer(simID));
            int inst =  param[0];
            SmsManager smsMan = SmsManager.getSmsManagerForSubscriptionId(inst);
            smsMan.sendTextMessage(toNum, null, smsText, null, null);


            /*if (simID == 0) {
                name = "isms";
                // for model : "Philips T939" name = "isms0"
            } else if (simID == 1) {
                name = "isms2";
            } else {
                throw new Exception("can not get service which for sim '" + simID + "', only 0,1 accepted as values");
            }
            Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            method.setAccessible(true);
            Object param = method.invoke(null, name);

            method = Class.forName("com.android.internal.telephony.ISms$Stub").getDeclaredMethod("asInterface", IBinder.class);
            method.setAccessible(true);
            Object stubObj = method.invoke(null, param);
            if (Build.VERSION.SDK_INT < 18) {
                method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                method.invoke(stubObj, toNum, centerNum, smsText, sentIntent, deliveryIntent);
            } else {
                method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                method.invoke(stubObj, ctx.getPackageName(), toNum, centerNum, smsText, sentIntent, deliveryIntent);
            }*/

            return true;
        } catch (ClassNotFoundException e) {
            Log.e("apipas", "ClassNotFoundException:" + e.getMessage());
            ////Toast.makeText(ctx, "ClassNotFoundException:" + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (NoSuchMethodException e) {
            Log.e("apipas", "NoSuchMethodException:" + e.getMessage());
            ///Toast.makeText(ctx, "NoSuchMethodException:" + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (InvocationTargetException e) {
            Log.e("apipas", "InvocationTargetException:" + e.getMessage());
            ////Toast.makeText(ctx, "InvocationTargetException:" + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IllegalAccessException e) {
            Log.e("apipas", "IllegalAccessException:" + e.getMessage());
            ///Toast.makeText(ctx, "IllegalAccessException:" + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("apipas", "Exception:" + e.getMessage());
            ///Toast.makeText(ctx, "Exception:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }


    public static boolean sendMultipartTextSMS(Context ctx, int simID, String toNum, String centerNum, ArrayList<String> smsTextlist, ArrayList<PendingIntent> sentIntentList, ArrayList<PendingIntent> deliveryIntentList) {
        String name;
        try {
            if (simID == 0) {
                name = "isms";
                // for model : "Philips T939" name = "isms0"
            } else if (simID == 1) {
                name = "isms2";
            } else {
                throw new Exception("can not get service which for sim '" + simID + "', only 0,1 accepted as values");
            }
            Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            method.setAccessible(true);
            Object param = method.invoke(null, name);

            method = Class.forName("com.android.internal.telephony.ISms$Stub").getDeclaredMethod("asInterface", IBinder.class);
            method.setAccessible(true);
            Object stubObj = method.invoke(null, param);
            if (Build.VERSION.SDK_INT < 18) {
                method = stubObj.getClass().getMethod("sendMultipartText", String.class, String.class, List.class, List.class, List.class);
                method.invoke(stubObj, toNum, centerNum, smsTextlist, sentIntentList, deliveryIntentList);
            } else {
                method = stubObj.getClass().getMethod("sendMultipartText", String.class, String.class, String.class, List.class, List.class, List.class);
                method.invoke(stubObj, ctx.getPackageName(), toNum, centerNum, smsTextlist, sentIntentList, deliveryIntentList);
            }
            return true;
        } catch (ClassNotFoundException e) {
            Log.e("apipas", "ClassNotFoundException:" + e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e("apipas", "NoSuchMethodException:" + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e("apipas", "InvocationTargetException:" + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("apipas", "IllegalAccessException:" + e.getMessage());
        } catch (Exception e) {
            Log.e("apipas", "Exception:" + e.getMessage());
        }
        return false;
    }


}
