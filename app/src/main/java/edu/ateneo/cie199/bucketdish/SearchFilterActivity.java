package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class SearchFilterActivity extends AppCompatActivity implements View.OnClickListener{

    private String zomatoToken = "81c4d728678c315f02168a91d762f025";
    private JSONArray restaurants;
    private JSONObject random_restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
    }

    public void setAppRestaurantsNearMe(double lat, double lon) throws JSONException {
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
                            random_restaurant = response.getJSONArray("nearby_restaurants").getJSONObject(index);
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
    public void setAppRestaurantsWithFilters(double lat, double lon,
                                             final double budget, String cuisines,
                                             final Boolean hasDelivery, int radius_meters) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.zomato.com/search";
        JSONObject jsonRequest = new JSONObject();

        jsonRequest.put("user-key", zomatoToken);
        jsonRequest.put("lat", lat);
        jsonRequest.put("lon", lon);
        jsonRequest.put("radius", radius_meters);
        jsonRequest.put("cuisines", cuisines);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* Things u wanna do */
                        try {
                            JSONArray temp_res = response.getJSONArray("nearby_restaurants");
                            JSONArray filtered = new JSONArray();
                            double res_cost;
                            boolean res_deli;
                            String res_cuisine;

                            for(int i = 0; i < temp_res.length(); i++ ){
                                res_cost = Double.parseDouble(temp_res.getJSONObject(i).get("average_cost_for_two").toString())/2;
                                if(res_cost > budget){
                                    temp_res.remove(i);
                                }

                                res_deli = Boolean.parseBoolean(temp_res.getJSONObject(i).get("has_online_delivery").toString());
                                if(res_deli != hasDelivery){
                                    temp_res.remove(i);
                                }
                            }
                            restaurants = temp_res;
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
        if (i == R.id.btn_search) {


        } else if (i == R.id.btn_cancel) {
            Intent logoutActivity = new Intent(SearchFilterActivity.this, LoginActivity.class);
            startActivity(logoutActivity);
        }


    }
}
