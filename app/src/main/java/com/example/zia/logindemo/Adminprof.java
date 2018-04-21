package com.example.zia.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Adminprof extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView result,create_elec,edit_cand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminprof);
        result = (TextView) findViewById(R.id.results);
        edit_cand = (TextView) findViewById(R.id.candidate);
        create_elec = (TextView) findViewById(R.id.create);

        edit_cand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adminprof.this, deletecandidate.class);
                startActivity(intent);
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
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}

