package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import java.util.List;

/**
 * Created by Salih on 11.05.2016.
 */
public interface IAskForGIFS {
    public void getGIFS(String keywords, List<Gifs> list,boolean newsearch);
}
