package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ChatKeyboardAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ConversationAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskSavedGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.ChatProcesses;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Salih on 6.06.2016.
 */
public class ChatActivity extends AppCompatActivity implements IRetrieveGIFs{

    //Variable declaration
    Animation slide_down,slide_up;
    CardView keyboard;
    RecyclerView gifList,conversation;
    Toolbar toolbar;
    ChatKeyboardAdapter adapter;
    ConversationAdapter conversationAdapter;
    List<Gifs> savedGifs;
    List<JSONObject> earlyConversations;
    String otherID,name;
    IntentFilter filter;
    MessageReceiver receiver;
    public static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
        otherID = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        initViews();
        makeCallForEarlyConversations(otherID);
        getSupportActionBar().setTitle(name);
        new AskSavedGIFs(this).retrieveGIFs(savedGifs);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
    public void initViews(){
        keyboard = (CardView)findViewById(R.id.keyboard);
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        gifList = (RecyclerView)findViewById(R.id.sendGifs);
        conversation = (RecyclerView)findViewById(R.id.previousTalks);
        savedGifs = new ArrayList<>();
        earlyConversations = new ArrayList<>();
        conversationAdapter = new ConversationAdapter(earlyConversations,this);
        adapter = new ChatKeyboardAdapter(savedGifs,this,otherID);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        setSupportActionBar(toolbar);
        slide_up.setFillAfter(true);
        slide_down.setFillAfter(true);
        gifList.setLayoutManager(layoutManager);
        gifList.setAdapter(adapter);
        conversation.setLayoutManager(new LinearLayoutManager(this));
        conversation.setAdapter(conversationAdapter);
        filter=new IntentFilter("com.teamspaghetti.gifster.newmessage");
        receiver = new MessageReceiver();
    }

    public void makeCallForEarlyConversations(String id){
        new ChatProcesses(null,this).getMessages(id);
    }
    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        savedGifs.clear();
        savedGifs.addAll(gifsList);
        adapter.notifyDataSetChanged();
    }
    public void getConversation(List<JSONObject> jsonObjectList){
        earlyConversations.clear();
        earlyConversations.addAll(jsonObjectList);
        conversationAdapter.notifyDataSetChanged();
        conversation.scrollToPosition(earlyConversations.size()-1);
    }
    public void addItem(JSONObject jsonObject){
        earlyConversations.add(jsonObject);
        conversationAdapter.notifyDataSetChanged();
        conversation.scrollToPosition(earlyConversations.size()-1);
    }
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                earlyConversations.add(new JSONObject(intent.getExtras().getString("jsonObject")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            conversationAdapter.notifyDataSetChanged();
            conversation.scrollToPosition(earlyConversations.size()-1);
        }
    }
}
