package edu.ateneo.cie199.bucketdish;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kyle Bartido on 13/11/2017.
 */

public class BucketdishApplication extends Application {
    private HttpClient mHttpClient = new DefaultHttpClient();

    public Object lists = new Object();

    public ArrayList<BucketList> bucketLists = new ArrayList<>();



    public ArrayList<User> users = new ArrayList<>();

    public ArrayList<BucketList> getBucketLists() {
        return bucketLists;
    }

    public void addToList(BucketList list){
        bucketLists.add(list);
    }


    public void clearList(){
        bucketLists.clear();
    }
    public User currentUser = new User();

    public HttpClient getHttpClient() {
        return mHttpClient;
    }

    public String test() {
        return "wow";
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getNearest() {
        HttpClient hc = getHttpClient();
        HttpGet request = new HttpGet("https://developers.zomato.com/api/v2.1/search?lat=14.579327&lon=121.056324&radius=1000&sort=real_distance&order=asc");
        HttpResponse response;
        String entity;
        try {
            response = hc.execute(request);
            return EntityUtils.toString(response.getEntity());

        } catch (IOException e) {
            Log.e("Error", "IOException occurred”);");
            return "error";
        }

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
}
