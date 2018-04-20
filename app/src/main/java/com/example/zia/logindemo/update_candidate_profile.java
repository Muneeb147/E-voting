package com.example.zia.logindemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update_candidate_profile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText update_tagline;
    private EditText update_manifesto;
    private Spinner update_position;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_candidate_profile);

        EditText et2 = (EditText) findViewById(R.id.update_manifesto);
        EditText et3 = (EditText) findViewById(R.id.update_tagline);


        et2.setHintTextColor(Color.WHITE);
        et3.setHintTextColor(Color.WHITE);

        final String uid;
        update_tagline = (EditText) findViewById(R.id.update_tagline);
        update_manifesto =  (EditText) findViewById(R.id.update_manifesto);
        update_position = (Spinner) findViewById(R.id.update_position);
        submit = (Button)  findViewById(R.id.submit);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference =  FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String tagline = update_tagline.getText().toString();
                        String manifesto = update_manifesto.getText().toString();
                        String position = update_position.getSelectedItem().toString();

                        databaseReference.child("users").child(uid).child("tagline").setValue(tagline);
                        databaseReference.child("users").child(uid).child("agenda").setValue(manifesto);
                        databaseReference.child("users").child(uid).child("position").setValue(position);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}