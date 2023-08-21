package api.pot.system.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import api.pot.system.Log;
import api.pot.system.R;
import api.pot.system.XApp;
import api.pot.system.XtatutBar;

import static api.pot.system.permissions.XPermission.isAccessExternalStorage;
import static api.pot.system.permissions.XPermission.isLocationEnabled;

public class PermissionActivity extends AppCompatActivity {

    public static void with(Context context) {
        context.startActivity(new Intent(context, PermissionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        XtatutBar.transparentStatutBar(this);

        requestLoader();
    }

    private void requestLoader() {
        switch (XPermission.requestCode){
            case RequestCode.REQUEST_CODE_CAMERA:
                if(XPermission.isAccessCamera(this)){
                    if(XPermission.listener!=null) XPermission.listener.onPermissionGranted();
                }else {
                    XPermission.isAccessCamera(this, RequestCode.REQUEST_CODE_CAMERA);
                }
                break;
            case RequestCode.REQUEST_CODE_SMS:
                if(XPermission.isAccessSms(this)){
                    if(XPermission.listener!=null) XPermission.listener.onPermissionGranted();
                }else {
                    XPermission.isAccessSms(this, RequestCode.REQUEST_CODE_CAMERA);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case RequestCode.REQUEST_CODE_CAMERA: {
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    if(XPermission.listener!=null) XPermission.listener.onPermissionGranted();
                } else {
                    if(XPermission.listener!=null) XPermission.listener.onPermissionDenied();
                }
            }
            case RequestCode.REQUEST_CODE_SMS: {
                if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                    if(XPermission.listener!=null) XPermission.listener.onPermissionGranted();
                } else {
                    if(XPermission.listener!=null) XPermission.listener.onPermissionDenied();
                }
            }
        }

        finish();
    }
}
