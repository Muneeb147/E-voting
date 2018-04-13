package com.example.zia.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {


    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;

    private EditText password;
    private TextView show;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        firebaseAuth = FirebaseAuth.getInstance();
//
//        editTextEmail = (EditText) findViewById(R.id.em);
//        editTextPassword = (EditText) findViewById(R.id.pass);
//        buttonSignIn = (Button) findViewById(R.id.loginbutton);
//        progressDialog = new ProgressDialog(this);
//
//        buttonSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginuser();
//            }
//        });
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

//    private void loginuser(){
//
//        String email = editTextEmail.getText().toString().trim();
//        String password  = editTextPassword.getText().toString().trim();
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        progressDialog.setMessage("Signing In Please Wait...");
//        progressDialog.show();
//
//        firebaseAuth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        //if the task is successfull
//                        if(task.isSuccessful()){
//                            //start the profile activity
//                            finish();
//                           startActivity(new Intent(getApplicationContext(), Profile.class));
//                        }else{
//                            Toast.makeText(getApplicationContext(),"Please enter Valid credentials",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//    }
}
