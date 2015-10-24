package com.example.haasith.parse2.util;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by sultankhan on 10/14/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "2tDbAvTa8jgp2Gg6S2vhAN73z7ahKhjuUg42ZPOu", "ikqObHQT7Gz9SrlU6yXY34WokA3E7odARqqrqOi3");
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
