package com.example.haasith.parse2.util;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.example.haasith.parse2.find_tutor.FindTutor;
import com.moxtra.sdk.MXAccountManager;
import com.moxtra.sdk.MXSDKException;
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
public class App extends MultiDexApplication {

    public ParseInstallation installation;

    private static final String TAG = "MoxieChatApplication";

    @Override
    public void onCreate()  {
        super.onCreate();

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "2tDbAvTa8jgp2Gg6S2vhAN73z7ahKhjuUg42ZPOu", "ikqObHQT7Gz9SrlU6yXY34WokA3E7odARqqrqOi3");

        try {
            ParseInstallation.getCurrentInstallation().save();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try{
            MXAccountManager.createInstance(this, "ovKEfVA_SQQ", "pODySaTS3Xs", true);
        } catch (MXSDKException.InvalidParameter invalidParameter) {
            Log.e(TAG, "Error when creating MXAccountManager instance.", invalidParameter);
        }


    }
}
