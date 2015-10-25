package com.example.haasith.parse2.auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.haasith.parse2.MyActivity;
import com.example.haasith.parse2.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by haasith on 7/8/15.
 */
public class SignUpActivity extends Activity {

    private EditText usernameView;
    private EditText passwordView;
    private EditText passwordAgainView;
    private EditText firstnameView;
    private EditText lastnameView;
    private EditText emailView;
    private EditText subjectone;
    private EditText college;
    private EditText degree;
    private ImageView profilepic;
    private Button uploadimage;
    private Bitmap picture;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up the signup form.
        firstnameView = (EditText) findViewById(R.id.firstname);
        lastnameView = (EditText) findViewById(R.id.lastname);
        emailView = (EditText) findViewById(R.id.email);
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.password);
        passwordAgainView = (EditText) findViewById(R.id.passwordAgain);
        subjectone = (EditText) findViewById(R.id.subject1);
        college = (EditText) findViewById(R.id.college);
        degree = (EditText) findViewById(R.id.degree);
        profilepic = (ImageView) findViewById(R.id.profilepic);
        uploadimage = (Button) findViewById(R.id.uploadimage);
        //profilepic.setOnClickListener(this);
        //uploadimage.setOnClickListener(this);
        // Set up the submit button click handler
        findViewById(R.id.action_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Validate the sign up data
                boolean validationError = false;
                StringBuilder validationErrorMessage =
                        new StringBuilder(getResources().getString(R.string.error_intro));
                if (isEmpty(usernameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_username));
                }
                if (isEmpty(firstnameView)) {
                      validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_firstname));
                }
                if (isEmpty(emailView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_email));
                }
                if (isEmpty(lastnameView)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_lastname));
                }
                if (isEmpty(subjectone)) {
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_subjectone));
                }
                if (isEmpty(passwordView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_join));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
                }
                if (!isMatching(passwordView, passwordAgainView)) {
                    if (validationError) {
                        validationErrorMessage.append(getResources().getString(R.string.error_blank_password));
                    }
                    validationError = true;
                    validationErrorMessage.append(getResources().getString(
                            R.string.error_mismatched_passwords));
                }
                validationErrorMessage.append(getResources().getString(R.string.error_end));
                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // Set up a progress dialog
                final ProgressDialog dlg = new ProgressDialog(SignUpActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();

                // Set up a new Parse user
                ParseUser user = new ParseUser();
                user.setUsername(usernameView.getText().toString());
                user.setPassword(passwordView.getText().toString());
                user.setEmail(emailView.getText().toString());
                user.put("firstname", firstnameView.getText().toString());
                user.put("lastname", lastnameView.getText().toString());
                user.put("subject1", subjectone.getText().toString());
                user.put("college", college.getText().toString());
                user.put("degree", degree.getText().toString());
                user.put("rating", 4.0);


                user.signUpInBackground(new SignUpCallback() {

                    @Override
                    public void done(ParseException e) {
                        dlg.dismiss();
                        if (e != null) {
                            // Show the error message
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Start an intent for the dispatch activity
                            Intent intent = new Intent(SignUpActivity.this, MyActivity.class);
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

    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}

