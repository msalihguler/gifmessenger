package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Salih on 21.05.2016.
 */
public interface IRegisterToServer {
    @POST("/registeruser")
    Call<ResponseBody> registerUser(@Query("id")String userid,@Query("lat")String latitude,@Query("long")String longitude);
    @GET("/getpeople")
    Call<ResponseBody> getUsers(@Query("id")String userid);
    @GET("/sendlikestatus")
    Call<ResponseBody> sendLikeStatus(@Query("m_id")String my_id,@Query("o_id")String userid,@Query("type")String type);

}
