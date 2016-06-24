package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.CircleTransform;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.MainApplication;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.GIFFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.MessageFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.NetworkErrorPageFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.OptionsFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.ProfileFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.SearchPeopleFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.INotifyActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.receiver.ConnectivityReceiver;
import com.teamspeaghetti.www.gifster.userinteractions.activities.WelcomeActivity;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,INotifyActivity,ConnectivityReceiver.ConnectivityReceiverListener {

    //Variable declarations
    ImageView profile_picture;
    TextView details;
    View header_holder;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if(ConnectivityReceiver.isConnected()) {
            navigationView.setCheckedItem(R.id.nav_searchpeople);
            Utils.startFragment(new SearchPeopleFragment(), getSupportFragmentManager());
        }else{
            Utils.startFragment(new NetworkErrorPageFragment(),getSupportFragmentManager());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MainApplication.getInstance().setConnectivityListener(this);
    }

    public void initViews(){
        //View initializations
        toolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        drawer= (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);

        //Get header and it's childs
        header_holder = navigationView.getHeaderView(0);
        profile_picture=(ImageView)header_holder.findViewById(R.id.profile_picture);
        details=(TextView)header_holder.findViewById(R.id.name);

        //Fill header of navigation view
        String imageUrl = "https://graph.facebook.com/"+Profile.getCurrentProfile().getId()+"/picture?type=large";
        Picasso.with(this).load(imageUrl).transform(new CircleTransform()).into(profile_picture);
        details.setText(Profile.getCurrentProfile().getFirstName());

        //Toolbar specifications
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Setlistener
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_searchpeople:
                if(ConnectivityReceiver.isConnected())
                    Utils.startFragment(new SearchPeopleFragment(),getSupportFragmentManager());
                else
                    openNetworkError();

                break;
            case R.id.nav_profile:
                if(ConnectivityReceiver.isConnected())
                    Utils.startFragment(new ProfileFragment(),getSupportFragmentManager());
                else
                    openNetworkError();

                break;
            case R.id.nav_messages:
                if(ConnectivityReceiver.isConnected())
                    Utils.startFragment(new MessageFragment(),getSupportFragmentManager());
                else
                    openNetworkError();

                break;
            case R.id.nav_searchgif:
                if(ConnectivityReceiver.isConnected())
                    Utils.startFragment(new GIFFragment(),getSupportFragmentManager());
                else
                    openNetworkError();

                break;
            case R.id.nav_about:
                if(ConnectivityReceiver.isConnected())
                    Utils.startFragment(new OptionsFragment(),getSupportFragmentManager());
                else
                    openNetworkError();
                break;
            case R.id.nav_logout:
                LoginManager.getInstance().logOut();
                this.getSharedPreferences("settings", Context.MODE_PRIVATE).edit().clear().commit();
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
    public void notifyActivitySelected(int pos) {
        navigationView.getMenu().getItem(pos).setChecked(true);
    }
    public void openNetworkError(){
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        Utils.startFragmentWithoutAnimation(new NetworkErrorPageFragment(), getSupportFragmentManager());
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected){
            navigationView.setCheckedItem(R.id.nav_searchpeople);
            Utils.startFragment(new SearchPeopleFragment(), getSupportFragmentManager());
        }
        if(!isConnected){
            openNetworkError();
        }
    }
}
