package edu.ateneo.cie199.bucketdish;

import android.app.Application;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class RandomizerActivity extends AppCompatActivity {
//    AsyncTask<Void, Void, Void> downloadTask =
//            new AsyncTask<Void, Void, Void>() {
//                @Override
//                protected Void doInBackground(Void... params) {
//                    TextView item = (TextView) findViewById(R.id.txv_item);
//                    BucketdishApplication macdaddy = (BucketdishApplication) getApplication();
//                    //restaurantData = macdaddy.getNearest();
//                    HttpClient hc = macdaddy.getHttpClient();
//                    HttpGet request = new HttpGet("https://developers.zomato.com/api/v2.1/search?lat=14.579327&lon=121.056324&radius=1000&sort=real_distance&order=asc");
//                    HttpResponse response;
//                    String entity = "ew";
//                    try {
//                        response = hc.execute(request);
//                        entity = EntityUtils.toString(response.getEntity());
//
//                    } catch (IOException e) {
//                        Log.e("Error", "IOException occurred”);");
//                        //return "error";
//                    }
//
//                    item.setText(entity);
//                    return null;
//                }
//                @Override
//                protected void onPostExecute(Void aVoid) {
//                    TextView item = (TextView) findViewById(R.id.txv_item);
//
//                    /* Do UI update tasks here */
//                    super.onPostExecute(aVoid);
//                    return;
//                }
//            };



    String restaurantData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomizer);
    Button btnRandom = (Button) findViewById(R.id.btn_random);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    btnRandom.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
              //downloadTask.execute();
            TextView item = (TextView) findViewById(R.id.txv_item);
            BucketdishApplication macdaddy = (BucketdishApplication) getApplication();
            //restaurantData = macdaddy.getNearest();
            HttpClient hc = macdaddy.getHttpClient();


            HttpGet request = new HttpGet("https://developers.zomato.com/api/v2.1/search?lat=14.579327&lon=121.056324&radius=1000&sort=real_distance&order=asc");
            request.setHeader("user-key","81c4d728678c315f02168a91d762f025");
            HttpResponse response;
            String entity = "ew";
            try {
                response = hc.execute(request);
                //entity.get
                entity = EntityUtils.toString(response.getEntity());

            } catch (IOException e) {
                Log.e("Error", "IOException occurred”);");
                //return "error";
            }

            item.setText(entity);
        }
    });


    }
}
