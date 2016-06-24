package com.teamspeaghetti.www.gifster.userinteractions.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;
import com.teamspeaghetti.www.gifster.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by Salih on 6.05.2016.
 */
public class LoginActivity extends AppIntro {
    /*
    * AppIntro is a intro screen library which designed by Paolo Rotolo
    * You can check his github repository https://github.com/PaoloRotolo
    * */



    //Facebook login callback listener will be held with this variable
    CallbackManager callbackManager;

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        callbackManager=CallbackManager.Factory.create();
        //Adding fragments for introduction
        addSlide(AppIntroFragment.newInstance("",getResources().getString(R.string.application_definiton),R.drawable.foto,getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("",getResources().getString(R.string.encourege_definiton),R.drawable.chat,getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("",getResources().getString(R.string.facebook_definition),R.drawable.facebook,getResources().getColor(R.color.colorPrimaryDark)));
        addSlide(AppIntroFragment.newInstance("",getResources().getString(R.string.ready_definition),R.drawable.thumbup,getResources().getColor(R.color.colorPrimaryDark)));
        //Specialization of the library
        setDoneText(getResources().getString(R.string.login));
        showSkipButton(false);
    }
    @Override
    public void onDonePressed() {
        //To facebook login screen

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));

        //CallBack listener
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        this.stopTracking();
                        Profile.setCurrentProfile(currentProfile);
                    }
                };
                profileTracker.startTracking();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                try {
                                    intent.putExtra("gender",object.getString("gender"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "gender");
                request.setParameters(parameters);
                request.executeAsync();

            }
            @Override
            public void onCancel() {
                Snackbar snackbar = Snackbar.make(pager,getResources().getString(R.string.login_cancel),Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            @Override
            public void onError(FacebookException error) {
                Snackbar snackbar;
                if(error.getMessage().equals("net::ERR_INTERNET_DISCONNECTED"))
                snackbar = Snackbar.make(pager,getResources().getString(R.string.network_error_message),Snackbar.LENGTH_SHORT);
                else
                snackbar = Snackbar.make(pager,getResources().getString(R.string.login_error)+error,Snackbar.LENGTH_SHORT);
                snackbar.show();
                error.printStackTrace();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSkipPressed() {}
    @Override
    public void onNextPressed() {}
    @Override
    public void onSlideChanged() {}
}
