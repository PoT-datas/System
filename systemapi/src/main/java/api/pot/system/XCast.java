package api.pot.system;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class XCast {

    public static float dt2fpx(Context context, float dp){
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }

    public static int dt2px(Context context, float dp){
        return (int) dt2fpx(context, dp);
    }

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
            date = date.toUpperCase();
            if(date.indexOf('T')==-1||date.indexOf('.')==-1) return null;
            date = date.replaceAll("T", " ");
            date = date.substring(0, date.indexOf('.'));
            return dateFromString(date);
        }
    }

    public static Date dateFromTZDate(String tzDate){///---wrong
        /*Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.ENGLISH);
        Log.e(C.TAG, "formatted string: "+sdf.format(c.getTime()));*/


        ///SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ZZZZZZ'Z'");
        try {
            Date d = format.parse(tzDate);
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

    public static int drawableToColor(Drawable drawable){
        try {
            return (((ColorDrawable)drawable).getColor());
        }catch (Exception e){}
        return Color.BLACK;
    }

    public static double meterFromMile(double miles) {
        return miles*1609.34f;
    }

    public static double meterToMile(double meters) {
        return meters*0.000621d;
    }

    public static boolean isGeoPosition(String data){
        //return data!=null && data.matches("-?[1-9][0-9]*(\\.[0-9]+)?,\\s*-?[1-9][0-9]*(\\.[0-9]+)?\n");
        return data!=null && data.matches("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$");
    }

    public static int getColorBetween(int c1, int c2, float growing) {
        return Color.rgb(Color.red(c1)+(int)(growing*(Color.red(c2)-Color.red(c1))),
                Color.green(c1)+(int)(growing*(Color.green(c2)-Color.green(c1))),
                Color.blue(c1)+(int)(growing*(Color.blue(c2)-Color.blue(c1))));
    }

    public static Date getMostRecentDate(Date d1, Date d2) {
        if(d1==null && d2==null) return null;
        if(d1!=null && d2==null) return d1;
        if(d1==null && d2!=null) return d2;
        return dateToTimestamp(d1)<dateToTimestamp(d2)?d2:d1;
    }

    public static Date dateAddSecond(Date date, int s) {
        return dateAddTime(date, s, Calendar.SECOND);
    }

    public static Date dateAddMinute(Date date, int m) {
        return dateAddTime(date, m, Calendar.MINUTE);
    }

    public static Date dateAddHour(Date date, int h) {
        return dateAddTime(date, h, Calendar.HOUR);
    }

    public static Date dateAddDay(Date date, int d) {
        return dateAddTime(date, d, Calendar.DAY_OF_MONTH);
    }

    public static Date dateAddTime(Date date, int time, int unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, time);
        return calendar.getTime();
    }

    public static double meter2Mile(double meter) {
        return 0.000621d*meter;
    }

    public static double mile2Meter(double mile) {
        return mile/0.000621d;
    }
}