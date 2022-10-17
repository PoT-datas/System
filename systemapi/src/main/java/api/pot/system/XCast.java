package api.pot.system;

import android.graphics.Rect;
import android.graphics.RectF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class XCast {
    public static String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String dateTime = dateFormat.format(date);
            return dateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dateFromString(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = format.parse(date);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date dateFromTimestamp(long time){
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(tz);
        String localTime = sdf.format(new Date(time * 1000));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long dateToTimestamp(Date date){
        return date.getTime();
    }

    public static Rect rectFToRect(RectF rectF){
        return new Rect((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
    }

    public static Object getRightType(String data) {
        try {
            return Integer.valueOf(data);
        }catch (Exception e){
            try {
                return Float.valueOf(data);
            }catch (Exception e1){
                return data;
            }
        }
    }

    public static float meterFromMile(float miles) {
        return miles*1609.34f;
    }
}