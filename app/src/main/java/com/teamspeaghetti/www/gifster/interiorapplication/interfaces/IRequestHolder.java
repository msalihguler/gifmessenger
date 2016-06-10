package com.teamspeaghetti.www.gifster.interiorapplication.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Salih on 9.06.2016.
 */
public interface IRequestHolder {
    /*
    *       PERSON REQUESTS
    * */
    @GET("/getmatches")
    Call<ResponseBody> getMatches(@Query("id")String userid);
    @POST("/registeruser")
    Call<ResponseBody> registerUser(@Query("id")String userid,@Query("lat")String latitude,@Query("long")String longitude,@Query("token")String token);
    @GET("/getpeople")
    Call<ResponseBody> getUsers(@Query("id")String userid);
    @GET("/sendlikestatus")
    Call<ResponseBody> sendLikeStatus(@Query("m_id")String my_id,@Query("o_id")String userid,@Query("type")String type);
    /*
    *       GIF REQUESTS
    * */
    @GET("/v1/gifs/search")
    Call<ResponseBody> makesearch(@Query("q")String query, @Query("api_key") String apikey,@Query("limit")String limit);
    @GET("/deletegif")
    Call<ResponseBody> sendDeleteRequest(@Query("url")String query, @Query("id") String apikey);
    @POST("/savegif")
    Call<ResponseBody> addToKeyBoard(@Query("gifurl") String url,@Query("id")String id);
    @GET("/getgifs")
    Call<ResponseBody> getGIFS(@Query("id")String id);
    /*
    *       CHAT REQUESTS
    * */
    @GET("/sendmessage")
    Call<ResponseBody> saveMessagetoServer(@Query("m_id")String m_id,@Query("o_id")String o_id,@Query("message")String msg);
    @GET("/getmessage")
    Call<ResponseBody> getMessages(@Query("m_id")String m_id,@Query("o_id")String o_id);
}
