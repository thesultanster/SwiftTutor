package com.example.haasith.parse2.current_session;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.tutor_list.TutorList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moxtra.sdk.MXChatManager;
import com.moxtra.sdk.MXSDKException;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class CurrentSession extends AppCompatActivity implements FinishUserSessionCommunicator, OnMapReadyCallback {

    Toolbar toolbar;
    Button finishSession;
    Button moxtra;
    Button findTutorButton;

    Button startChrono;
    Chronometer chronometer;
    Boolean timerStarted = false;
    long lastPause=0;

    String clientId;
    String tutorId;
    String sessionId;
    String sessionType;
    TextView state;
    TextView tipText;
    TextView moxtraState;
    LinearLayout meetingCard;
    LinearLayout nullMeetingCard;
    LinearLayout inPersonMeetingCard;
    ParseObject session;
    Handler mHandler;

    NestedScrollView mScrollView;
    Marker marker;
    GoogleMap mMap;
    Boolean moveCamera = true;


    private static final String TAG = "MoxieChatApplication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_session);

        GetSharedPreferences();
        InflateVariables();
        SetClickListeners();

/*
        switch (sessionType){
            case "inPerson":
                StartInPersonSession();
                break;
            default:
                // If No Session Exists
                if(sessionId.equals("NOT_DEFINED")){
                    StartNullSession();
                } else {
                    StartParseListenerThread();
                }
                break;

        }
        */

        // If No Session Exists
        if (sessionId.equals("NOT_DEFINED")) {
            StartNullSession();
        } else {
            StartParseListenerThread();
        }


        // If client
        if (ParseUser.getCurrentUser().getObjectId().equals(clientId)) {
            chronometer.setVisibility(View.GONE);
        }
        // If tutor
        else {
            UpdateClientMarker();
        }


    }

    void UpdateClientMarker() {
        // Get Client Location
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", clientId);
        query.findInBackground(new FindCallback<ParseUser>() {


            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    ParseGeoPoint pPoint = users.get(0).getParseGeoPoint("location");
                    LatLng loc = new LatLng(pPoint.getLatitude(), pPoint.getLongitude());
                    mMap.clear();
                    Marker mMarker = mMap.addMarker(new MarkerOptions().position(loc).draggable(false).title("Meet Client Here"));

                } else {
                    Log.d("Currsesh user loc", "Error: " + e.getMessage());
                }
            }


        });
    }

    //TODO: Delete this
    void StartInPersonSession() {

        inPersonMeetingCard.setVisibility(View.VISIBLE);
        meetingCard.setVisibility(View.GONE);
        nullMeetingCard.setVisibility(View.GONE);

    }

    void StartNullSession() {

        meetingCard.setVisibility(View.GONE);
        nullMeetingCard.setVisibility(View.VISIBLE);

        state.setText("Session ID Null");
        finishSession.setVisibility(View.INVISIBLE);

    }

    void GetSharedPreferences() {

        SharedPreferences prefs = getSharedPreferences("CurrentSessionDetails", MODE_PRIVATE);

        Log.d("SharedPref", "Retrived Objects");

        tutorId = prefs.getString("tutorId", "NOT_DEFINED");
        clientId = prefs.getString("clientId", "NOT_DEFINED");
        sessionId = prefs.getString("sessionId", "NOT_DEFINED");
        sessionType = prefs.getString("sessionType", "NOT_DEFINED");


        // TODO: Delete this
        Log.d("SharedPref tutorId", tutorId);
        Log.d("SharedPref clientId", clientId);
        Log.d("SharedPref sessionId", sessionId);
        Log.d("SharedPref sessionType", sessionType);

    }

    void InflateVariables() {
        finishSession = (Button) findViewById(R.id.finishButton);
        findTutorButton = (Button) findViewById(R.id.findTutorButton);
        startChrono = (Button) findViewById(R.id.startChrono);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long t = SystemClock.elapsedRealtime() - cArg.getBase();
                cArg.setText(DateFormat.format("kk:mm:ss", t));
            }
        });
        moxtra = (Button) findViewById(R.id.moxtraButton);
        tipText = (TextView) findViewById(R.id.tipText);
        state = (TextView) findViewById(R.id.state);
        moxtraState = (TextView) findViewById(R.id.moxtraState);
        meetingCard = (LinearLayout) findViewById(R.id.meetingCard);
        nullMeetingCard = (LinearLayout) findViewById(R.id.nullMeetingCard);
        mMap = ((CurrentSessionMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        ((CurrentSessionMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        mScrollView = (NestedScrollView) findViewById(R.id.scrollView);
        ((CurrentSessionMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new CurrentSessionMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

    }

    void SetClickListeners() {

        finishSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FinishUserSessionDialog myDialog = new FinishUserSessionDialog();
                myDialog.show(fragmentManager, "Please Rate Tutor");
            }
        });


        moxtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If no meeting, then make one
                if (session.getString("meetingId").equals("0")) {
                    StartMeeting();
                }
                // If meeting already exists, then join it
                else {
                    JoinMeeting(session.getString("meetingId"));
                }


                MXChatManager.getInstance().setOnMeetEndListener(new MXChatManager.OnEndMeetListener() {
                    @Override
                    public void onMeetEnded(String s) {
                        session.put("meetingId", "0");
                        session.saveInBackground();

                    }
                });


            }

        });

        findTutorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TutorList.class);
                startActivity(intent);
            }
        });

        startChrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (timerStarted){
                   lastPause = SystemClock.elapsedRealtime();
                   chronometer.stop();
               }
                else{

                   //chronometer.setBase(SystemClock.elapsedRealtime());
                   chronometer.setBase(chronometer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                   chronometer.start();
               }

            }
        });

    }

    void StartParseListenerThread() {

        meetingCard.setVisibility(View.VISIBLE);
        nullMeetingCard.setVisibility(View.GONE);

        UpdateState();

        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(8000);
                        mHandler.post(new Runnable() {

                            @Override
                            public void run() {
                                UpdateState();
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        }).start();


    }

    void UpdateState() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TutorSession");
        query.getInBackground(sessionId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    session = object;

                    // If Client
                    if (ParseUser.getCurrentUser().getObjectId().equals(clientId)) {

                        // Check Acceptance Status
                        if (session.get("tutorAccepted") == false) {
                            state.setText("Request Not Accepted");
                        } else {
                            state.setText("Connected to Tutor");
                        }

                        // Check Rejection Status
                        if (session.get("tutorRejected") == true) {
                            state.setText("Offer Rejected");
                            moxtra.setVisibility(View.GONE);
                        }

                        // If tutor finished session
                        if (session.get("tutorRelease") == true) {
                            state.setText("Tutor Left the Session");
                            moxtra.setVisibility(View.INVISIBLE);
                        }

                        if (session.get("sessionType").toString().equals("inPerson")) {

                        }

                        // Get Tutor Location
                        if (session.get("tutorLocation") != null) {

                            LatLng loc = new LatLng(session.getParseGeoPoint("tutorLocation").getLatitude(), session.getParseGeoPoint("tutorLocation").getLongitude());
                            mMap.clear();
                            Marker mMarker = mMap.addMarker(new MarkerOptions().position(loc).draggable(false).title("Tutor Location"));
                        }


                    }
                    // If Tutor
                    else {
                        state.setText("Connected to Client");

                        // If client finished session
                        if (session.get("clientRelease") == true) {
                            state.setText("Client Left the Session");
                        }


                        // If meeting ID is 0, that means no meeting exists
                        if (!session.getString("meetingId").equals("0")) {
                            moxtraState.setText("Please Join Meeting");
                            moxtra.setText("Join Meeting");
                        } else {
                            // something went wrong
                        }

                        UpdateClientMarker();

                    }

                }
            }

        });


    }


    void StartMeeting() {
        try {
            MXChatManager.getInstance().startMeet("Test meet", null, null, new MXChatManager.OnStartMeetListener() {
                @Override
                public void onStartMeetDone(String meetId, String meetURL) {
                    Log.d(TAG, "Meet Started" + meetId);

                    session.put("meetingId", meetId);
                    session.saveInBackground();

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

    public void JoinMeeting(String meetID) {

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
    public void onDialogFinish() {

        Log.d("sessionId", sessionId);

        SharedPreferences.Editor prefs = getSharedPreferences("CurrentSessionDetails", MODE_PRIVATE).edit();

        prefs.putString("tutorId", "NOT_DEFINED");
        prefs.putString("clientId", "NOT_DEFINED");
        prefs.putString("sessionId", "NOT_DEFINED");
        prefs.apply();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("TutorSession");
        query.getInBackground(sessionId, new GetCallback<ParseObject>() {
            public void done(ParseObject session, ParseException e) {
                if (e == null) {

                    // If Client
                    if (ParseUser.getCurrentUser().getObjectId().equals(clientId)) {
                        // Then Client finished session
                        session.put("clientRelease", true);
                    }
                    // If Tutor
                    else {
                        // Then Tutor finished session
                        session.put("tutorRelease", true);
                    }

                    // If both sides finished session
                    if (session.get("clientRelease") == session.get("tutorRelease")) {

                        // Session is completed
                        session.put("isCompleted", true);

                    }

                    session.saveInBackground();

                } else {
                    // something went wrong
                }
            }
        });


        Intent intent = new Intent(this, TutorList.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        finishSession.performClick();
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        mMap = map;

        GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                /*
                Marker mMarker = map.addMarker(new MarkerOptions().position(loc).draggable(true).title("Meet Here")
                        .snippet("Long press and drag to set location."));

                marker = mMarker;


                map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker mark) {
                        //Log.d("map", String.valueOf(marker.getPosition().latitude));
                        marker = mark;
                    }
                });


               */


                if (mMap != null && moveCamera) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                    moveCamera = false;
                }

                Log.d("inside map callback",ParseUser.getCurrentUser().getObjectId() + " " + clientId);

                // If client
                if (ParseUser.getCurrentUser().getObjectId().equals(clientId)) {
                    ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());
                    ParseUser.getCurrentUser().put("location",point);
                    ParseUser.getCurrentUser().saveInBackground();
                }
                // If tutor
                else {
                    ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

                    if(session!=null) {
                        session.put("tutorLocation", point);
                        session.saveInBackground();
                    }
                }


            }
        };


        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);

    }


    void StartTutorMap() {

    }


}
