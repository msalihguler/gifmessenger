package com.teamspeaghetti.www.gifster.interiorapplication.services;

import android.util.Log;

import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.GPSTracker;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;

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
    public void sendRegistrationToServer(String token){
        new UserProcesses(getApplicationContext()).sendRequest(AccessToken.getCurrentAccessToken().getUserId(),
                String.valueOf(new GPSTracker(this).getLatitude()),
                String.valueOf(new GPSTracker(this).getLongitude()), token);
    }
}
