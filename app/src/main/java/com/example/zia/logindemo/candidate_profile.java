package com.example.zia.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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
    //view objects
    private TextView textViewUserEmail;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profile);
        final String uid;
        firebaseAuth = FirebaseAuth.getInstance();
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);



        FirebaseUser user = firebaseAuth.getCurrentUser();
        uid = user.getUid();





        databaseReference= FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("users").child(uid).child("name").getValue(String.class);
                String candi = dataSnapshot.child("users").child(uid).child("usertype").getValue(String.class);
                textViewUserEmail.setText("Welcome "+candi +"\n" + username +" your profile is under construction");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





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
