package com.example.haasith.parse2.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.find_tutor.FindTutor;
import com.moxtra.sdk.MXAccountManager;
import com.moxtra.sdk.MXChatManager;
import com.moxtra.sdk.MXSDKConfig;
import com.moxtra.sdk.MXSDKException;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

//com.facebook.FacebookSdk

public class LoginActivity extends Activity implements MXAccountManager.MXAccountLinkListener {

    private EditText usernameView;
    private EditText passwordView;
    private static final String TAG = "MoxieChatApplication";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);

        // Set up the submit button click handler
        findViewById(R.id.action_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Validate the log in data
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder("Enter a username");
                if (isEmpty(usernameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
                }
                if (isEmpty(passwordView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
                }
                validationErrorMessage.append(getResources().getString(R.string.error_end));

                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(LoginActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Logging in.  Please wait.");
                dlg.show();
                // Call the Parse login method
                ParseUser.logInInBackground(usernameView.getText().toString(), passwordView.getText()
                        .toString(), new LogInCallback() {

                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Log.d("Login Status", "Fail");
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Log.d("Login Status", "Success");
                            Intent intent = new Intent(LoginActivity.this, FindTutor.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Log.d("Login Status", "Success");

                            setupMoxtraUser(user.getString("username"), user.getString("lastname"), user.getUsername());
                        }
                    }
                });
            }
        });
    }

    public void setupMoxtraUser(String fname, String lname, String uniqueid) {
        final MXSDKConfig.MXUserInfo mxUserInfo = new MXSDKConfig.MXUserInfo(uniqueid, MXSDKConfig.MXUserIdentityType.IdentityUniqueId);
        final MXSDKConfig.MXProfileInfo mxProfileInfo = new MXSDKConfig.MXProfileInfo(fname, lname, "");
        MXAccountManager.getInstance().setupUser(mxUserInfo, mxProfileInfo, null, null, this);
    }


    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onLinkAccountDone(boolean b) {
        if (b) {
            Log.i(TAG, "Linked to moxtra successfully.");
            startMeet();
        } else {
            Toast.makeText(this, "Failed to setup moxtra user.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Failed to setup moxtra.");
        }

    }

    public void startMeet() {
        try {
            MXChatManager.getInstance().startMeet("Test meet", null, null, new MXChatManager.OnStartMeetListener() {
                @Override
                public void onStartMeetDone(String meetId, String meetURL) {
                    Log.d(TAG, "Meet Started" + meetId);
                }

                @Override
                public void onStartMeetFailed(int i, String s) {
                    Log.e(TAG, "onStartMeetFailed" + s);
                }
            });
        } catch (MXSDKException.Unauthorized unauthorized) {
            Log.e(TAG, "Error when start meet", unauthorized);
        } catch (MXSDKException.MeetIsInProgress meetIsInProgress) {
            Log.e(TAG, "Error when start meet", meetIsInProgress);
        }


    }

    public void joinMeet(String meetID) {

        try {
            MXChatManager.getInstance().joinMeet(meetID, ParseUser.getCurrentUser().getString("firstname"),
                    new MXChatManager.OnJoinMeetListener() {
                        @Override
                        public void onJoinMeetDone(String meetId, String meetUrl) {
                            Log.d(TAG, "Joined meet: " + meetId);
                        }

                        @Override
                        public void onJoinMeetFailed() {
                            Log.e(TAG, "Unable to join meet.");
                        }
                    });
        } catch (MXSDKException.MeetIsInProgress meetIsInProgress) {
            Log.e(TAG, "Error when join meet", meetIsInProgress);
        }
    }

}




