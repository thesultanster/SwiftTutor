package com.example.haasith.parse2.search_list;

import android.location.Location;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class TutorListRecyclerInfo {

    ParseObject user;
    public TutorListRecyclerInfo(ParseObject user)
    {
        super();
        this.user = user;
    }
    public String getUsername()
    {
        return user.get("username").toString();
    }
    public String getLowestPrice()
    {
        return user.get("Homework").toString();
    }
    public String getFirstName()
    {
        return user.get("firstname").toString();
    }
    public String getLastName()
    {
        return user.get("lastname").toString();
    }
    public String getParseObjectId()
    {
        return user.getObjectId();
    }

    public float getDistance(){

        Location locationA = new Location("point A");

        locationA.setLatitude(ParseUser.getCurrentUser().getParseGeoPoint("location").getLatitude());
        locationA.setLongitude(ParseUser.getCurrentUser().getParseGeoPoint("location").getLongitude());

        Location locationB = new Location("point B");

        locationB.setLatitude(user.getParseGeoPoint("location").getLatitude());
        locationB.setLongitude(user.getParseGeoPoint("location").getLongitude());

        float distance = locationA.distanceTo(locationB);

        return distance;

    }

}
