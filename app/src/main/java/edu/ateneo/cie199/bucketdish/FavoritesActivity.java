package edu.ateneo.cie199.bucketdish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText listName;
    EditText itemName;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        // Write a message to the database
        listName = (EditText) findViewById(R.id.edt_listname);
        itemName = (EditText) findViewById(R.id.edt_item);
        add = (Button) findViewById(R.id.btn_addtolist);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        findViewById(R.id.btn_addtolist).setOnClickListener(this);
        findViewById(R.id.btn_newlist).setOnClickListener(this);
        findViewById(R.id.btn_myLists).setOnClickListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    private void doneRestaurant( String id, String uniqueId){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("users").child(uniqueId).push().setValue(id);
    }

    private void addItem(final String listName,final String itemName){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
// ...
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //BucketList newList = new BucketList(mAuth.getUid(), mAuth.getCurrentUser().)
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();
        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(listName)) {
                    Log.d("OH NO","MERON NA BOY");
                    long number = snapshot.getChildrenCount();
                    Log.d("This is the Nth list", ""+number);
                    String key =rootRef.child("lists").child(listName).push().getKey();
                    Log.d("This is User Key", key);
                    rootRef.child("lists").child(listName).child(key).setValue(itemName);
                    //rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                    //doneRestaurant(itemName, key);
                }
                else
                {
                    String key =rootRef.child("lists").child(listName).push().getKey();
                    rootRef.child("lists").child(listName).child(key).setValue(itemName);
                    //rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
                    //doneRestaurant(itemName, key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void newList(final String listName){
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        //BucketList newList = new BucketList(mAuth.getUid(), mAuth.getCurrentUser().)
        final FirebaseUser user = mAuth.getCurrentUser();
        final BucketdishApplication app = (BucketdishApplication) getApplication();

        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String list = "";
                if (snapshot.hasChild(listName)) {


                    BucketList hist = new BucketList(listName,user.getUid(),app.getCurrentUser().getUsername(), list );

                    Log.d("This is User Key", ""+app.getCurrentUser().getUsername());
                    rootRef.child("lists").child(listName +" by "+app.getCurrentUser().getUsername()).setValue(hist);
                    //rootRef.child("lists").child(listName).push().setValue(hist);
                    //doneRestaurant(itemName, key);
                }
                else
                {
                    BucketList hist = new BucketList(listName,user.getUid(),app.getCurrentUser().getUsername(), list );

                    Log.d("This is User Key", ""+app.getCurrentUser().getUsername());
                    rootRef.child("lists").child(listName +" by "+app.getCurrentUser().getUsername()).setValue(hist);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void myLists(){


        final DatabaseReference listRef = FirebaseDatabase.getInstance().getReference("lists");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        Query q = listRef.orderByChild("creatorID").equalTo(user.getUid());
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        ArrayList<BucketList> holder = app.getBucketLists();
        for (BucketList list:holder
             ) {
            Log.d("MJKHK", "wow puta"+list.getName());

        };
        app.clearList();



        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("Mylist", "wow this is "+dataSnapshot.getKey());
                app.addToList(dataSnapshot.getValue(BucketList.class));

                Log.d("MyYYYNE", "MINE "+app.getBucketLists());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




    @Override
    public void onClick(View view) {
        int i = view.getId();
        if(i==R.id.btn_addtolist)
        {
            addItem(listName.getText().toString(), itemName.getText().toString());
        }
        else if(i==R.id.btn_newlist)
        {
            newList(listName.getText().toString());
        }
        else if(i==R.id.btn_myLists)
        {
            myLists();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
