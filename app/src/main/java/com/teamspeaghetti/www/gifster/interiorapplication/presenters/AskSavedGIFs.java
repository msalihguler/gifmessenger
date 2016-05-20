package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.KeyboardFragment;
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
public class AskSavedGIFs implements IRetrieveGIFs {
    KeyboardFragment _context;
    List<Gifs> tempList = new ArrayList<>();

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
}
