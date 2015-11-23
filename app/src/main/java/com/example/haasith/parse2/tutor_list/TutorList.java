package com.example.haasith.parse2.tutor_list;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.haasith.parse2.profile.Profile;
import com.example.haasith.parse2.R;
import com.example.haasith.parse2.util.NavigationDrawerFramework;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TutorList extends NavigationDrawerFramework  {

    RecyclerView recyclerView;
    private TutorListRecyclerAdapter adapter;
    LocationManager mLocationManager;
    SearchView searchView;
    Button math;
    Button english;
    Button history;
    Button all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        getToolbar().setTitle("Find Tutors");

        handleIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new TutorListRecyclerAdapter(TutorList.this, new ArrayList<TutorListRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TutorList.this));

        math = (Button) findViewById(R.id.math);
        history = (Button) findViewById(R.id.history);
        english = (Button) findViewById(R.id.english);
        all = (Button) findViewById(R.id.all);

        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("math", true);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("history", true);
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("english", true);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter = new TutorListRecyclerAdapter(TutorList.this, new ArrayList<TutorListRecyclerInfo>());
                recyclerView.setAdapter(adapter);

                ParseGeoPoint userLocation = (ParseGeoPoint) ParseUser.getCurrentUser().get("location");
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereNear("location", userLocation);
                query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                query.findInBackground(new FindCallback<ParseUser>() {


                    public void done(List<ParseUser> users, ParseException e) {
                        if (e == null)
                        {

                            //ParseObject.pinAllInBackground(users);

                            Toast.makeText(TutorList.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                            Log.d("username", "Retrieved " + users.size() + " username");
                            for (int i = 0; i < users.size(); i++)
                            {
                                adapter.addRow(new TutorListRecyclerInfo(users.get(i)));
                            }
                        }
                        else
                        {
                            Log.d("score", "Error: " + e.getMessage());
                        }

                    }
                });

            }
        });


        ParseGeoPoint userLocation = (ParseGeoPoint) ParseUser.getCurrentUser().get("location");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNear("location", userLocation);
        query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<ParseUser>() {


            public void done(List<ParseUser> users, ParseException e) {
                if (e == null)
                {

                    //ParseObject.pinAllInBackground(users);

                    Toast.makeText(TutorList.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                    Log.d("username", "Retrieved " + users.size() + " username");
                    for (int i = 0; i < users.size(); i++)
                    {
                        adapter.addRow(new TutorListRecyclerInfo(users.get(i)));
                    }
                }
                else
                {
                    Log.d("score", "Error: " + e.getMessage());
                }

            }
        });


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // TODO: Change to 90000
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 0, mLocationListener);


    }

    public void profile(View view)
    {
        Intent openprofile = new Intent(this, Profile.class);
        startActivity(openprofile);

    }


    // Toolbar Overrides
    /***************************************************************************************************************/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_find_tutor, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) TutorList.this.getSystemService(Context.SEARCH_SERVICE);

        searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(TutorList.this.getComponentName()));
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String search = intent.getStringExtra(SearchManager.QUERY);

            adapter = new TutorListRecyclerAdapter(TutorList.this, new ArrayList<TutorListRecyclerInfo>());
            recyclerView.setAdapter(adapter);

            ParseGeoPoint userLocation = (ParseGeoPoint) ParseUser.getCurrentUser().get("location");

            ParseQuery<ParseUser> q1 = ParseUser.getQuery();

            if(!search.equals(""))
                q1.whereContains("subject1", search);


            //q1.or(queries);
            q1.whereNear("location", userLocation);
            q1.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
            q1.findInBackground(new FindCallback<ParseUser>() {


                public void done(List<ParseUser> users, ParseException e) {
                    if (e == null) {

                        //ParseObject.pinAllInBackground(users);

                        Toast.makeText(TutorList.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                        Log.d("username", "Retrieved " + users.size() + " username");
                        for (int i = 0; i < users.size(); i++) {
                            adapter.addRow(new TutorListRecyclerInfo(users.get(i)));
                        }
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }

                }
            });



        }
    }



    /***************************************************************************************************************/

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            ParseGeoPoint point = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
            ParseUser.getCurrentUser().put("location",point);
            ParseUser.getCurrentUser().saveInBackground();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

}
