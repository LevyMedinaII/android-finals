package edu.ateneo.cie199.bucketdish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView txvName = (TextView) findViewById(R.id.txv_name);
        TextView txvEmail = (TextView) findViewById(R.id.txv_email);
        TextView txvContact = (TextView) findViewById(R.id.txv_contact);

        txvName.setText("INSERT TEXT HERE");
        txvEmail.setText("INSERT TEXT HERE");
        txvContact.setText("INSERT TEXT HERE");
    }
}
