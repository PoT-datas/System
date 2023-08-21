package api.pot.system.permissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import api.pot.system.XApp;

public class XPermission {
    private static final int DONT_REQUEST = -1;

    public static int requestCode = RequestCode.REQUEST_CODE_DONT_REQUEST;
    public static XPermissionListener listener;
    public static void listener(XPermissionListener xPermissionListener){
        listener = xPermissionListener;
    }

    @SuppressLint("NewApi")
    public static boolean isLocationEnabled(Activity activity) {
        return isLocationEnabled(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isLocationEnabled(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity.getBaseContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, code);
        } else {
            return true;
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static boolean isAccessExternalStorage(Activity activity) {
        return isAccessExternalStorage(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessExternalStorage(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(activity.getBaseContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
        } else {
            return true;
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static boolean isAccessPhoneState(Activity activity) {
        return isAccessPhoneState(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessPhoneState(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE}, code);
        } else {
            return true;
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static boolean isAccessAudioRec(Activity activity) {
        return isAccessExternalStorage(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessAudioRec(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.RECORD_AUDIO}, code);
        } else {
            return true;
        }
        return false;
    }

    public static void isAccessCamera(XPermissionListener xPermissionListener) {
        if(XApp.context!=null)
            isAccessCamera(XApp.context, xPermissionListener);
    }

    public static void isAccessCamera(Context context, XPermissionListener xPermissionListener) {
        listener = xPermissionListener;
        requestCode = RequestCode.REQUEST_CODE_CAMERA;
        PermissionActivity.with(context);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessCamera(Activity activity) {
        return isAccessCamera(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessCamera(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.CAMERA}, code);
        } else {
            return true;
        }
        return false;
    }

    /*@SuppressLint("NewApi")
    public static boolean isAccessSms(Activity activity) {
        return isAccessExternalStorage(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessSms(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.READ_SMS}, code);
        } else {
            return true;
        }
        return false;
    }*/

    public static void isAccessSms(XPermissionListener xPermissionListener) {
        if(XApp.context!=null)
            isAccessSms(XApp.context, xPermissionListener);
    }

    public static void isAccessSms(Context context, XPermissionListener xPermissionListener) {
        listener = xPermissionListener;
        requestCode = RequestCode.REQUEST_CODE_SMS;
        PermissionActivity.with(context);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessSms(Activity activity) {
        return isAccessSms(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessSms(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.SEND_SMS}, code);
        } else {
            return true;
        }
        return false;
    }

    @SuppressLint("NewApi")
    public static boolean isAccessContact(Activity activity) {
        return isAccessContact(activity, DONT_REQUEST);
    }

    @SuppressLint("NewApi")
    public static boolean isAccessContact(Activity activity, int code) {
        // My location
        if (ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(activity.getBaseContext(), Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if(code!=DONT_REQUEST) activity.requestPermissions(new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS}, code);
        } else {
            return true;
        }
        return false;
    }
}