package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Salih on 21.05.2016.
 */
public interface IDeleteGIFRequest {
    @GET("/deletegif")
    Call<ResponseBody> sendDeleteRequest(@Query("url")String query, @Query("id") String apikey);
}
