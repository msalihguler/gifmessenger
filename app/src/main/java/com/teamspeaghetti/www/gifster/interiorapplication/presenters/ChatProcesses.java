package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import android.content.Context;
import android.util.Log;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.MessageFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IChatMethods;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IChatRequests;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRegisterToServer;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Salih on 4.06.2016.
 */
public class ChatProcesses implements IChatMethods{
    MessageFragment _fragment;
    Context _context;
    public ChatProcesses(MessageFragment fragment, Context context){
        this._context=context;
        this._fragment=fragment;
    }
    @Override
    public void getMatches() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IChatRequests requestInterface =retrofit.create(IChatRequests.class);
        Call<ResponseBody> call = requestInterface.getMatches(Profile.getCurrentProfile().getId());
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
