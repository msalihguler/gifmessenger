package com.teamspeaghetti.www.gifster.interiorapplication.services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.GPSTracker;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Salih on 10.06.2016.
 */
public class FirebaseTokenHolder extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        try {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d("Token", "Refreshed token: " + refreshedToken);
            sendRegistrationToServer(refreshedToken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sendRegistrationToServer(final String token){
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                        new UserProcesses(getApplicationContext()).sendRequest(AccessToken.getCurrentAccessToken().getUserId(),
                                String.valueOf(new GPSTracker(FirebaseTokenHolder.this).getLatitude()),
                                String.valueOf(new GPSTracker(FirebaseTokenHolder.this).getLongitude()), token,object.getString("gender"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "gender");
        request.setParameters(parameters);
        request.executeAsync();

    }
}
