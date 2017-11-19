package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CustomButton btnRand = (CustomButton) findViewById(R.id.imgbtn_randsearch);
        btnRand.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchSearchFilterActivity = new Intent(MainActivity.this, SearchFilterActivity.class);
                        startActivity(launchSearchFilterActivity);
                    }
                }
        );
    }
}
