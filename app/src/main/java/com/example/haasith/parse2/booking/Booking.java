package com.example.haasith.parse2.booking;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        InflateVariables();
        SetClickListeners();

    }

    private void InflateVariables() {

        // Map Variables
        map = ((BookingMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        ((BookingMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new BookingMapFragment.OnTouchListener() { @Override public void onTouch() { mScrollView.requestDisallowInterceptTouchEvent(true); }  });

        // Tip Card Variables
        okText = (TextView) findViewById(R.id.okText);
        tipCard = (LinearLayout) findViewById(R.id.tipCard);




    }

    private void SetClickListeners(){

        okText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipCard.setVisibility(View.GONE);
            }
        });

    }
}
