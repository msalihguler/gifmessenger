package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity implements IMockRetrievedInformation,NavigationView.OnNavigationItemSelectedListener,IRetrieveGIFs {

    UserDetailRetriever detailRetriever;
    AskingGIFProcess askingGIFProcess;
    JSONObject userInfo;
    View header_holder;
    TextView details;
    ImageView profile_picture;
    Snackbar snackbar;
    List<Gifs> real_gif_list = new ArrayList<Gifs>();
    GifAdapter adapter;
    @BindView(R.id.custom_toolbar)Toolbar toolbar;
    @BindView(R.id.drawer_layout)DrawerLayout drawer;
    @BindView(R.id.nav_view)NavigationView navigationView;
    @BindView(R.id.gif_search)EditText gifsearch;
    @BindView(R.id.progress_bar)ProgressBar progressBar;
    @BindView(R.id.giflogo)ImageView giflogo;
    @BindView(R.id.gif_holder)RecyclerView gifholder;
    @OnClick(R.id.giflogo)public void click(View view){
        view.setVisibility(View.GONE);
    }
    @OnEditorAction(R.id.gif_search)boolean onEditorAction(int actionId){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(actionId==EditorInfo.IME_ACTION_SEARCH){
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                progressBar.setVisibility(View.VISIBLE);
                askingGIFProcess.getGIFS(gifsearch.getText().toString(),real_gif_list,true);
                return true;
            }else{
                return true;
            }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        header_holder = navigationView.getHeaderView(0);
        details=(TextView)header_holder.findViewById(R.id.name);
        profile_picture=(ImageView)header_holder.findViewById(R.id.profile_picture);
        adapter = new GifAdapter(real_gif_list);
        gifholder.setLayoutManager(new LinearLayoutManager(this));
        gifholder.setAdapter(adapter);
        detailRetriever= new UserDetailRetriever(this);
        askingGIFProcess = new AskingGIFProcess(this);
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

    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        real_gif_list.clear();
        real_gif_list.addAll(gifsList);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);

    }
}
