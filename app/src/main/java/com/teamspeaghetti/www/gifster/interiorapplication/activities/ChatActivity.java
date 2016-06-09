package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ChatKeyboardAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskSavedGIFs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 6.06.2016.
 */
public class ChatActivity extends AppCompatActivity implements IRetrieveGIFs{
    //Variable declaration
    Animation slide_down,slide_up;
    CardView keyboard;
    RecyclerView gifList;
    Toolbar toolbar;
    ChatKeyboardAdapter adapter;
    List<Gifs> savedGifs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
        initViews();
        getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));
        new AskSavedGIFs(this).retrieveGIFs(savedGifs);
    }
    public void initViews(){
        keyboard = (CardView)findViewById(R.id.keyboard);
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        gifList = (RecyclerView)findViewById(R.id.sendGifs);
        savedGifs = new ArrayList<>();
        adapter = new ChatKeyboardAdapter(savedGifs,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        setSupportActionBar(toolbar);
        slide_up.setFillAfter(true);
        slide_down.setFillAfter(true);
        gifList.setLayoutManager(layoutManager);
        gifList.setAdapter(adapter);
    }

    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        savedGifs.clear();
        savedGifs.addAll(gifsList);
        adapter.notifyDataSetChanged();
    }


}
