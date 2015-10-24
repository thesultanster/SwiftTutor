package com.example.haasith.parse2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseUser;


public class SignUpOrLoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Parse.enableLocalDatastore(getApplicationContext());
        Parse.initialize(this, "2tDbAvTa8jgp2Gg6S2vhAN73z7ahKhjuUg42ZPOu", "ikqObHQT7Gz9SrlU6yXY34WokA3E7odARqqrqOi3");

        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            Log.d("Login Status", "Current User Exists");
            startActivity(new Intent(this, MyActivity.class));
        }

        // Log in button click handler
        ((Button) findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                startActivity(new Intent(SignUpOrLoginActivity.this, LoginActivity.class));
            }
        });

        // Sign up button click handler
        ((Button) findViewById(R.id.signup)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                startActivity(new Intent(SignUpOrLoginActivity.this, SignUpActivity.class));
            }
        });
    }



}
