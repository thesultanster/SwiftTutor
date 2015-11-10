package com.example.haasith.parse2.booking;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haasith.parse2.R;
import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Text;


/*
    The booking activity is used by the client to book a tutor.
    The client specifies between scheduled or on-demand meeting
    The client specifies the type of order ( Homework / Midterm Review / Crash Course )
    The client specifies payment type.
 */

public class Booking extends AppCompatActivity {

    // Map Variables
    private NestedScrollView mScrollView;
    private GoogleMap map;

    // Tip Card Variables
    private LinearLayout tipCard;
    private TextView okText;

    // GigCard Variables
    private CheckBox homework;
    private CheckBox test;
    private CheckBox crash;
    private TextView homeworkPrice;
    private TextView testPrice;
    private TextView crashPrice;
    private TextView total;
    private int tutorHomework;
    private int tutorTest;
    private int tutorCrash;
    private int sum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        GetExtras();
        InflateVariables();
        SetClickListeners();
    }

    private void InflateVariables() {

        // Map Variables
        map = ((BookingMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        ((BookingMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new BookingMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        // Tip Card Variables
        okText = (TextView) findViewById(R.id.okText);
        tipCard = (LinearLayout) findViewById(R.id.tipCard);

        // GigCard Variables
        homework = (CheckBox) findViewById(R.id.homeworkAssignment);
        test = (CheckBox) findViewById(R.id.testReview);
        crash = (CheckBox) findViewById(R.id.crashCourse);
        homeworkPrice = (TextView) findViewById(R.id.homeworkPrice);
        testPrice = (TextView) findViewById(R.id.testReviewPrice);
        crashPrice = (TextView) findViewById(R.id.crashCoursePrice);
        total = (TextView) findViewById(R.id.total);
        homeworkPrice.setText("$" + tutorHomework);
        testPrice.setText("$" + tutorTest);
        crashPrice.setText("$" + tutorCrash);
        total.setText("$" + sum);

    }

    // Set Click Listeners
    private void SetClickListeners() {

        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipCard.setVisibility(View.GONE);
            }
        });

        homework.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTotalText(isChecked, tutorHomework);
            }
        });

        test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTotalText(isChecked, tutorTest);
            }
        });

        crash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setTotalText(isChecked, tutorCrash);
            }
        });

    }


    // Used to get Extras from previous Activity
    // TODO: Convert to shared preferences
    private void GetExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tutorHomework = extras.getInt("tutorHomework");
            tutorTest = extras.getInt("tutorTest");
            tutorCrash = extras.getInt("tutorCrash");
        }
    }


    // Used to calculate the Total Sum and Display on UI
    // Called everytime checkbox is checked / unchecked
    private void setTotalText(boolean isChecked, int price) {
        if (isChecked) { sum += price; } else { sum -= price; }
        total.setText("$" + sum);
    }

}
