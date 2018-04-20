package com.example.zia.logindemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class register_backup extends AppCompatActivity {

    Session session = null;
    private EditText Name;
    private EditText Email;
    private RadioButton Utype1,Utype2;
    private RadioButton radioButton6,radioButton7;
    private Spinner s1,s;
    private String school,gender;


    private TextView Info;
    private Button Register;
    private Button Login;
    private ProgressDialog pd;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    private void registerUser() {
        final String name = Name.getText().toString();
        //final String pass1 = Password.getText().toString();
        final String email = Email.getText().toString();
        String usertype = "";
        if(radioButton6.isChecked()){

            usertype = radioButton6.getText().toString();
        } else if (radioButton7.isChecked()){
            usertype = radioButton7.getText().toString();

        }




    if((TextUtils.isEmpty(name)) || (TextUtils.isEmpty(email)) || (TextUtils.isEmpty(usertype))){
        Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
        return;
    }
        pd.setMessage("Registering User");
        pd.show();
        final  String pass = "press@"+ email.split("@")[0];
        final String finalUsertype = usertype;
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");
                    session = Session.getDefaultInstance(props, new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("evotingapplication@gmail.com", "Motu@7256");
                        }
                    });

                    try{

                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("evotingapplication@gmail.com"));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject("Password for E-voting");
                        message.setContent("<h3> THANKS FOR JOINING E-Voting </h3> <p> Your password is </p> <b>" + pass + "</b>", "text/html; charset=utf-8");
                        Transport.send(message);
                    } catch(MessagingException e) {
                        e.printStackTrace();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    String id1 = firebaseAuth.getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    if(finalUsertype.equals("Candidate")){

                        Candidate can = new Candidate(name,email,finalUsertype, school,gender,"",0,"");
                        databaseReference.child("users").child(id1).setValue(can);
                    } else{
                        Voter voter = new Voter(name,email,finalUsertype, school,gender,true);
                        databaseReference.child("users").child(id1).setValue(voter);

                    }
                    pd.dismiss();
                    Toast.makeText(register_backup.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(register_backup.this, Login.class);
                    startActivity(intent);
//                    SmsManager manager = SmsManager.getDefault();
//                    manager.sendTextMessage("03234868883",null,pass+" is your password for E-voting",null,null);
                } else{
                    pd.dismiss();
                    Toast.makeText(register_backup.this," Not Registered Successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_backup);
        firebaseAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
        Email = (EditText) findViewById(R.id.email);
        Name = (EditText) findViewById(R.id.name);
        Register = (Button) findViewById(R.id.register);
        s = findViewById(R.id.dropdown1);
        s1 = findViewById(R.id.dropdown2);
        radioButton6 = (RadioButton)findViewById(R.id.radioButton8);
        radioButton7 = (RadioButton)findViewById(R.id.radioButton10);

        EditText et2 = (EditText) findViewById(R.id.name);
        EditText et3 = (EditText) findViewById(R.id.email);

        et2.setHintTextColor(Color.WHITE);
        et3.setHintTextColor(Color.WHITE);

        //Info.setText("Already Have an Account?");
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("DATA",Password.getText().toString());
                //validate(Name.getText().toString(),Password.getText().toString());
                // insert into database
                registerUser();
            }


        });

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                school = adapterView.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                gender = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
