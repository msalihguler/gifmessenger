package com.teamspeaghetti.www.gifster.interiorapplication.model;

/**
 * Created by msalihguler on 22.05.2016.
 */
public class FacebookProfile {
    public String id;
    public String name;
    public String profilepicurl;

    public FacebookProfile(String id, String name, String profilepicurl) {
        this.id = id;
        this.name = name;
        this.profilepicurl = profilepicurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilepicurl() {
        return profilepicurl;
    }

    public void setProfilepicurl(String profilepicurl) {
        this.profilepicurl = profilepicurl;
    }
}
