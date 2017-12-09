package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ListContentActivity extends AppCompatActivity {
    ArrayList<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_content);
        //ArrayList<Restaurant> data = new ArrayList<>();

        final Intent receivedQueries = getIntent();
        final String listName = receivedQueries.getStringExtra("listname");
        ListView listContent = (ListView) findViewById(R.id.lsv_content);
        Log.d("this is sasadsad"," "+listName);


        final ArrayAdapter<String> mAdapter;
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data);

        listContent.setAdapter(mAdapter);
        Query query = rootRef.child("RecommendationLists/"+listName+"/Restaurants");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("KEYYEYE",""+dataSnapshot.getChildren() );
//                    HashMap container = (HashMap) dataSnapshot.getValue();
//

                    for (DataSnapshot list : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"

                        Log.d("this is MINE"," "+list.getKey());

                        updateList(list.getKey());
                        mAdapter.notifyDataSetChanged();
                    }

                    // dataSnapshot is the "issue" node with all children with id 0
                    //data=  dataSnapshot.child("Restaurants").getValue(ArrayList.class);
                    //updateList(dataSnapshot.child("Restaurants").getValue(ArrayList.class));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        TextView name = (TextView)findViewById(R.id.txv_title);
        name.setText(listName);
        Button back = (Button) findViewById(R.id.btn_back);
        Button edit = (Button) findViewById(R.id.btn_edit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favoritesActivityIntent = new Intent(ListContentActivity.this, FavoriteListsActivity.class);
                startActivity(favoritesActivityIntent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent editActivityIntent = new Intent(ListContentActivity.this, EditListActivity.class);
                editActivityIntent.putExtra("listname", listName);
                startActivity(editActivityIntent);

                
            }
        });

    }
    public void updateList(String input)
    {
        data.add(input);
    }
}
