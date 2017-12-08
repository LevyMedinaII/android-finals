package edu.ateneo.cie199.bucketdish;

import android.app.usage.NetworkStats;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditListActivity extends AppCompatActivity {

    ArrayList<Restaurant> bucketDish = new ArrayList<>();
    ArrayList<Restaurant> selected = new ArrayList<>();
    private  ArrayAdapter<Restaurant> mAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list);
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Boolean listExists = false;
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String userId= user.getUid();
        String createdBy = app.getCurrentUser().getUsername();
        final Intent receivedQueries = getIntent();
        final String listName = receivedQueries.getStringExtra("listname");
        Log.e("Hey Man Heres ur name",""+listName);

        bucketDish = app.getBucketList();

        mAdapter = new ArrayAdapter<Restaurant>(this,android.R.layout.simple_list_item_checked,bucketDish);

        final ListView lsvList = (ListView) findViewById(R.id.lsv_edit_list);
        lsvList.setChoiceMode(lsvList.CHOICE_MODE_MULTIPLE);
        lsvList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Button btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected.clear();
                        Log.e("HISADI0","ASDASLD");
                        for(int i=0; i<lsvList.getCount(); i++){
                            CheckedTextView item = (CheckedTextView) lsvList.getChildAt(i);
                            Restaurant resto = (Restaurant) lsvList.getItemAtPosition(i);
                            if(item.isChecked())
                            {
                                Log.e("HISADI0","111");
                                selected.add(resto);
                            }
                        }
                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                    rootRef.child("RecommendationLists").child(listName).child("Restaurants").setValue(null);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        int iterate = 0;
                        for(final Restaurant resto : selected){
                            Log.e("S",resto.getName());
                            iterate++;
                            final int iterateHolder = iterate;
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                        rootRef.child("RecommendationLists").child(listName).child("Restaurants").child(String.valueOf(iterateHolder)).setValue(resto);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

        );


    }
}
