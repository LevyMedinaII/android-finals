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

public class SearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final DatabaseReference userRef = database.getReference("lists/"+user.getUid());

        CheckBox chkBucketDish = (CheckBox)findViewById(R.id.chk_bucketdish);


        final Intent receivedQueries = getIntent();
        TextView name = (TextView) findViewById(R.id.txv_restaurant);
        TextView price = (TextView) findViewById(R.id.txv_price);
        TextView address = (TextView) findViewById(R.id.txv_address);

        name.setText(receivedQueries.getStringExtra("name"));
        price.setText(receivedQueries.getStringExtra("price"));
        address.setText(receivedQueries.getStringExtra("location"));

        chkBucketDish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==Boolean.TRUE)
                {
                    addItem(receivedQueries.getStringExtra("name"));
                }
                else
                {
                    deleteItem(receivedQueries.getStringExtra("name"));
                }

            }
        });

        Button back = (Button) findViewById(R.id.btn_research);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(SearchResultActivity.this, SearchFilterActivity.class);
                startActivity(searchIntent);
            }
        });
    }

    private void addItem(final String itemName){
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
                rootRef.child("lists").child(userId).child(itemName).setValue(itemName);
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
}
