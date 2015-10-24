package com.example.haasith.parse2.search_list;

import android.app.FragmentManager;
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
import android.widget.Toast;

import com.example.haasith.parse2.Profile;
import com.example.haasith.parse2.R;
import com.example.haasith.parse2.util.NavigationDrawerFramework;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FindTutor extends NavigationDrawerFramework {

    RecyclerView recyclerView;
    private TutorListRecyclerAdapter Adapter;
    LocationManager mLocationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_tutor);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Adapter = new TutorListRecyclerAdapter(FindTutor.this, new ArrayList<TutorListRecyclerInfo>());
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(FindTutor.this));


        Log.d("Test Parse Find Tutor", ParseUser.getCurrentUser().getUsername().toString());


        ParseGeoPoint userLocation = (ParseGeoPoint) ParseUser.getCurrentUser().get("location");
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNear("location", userLocation);
        query.findInBackground(new FindCallback<ParseUser>() {


            public void done(List<ParseUser> users, ParseException e) {
                if (e == null)
                {
                    Toast.makeText(FindTutor.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
                    Log.d("username", "Retrieved " + users.size() + " username");
                    for (int i = 0; i < users.size(); i++)
                    {
                        Adapter.addRow(new TutorListRecyclerInfo(users.get(i)));
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
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 0, mLocationListener);

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

        SearchManager searchManager = (SearchManager) FindTutor.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(FindTutor.this.getComponentName()));
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
