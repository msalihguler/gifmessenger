package com.teamspeaghetti.www.gifster.interiorapplication.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Salih on 11.05.2016.
 */
public class Gifs {
    @SerializedName("url")
    String url;
    String id;
    String name;
    public Gifs(){

    }
    public Gifs(String url){
        setUrl(url);
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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



}
