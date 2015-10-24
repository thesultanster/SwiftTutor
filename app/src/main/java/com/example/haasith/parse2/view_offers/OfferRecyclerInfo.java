package com.example.haasith.parse2.view_offers;

import android.location.Location;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class OfferRecyclerInfo {

    ParseObject user;
    public OfferRecyclerInfo(ParseObject user)
    {
        super();
        this.user = user;
    }
    public String getUsername()
    {
        return user.get("username").toString();
    }
    public String getPrice()
    {
        return user.get("Homework").toString();
    }
    public String getParseObjectId()
    {
        return user.getObjectId();
    }

    public double getDistance(){

        Location locationA = new Location("point A");

        locationA.setLatitude(ParseUser.getCurrentUser().getParseGeoPoint("location").getLatitude());
        locationA.setLongitude(ParseUser.getCurrentUser().getParseGeoPoint("location").getLongitude());

        Location locationB = new Location("point B");

        locationB.setLatitude(user.getParseGeoPoint("location").getLatitude());
        locationB.setLongitude(user.getParseGeoPoint("location").getLongitude());

        double distance = locationA.distanceTo(locationB);

        // Convert meters to miles
        distance = distance*0.000621371;
        return round(distance,2);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
