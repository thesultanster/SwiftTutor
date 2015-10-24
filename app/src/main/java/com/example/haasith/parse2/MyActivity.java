package com.example.haasith.parse2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseUser;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Parse.initialize(this, "2tDbAvTa8jgp2Gg6S2vhAN73z7ahKhjuUg42ZPOu", "ikqObHQT7Gz9SrlU6yXY34WokA3E7odARqqrqOi3");
        Log.d("Test Parse", ParseUser.getCurrentUser().getUsername().toString());
    }
        /*ParseQuery<ParseObject> query2 = ParseQuery.getQuery("username");
        query.getInBackground("xWMyZ4YEGZ", new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                } else {
                    // something went wrong
                }
            }
        });

    }*/

    public void list(View view){
        Intent openlist =new Intent(this,TutorList.class);
        startActivity(openlist);
    }

    /*public void camera(View view){
        Intent opencamera =new Intent(this,camera.class);
        startActivity(opencamera);
    }*/





}



