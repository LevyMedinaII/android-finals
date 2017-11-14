package edu.ateneo.cie199.bucketdish;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Levy on 11/13/2017.
 */

@SuppressLint("MissingPermission")
public class BucketDishApp extends Application implements LocationListener {
    private String zomatoToken = "81c4d728678c315f02168a91d762f025";
    private String[] permissions = {"ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION"};
    private JSONArray restaurants;
    private LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    private Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    private double longitude = location.getLongitude();
    private double latitude = location.getLatitude();
//    private final LocationListener locationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//        }
//
//        @Override
//        public void onStatusChanged(String s, int i, Bundle bundle) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(String s) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(String s) {
//
//        }
//    };
//
//
//    public JSONObject geolocate() throws JSONException {
//        JSONObject result = new JSONObject();
//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        }
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//
//        longitude = location.getLongitude();
//        latitude = location.getLatitude();
//
//        result.put("latitude", Double.toString(latitude));
//        result.put("longitude", Double.toString(longitude));
//
//        System.out.println("Latitude" + result.get("latitude"));
//
//        return result;
//    }

    public double getLongitude() {
        System.out.println("Longitude "+ longitude);
        return longitude;
    }

    public double getLatitude() {
        System.out.println("Latitude "+ latitude);
        return latitude;
    }

    public void setAppRestaurantsNearMe() throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.zomato.com/geocode";
        JSONObject jsonRequest = new JSONObject();

        jsonRequest.put("user-key", zomatoToken);
        jsonRequest.put("lat", latitude);
        jsonRequest.put("lon", longitude);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* Things u wanna do */
                        try {
                            restaurants = response.getJSONArray("nearby_restaurants");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    // sets this classes location
    @Override
    public void onLocationChanged(Location loc) {
        location = loc;
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
