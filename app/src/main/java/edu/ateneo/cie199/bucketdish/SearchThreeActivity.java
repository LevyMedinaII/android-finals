package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_three);
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference userRef = database.getReference("lists/"+user.getUid());

        final Intent receivedQueries = getIntent();

        TextView name = (TextView) findViewById(R.id.txv_name);
        TextView price = (TextView) findViewById(R.id.txv_price);
        TextView address = (TextView) findViewById(R.id.txv_address);
        TextView cuisines = (TextView) findViewById(R.id.txv_cuisines);

        TextView name2 = (TextView) findViewById(R.id.txv_restaurant2);
        TextView price2 = (TextView) findViewById(R.id.txv_price2);
        TextView address2 = (TextView) findViewById(R.id.txv_address2);
        TextView cuisines2 = (TextView) findViewById(R.id.txv_cuisines2);

        TextView name3 = (TextView) findViewById(R.id.txv_restaurant3);
        TextView price3 = (TextView) findViewById(R.id.txv_price3);
        TextView address3 = (TextView) findViewById(R.id.txv_address3);
        TextView cuisines3 = (TextView) findViewById(R.id.txv_cuisines3);

        name.setText(receivedQueries.getStringExtra("name"));
        price.setText(receivedQueries.getStringExtra("price"));
        address.setText(receivedQueries.getStringExtra("location"));
        cuisines.setText(receivedQueries.getStringExtra("cuisines"));

        name2.setText(receivedQueries.getStringExtra("name2"));
        price2.setText(receivedQueries.getStringExtra("price2"));
        address2.setText(receivedQueries.getStringExtra("location2"));
        cuisines2.setText(receivedQueries.getStringExtra("cuisines2"));

        name3.setText(receivedQueries.getStringExtra("name3"));
        price3.setText(receivedQueries.getStringExtra("price3"));
        address3.setText(receivedQueries.getStringExtra("location3"));
        cuisines3.setText(receivedQueries.getStringExtra("cuisines3"));

        final double budg = receivedQueries.getDoubleExtra("budget",0);
        final double lon = receivedQueries.getDoubleExtra("longitude", 0);
        final double lat = receivedQueries.getDoubleExtra("latitude", 0);
        final String cuisineInts = receivedQueries.getStringExtra("cuisineInts");

//        JSONArray myBucketList = app.getMyLists().names();
//        ArrayList<String> restaurantsInList = app.getMyLists().

        Button another = (Button) findViewById(R.id.btn_back);
        Button newFilter = (Button) findViewById(R.id.btn_newFilter);

//        newFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent searchIntent = new Intent(SearchThreeActivity.this, SearchFilterActivity.class);
//                startActivity(searchIntent);
//                finish();
//            }
//        });
//
//        another.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Intent resultActivity = new Intent(SearchThreeActivity.this, SearchFilterActivity.class);
//                resultActivity.putExtra("latitude", lat);
//                resultActivity.putExtra("longitude", lon);
//                resultActivity.putExtra("cuisineInts", cuisineInts);
//                resultActivity.putExtra("budget", budg);
//                resultActivity.putExtra("research", 1);
//                startActivityForResult(resultActivity, 2);
//                finish();
//
//            }
//        });

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
        //BucketList newList = new BucketList(mAuth.getUid(), mAuth.getCurrentUser().)
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();
        //final Restaurant current = app.getCheckoutRestaurant();
        //final String itemName = current.getName();
        //String cred =
        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("OH NO","MERON NA BOY");
                long number = snapshot.getChildrenCount();
                Log.d("This is the Nth list", ""+number);
//                    String key =rootRef.child("lists").child(app.getCurrentUser().getUsername()).push().getKey();
//                    Log.d("This is User Key", key);
                rootRef.child("lists").child(userId).child(itemName).setValue(newRest);
                //rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                //doneRestaurant(itemName, key);
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

        //BucketList newList = new BucketList(mAuth.getUid(), mAuth.getCurrentUser().)
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();
        //final Restaurant current = app.getCheckoutRestaurant();
        //final String itemName = current.getName();
        //String cred =
        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("OH NO","MERON NA BOY");
                long number = snapshot.getChildrenCount();
                Log.d("This is the Nth list", ""+number);
//                    String key =rootRef.child("lists").child(app.getCurrentUser().getUsername()).push().getKey();
//                    Log.d("This is User Key", key);
                rootRef.child("lists").child(userId).child(itemName).removeValue();
                //rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                //doneRestaurant(itemName, key);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            TextView name = (TextView) findViewById(R.id.txv_name);
            TextView price = (TextView) findViewById(R.id.txv_price);
            TextView address = (TextView) findViewById(R.id.txv_address);
            TextView cuisines = (TextView) findViewById(R.id.txv_cuisines);

            name.setText(data.getStringExtra("name"));
            price.setText(data.getStringExtra("price"));
            address.setText(data.getStringExtra("location"));
            cuisines.setText(data.getStringExtra("cuisines"));
            //do the things u wanted
        }
    }
}
