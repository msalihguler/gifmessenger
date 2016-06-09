package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IChatMethods;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    Fragment _fragment;
    Context _context;
    public ChatProcesses(){}
    public ChatProcesses(Fragment fragment, Context context){
        this._context=context;
        this._fragment=fragment;
    }
    @Override
    public void getMatches() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.getMatches(Profile.getCurrentProfile().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = new JSONArray(jsonObject.getString("matches"));
                    UserProcesses processes = new UserProcesses(_context,_fragment);
                    for(int i=0;i<jsonArray.length();i++){
                        processes.getInformation(jsonArray.getString(i));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

    @Override
    public void sendMessage(String otherID,String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.saveMessagetoServer(Profile.getCurrentProfile().getId(),otherID,
                url);
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

    @Override
    public void getMessages(String otherID) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.getMatches(Profile.getCurrentProfile().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

}
