package com.example.haasith.parse2.view_offers;

import android.location.Location;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class OfferRecyclerInfo {

    ParseUser user;
    ParseObject session;
    public OfferRecyclerInfo(ParseObject session)
    {
        super();
        this.session = session;
        this.user = (ParseUser) session.get("client");
    }
    public String getUsername()
    {
        return user.get("username").toString();
    }
    public String getPrice()
    {
        return user.get("Homework").toString();
    }
    public String getClientId()
    {
        return user.getObjectId();
    }
    public String getSessionId()
    {
        return session.getObjectId();
    }

    public void RejectOffer(){
        session.put("tutorRejected",true);
        session.saveInBackground();
    }

    public void AcceptOffer(){
        session.put("tutorAccepted",true);
        session.saveInBackground();
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
