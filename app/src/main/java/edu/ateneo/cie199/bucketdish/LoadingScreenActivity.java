package edu.ateneo.cie199.bucketdish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by ljda0 on 07/12/2017.
 */

public class LoadingScreenActivity extends AppCompatActivity {
    protected boolean active = true;
    protected static int SPLASH_TIME = 4000; // time to display the splash screen in ms



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading_screen);
        Dialog pBarDialog = new Dialog(this);
        pBarDialog.setContentView(R.layout.layout_loading_screen);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (active && (waited < SPLASH_TIME)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    startActivity(new Intent(LoadingScreenActivity.this,
                            MainActivity.class));
                    finish();
                }
            }
        };
        splashTread.start();
    }
}
