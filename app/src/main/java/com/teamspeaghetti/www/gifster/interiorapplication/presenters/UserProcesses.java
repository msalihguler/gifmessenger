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
    public void sendRequest(String id,String latitude,String longitude,String token,String gender,String language) {
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.registerUser(id,latitude,longitude,token,gender,language);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("response",response.body().string());
                } catch (Exception e) {
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
                    peopleList.clear();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String temp = jsonObject.getString("message");
                    JSONArray jsonObject1 = new JSONArray(temp);
                    for(int i=0;i<jsonObject1.length();i++) {
                        String id = ((JSONObject) jsonObject1.get(i)).getString("userid");
                        String gender = ((JSONObject) jsonObject1.get(i)).getString("gender");
                        peopleList.add(new People(id,gender));

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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {}
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @Override
    public void revealProfile(String id) {
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        String name = Profile.getCurrentProfile().getName();
        String m_id = Profile.getCurrentProfile().getId();
        Call<ResponseBody> call = requestInterface.revealProfile(name,m_id,id);
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
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
    public void getInformation(final String id,final String gender) {
        if(id.equals("dummytext")){
            if(_fragment instanceof MessageFragment)
                ((MessageFragment)_fragment).createList(null);
        }else {
            GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + id, null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    try {
                        JSONObject object = new JSONObject(response.getRawResponse());
                        String id = object.getString("id");
                        String url = "https://graph.facebook.com/"+id+"/picture?type=large";
                        People people = new People();
                        people.setId(id);
                        people.setName(object.getString("name"));
                        people.setFirst_name(object.getString("first_name"));
                        people.setUrl(object.getString("link"));
                        people.setGender(gender);
                        people.setProfile_url(url);
                        if (_fragment instanceof SearchPeopleFragment)
                            ((SearchPeopleFragment) _fragment).createList(people);
                        if (_fragment instanceof MessageFragment)
                            ((MessageFragment) _fragment).createList(people);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,first_name");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
    public void clearTokenFromServer(String id){
        retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.deletetoken(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
