package edu.ateneo.cie199.bucketdish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class LoginActivity2 extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mUsernameField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private TextView mStatusTextView;
    private TextView mDetailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();


        mEmailField =(EditText) findViewById(R.id.edt_email);
        mPasswordField = (EditText) findViewById(R.id.edt_password);
        mUsernameField = (EditText) findViewById(R.id.edt_username);
        mFirstNameField = (EditText) findViewById(R.id.edt_firstname);
        mLastNameField = (EditText) findViewById(R.id.edt_lastname);
        mDetailTextView = (TextView)findViewById(R.id.txv_details);
        mStatusTextView = (TextView)findViewById(R.id.txv_status);


        // Buttons
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_logout).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_start).setOnClickListener(this);


    }
    @Override
    public void onStart() {
        super.onStart();


        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser, Boolean.FALSE);
    }
    private void createAccount(final String email, final String password, final String username, final String firstname, final String lastname) {
        Log.d(TAG, "createAccount:" + email);
        mAuth = FirebaseAuth.getInstance();
//        if (!validateForm()) {
//            return;
//        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, Boolean.TRUE);
                            writeNewUser(email, username, firstname, lastname);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, Boolean.TRUE);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        //writeNewUser(email, username, firstname, lastname);

        // [END create_user_with_email]
    }



    private void writeNewUser(String email, String username, String firstname, String lastname) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ArrayList newList = new ArrayList();
        User newUser = new User(email,username ,firstname, lastname, newList);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        mDatabase.child("users").child(userId).setValue(newUser);
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        mAuth = FirebaseAuth.getInstance();
//        if (!validateForm()) {
//            return;
//        }

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user, Boolean.FALSE);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null, Boolean.FALSE);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Log.d(TAG, "GG BOY");
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Log.d(TAG, "GG BOY");
        updateUI(null, Boolean.FALSE);
    }

    private void next() {
        Intent LoginActivityIntent = new Intent(LoginActivity2.this, FavoritesActivity.class);
        startActivity(LoginActivityIntent);
        finish();
    }


    private void updateUI(FirebaseUser user, Boolean create) {
        //hideProgressDialog();
        if (user != null) {
        mStatusTextView.setText("You're logged in");
        mDetailTextView.setText(user.getDisplayName());
            findViewById(R.id.edt_email).setVisibility(View.GONE);
            findViewById(R.id.edt_password).setVisibility(View.GONE);
            findViewById(R.id.btn_login).setVisibility(View.GONE);
            findViewById(R.id.btn_create).setVisibility(View.GONE);
            findViewById(R.id.btn_next).setVisibility(View.VISIBLE);
            findViewById(R.id.edt_lastname).setVisibility(View.GONE);
            findViewById(R.id.edt_firstname).setVisibility(View.GONE);
            findViewById(R.id.edt_username).setVisibility(View.GONE);
            findViewById(R.id.btn_start).setVisibility(View.GONE);
            findViewById(R.id.btn_next).setVisibility(View.VISIBLE);


        } else if (user == null && create==Boolean.FALSE) {
            //mStatusTextView.setText(R.string.signed_o);
            //mDetailTextView.setText(null);
            findViewById(R.id.edt_email).setVisibility(View.VISIBLE);
            findViewById(R.id.edt_password).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_login).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_create).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_logout).setVisibility(View.GONE);
            findViewById(R.id.edt_lastname).setVisibility(View.GONE);
            findViewById(R.id.edt_firstname).setVisibility(View.GONE);
            findViewById(R.id.edt_username).setVisibility(View.GONE);
            findViewById(R.id.btn_start).setVisibility(View.GONE);
            findViewById(R.id.txv_details).setVisibility(View.GONE);
            findViewById(R.id.txv_status).setVisibility(View.GONE);
            findViewById(R.id.btn_next).setVisibility(View.GONE);

        }
        else if (user == null && create==Boolean.TRUE )
        {
            findViewById(R.id.edt_email).setVisibility(View.VISIBLE);
            findViewById(R.id.edt_password).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_next).setVisibility(View.GONE);
            findViewById(R.id.btn_login).setVisibility(View.GONE);
            findViewById(R.id.btn_create).setVisibility(View.GONE);
            findViewById(R.id.btn_logout).setVisibility(View.GONE);
            findViewById(R.id.edt_lastname).setVisibility(View.VISIBLE);
            findViewById(R.id.edt_firstname).setVisibility(View.VISIBLE);
            findViewById(R.id.edt_username).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_start).setVisibility(View.VISIBLE);
            findViewById(R.id.txv_details).setVisibility(View.GONE);
            findViewById(R.id.txv_status).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_create) {
            updateUI(null, Boolean.TRUE);
        } else if (i == R.id.btn_login) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
         else if (i == R.id.btn_logout) {
            signOut();
        }
        else if (i == R.id.btn_next) {
            next();
        }
        else if (i == R.id.btn_start) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(), mUsernameField.getText().toString(), mFirstNameField.getText().toString(), mLastNameField.getText().toString());
        }
//      else if (i == R.id.verify_email_button) {
//            sendEmailVerification();
//        }
    }
}
