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
import com.example.haasith.parse2.search_list.FindTutor;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

//com.facebook.FacebookSdk

public class LoginActivity extends Activity {

    private EditText usernameView;
    private EditText passwordView;

    @Override
    public void onCreate(Bundle savedInstanceState){
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
                            Log.d("Login Status","Fail");
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Log.d("Login Status","Success");
                            Intent intent = new Intent(LoginActivity.this, FindTutor.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
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
}




