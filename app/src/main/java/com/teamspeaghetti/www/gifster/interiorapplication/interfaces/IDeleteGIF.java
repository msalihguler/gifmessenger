package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import android.content.Context;

import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import java.util.List;

/**
 * Created by Salih on 21.05.2016.
 */
public interface IDeleteGIF {
    public void deleteGIF(String url,List<Gifs> gifsList,int position);
}
