package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.teamspeaghetti.www.gifster.CircleTransform;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.GifAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.GIFFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskingGIFProcess;
import com.teamspeaghetti.www.gifster.userinteractions.activities.WelcomeActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IMockRetrievedInformation;
import com.teamspeaghetti.www.gifster.userinteractions.presenters.UserDetailRetriever;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,IMockRetrievedInformation {

    UserDetailRetriever detailRetriever;
    ImageView profile_picture;
    TextView details;
    JSONObject userInfo;
    View header_holder;
    @BindView(R.id.custom_toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView(R.id.nav_view)NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        header_holder = navigationView.getHeaderView(0);
        detailRetriever= new UserDetailRetriever(this);
        details=(TextView)header_holder.findViewById(R.id.name);
        profile_picture=(ImageView)header_holder.findViewById(R.id.profile_picture);
        detailRetriever.retrieveInformation();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        navigationView.setCheckedItem(R.id.nav_searchgif);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
        int id = item.getItemId();
        switch (id){
            case R.id.nav_profile:
                Utils.createSnackBar(drawer,"profile clicked");
                break;
            case R.id.nav_messages:
                Utils.createSnackBar(drawer,"messages clicked");
                break;
            case R.id.nav_searchgif:
                Utils.startFragment(new GIFFragment(),getSupportFragmentManager());
                break;
            case R.id.nav_about:
                Utils.createSnackBar(drawer,"about clicked");
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
}
