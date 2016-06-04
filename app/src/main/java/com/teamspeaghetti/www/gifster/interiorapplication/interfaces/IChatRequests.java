package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Salih on 4.06.2016.
 */
public interface IChatRequests {
    @GET("/getmatches")
    Call<ResponseBody> getMatches(@Query("id")String userid);
}
