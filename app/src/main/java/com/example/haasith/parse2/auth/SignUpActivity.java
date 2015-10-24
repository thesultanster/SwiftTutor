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
    private EditText subjecttwo;
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
        subjecttwo = (EditText) findViewById(R.id.subject2);
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
                user.put("subject2", subjecttwo.getText().toString());


                /*Drawable drawable = res.getDrawable(R.drawable.myimage);
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] data = stream.toByteArray();

                ParseFile file = new ParseFile("resume.txt", data);
                file.saveInBackground();


                ParseObject jobApplication = new ParseObject("Tickles");
                jobApplication.put("mediatype", "image");
                jobApplication.put("ticklecreatedid","Mayura");
                jobApplication.put("mediaurl", file);
                jobApplication.saveInBackground();*/

//Retrieve
                /*ParseFile applicantResume = (ParseFile)jobApplication.get("mediaurl");
                applicantResume.getDataInBackground(new GetDataCallback() {
                    public void done(byte[] data, ParseException e) {
                        if (e == null) {
                            // data has the bytes for the resume
                            ImageView image = (ImageView) findViewById(R.id.profilepic);
                            Bitmap bMap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            image.setImageBitmap(bMap);


                        } else {
                            // something went wrong
                        }
                    }
                });*/









                // Create the ParseFile
                /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usman);
                byteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();*/

                //ParseFile file = new ParseFile("pic.png", image);
                //user.put("imagefile", file);

                /*byte[] data = "Working at Parse is great!".getBytes();
                ParseFile file = new ParseFile("pic.png", data);
                ParseObject userpic = new ParseObject("picture");
                userpic.put("userpic", file);
                userpic.saveInBackground();*/
                /*ParseFile file = new ParseFile("picturePath", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();
                // Create a New Class called "ImageUpload" in Parse
                ParseObject imgupload = new ParseObject("Image");
                // Create a column named "ImageName" and set the string
                imgupload.put("Image", "picturePath");
                // Create a column named "ImageFile" and insert the image
                imgupload.put("ImageFile", file);*/
                // Call the Parse signup method
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

