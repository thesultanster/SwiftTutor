package com.example.haasith.parse2;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class TutorListRecyclerInfo {

    ParseObject user;
    public TutorListRecyclerInfo(ParseUser user)
    {
        super();
        this.user = user;
    }
    public String getUsername()
    {
        return user.get("username").toString();
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
}
