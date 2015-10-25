package com.example.haasith.parse2.profile;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.haasith.parse2.user_session.CurrentSession;
import com.example.haasith.parse2.R;
import com.example.haasith.parse2.payment.cardActivity;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Profile extends AppCompatActivity implements ConfirmPaymentCommunicator
{

    private TextView mtutorUsername;
    private TextView tutorFirstname;
    TextView college;
    TextView degree;
    private Button confirmPayment;
    private String tutorFirst;
    private String tutorLast;
    private String tutorUsername;
    private String tutorId;
    String tutorCollege;
    String tutorDegree;
    double rating;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            tutorUsername = extras.getString("username");
            tutorId = extras.getString("tutorId");
            tutorFirst = extras.getString("firstname");
            tutorLast = extras.getString("lastname");
            rating = extras.getDouble("rating");
            tutorCollege = extras.getString("college");
            tutorDegree = extras.getString("degree");
        }


        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getBackground().setAlpha(300);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setTitle(tutorFirst + " " + tutorLast);
        collapsingToolbarLayout.setExpandedTitleColor(0xFFffffff);

        mtutorUsername = (TextView) findViewById(R.id.username);
        college = (TextView) findViewById(R.id.college);
        degree = (TextView) findViewById(R.id.degree);
        confirmPayment = (Button) findViewById(R.id.confirmpayment);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        ratingBar.setRating((float)rating);
        mtutorUsername.setText(tutorUsername);
        college.setText(tutorCollege);
        degree.setText(tutorDegree);

        confirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                ConfirmPaymentDialog myDialog = new ConfirmPaymentDialog();
                myDialog.show(fragmentManager, "Confirm Payment");
            }
        });



    }

    public void openpayment(View view) {
        Intent opencardpayment = new Intent(this, cardActivity.class);
        startActivity(opencardpayment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onDialogPayment() {

        final ParseObject session = new ParseObject("TutorSession");
        session.put("clientId", ParseUser.getCurrentUser().getObjectId());
        session.put("client",ParseUser.getCurrentUser());
        session.put("tutorId", tutorId);
        session.put("userRelease", false);
        session.put("tutorRelease", false);
        session.put("isCompleted",false);
        session.put("tutorAccepted",false);
        session.put("tutorRejected", false);
        session.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Intent intent = new Intent(getApplicationContext(), CurrentSession.class);
                intent.putExtra("clientId", ParseUser.getCurrentUser().getObjectId());
                intent.putExtra("tutorId", tutorId);
                intent.putExtra("sessionId",session.getObjectId());
                startActivity(intent);
            }
        });



    }
}
