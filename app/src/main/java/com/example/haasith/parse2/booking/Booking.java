package com.example.haasith.parse2.booking;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormatSymbols;

import com.example.haasith.parse2.R;
import com.google.android.gms.maps.GoogleMap;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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

    // Gig Card Variables
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

    // Date Time Card Variable
    LinearLayout timeCard;
    LinearLayout dateCard;
    TextView day;
    TextView monthYear;
    TextView dayOfWeek;
    Calendar c = Calendar.getInstance();
    int startYear = c.get(Calendar.YEAR);
    int startMonth = c.get(Calendar.MONTH);
    int startDay = c.get(Calendar.DAY_OF_MONTH);
    String week;


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

        // Date Time Card Variable
        timeCard = (LinearLayout) findViewById(R.id.timeCard);
        dateCard = (LinearLayout) findViewById(R.id.dateCard);
        day = (TextView) findViewById(R.id.day);
        dayOfWeek = (TextView) findViewById(R.id.dayOfWeek);
        monthYear = (TextView) findViewById(R.id.monthYear);

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

        timeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePicker();
            }
        });

        dateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePicker();
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
        if (isChecked) {
            sum += price;
        } else {
            sum -= price;
        }
        total.setText("$" + sum);
    }


    void ShowDatePicker(){
        DialogFragment dialogFragment = new StartDatePicker();
        dialogFragment.show(getSupportFragmentManager(), "start_date_picker");
    }

    void ShowTimePicker(){
        DialogFragment dialogFragment = new TimePicker();
        dialogFragment.show(getSupportFragmentManager(), "start_time_picker");
    }



    // DatePicker Class
    class StartDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            // Use the current date as the default date in the picker
            DatePickerDialog dialog = new DatePickerDialog(Booking.this, this, startYear, startMonth, startDay);
            return dialog;

        }

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // Do something with the date chosen by the user
            startYear = year;
            startMonth = monthOfYear;
            startDay = dayOfMonth;

            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            Date date = new Date(startYear, startMonth, startDay-1);
            week = simpledateformat.format(date);

            updateDateTimeCard();

            Log.d("date", String.valueOf(startDay));

        }
    }

    // DatePicker Class
    class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            TimePickerDialog dialog = new TimePickerDialog(Booking.this, this, startYear, startMonth, false);
            return dialog;

        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {

        }
    }

    void updateDateTimeCard(){
        day.setText(startDay+"");
        dayOfWeek.setText(week);
        monthYear.setText(new DateFormatSymbols().getMonths()[startMonth] + ", " + startYear);
    }

    void updateTimeCard(){
        day.setText(startDay+"");
        dayOfWeek.setText(week);
        monthYear.setText(new DateFormatSymbols().getMonths()[startMonth] + ", " + startYear);
    }


}


