package com.teamspeaghetti.www.gifster.interiorapplication.presenters;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.ChatActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.GIFFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IAskForGIFS;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestHolder;
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
    Fragment fragment;
    Activity activity;
    String lastsearch;
    Retrofit retrofit;
    IRequestHolder requestInterface;
    Context context;
    public AskingGIFProcess(Activity _activity){
        this.activity=_activity;
        context = _activity;
        retrofit = new Retrofit.Builder().baseUrl(_activity.getResources().getString(R.string.giphyurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
    }
    public AskingGIFProcess(Fragment fragmentInstance){
        this.fragment=fragmentInstance;
        context = fragmentInstance.getContext();
        retrofit = new Retrofit.Builder().baseUrl(fragmentInstance.getResources().getString(R.string.giphyurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
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

        makeRequestToGetGifs(tempList,limit,lastsearch);
    }

    public void makeRequestToGetGifs(List<Gifs> temp,int limit,String searchWords){
        final List<Gifs> tempList = new ArrayList<>();
        tempList.addAll(temp);
        Call<ResponseBody> call = requestInterface.makesearch(searchWords,context.getString(R.string.giphy_key),String.valueOf(limit));
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
                    if(fragment !=null) {
                        if (fragment instanceof GIFFragment)
                            ((GIFFragment) fragment).retrieveGIFs(tempList);
                    }
                    if(activity != null){
                        if(activity instanceof ChatActivity)
                            ((ChatActivity)activity).retrieveGIFs(tempList);
                    }
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
