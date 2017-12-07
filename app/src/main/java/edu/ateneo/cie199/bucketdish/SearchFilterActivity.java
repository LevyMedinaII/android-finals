package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SearchFilterActivity extends AppCompatActivity implements View.OnClickListener{

    private String zomatoToken = "81c4d728678c315f02168a91d762f025";
    private Restaurant random_restaurant, random_restaurant2, random_restaurant3;
    private String[] permissions = {"ACCESS_FINE_LOCATION", "ACCESS_COARSE_LOCATION"};
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_FINE_LOCATION=0;
    private Location userLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                // Do something here
                                userLocation = location;

                            }
                        }
                    });
        }

        Button searchButton = (Button) findViewById(R.id.btn_search);
        Button cancelButton = (Button) findViewById(R.id.btn_cancel);
        Button searchThreeButton = (Button) findViewById(R.id.btn_searchThree);


        final Intent resultActivity = new Intent(SearchFilterActivity.this, SearchResultActivity.class);
        final Intent resultThreeActivity = new Intent(SearchFilterActivity.this, SearchThreeActivity.class);

        final Intent receivedQueries = getIntent();

        final double budg = receivedQueries.getDoubleExtra("budget",0);
        final double lon = receivedQueries.getDoubleExtra("longitude", 0);
        final double lat = receivedQueries.getDoubleExtra("latitude", 0);
        final String cuisineInts = receivedQueries.getStringExtra("cuisineInts");
        final Integer research = receivedQueries.getIntExtra("research", 0);

        if(research.equals(1))
        {
            try {
                setAppRestaurantsWithFilters(
                        lat,
                        lon,
                        budg,
                        cuisineInts,
                        false,
                        500,
                        false,
                        resultActivity
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(research.equals(2))
        {
            try {
                setAppRestaurantsWithFilters(
                        lat,
                        lon,
                        budg,
                        cuisineInts,
                        false,
                        500,
                        false,
                        resultThreeActivity
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutActivity = new Intent(SearchFilterActivity.this, LoginActivity.class);
                startActivity(logoutActivity);
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(SearchFilterActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(SearchFilterActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(SearchFilterActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object

                                    Spinner cuisine = (Spinner) findViewById(R.id.spn_cuisine);
                                    EditText price = (EditText) findViewById(R.id.edt_price);

                                    final boolean isGreaterThanComp=false;

                                    final String cuisineVal = cuisine.getSelectedItem().toString();
                                    final Double budget = Double.parseDouble(price.getText().toString());

                                    try {
                                        setAppRestaurantsWithFilters(
                                                location.getLatitude(),
                                                location.getLongitude(),
                                                budget,
                                                cuisineVal,
                                                false,
                                                500,
                                                false,
                                                resultActivity
                                        );
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    Log.d("THIS IS THE REQUEST", "WOW pano yan" );
                                }
                            }
                        });
            }
        });
        searchThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(SearchFilterActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(SearchFilterActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {

                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(SearchFilterActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Spinner cuisine = (Spinner) findViewById(R.id.spn_cuisine);
                                    EditText price = (EditText) findViewById(R.id.edt_price);
                                    final boolean isGreaterThanComp=false;
                                    final String cuisineVal = cuisine.getSelectedItem().toString();
                                    final Double budget = Double.parseDouble(price.getText().toString());

                                    try {
                                        getAppThreeRestaurantsWithFilters(
                                                location.getLatitude(),
                                                location.getLongitude(),
                                                budget,
                                                cuisineVal,
                                                false,
                                                500,
                                                false,
                                                resultThreeActivity
                                        );
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    Log.d("THIS IS THE REQUEST", "WOW pano yan" );
                                }
                            }
                        });
            }
        });
    }
    private void loadPermissions(String perm,int requestCode) {
        if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, perm)) {
                ActivityCompat.requestPermissions(this, new String[]{perm},requestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // granted
                }
                else{
                    // no granted
                }
                return;
            }

        }

    }

    public void setRandomRestaurant(double lat, double lon) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.zomato.com/geocode";
        JSONObject jsonRequest = new JSONObject();

        jsonRequest.put("user-key", zomatoToken);
        jsonRequest.put("lat", lat);
        jsonRequest.put("lon", lon);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* Things u wanna do */
                        try {
                            Random rand = new Random();
                            int index = rand.nextInt(response.getJSONArray("nearby_restaurants").length());
                            JSONObject selected = response.getJSONArray("nearby_restaurants").getJSONObject(index);

                            String res_name = selected.getString("name");
                            JSONObject loc = selected.getJSONObject("location");
                            String res_location = loc.getString("address") + loc.getString("locality") + loc.getString("city");
                            String res_cuisines = selected.getString("cuisines");
                            Double res_budget = Double.parseDouble(selected.getString("average_cost_for_two"))/2;

                            random_restaurant = new Restaurant(res_name, res_location, res_cuisines, res_budget);

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
    public void setAppRestaurantsWithFilters(final double lat, final double lon,
                                             final double budget, final String cuisines,
                                             final Boolean hasDelivery,
                                             double radius_meters, final Boolean isGreaterThan,
                                             final Intent resultActivity) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://developers.zomato.com/api/v2.1/search?lat="+lat+"&lon="+lon+"&radius="
                +radius_meters+"&cuisines="+getCuisineID(cuisines)+"&sort=real_distance&order=asc&count=125";
        Log.d("THIS IS THE REQUEST", url);
        JSONObject jsonRequest = new JSONObject();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* Things u wanna do */

                        Log.d("Confirmation", "You have reached up to this point");
                        try {
                            JSONArray temp_res = response.getJSONArray("restaurants");
                            JSONArray filtered = new JSONArray();
                            double res_cost;
                            boolean res_deli;
                            String res_cuisine;
                            int iterations = temp_res.length();

                            Log.d("selected", temp_res.toString(4));
                            for(int i = 0; i < iterations; i++ ){
                                if(iterations == 0) {
                                    break;
                                }
                                res_cost = Double.parseDouble(temp_res.getJSONObject(i).getJSONObject("restaurant").get("average_cost_for_two").toString())/2;
                                res_deli = Boolean.parseBoolean(temp_res.getJSONObject(i).getJSONObject("restaurant").get("has_online_delivery").toString());

                                Log.d(iterations+" and " + i, temp_res.getJSONObject(i).getJSONObject("restaurant").get("name")+ "" + res_cost + " vs " +budget );
                                if (res_cost > budget) {
                                    temp_res.remove(i);
                                    i = 0;
                                    Log.d("BUDGET LOST", res_cost + " > " +budget);

                                }
                                iterations = temp_res.length();
                                }
                            iterations = temp_res.length();
                            JSONArray filtered_restaurants = temp_res;
                            Log.d("FILTERED BABY", filtered_restaurants.toString(4));

                            Random rand = new Random();
                            if(filtered_restaurants.length()>0) {
                                int index = rand.nextInt(filtered_restaurants.length());
                                int index2 = rand.nextInt(filtered_restaurants.length());
                                while(index2 == index) {
                                    index2 =  rand.nextInt(filtered_restaurants.length());
                                }
                                int index3 = rand.nextInt(filtered_restaurants.length());
                                while(index3 == index || index3 == index2) {
                                    index3 =  rand.nextInt(filtered_restaurants.length());
                                }

                                JSONObject selected = filtered_restaurants.getJSONObject(index);
                                JSONObject selected2 = filtered_restaurants.getJSONObject(index2);
                                JSONObject selected3 = filtered_restaurants.getJSONObject(index3);

                                //result 1
                                String res_name = selected.getJSONObject("restaurant").getString("name");
                                JSONObject loc = selected.getJSONObject("restaurant").getJSONObject("location");
                                String res_location = loc.getString("address") + loc.getString("locality") + loc.getString("city");
                                String res_cuisines = selected.getJSONObject("restaurant").getString("cuisines");
                                Double res_budget = Double.parseDouble(selected.getJSONObject("restaurant").getString("average_cost_for_two")) / 2;

                                //putToResults1
                                resultActivity.putExtra("name", res_name);
                                resultActivity.putExtra("location", res_location);
                                resultActivity.putExtra("price", res_budget.toString());
                                resultActivity.putExtra("cuisines", res_cuisines);

                                resultActivity.putExtra("latitude", lat);
                                resultActivity.putExtra("longitude", lon);
                                resultActivity.putExtra("cuisineInts", cuisines);
                                resultActivity.putExtra("budget", budget);

                                startActivity(resultActivity);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("user-key", zomatoToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }
    public void getAppThreeRestaurantsWithFilters(final double lat, final double lon,
                                             final double budget, final String cuisines,
                                             final Boolean hasDelivery,
                                             double radius_meters, final Boolean isGreaterThan,
                                             final Intent resultActivity) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://developers.zomato.com/api/v2.1/search?lat="+lat+"&lon="+lon+"&radius="
                +radius_meters+"&cuisines="+getCuisineID(cuisines)+"&sort=real_distance&order=asc&count=125";
        Log.d("THIS IS THE REQUEST", url);
        JSONObject jsonRequest = new JSONObject();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* Things u wanna do */

                        Log.d("Confirmation", "You have reached up to this point");
                        try {
                            JSONArray temp_res = response.getJSONArray("restaurants");
                            JSONArray filtered = new JSONArray();
                            double res_cost;
                            boolean res_deli;
                            String res_cuisine;
                            int iterations = temp_res.length();

                            Log.d("selected", temp_res.toString(4));
                            for(int i = 0; i < iterations; i++ ){
                                if(iterations == 0) {
                                    break;
                                }
                                res_cost = Double.parseDouble(temp_res.getJSONObject(i).getJSONObject("restaurant").get("average_cost_for_two").toString())/2;
                                res_deli = Boolean.parseBoolean(temp_res.getJSONObject(i).getJSONObject("restaurant").get("has_online_delivery").toString());

                                Log.d(iterations+" and " + i, temp_res.getJSONObject(i).getJSONObject("restaurant").get("name")+ "" + res_cost + " vs " +budget );
                                if (res_cost > budget) {
                                    temp_res.remove(i);
                                    i = 0;
                                    Log.d("BUDGET LOST", res_cost + " > " +budget);

                                }
                                iterations = temp_res.length();
                            }
                            iterations = temp_res.length();
                            JSONArray filtered_restaurants = temp_res;
                            Log.d("FILTERED BABY", filtered_restaurants.toString(4));

                            Random rand = new Random();
                            if(filtered_restaurants.length()>0) {
                                int index = rand.nextInt(filtered_restaurants.length());
                                int index2 = rand.nextInt(filtered_restaurants.length());
                                while(index2 == index) {
                                    index2 =  rand.nextInt(filtered_restaurants.length());
                                }
                                int index3 = rand.nextInt(filtered_restaurants.length());
                                while(index3 == index || index3 == index2) {
                                    index3 =  rand.nextInt(filtered_restaurants.length());
                                }

                                JSONObject selected = filtered_restaurants.getJSONObject(index);
                                JSONObject selected2 = filtered_restaurants.getJSONObject(index2);
                                JSONObject selected3 = filtered_restaurants.getJSONObject(index3);

                                //result 1
                                String res_name = selected.getJSONObject("restaurant").getString("name");
                                JSONObject loc = selected.getJSONObject("restaurant").getJSONObject("location");
                                String res_location = loc.getString("address") + loc.getString("locality") + loc.getString("city");
                                Double res_latitude = Double.parseDouble(loc.getString("latitude"));
                                Double res_longitude = Double.parseDouble(loc.getString("longitude"));
                                String res_cuisines = selected.getJSONObject("restaurant").getString("cuisines");
                                Double res_budget = Double.parseDouble(selected.getJSONObject("restaurant").getString("average_cost_for_two")) / 2;

                                //result 2
                                String res_name2 = selected2.getJSONObject("restaurant").getString("name");
                                JSONObject loc2 = selected2.getJSONObject("restaurant").getJSONObject("location");
                                String res_location2 = loc2.getString("address") + loc.getString("locality") + loc.getString("city");
                                String res_cuisines2 = selected2.getJSONObject("restaurant").getString("cuisines");
                                Double res_budget2 = Double.parseDouble(selected2.getJSONObject("restaurant").getString("average_cost_for_two")) / 2;
                                random_restaurant2 = new Restaurant(res_name2, res_location2, res_cuisines, res_budget2);

                                //result 3
                                String res_name3 = selected3.getJSONObject("restaurant").getString("name");
                                JSONObject loc3 = selected3.getJSONObject("restaurant").getJSONObject("location");
                                String res_location3 = loc3.getString("address") + loc.getString("locality") + loc.getString("city");
                                String res_cuisines3 = selected3.getJSONObject("restaurant").getString("cuisines");
                                Double res_budget3 = Double.parseDouble(selected3.getJSONObject("restaurant").getString("average_cost_for_two")) / 2;
                                random_restaurant3 = new Restaurant(res_name3, res_location3, res_cuisines, res_budget3);

                                //putToResults1
                                resultActivity.putExtra("name", res_name);
                                resultActivity.putExtra("location", res_location);
                                resultActivity.putExtra("price", res_budget.toString());
                                resultActivity.putExtra("cuisines", res_cuisines);
                                resultActivity.putExtra("restolat", res_latitude);
                                resultActivity.putExtra("restolon", res_longitude);
                                resultActivity.putExtra("latitude", lat);
                                resultActivity.putExtra("longitude", lon);
                                resultActivity.putExtra("cuisineInts", cuisines);
                                resultActivity.putExtra("budget", budget);

                                //putToResults2
                                resultActivity.putExtra("name2", res_name2);
                                resultActivity.putExtra("location2", res_location2);
                                resultActivity.putExtra("price2", res_budget2.toString());
                                resultActivity.putExtra("cuisines2", res_cuisines2);

                                resultActivity.putExtra("latitude2", lat);
                                resultActivity.putExtra("longitude2", lon);
                                resultActivity.putExtra("cuisineInts2", cuisines);
                                resultActivity.putExtra("budget2", budget);

                                //putToResults3
                                resultActivity.putExtra("name3", res_name2);
                                resultActivity.putExtra("location3", res_location2);
                                resultActivity.putExtra("price3", res_budget2.toString());
                                resultActivity.putExtra("cuisines3", res_cuisines2);

                                resultActivity.putExtra("latitude3", lat);
                                resultActivity.putExtra("longitude3", lon);
                                resultActivity.putExtra("cuisineInts3", cuisines);
                                resultActivity.putExtra("budget3", budget);

                                startActivity(resultActivity);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("user-key", zomatoToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }
    public void getRestaurantDetails(String res_id) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.zomato.com/search";
        JSONObject jsonRequest = new JSONObject();

        jsonRequest.put("user-key", zomatoToken);
        jsonRequest.put("res_id", res_id);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* Things u wanna do */
                        JSONObject res_details = response;
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
    /* API End */

    @Override

    public void onClick(View view) {
        int i = view.getId();
//        if (i == R.id.btn_search) {
//
//
//        } else if (i == R.id.btn_cancel) {
//
//        }
    }
    public String getCuisineID(String cuisine){
        HashMap<String, String> cuisineMap = new HashMap<>();
        cuisineMap.put("Any","");
        cuisineMap.put("American","1");
        cuisineMap.put("Asian","3");
        cuisineMap.put("BBQ","193");
        cuisineMap.put("Beverages","270");
        cuisineMap.put("Burger","168");
        cuisineMap.put("Cafe","30");
        cuisineMap.put("Chinese","25");
        cuisineMap.put("Dessert","100");
        cuisineMap.put("Fast Food","40");
        cuisineMap.put("Filipino","112");
        cuisineMap.put("Greek","156");
        cuisineMap.put("Healthy Food","143");
        cuisineMap.put("Ice Cream","233");
        cuisineMap.put("Indian","148");
        cuisineMap.put("Italian","55");
        cuisineMap.put("Japanese","60");
        cuisineMap.put("Sushi","177");
        cuisineMap.put("Ramen","320");
        cuisineMap.put("Korean","67");
        cuisineMap.put("Pizza","82");
        cuisineMap.put("Street Food","90");
        return cuisineMap.get(cuisine);
    }
}
