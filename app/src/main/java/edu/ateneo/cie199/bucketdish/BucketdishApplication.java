package edu.ateneo.cie199.bucketdish;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Kyle Bartido on 13/11/2017.
 */

public class BucketdishApplication extends Application {


    public Object lists = new Object();

    public ArrayList<Restaurant> bucketList = new ArrayList<>();

    public List<String> recommendList = new ArrayList<String>();

    public List<String> getRecommendList() {
        return recommendList;
    }

    public void setRecommendList(List<String> recommendList) {
        this.recommendList = recommendList;
    }

    public ArrayList<Restaurant> getBucketList() {
        return bucketList;
    }

    public void setBucketList(ArrayList<Restaurant> bucketList) {
        this.bucketList = bucketList;
    }


    public HashMap collector = new HashMap();

    public void setCollector(HashMap collector) {
        this.collector = collector;
    }



    public JSONObject getMyLists() {
        return myLists;
    }

//    public ArrayList<Restaurant> createBucketList()  {
//        ArrayList<Restaurant> holder = new ArrayList<>();
//        for(int i=0; i<collector.size(); i++)
//        {
//
////            Restaurant rest = new Restaurant(collector.)
//        }
//
////        JSONArray names = getMyLists().names();
////        Log.d("INSIDE ERROR", "Value is: " + names);
////        ArrayList<Restaurant> holder = new ArrayList<>();
////        for(int i=0; i<getMyLists().length(); i++)
////        {
////
////            String name = names.getString(i);
////
////            Log.d("INSIDE ERROR", "Value is: " + getMyLists().getJSONObject(name).getString("budget"));
////            JSONArray values = getMyLists().getJSONArray(name);
////            Log.d("INSIDE ERROR", "Value is: " + values);
////            //Restaurant rest = new Restaurant(values.getString(0),values.getString(1),values.getString(2),values.getDouble(3));
////            //holder.add(rest);
////        }
////        return holder;
//    }

    public void setMyLists(JSONObject myLists) {
        this.myLists = myLists;
    }

    public JSONObject myLists = new JSONObject();
    public ArrayList<User> users = new ArrayList<>();
    public User currentUser = new User();


    public ArrayList<User> getUsers() {
        return users;
    }


    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void getUser(String id) {
//        HttpClient hc = getHttpClient();
//        String qFirstName = "https://bucketdish-20f26.firebaseio.com/users/"+id+"/firstName.json";
//        String qLastName = "https://bucketdish-20f26.firebaseio.com/users/"+id+"/lastName.json";
//        String qUserName = "https://bucketdish-20f26.firebaseio.com/users/"+id+"/userName.json";
//
//        HttpGet request = new HttpGet(q);
//        HttpResponse response;
//        String entity;
//        try {
//            response = hc.execute(request);
//
//
//        } catch (IOException e) {
//            Log.e("Error", "IOException occurred”);");
//            return;
//        }


    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Object getLists() {
        return lists;
    }

    public void setLists(Object lists) {
        this.lists = lists;
    }

    public Restaurant searchBucketListbyName(List<Restaurant> list, String Name){
        for(Restaurant resto : list){
            if(resto.getName().matches(Name)) return resto;
        }
        return null;
    }


}
