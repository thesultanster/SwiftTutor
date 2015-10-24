package com.example.haasith.parse2;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TutorList extends Activity {

    SearchView sv;
    RecyclerView recyclerView;
    private TutorListRecyclerAdapter Adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);
        FragmentManager fragmentManager = getFragmentManager();
        recyclerView = (RecyclerView) findViewById(R.id.yourTimelineRecyclerView);
        sv = (SearchView) findViewById(R.id.searchView);
        /*sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                TutorListRecyclerAdapter.getFilter().filter(text);
                return false;
            }
        });*/
        Adapter = new TutorListRecyclerAdapter(TutorList.this, new ArrayList<TutorListRecyclerInfo>());
        recyclerView.setAdapter(Adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TutorList.this));
        //Adapter.addRow(new TutorListRecyclerInfo("name",new ParseObject("haasith2")));


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {


            public void done(List<ParseUser> users, ParseException e) {
                if (e == null)
                {
                    Toast.makeText(TutorList.this, String.valueOf(users.size()), Toast.LENGTH_SHORT).show();
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

    }

    public void profile(View view)
    {
        Intent openprofile = new Intent(this,profile.class);
        startActivity(openprofile);

    }
}
