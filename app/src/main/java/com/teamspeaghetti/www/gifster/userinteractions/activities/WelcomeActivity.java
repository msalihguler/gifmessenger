package com.teamspeaghetti.www.gifster.userinteractions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;

/**
 * Created by Salih on 6.05.2016.
 */
public class WelcomeActivity extends AppCompatActivity{
    /*
    *
    *  Starting point of the activity. Checks whether you are loggedin or not.
    *
    * */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Facebook initialization
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //Login checking and forwarding user to related page.
        if(isLoggedIn()){
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
        //Method to check if user loggedin
    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


}
