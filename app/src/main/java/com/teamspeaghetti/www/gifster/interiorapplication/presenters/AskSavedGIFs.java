package com.teamspeaghetti.www.gifster.interiorapplication.presenters;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.ChatActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.FullScreenGIFsFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.KeyboardFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IDeleteGIF;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestHolder;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Salih on 20.05.2016.
 */
public class AskSavedGIFs implements IRetrieveGIFs,IDeleteGIF {
    Fragment _fragment_context;
    Activity _activity_context;
    Context _context;
    List<Gifs> tempList = new ArrayList<>();
    public AskSavedGIFs(){}
    public AskSavedGIFs(Fragment context){
        this._fragment_context=context;
        _context = context.getContext();
    }
    public AskSavedGIFs(Activity context){
        this._activity_context=context;
        _context = context;

    }

    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.getGIFS(Profile.getCurrentProfile().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("urlist");
                    for(int i=0;i<jsonArray.length();i++){
                        tempList.add(new Gifs(jsonArray.getString(i)));
                    }
                    if(_fragment_context!=null)
                        ((KeyboardFragment)_fragment_context).retrieveGIFs(tempList);
                    if(_activity_context!=null)
                            ((ChatActivity)_activity_context).retrieveGIFs(tempList);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void deleteGIF(String url, List<Gifs> gifsList, final int pos) {
        tempList.clear();
        tempList = gifsList;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.sendDeleteRequest(url,Profile.getCurrentProfile().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(!jsonObject.getBoolean("error")){
                        tempList.remove(pos);
                        if(_fragment_context!=null)
                        ((FullScreenGIFsFragment)_fragment_context).retrieveGIFs(tempList);
                    }else{
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
