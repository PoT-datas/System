package api.pot.system.provider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import api.pot.system.XPermission;

import static android.location.GpsStatus.GPS_EVENT_STARTED;
import static android.location.GpsStatus.GPS_EVENT_STOPPED;

public class XProvider {
    private static XProvider xProvider;

    private static Activity activity;
    private static ProviderListener providerListener;

    public XProvider(Activity activity) {
        this.activity = activity;
    }

    public static XProvider with(Activity activity){
        if(xProvider==null) xProvider = new XProvider(activity);
        return xProvider;
    }

    public XProvider listener(final ProviderListener providerListener){
        this.providerListener = providerListener;
        ///
        if(activity==null) return xProvider;
        ///
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return xProvider;
        }
        LocationManager lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        lm.addGpsStatusListener(new android.location.GpsStatus.Listener() {
            public void onGpsStatusChanged(int event) {
                switch (event) {
                    case GPS_EVENT_STARTED:
                        if(providerListener!=null) providerListener.onStateChange(true);
                        break;
                    case GPS_EVENT_STOPPED:
                        if(providerListener!=null) providerListener.onStateChange(false);
                        break;
                }
            }
        });
        ///
        return xProvider;
    }

    public static boolean isGPSProviderEnabled(){
        return isProviderEnabled( LocationManager.GPS_PROVIDER ) ;
    }

    public static boolean isProviderEnabled(String provider){
        if(activity==null) return false;
        ///
        final LocationManager manager = (LocationManager) activity.getSystemService( Context.LOCATION_SERVICE );
        return manager.isProviderEnabled( provider ) ;
    }

    public static void viewGPSProviderSettings() {
        viewProviderSettings("gps");
    }

    public static void viewProviderSettings(String s) {
        if(activity==null) return;
        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}
