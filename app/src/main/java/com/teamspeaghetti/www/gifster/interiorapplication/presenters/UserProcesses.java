package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import android.util.Log;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRegisterToServer;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IUserRequestHandler;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Salih on 21.05.2016.
 */
public class UserProcesses implements IUserRequestHandler{


    @Override
    public void sendRequest(String id,String latitude,String longitude) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.166:3000").addConverterFactory(GsonConverterFactory.create()).build();
        IRegisterToServer requestInterface =retrofit.create(IRegisterToServer.class);
        Call<ResponseBody> call = requestInterface.registerUser(id,latitude,longitude);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("response",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }
}
