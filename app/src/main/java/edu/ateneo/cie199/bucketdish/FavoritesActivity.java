//package edu.ateneo.cie199.bucketdish;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class FavoritesActivity extends AppCompatActivity implements View.OnClickListener {
//
//    EditText listName;
//    EditText itemName;
//    Button add;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorites);
//        // Write a message to the database
//        listName = (EditText) findViewById(R.id.edt_listname);
//        itemName = (EditText) findViewById(R.id.edt_item);
//        add = (Button) findViewById(R.id.btn_addtolist);
//
//        findViewById(R.id.btn_addtolist).setOnClickListener(this);
//
//    }
//
//    private void doneRestaurant( String id, String uniqueId){
//        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        rootRef.child("users").child(uniqueId).push().setValue(id);
//    }
//
//    private void addItem(final String listName,final String itemName){
//        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//// ...
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = mAuth.getCurrentUser();
//        final String userId= user.getUid();
//        rootRef.child("lists").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                if (snapshot.hasChild(listName)) {
//                    Log.d("OH NO","MERON NA BOY");
//
//                    String key =rootRef.child("lists").child(listName).push().getKey();
//                    Log.d("This is User Key", key);
//                    rootRef.child("lists").child(listName).child(key).setValue(itemName);
//                    rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
//                    //doneRestaurant(itemName, key);
//
//                }
//                else
//                {
//                    String key =rootRef.child("lists").child(listName).push().getKey();
//                    rootRef.child("lists").child(listName).child(key).setValue(itemName);
//                    rootRef.child("users").child(userId).child("finishedList").push().setValue(itemName);
//                    //doneRestaurant(itemName, key);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        if(i==R.id.btn_addtolist)
//        {
//            addItem(listName.getText().toString(), itemName.getText().toString());
//        }
//    }
//}
