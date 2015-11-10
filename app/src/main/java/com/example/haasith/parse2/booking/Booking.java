package com.example.haasith.parse2.booking;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.haasith.parse2.R;
import com.google.android.gms.maps.GoogleMap;


/*
    The booking activity is used by the client to book a tutor.
    The client specifies between scheduled or on-demand meeting
    The client specifies the type of order ( Homework / Midterm Review / Crash Course )
    The client specifies payment type.
 */

public class Booking extends AppCompatActivity {

    private NestedScrollView mScrollView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        map = ((BookingMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);

        ((BookingMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new BookingMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

    }
}
