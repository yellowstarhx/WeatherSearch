package com.example.weatherapp.appDataHandler;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHandler {

    public static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    public static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 2;

    public static boolean checkLocation(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i("ACCESS FINE LOCATION", "Permission  NOT GRANTED");
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        } else {
            // Permission has already been granted
            Log.i("ACCESS FINE LOCATION", "Permission GRANTED");
            return true;
        }
    }

    public static boolean checkNetworkLocation(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i("ACCESS FINE LOCATION", "Permission  NOT GRANTED");
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        } else {
            // Permission has already been granted
            Log.i("ACCESS COARSE LOCATION", "Permission  GRANTED");
            return true;
        }
    }
//    In Activity
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case PermissionHandler.MY_PERMISSIONS_REQUEST_FINE_LOCATION:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted, yay! Do the
//                    // contacts-related task you need to do.
//                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//                    LocationHandler.usingGPS(currentLoc, locationManager);
//                } else {
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Log.e("ERROR", "Permission FINE_LOCATION Denied");
//                }
//                return;
//
//            // other 'case' lines to check for other
//            // permissions this app might request.
//            case PermissionHandler.MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission was granted
//                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//                    LocationHandler.usingNetwork(currentLoc, locationManager);
//                } else {
//                    Log.e("ERROR", "Permission COARSE_LOCATION Denied");
//                }
//                return;
//        }
//    }

}
