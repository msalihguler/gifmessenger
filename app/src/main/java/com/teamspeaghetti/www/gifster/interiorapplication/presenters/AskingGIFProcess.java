package com.teamspeaghetti.www.gifster.interiorapplication.presenters;


import android.util.Log;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.GIFFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IAskForGIFS;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestInterface;
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
 * Created by Salih on 11.05.2016.
 */
public class AskingGIFProcess implements IAskForGIFS {
    GIFFragment mainActivity;
    String lastsearch;
    public AskingGIFProcess(GIFFragment activityInstance){
        this.mainActivity=activityInstance;
    }
    @Override
    public void getGIFS(String keywords, final List<Gifs> list,boolean newsearch) {
        final List<Gifs> tempList;
        int limit = 0;
        if(newsearch){
            tempList = new ArrayList<Gifs>();
            limit=10;
            lastsearch=keywords;
        }else{
            tempList = new ArrayList<Gifs>();
            limit = list.size()+10;
        }

        makeRequestToGetGifs(tempList,limit);
    }

    public void makeRequestToGetGifs(List<Gifs> temp,int limit){
        final List<Gifs> tempList = temp;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mainActivity.getResources().getString(R.string.giphyurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestInterface requestInterface =retrofit.create(IRequestInterface.class);
        Call<ResponseBody> call = requestInterface.makesearch(lastsearch,mainActivity.getString(R.string.giphy_key),String.valueOf(limit));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject tempObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = tempObject.getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        String url = (String)((JSONObject)((JSONObject)((JSONObject)jsonArray.get(i)).get("images")).get("fixed_width_downsampled")).get("url");
                        tempList.add(new Gifs(url));
                    }
                    mainActivity.retrieveGIFs(tempList);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
