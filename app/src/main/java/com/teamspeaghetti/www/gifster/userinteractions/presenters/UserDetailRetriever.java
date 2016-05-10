package com.teamspeaghetti.www.gifster.userinteractions.presenters;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.teamspeaghetti.www.gifster.userinteractions.interfaces.IMockRetrievedInformation;
import com.teamspeaghetti.www.gifster.userinteractions.interfaces.IRetrieveUserInformation;

import org.json.JSONObject;

/**
 * Created by Salih on 7.05.2016.
 */
public class UserDetailRetriever implements IRetrieveUserInformation {

    IMockRetrievedInformation _context;
    JSONObject userinformation;
    public UserDetailRetriever(IMockRetrievedInformation context){
        this._context=context;
    }

    @Override
    public void retrieveInformation() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,GraphResponse response) {
                        _context.userInformation(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,first_name,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
