package api.pot.system;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class Permission {
    private static final int DONT_REQUEST = -1;

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
}
