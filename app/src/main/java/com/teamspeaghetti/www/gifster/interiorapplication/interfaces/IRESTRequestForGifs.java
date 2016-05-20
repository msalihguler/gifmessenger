package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Salih on 20.05.2016.
 */
public interface IRESTRequestForGifs {
    @GET("/getgifs")
    Call<ResponseBody> getGIFS(@Query("id")String id);
}
