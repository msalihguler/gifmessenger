package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.teamspeaghetti.www.gifster.CircleTransform;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.userinteractions.activities.WelcomeActivity;
import com.teamspeaghetti.www.gifster.userinteractions.interfaces.IMockRetrievedInformation;
import com.teamspeaghetti.www.gifster.userinteractions.presenters.UserDetailRetriever;

import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMockRetrievedInformation,NavigationView.OnNavigationItemSelectedListener {

    UserDetailRetriever detailRetriever;
    JSONObject userInfo;
    View header_holder;
    TextView details;
    ImageView profile_picture;
    @BindView(R.id.custom_toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView(R.id.nav_view)NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        header_holder = navigationView.getHeaderView(0);
        details=(TextView)header_holder.findViewById(R.id.name);
        profile_picture=(ImageView)header_holder.findViewById(R.id.profile_picture);
        detailRetriever= new UserDetailRetriever(this);
        detailRetriever.retrieveInformation();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void userInformation(JSONObject retrievedInformation) {
        userInfo = retrievedInformation;
        try {
            details.setText(userInfo.getString("first_name"));
            String imageUrl = userInfo.getJSONObject("picture").getJSONObject("data").getString("url");
            Picasso.with(this).load(imageUrl).transform(new CircleTransform()).into(profile_picture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Snackbar snackbar;
        switch (id){
            case R.id.nav_profile:
                snackbar = Snackbar.make(drawer,"profile clicked",Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
            case R.id.nav_messages:
                snackbar = Snackbar.make(drawer,"messages clicked",Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
            case R.id.nav_about:
                snackbar = Snackbar.make(drawer,"about clicked",Snackbar.LENGTH_SHORT);
                snackbar.show();
                break;
            case R.id.nav_logout:
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(this,WelcomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
