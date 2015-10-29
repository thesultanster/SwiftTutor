package com.example.haasith.parse2.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.find_tutor.FindTutor;
import com.moxtra.sdk.MXAccountManager;
import com.moxtra.sdk.MXChatManager;
import com.moxtra.sdk.MXSDKConfig;
import com.moxtra.sdk.MXSDKException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class SignUpOrLoginActivity extends Activity implements MXAccountManager.MXAccountLinkListener {

    private static final String TAG = "MoxieChatApplication";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ParseInstallation.getCurrentInstallation().put("userId", ParseUser.getCurrentUser().getObjectId());
        //ParseInstallation.getCurrentInstallation().saveInBackground();

        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity

            setupMoxtraUser(ParseUser.getCurrentUser().getString("username"), ParseUser.getCurrentUser().getString("lastname"), ParseUser.getCurrentUser().getUsername());
            Log.d("Login Status", "Current User Exists");
            //startActivity(new Intent(this, FindTutor.class));
            //finish();
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

    public void setupMoxtraUser(String fname, String lname, String uniqueid) {
        final MXSDKConfig.MXUserInfo mxUserInfo = new MXSDKConfig.MXUserInfo(uniqueid, MXSDKConfig.MXUserIdentityType.IdentityUniqueId);
        final MXSDKConfig.MXProfileInfo mxProfileInfo = new MXSDKConfig.MXProfileInfo(fname, lname, "");
        MXAccountManager.getInstance().setupUser(mxUserInfo, mxProfileInfo, null, null, this);
    }





    @Override
    public void onLinkAccountDone(boolean b) {
        if (b) {
            Log.i(TAG, "Linked to moxtra successfully.");
            //startMeet();
        } else {
            Toast.makeText(this, "Failed to setup moxtra user.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to setup moxtra.");
        }

    }



}
