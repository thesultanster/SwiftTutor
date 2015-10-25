package com.example.haasith.parse2.view_offers;

import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.find_tutor.TutorListRecyclerAdapter;
import com.example.haasith.parse2.find_tutor.TutorListRecyclerInfo;
import com.example.haasith.parse2.util.NavigationDrawerFramework;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewOffers extends NavigationDrawerFramework {

    RecyclerView recyclerView;
    private OfferRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_offers);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new OfferRecyclerAdapter(ViewOffers.this, new ArrayList<OfferRecyclerInfo>());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewOffers.this));

        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("TutorSession");
        innerQuery.whereEqualTo("tutorId", ParseUser.getCurrentUser().getObjectId());
        innerQuery.whereEqualTo("isCompleted",false);
        innerQuery.whereEqualTo("tutorRejected",false);
        innerQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> offers, ParseException e) {

                for (int i = 0; i < offers.size(); i++)
                {
                    adapter.addRow(new OfferRecyclerInfo( offers.get(i)));
                }

            }
        });


    }

}
