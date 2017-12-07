package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

public class BucketDishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_dish);
        final BucketdishApplication app = (BucketdishApplication) getApplication();


        Button btnBack = (Button) findViewById(R.id.btn_back);
        Button btnSearch = (Button) findViewById(R.id.btn_search);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchBucketDishActivity = new Intent(BucketDishActivity.this, MainActivity.class);
                finish();
                startActivity(launchBucketDishActivity);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchBucketDishActivity = new Intent(BucketDishActivity.this, SearchFilterActivity.class);
                finish();
                startActivity(launchBucketDishActivity);
            }
        });
        ArrayAdapter<Restaurant> mAdapter = new BucketDishAdapter(this, app.getBucketList());

        ListView bucketListView = (ListView) findViewById(R.id.lsv_bucketdish);
        bucketListView.setAdapter(mAdapter);

        /* And then notify the adapter that the dataset has changed for good measure */
        mAdapter.notifyDataSetChanged();
    }
}
