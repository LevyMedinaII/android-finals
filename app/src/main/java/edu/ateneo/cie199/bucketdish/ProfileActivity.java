package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView txvName = (TextView) findViewById(R.id.txv_name);
        TextView txvEmail = (TextView) findViewById(R.id.txv_email);
        TextView txvContact = (TextView) findViewById(R.id.txv_contact);
        TextView txvUsername = (TextView) findViewById(R.id.txv_username);

        txvName.setText("INSERT TEXT HERE");
        txvEmail.setText("INSERT TEXT HERE");
        txvContact.setText("INSERT TEXT HERE");
        txvUsername.setText("INSERT TEXT HERE");

        CustomButton btnBack = (CustomButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });
    }
}
