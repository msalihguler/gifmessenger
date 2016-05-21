package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import android.content.Context;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.FullScreenGIFsFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.KeyboardFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IDeleteGIF;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IDeleteGIFRequest;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRESTRequestForGifs;
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
    KeyboardFragment _context;
    FullScreenGIFsFragment _context2;
    List<Gifs> tempList = new ArrayList<>();
    public AskSavedGIFs(){
    }
    public AskSavedGIFs(FullScreenGIFsFragment context){
        this._context2=context;
    }

    public AskSavedGIFs(KeyboardFragment context){
        this._context=context;
    }
    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.166:3000").addConverterFactory(GsonConverterFactory.create()).build();
        IRESTRequestForGifs requestInterface =retrofit.create(IRESTRequestForGifs.class);
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
                    _context.retrieveGIFs(tempList);
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.166:3000").addConverterFactory(GsonConverterFactory.create()).build();
        IDeleteGIFRequest requestInterface =retrofit.create(IDeleteGIFRequest.class);
        Call<ResponseBody> call = requestInterface.sendDeleteRequest(url,Profile.getCurrentProfile().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(!jsonObject.getBoolean("error")){
                        tempList.remove(pos);
                        _context2.retrieveGIFs(tempList);
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
