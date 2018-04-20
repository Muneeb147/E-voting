package com.example.zia.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class candidate_profile extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private TextView candidate_name;
    private TextView candidate_tagline;
    private TextView candidate_position;
    private TextView candidate_batch;
    private TextView candidate_school;
    private TextView candidate_gender;
    private TextView candidate_manifesto;
    private ImageView update_profile;

    //view objects
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);
        final String uid;
        firebaseAuth = FirebaseAuth.getInstance();

        candidate_name = (TextView) findViewById(R.id.candidate_name);
        candidate_tagline = (TextView) findViewById(R.id.candidate_tagline);
        candidate_position = (TextView) findViewById(R.id.position);
        candidate_school = (TextView) findViewById(R.id.school);
        candidate_batch = (TextView) findViewById(R.id.batch);
        candidate_gender = (TextView) findViewById(R.id.gender);
        candidate_manifesto = (TextView) findViewById(R.id.Manifesto);

        update_profile = (ImageView) findViewById(R.id.update_profile);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();


        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("users").child(uid).child("name").getValue(String.class);
                String cand_tagline = dataSnapshot.child("users").child(uid).child("tagline").getValue(String.class);
                String cand_position = dataSnapshot.child("users").child(uid).child("position").getValue(String.class);
                String cand_gender = dataSnapshot.child("users").child(uid).child("gender").getValue(String.class);
                String cand_school = dataSnapshot.child("users").child(uid).child("school").getValue(String.class);
                String cand_batch = dataSnapshot.child("users").child(uid).child("batch").getValue(String.class);
                String cand_manifesto = dataSnapshot.child("users").child(uid).child("agenda").getValue(String.class);

                if(username == null){
                    candidate_name.setText("");
                }else{
                    candidate_name.setText(username);
                }
                if(cand_tagline == null){
                    candidate_tagline.setText("");
                }else{
                    candidate_tagline.setText(cand_tagline);
                }
                if(cand_gender== null){
                    candidate_gender.setText("");
                }else{
                    candidate_gender.setText("Gender: " + cand_gender);
                }
                if(cand_position== null){
                    candidate_position.setText("");
                }else{
                    candidate_position.setText("Position: " + cand_position);
                }
                if(cand_school == null){
                    candidate_school.setText("");
                }else{
                    candidate_school.setText("School: " + cand_school);
                }
                if(cand_batch == null){
                    candidate_batch.setText("");
                }else{
                    candidate_batch.setText("Batch: " + cand_batch);
                }
                if(cand_manifesto == null){
                    candidate_manifesto.setText("");
                }else{
                    candidate_manifesto.setText("Manifesto: " + cand_manifesto);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //button for updating candidate profile

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCandProf();
            }
        });

    }

    private void updateCandProf(){

        startActivity(new Intent(getApplicationContext(), update_candidate_profile.class));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.home){
            Intent i = new Intent(getApplicationContext(), homepage.class);
            startActivity(i);
        }
        else if (item.getItemId() == R.id.logout){
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
        else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
