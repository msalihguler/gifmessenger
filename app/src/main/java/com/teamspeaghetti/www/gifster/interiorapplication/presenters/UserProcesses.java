package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.SearchPeopleFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IOtherPeopleInformationRetriever;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRegisterToServer;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IUserRequestHandler;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;

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
 * Created by Salih on 21.05.2016.
 */
public class UserProcesses implements IUserRequestHandler,IOtherPeopleInformationRetriever{
    Context _context;
    List<People> peopleList = new ArrayList<People>();
    SearchPeopleFragment _fragment;
    public UserProcesses(){}
    public UserProcesses(Context context){
        this._context=context;
    }
    public UserProcesses(Context context,SearchPeopleFragment fragment){
        this._context=context; this._fragment=fragment;
    }

    @Override
    public void sendRequest(String id,String latitude,String longitude) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
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

    @Override
    public void getPeople(String id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRegisterToServer requestInterface =retrofit.create(IRegisterToServer.class);
        Call<ResponseBody> call = requestInterface.getUsers(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String temp = jsonObject.getString("message");
                    JSONArray jsonObject1 = new JSONArray(temp);
                    String id = ((JSONObject)jsonObject1.get(0)).getString("userid");
                    peopleList.add(new People(id));
                    _fragment.createList(peopleList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

    @Override
    public void getInformation(final String id) {
        GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + id, null, HttpMethod.GET, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                try {
                    Log.e("lo",response.getRawResponse());
                    JSONObject object = new JSONObject(response.getRawResponse());
                    People people = new People();
                    people.setId(object.getString("id"));
                    people.setName(object.getString("name"));
                    people.setProfile_url(((JSONObject)((JSONObject)object.get("picture")).get("data")).getString("url"));
                    peopleList.add(people);
                    _fragment.createList(peopleList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,first_name,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
