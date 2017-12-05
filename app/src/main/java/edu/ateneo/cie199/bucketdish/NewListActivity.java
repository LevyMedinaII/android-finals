package edu.ateneo.cie199.bucketdish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewListActivity extends AppCompatActivity implements View.OnClickListener {

    EditText listName;
    EditText description;
    Button add;
    Boolean listExists;
    String selectedList;
    String selectedRestaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_new_list);
        // Write a message to the database
        listName = (EditText) findViewById(R.id.edt_listname);
        description = (EditText) findViewById(R.id.edt_description);
        listExists = false;
        add = (Button) findViewById(R.id.btn_addnewlist);
        findViewById(R.id.btn_addnewlist).setOnClickListener(this);
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        String createdBy = app.getCurrentUser().getUsername();
        final List<String> listNames = new ArrayList<String>();
        final List<String> restaurantNames = new ArrayList<String>();
        ArrayList<Restaurant> pool = app.getBucketList();

        for(int x=0; x<pool.size(); x++)
        {
            restaurantNames.add(pool.get(x).getName());
            Log.d("this is MINE"," "+restaurantNames);

        }
        Query giveLists = rootRef.child("RecommendationLists").orderByChild("CreatedBy").equalTo(createdBy);
//        giveLists.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // dataSnapshot is the "issue" node with all children with id 0
//                    for (DataSnapshot list : dataSnapshot.getChildren()) {
//                        // do something with the individual "issues"
//                        listNames.add(list.getKey());
//                        Log.d("this is MINE"," "+listNames);
//                    }
//                }
//            }
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        Spinner lists = (Spinner) findViewById(R.id.spn_lists);
        Spinner restaurant = (Spinner) findViewById(R.id.spn_restaurant);
        lists.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedList = parent.getItemAtPosition(position).toString(); //this is your selected item
        /* And set the ImageView's resource to it */
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        restaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedRestaurant = parent.getItemAtPosition(position).toString(); //this is your selected item
        /* And set the ImageView's resource to it */
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ArrayAdapter<String> listsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, app.getRecommendList());
        listsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        lists.setAdapter(listsAdapter);

        ArrayAdapter<String> restaurantAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, restaurantNames);
        restaurantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        restaurant.setAdapter(restaurantAdapter);


    }

    private void doneRestaurant( String id, String uniqueId){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("users").child(uniqueId).push().setValue(id);
    }

    private void linkList( final String listName, final String itemName){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                    rootRef.child("RecommendationLists").child(listName).child("Restaurants").setValue(itemName);

//                    rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                    //doneRestaurant(itemName, key);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void newList(final String listName,final String description, final String username ){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Boolean listExists = false;
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        String createdBy = app.getCurrentUser().getUsername();

        listReset();
        Query query = rootRef.child("RecommendationLists").orderByChild("Name").equalTo(listName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    listExists();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query giveLists = rootRef.child("RecommendationLists").orderByChild("CreatedBy").equalTo(createdBy);
        giveLists.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot list : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Log.d("this is MINE"," "+list.getKey());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (listCheck()==true) {
                    Log.d("OH NO","MERON NA BOY");

//                    String key =rootRef.child("lists").child(listName).push().getKey();
                    //Log.d("This is User Key", key);
//                    rootRef.child("lists").child(listName).child(key).setValue(itemName);
//                    rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                    //doneRestaurant(itemName, key);
                }
                else
                {
                    String key =rootRef.child("lists").child(listName).push().getKey();
                    rootRef.child("RecommendationLists").child(listName);
                    rootRef.child("RecommendationLists").child(listName).child("Description").setValue(description);
                    rootRef.child("RecommendationLists").child(listName).child("CreatedBy").setValue(username);
                    rootRef.child("RecommendationLists").child(listName).child("Restaurants").setValue("");

//                    rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                    //doneRestaurant(itemName, key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i==R.id.btn_addnewlist)
        {
            final BucketdishApplication app = (BucketdishApplication) getApplication();
            String username = app.getCurrentUser().getUsername();
            newList(listName.getText().toString(), description.getText().toString(), username );
        }
        else if(i==R.id.btn_link)
        {
            linkList(selectedList, selectedRestaurant);
        }
    }
    private void listExists(){
        listExists=true;
    }
    private void listReset(){
        listExists=false;
    }
    private Boolean listCheck(){
        return listExists;
    }
}
