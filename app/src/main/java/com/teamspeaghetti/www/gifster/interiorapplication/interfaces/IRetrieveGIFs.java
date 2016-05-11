package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Salih on 11.05.2016.
 */
public interface IRetrieveGIFs {
    public void retrieveGIFs(List<Gifs> gifsList);
}
