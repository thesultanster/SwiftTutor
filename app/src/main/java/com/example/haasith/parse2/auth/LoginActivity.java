package com.example.haasith.parse2.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.find_tutor.FindTutor;
import com.example.haasith.parse2.ApplicationData;
import com.example.haasith.parse2.stripe_connect.StripeApp;
import com.example.haasith.parse2.stripe_connect.StripeButton;
import com.example.haasith.parse2.stripe_connect.StripeConnectListener;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.stripe.Stripe;

//com.facebook.FacebookSdk

public class LoginActivity extends Activity  {

    private EditText usernameView;
    private EditText passwordView;
    private static final String TAG = "MoxieChatApplication";

    StripeApp mApp;
    StripeButton mStripeButton;
    private TextView tvSummary;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tvSummary = (TextView) findViewById(R.id.tvSummary);

        mApp = new StripeApp(this, "TestGuy", ApplicationData.CLIENT_ID,
                ApplicationData.SECRET_KEY, ApplicationData.CALLBACK_URL);

        mStripeButton = (StripeButton) findViewById(R.id.btnStripeConnect);
        mStripeButton.setStripeApp(mApp);
        mStripeButton.addStripeConnectListener(new StripeConnectListener() {

            @Override
            public void onConnected() {
                tvSummary.setText("Connected as " + mApp.getAccessToken());

                /*
                URL url = new URL("https://api.stripe.com/v1/charges");
                String urlParameters = "code=" + code
                        + "&client_secret=" + mSecretKey
                        + "&grant_type=authorization_code";
                AppLog.i(TAG, "getAccessToken", "Getting access token with code:" + code);
                AppLog.i(TAG, "getAccessToken", "Opening URL " + url.toString() + "?" + urlParameters);

                String response = StripeUtils.executePost(TOKEN_URL, urlParameters);
                JSONObject obj = new JSONObject(response);

                AppLog.i(TAG, "getAccessToken", "String data[access_token]:			" + obj.getString("access_token"));
                AppLog.i(TAG, "getAccessToken", "String data[livemode]:				" + obj.getBoolean("livemode"));
                AppLog.i(TAG, "getAccessToken", "String data[refresh_token]:			" + obj.getString("refresh_token"));
                AppLog.i(TAG, "getAccessToken", "String data[token_type]:			" + obj.getString("token_type"));
                AppLog.i(TAG, "getAccessToken", "String data[stripe_publishable_key]: " + obj.getString("stripe_publishable_key"));
                AppLog.i(TAG, "getAccessToken", "String data[stripe_user_id]:		" + obj.getString("stripe_user_id"));
                AppLog.i(TAG, "getAccessToken", "String data[scope]:					" + obj.getString("scope"));
                */

            }

            @Override
            public void onDisconnected() {
                tvSummary.setText("Disconnected");
            }

            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
            }

        });


        Stripe.apiKey = mApp.getAccessToken();




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

                        }
                    }
                });
            }
        });
    }


    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(resultCode) {
            case StripeApp.RESULT_CONNECTED:
                tvSummary.setText("Connected as " + mApp.getAccessToken());
                break;
            case StripeApp.RESULT_ERROR:
                String error_description = data.getStringExtra("error_description");
                Toast.makeText(LoginActivity.this, error_description, Toast.LENGTH_SHORT).show();
                break;
        }

    }


}




