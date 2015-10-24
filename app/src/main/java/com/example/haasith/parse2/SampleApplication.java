package com.example.haasith.parse2;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by Haasith on 9/25/2015.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate(){
        Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_id));
    }
}
