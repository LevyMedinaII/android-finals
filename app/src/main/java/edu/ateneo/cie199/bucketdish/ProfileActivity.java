package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    EditText edtContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final BucketdishApplication app = (BucketdishApplication) getApplication();
        User currentUser = app.getCurrentUser();
        TextView txvName = (TextView) findViewById(R.id.txv_name);
        TextView txvEmail = (TextView) findViewById(R.id.txv_email);
        final TextView txvContact = (TextView) findViewById(R.id.txv_contact);
        TextView txvUsername = (TextView) findViewById(R.id.txv_username);
        edtContact = (EditText) findViewById(R.id.edt_contact);
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();



        txvName.setText(app.getCurrentUser().getFirstname()+" "+app.getCurrentUser().getLastname());
        txvEmail.setText(app.getCurrentUser().getEmail());
        if(app.getCurrentUser().getContactnumber().matches(""))
        {
            txvContact.setText("You haven't provided a contact number yet!");
            updateUI(2);
        }
        else
        {
            txvContact.setText(app.getCurrentUser().getContactnumber());
            updateUI(1);
        }

        txvUsername.setText(app.getCurrentUser().getUsername());

        CustomButton btnBack = (CustomButton) findViewById(R.id.btn_back);
        CustomButton btnContact = (CustomButton) findViewById(R.id.btn_contact);
        CustomButton btnInput = (CustomButton) findViewById(R.id.btn_input);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUI(3);
            }
        });
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.getCurrentUser().setContactnumber(edtContact.getText().toString());
                final FirebaseUser user = mAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference userRef = database.getReference("users/"+user.getUid());
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        {
                            rootRef.child("users").child(user.getUid()).child("contactnumber").setValue(edtContact.getText().toString());
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                txvContact.setText(edtContact.getText().toString());
                updateUI(1);

            }
        });
    }
    public void updateUI(int state)
    {
        if(state==1)//if there is contact number
        {
            findViewById(R.id.edt_contact).setVisibility(View.GONE);
            findViewById(R.id.btn_contact).setVisibility(View.GONE);
            findViewById(R.id.btn_input).setVisibility(View.GONE);
            findViewById(R.id.txv_contact).setVisibility(View.VISIBLE);
        }
        else if(state==2)//if there there is no contact number
        {
            findViewById(R.id.edt_contact).setVisibility(View.GONE);
            findViewById(R.id.btn_contact).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_input).setVisibility(View.GONE);
            findViewById(R.id.txv_contact).setVisibility(View.VISIBLE);
        }
        else if(state==3)//if they are editing contact number
        {
            findViewById(R.id.edt_contact).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_contact).setVisibility(View.GONE);
            findViewById(R.id.btn_input).setVisibility(View.VISIBLE);
            findViewById(R.id.txv_contact).setVisibility(View.GONE);
        }

    }
}
