package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser==null)
        {
            Intent logoutActivity = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(logoutActivity);
        }
        CustomButton btnRand = (CustomButton) findViewById(R.id.imgbtn_randsearch);
        CustomButton btnLogout = (CustomButton) findViewById(R.id.btn_logout);
//        Button btnRand = (Button) findViewById(R.id.imgbtn_randsearch);
//        Button btnLogout = (Button) findViewById(R.id.btn_logout);
        btnRand.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchSearchFilterActivity = new Intent(MainActivity.this, SearchFilterActivity.class);
                        startActivity(launchSearchFilterActivity);
                    }
                }
        );
        btnLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signOut();
                        Intent logoutActivity = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(logoutActivity);
                    }
                }
        );


    }

    private void signOut() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

    }
}
