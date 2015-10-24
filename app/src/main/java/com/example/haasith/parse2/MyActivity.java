package com.example.haasith.parse2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.haasith.parse2.search_list.FindTutor;
import com.parse.Parse;
import com.parse.ParseUser;

public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

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
        Intent openlist =new Intent(this, FindTutor.class);
        startActivity(openlist);
    }

    /*public void camera(View view){
        Intent opencamera =new Intent(this,camera.class);
        startActivity(opencamera);
    }*/





}



