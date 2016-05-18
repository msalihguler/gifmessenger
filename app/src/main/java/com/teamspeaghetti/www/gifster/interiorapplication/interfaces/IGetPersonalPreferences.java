package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Salih on 18.05.2016.
 */
public interface IGetPersonalPreferences {
    @POST("/savegif")
    Call<ResponseBody> addToKeyBoard(@Query("gifurl") String url,@Query("id")String id);
}
