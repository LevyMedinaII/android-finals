package edu.ateneo.cie199.bucketdish;

import android.app.Application;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Kyle Bartido on 13/11/2017.
 */

public class BucketdishApplication extends Application {
    private HttpClient mHttpClient = new DefaultHttpClient();


    public HttpClient getHttpClient() {
        return mHttpClient;
    }

    public String test() {
        return "wow";
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


}
