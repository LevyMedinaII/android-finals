package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class SearchResultActivity extends AppCompatActivity {

    SearchFilterActivity filter = new SearchFilterActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        final BucketdishApplication app = (BucketdishApplication) getApplication();

        // Firebase authentication and connection
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference userRef = database.getReference("lists/"+user.getUid());

        // Initialize UI Components
        CheckBox chkBucketDish = (CheckBox)findViewById(R.id.chk_bucketdish);
        TextView name = (TextView) findViewById(R.id.txv_name);
        TextView price = (TextView) findViewById(R.id.txv_price);
        TextView address = (TextView) findViewById(R.id.txv_address);
        TextView cuisines = (TextView) findViewById(R.id.txv_cuisines);

        // Get Search Queries, Results and Filters
        final Intent receivedQueries = getIntent();

        name.setText(receivedQueries.getStringExtra("name"));
        price.setText(receivedQueries.getStringExtra("price"));
        address.setText(receivedQueries.getStringExtra("location"));
        cuisines.setText(receivedQueries.getStringExtra("cuisines"));

        final double budg = receivedQueries.getDoubleExtra("budget",0);
        final double lon = receivedQueries.getDoubleExtra("longitude", 0);
        final double lat = receivedQueries.getDoubleExtra("latitude", 0);
        final String cuisineInts = receivedQueries.getStringExtra("cuisineInts");

        // Set Restaurant to "Bucketdish"-ed in UI if already in Firebase
        ArrayList<Restaurant> myBucketList = app.getBucketList();
        if(myBucketList!=null)
        {
            int listSize = myBucketList.size();
            for(int i=0; i<listSize; i++)
            {
                if(myBucketList.get(i).getName().matches(receivedQueries.getStringExtra("name")))
                {
                    chkBucketDish.setChecked(true);
                }
            }
        }

        // Attach listener; onChange add item to firebase or remove item from firebase
        chkBucketDish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==Boolean.TRUE)
                {
                    addItem(receivedQueries.getStringExtra("name"), receivedQueries.getStringExtra("location"), receivedQueries.getStringExtra("price"), receivedQueries.getStringExtra("cuisines"));
                }
                else
                {
                    deleteItem(receivedQueries.getStringExtra("name"));
                }

            }
        });

        // Initialize Button UI Components
        Button another = (Button) findViewById(R.id.btn_back);
        Button newFilter = (Button) findViewById(R.id.btn_newFilter);
        final Button directions = (Button) findViewById(R.id.btn_directions);

        // Add Button Listeners
        newFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Go back to Search Filter Activity
                Intent searchIntent = new Intent(SearchResultActivity.this, SearchFilterActivity.class);
                startActivity(searchIntent);
                finish();
            }
        });

        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Restart Search Algorithm
                final Intent resultActivity = new Intent(SearchResultActivity.this, SearchFilterActivity.class);
                resultActivity.putExtra("latitude", lat);
                resultActivity.putExtra("longitude", lon);
                resultActivity.putExtra("cuisineInts", cuisineInts);
                resultActivity.putExtra("budget", budg);
                resultActivity.putExtra("research", 1);
                startActivityForResult(resultActivity, 2);
                finish();

            }
        });

        directions.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent directionsActivity = new Intent(SearchResultActivity.this, DirectionActivity.class);
                        directionsActivity.putExtra("userlat",lat);
                        directionsActivity.putExtra("userlong",lon);
                        directionsActivity.putExtra("restolat",getIntent().getDoubleExtra("restolat",0));
                        directionsActivity.putExtra("restolong",getIntent().getDoubleExtra("restolong",0));
                        directionsActivity.putExtra("restoname", getIntent().getStringExtra("name"));
                        startActivity(directionsActivity);
                    }
                }
        );

//        resultActivity.putExtra("name", res_name);
//        resultActivity.putExtra("location", res_location);
//        resultActivity.putExtra("price", res_budget.toString());
//        resultActivity.putExtra("cuisines", res_cuisines);
//
//
//        startActivity(resultActivity);
    }

    private void addItem(final String itemName, final String location, final String price, final String cuisines ){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final Restaurant newRest = new Restaurant(itemName,location,cuisines,Double.parseDouble(price));
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();

        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("OH NO","MERON NA BOY");
                long number = snapshot.getChildrenCount();
                Log.d("This is the Nth list", ""+number);
                rootRef.child("lists").child(userId).child(itemName).setValue(newRest);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void deleteItem(final String itemName){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();

        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("OH NO","MERON NA BOY");
                long number = snapshot.getChildrenCount();
                Log.d("This is the Nth list", ""+number);
                rootRef.child("lists").child(userId).child(itemName).removeValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
