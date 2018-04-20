package com.example.zia.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private EditText password;
    private TextView show;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        EditText et2 = (EditText) findViewById(R.id.email);
        EditText et3 = (EditText) findViewById(R.id.password);

        et2.setHintTextColor(Color.WHITE);
        et3.setHintTextColor(Color.WHITE);
//
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonSignIn = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });
        password = (EditText)findViewById(R.id.password);
        show = (TextView)findViewById(R.id.show);

        show.setVisibility(View.GONE);
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(password.getText().length()> 0){
                    show.setVisibility(View.VISIBLE);
                }

                else{
                    show.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show.getText() == "SHOW"){
                    show.setText("HIDE");
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    password.setSelection(password.length());

                }
                else{
                    show.setText("SHOW");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setSelection(password.length());

                }
            }
        });
    }

    private void loginuser(){

        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        if(email.toLowerCase().equals("admin")){

            email = "muneebzia09@gmail.com";
            password = "admin123";
            progressDialog.setMessage("Signing In As Admin...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //if the task is successfull
                            if(task.isSuccessful()){
                                //start the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), Adminprof.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"Please enter Valid credentials",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
        else {

            progressDialog.setMessage("Signing In Please Wait...");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //if the task is successfull
                            if (task.isSuccessful()) {
                                //start the profile activity


                                firebaseUser = firebaseAuth.getCurrentUser();
                                final String uid = firebaseUser.getUid();

                                databaseReference= FirebaseDatabase.getInstance().getReference();
                                databaseReference.addValueEventListener(new ValueEventListener(){
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String usertype = dataSnapshot.child("users").child(uid).child("usertype").getValue(String.class);
                                        if(usertype.toLowerCase().equals("voter")){
                                            Log.d("vot","yehan aya");
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), homepage.class));
                                        } else if(usertype.toLowerCase().equals("candidate")){
                                            Log.d("can","yehan aya");
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), candidate_profile.class));
                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter Valid credentials", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }
}
