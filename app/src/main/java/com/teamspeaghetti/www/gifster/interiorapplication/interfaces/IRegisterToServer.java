package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Salih on 21.05.2016.
 */
public interface IRegisterToServer {
    @POST("/registeruser")
    Call<ResponseBody> registerUser(@Query("id")String userid);
}
