package com.example.haasith.parse2;

/**
 * Created by Haasith on 9/25/2015.
 */

import android.app.Application;

/**
 * Created by rufflez on 7/8/14.
 */
public class DispatchActivity extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        //ParseInstallation.getCurrentInstallation().saveInBackground();
        /*// Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            startActivity(new Intent(this, MyActivity.class));
        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, SignUpOrLoginActivity.class));
        }
        */
    }
}
