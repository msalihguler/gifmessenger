package com.teamspeaghetti.www.gifster.interiorapplication.presenters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.MessageFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.RevealedProfilesFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.SearchPeopleFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IOtherPeopleInformationRetriever;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestHolder;
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
    Fragment _fragment;
    Retrofit retrofit;
    IRequestHolder requestInterface;
    public UserProcesses(){}
    public UserProcesses(Context context){
        this._context=context;
    }
    public UserProcesses(Context context,Fragment fragment){
        this._context=context; this._fragment=fragment;
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
    }

    @Override
    public void sendRequest(String id,String latitude,String longitude,String token) {
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.registerUser(id,latitude,longitude,token);
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
        Call<ResponseBody> call = requestInterface.getUsers(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String temp = jsonObject.getString("message");
                    JSONArray jsonObject1 = new JSONArray(temp);
                    for(int i=0;i<jsonObject1.length();i++) {
                        String id = ((JSONObject) jsonObject1.get(i)).getString("userid");
                        peopleList.add(new People(id));
                    }
                    if(_fragment instanceof SearchPeopleFragment)
                        ((SearchPeopleFragment)_fragment).getRetrievedPeople(peopleList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {}
        });
    }

    @Override
    public void sendLikeStatus(String m_id, String o_id,String type) {
        Call<ResponseBody> call = requestInterface.sendLikeStatus(m_id,o_id,type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Boolean match = jsonObject.getBoolean("match");
                    if (match){
                        Log.e("match","yes");
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

    @Override
    public void revealProfile(String id) {
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.revealProfile(Profile.getCurrentProfile().getName(),
                Profile.getCurrentProfile().getLinkUri().toString(),
                Profile.getCurrentProfile().getProfilePictureUri(150,150).toString(),
                Profile.getCurrentProfile().getId(),id);
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
    public void getRevealedProfiles(String id) {
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.getReveals(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    List<JSONObject> revealed_list = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String last = jsonObject.getString("message");
                    if(!(last.equals("no reveals yet"))){
                    JSONArray jsonArray = new JSONArray(last);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject temp = new JSONObject(jsonArray.getString(i));
                            revealed_list.add(temp);
                        }
                    }
                if(_fragment instanceof RevealedProfilesFragment)
                    ((RevealedProfilesFragment)_fragment).getResponseForRevealedProfiles(revealed_list);
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
    public void deleteProfile(String id) {
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.deleteprofile(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getInformation(final String id) {
        if(id.equals("dummytext")){
            if(_fragment instanceof MessageFragment)
                ((MessageFragment)_fragment).createList(null);
        }else {
            GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + id, null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    try {
                        JSONObject object = new JSONObject(response.getRawResponse());
                        String url = ((JSONObject) ((JSONObject) object.get("picture")).get("data")).getString("url");
                        People people = new People();
                        people.setId(object.getString("id"));
                        people.setName(object.getString("name"));
                        people.setFirst_name(object.getString("first_name"));
                        people.setGender(object.getString("gender"));
                        people.setUrl(object.getString("link"));
                        people.setProfile_url(url);
                        if (_fragment instanceof SearchPeopleFragment)
                            ((SearchPeopleFragment) _fragment).createList(people);
                        if (_fragment instanceof MessageFragment)
                            ((MessageFragment) _fragment).createList(people);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,first_name,picture.type(large),gender");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
}
