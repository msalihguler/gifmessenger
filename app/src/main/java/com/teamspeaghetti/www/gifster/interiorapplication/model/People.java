package com.teamspeaghetti.www.gifster.interiorapplication.model;

/**
 * Created by msalihguler on 22.05.2016.
 */
public class People {
    public String id;
    public String likes;
    public String dislikes;
    public String name;
    public String profile_url;
    public String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }
    public People() {
    }
    public People(String id) {
        setId(id);
    }

    public People(String id, String likes, String location) {
        this.id = id;
        this.likes = likes;
        this.location = location;
    }

    public People(String id, String likes, String location, String dislikes) {
        this.id = id;
        this.likes = likes;
        this.location = location;
        this.dislikes = dislikes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}