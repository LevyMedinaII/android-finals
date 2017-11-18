package edu.ateneo.cie199.bucketdish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;


public class ListActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG ="Hello";
    BucketdishApplication macdaddy = (BucketdishApplication) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        final BucketdishApplication macdaddy = (BucketdishApplication) getApplication();
        Log.d("MacDaddy Here", "Value is: " + macdaddy.getLists());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        findViewById(R.id.btn_gimme).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final BucketdishApplication macdaddy = (BucketdishApplication) getApplication();
        int i = v.getId();
        if (i == R.id.btn_gimme) {
            Log.d("Updates Bro", "Value is: " + macdaddy.getLists());
            Log.d("HEYMAN Bro", "Value is: " + macdaddy.getCurrentUser().getUsername());

        }
    }
}
