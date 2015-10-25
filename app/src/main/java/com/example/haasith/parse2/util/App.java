package com.example.haasith.parse2.util;

import android.app.Application;

import com.example.haasith.parse2.find_tutor.FindTutor;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;

/**
 * Created by sultankhan on 10/14/15.
 */
public class App extends Application {

    public ParseInstallation installation;

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "2tDbAvTa8jgp2Gg6S2vhAN73z7ahKhjuUg42ZPOu", "ikqObHQT7Gz9SrlU6yXY34WokA3E7odARqqrqOi3");

        try {
            ParseInstallation.getCurrentInstallation().save();
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }
}
