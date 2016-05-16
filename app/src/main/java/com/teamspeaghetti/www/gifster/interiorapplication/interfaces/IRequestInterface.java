package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Salih on 11.05.2016.
 */
public interface IRequestInterface {
    @GET("/v1/gifs/search")
    Call<ResponseBody> makesearch(@Query("q")String query, @Query("api_key") String apikey,@Query("limit")String limit);
}
