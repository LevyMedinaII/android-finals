package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        CustomButton btnList = (CustomButton) findViewById(R.id.imgbtn_bucketlist);
        CustomButton btnNew = (CustomButton) findViewById(R.id.imgbtn_newlist);
        final BucketdishApplication app = (BucketdishApplication) getApplication();
//        Button btnRand = (Button) findViewById(R.id.imgbtn_randsearch);
//        Button btnLogout = (Button) findViewById(R.id.btn_logout);
        btnList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchBucketDishActivity = new Intent(MainActivity.this, BucketDishActivity.class);
                        finish();
                        startActivity(launchBucketDishActivity);


                    }
                }
        );
        btnRand.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchSearchFilterActivity = new Intent(MainActivity.this, SearchFilterActivity.class);
                        finish();
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
        btnNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent launchBucketDishActivity = new Intent(MainActivity.this, NewListActivity.class);
                        finish();
                        startActivity(launchBucketDishActivity);


                    }
                }
        );
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference listRef = database.getReference("lists/"+currentUser.getUid());




        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<Restaurant> container = new ArrayList<>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("name").getValue();
                    Long budget = (Long) messageSnapshot.child("budget").getValue()+0;
                    String cuisines = (String) messageSnapshot.child("cuisines").getValue();
                    String location = (String) messageSnapshot.child("location").getValue();
                    Double budgetDouble = Double.valueOf(budget);
                    Restaurant newRest = new Restaurant(name, location, cuisines, budgetDouble);
                    container.add(newRest);

                }
                app.setBucketList(container);
//                HashMap listValue = (HashMap) dataSnapshot.getValue();
//                Iterator it = listValue.entrySet().iterator();
//                JSONObject jsonObject = new JSONObject();
//                while (it.hasNext()) {
//                    Map.Entry pairs = (Map.Entry)it.next();
//                    try {
//                        jsonObject.put( (String) pairs.getKey(), pairs.getValue() );
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.d("ListValue", "Value is: " + listValue);
//
//                    Log.d("JSONObject "+ jsonObject.toString(), "Value is:");



                //app.setMyLists(jsonObject);
//                app.setCollector(listValue);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Error", "Failed to read value.", error.toException());
            }
        });
        DatabaseReference rootRef = database.getReference();
        final List<String> listNames = new ArrayList<String>();
        Query giveLists = rootRef.child("RecommendationLists").orderByChild("CreatedBy").equalTo(app.getCurrentUser().getUsername());
        giveLists.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot list : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        listNames.add(list.getKey());
                        Log.d("this is MINE", " " + listNames);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        app.setRecommendList(listNames);


    }

    private void signOut() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

    }
}
