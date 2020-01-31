package com.example.weatherapp.appDataHandler;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.core.content.ContextCompat;

public class LocationHandler {
    private double lat;
    private double lon;
    private boolean isSet;

    private static final double DEFAULT_LAT = 0;
    private static final double DEFAULT_LON = 0;

    public LocationHandler() {
        lat = DEFAULT_LAT;
        lon = DEFAULT_LON;
//        isSet = false;
    }

    public LocationHandler(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
//        isSet = true;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

//    public boolean getIsSet() {return  isSet; }

    @Override
    public String toString() {
        return "lat: " + lat + ", lon: " + lon;
    }

    public static final String IP_API = "http://ip-api.com/json";

    public static LocationHandler getCurrentLocation(Activity activity) {
        Context mContext = activity.getApplicationContext();
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // try to use GPS & network
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("ACCESS FINE LOCATION", "Permission GRANTED");
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return new LocationHandler(location.getLatitude(), location.getLongitude());
        } else if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("ACCESS COARSE LOCATION", "Permission  GRANTED");
            Location location =  locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            return new LocationHandler(location.getLatitude(), location.getLongitude());
        }
        // have no access
        return null;
    }

    public static void getCurrentLocation(Activity activity, LocationHandler locHdl) {
        Context mContext = activity.getApplicationContext();
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (PermissionHandler.checkLocation(activity)) {
            usingGPS(locHdl, locationManager);
        } else if (!locHdl.isSet && PermissionHandler.checkNetworkLocation(activity)) {
            usingNetwork(locHdl, locationManager);
        } else if (!locHdl.isSet) {
            setLocationByIP(mContext, locHdl);
        }

    }

    public static void usingGPS(LocationHandler locHdl, LocationManager locationManager) {
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setLocationHandler(locHdl, location);
        } catch (SecurityException e) {
            Log.e("ERROR", "Permission Denied Exception");
        }
    }

    public static void usingNetwork(LocationHandler locHdl, LocationManager locationManager) {
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            setLocationHandler(locHdl, location);
        } catch (SecurityException e) {
            Log.e("ERROR", "Permission Denied Exception");
        }
    }

    private static void setLocationHandler(LocationHandler locHdl, Location location) {
        locHdl.lat = location.getLatitude();
        locHdl.lon = location.getLongitude();
        locHdl.isSet = true;
    }

    public static void setLocationByIP(Context context, final LocationHandler locHdl) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, IP_API, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            double lat = response.getDouble("lat");
                            double lon = response.getDouble("lon");

                            locHdl.lat = lat;
                            locHdl.lon = lon;
                            locHdl.isSet = true;

                        } catch (JSONException e) {
                            Log.e("JSON Exception", "Error get location by IP");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", "Error get location by IP");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
