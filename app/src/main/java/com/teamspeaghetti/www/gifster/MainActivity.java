package com.teamspeaghetti.www.gifster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMockRetrievedInformation {

    UserDetailRetriever detailRetriever;
    JSONObject userInfo;
    @BindView(R.id.custom_logout)Button logout;
    @OnClick(R.id.custom_logout) void onClick() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
    @BindView(R.id.userdetails)TextView details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        detailRetriever= new UserDetailRetriever(this);
        detailRetriever.retrieveInformation();
    }

    @Override
    public void userInformation(JSONObject retrievedInformation) {
        userInfo = retrievedInformation;
        try {
            details.setText(userInfo.getString("first_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
