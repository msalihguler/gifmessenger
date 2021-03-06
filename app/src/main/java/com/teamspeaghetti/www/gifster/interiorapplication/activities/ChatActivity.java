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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ChatKeyboardAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ConversationAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskSavedGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskingGIFProcess;
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
    EditText editText;
    AskSavedGIFs askSavedGIFs;
    ChatProcesses chatProcesses;
    ProgressBar progressBar;
    LinearLayout errorPage;
    TextView nokeyboardError;
    public static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
        otherID = getIntent().getExtras().getString("id");
        name = getIntent().getExtras().getString("name");
        initViews();
        searchTextChangingListener();
        makeCallForEarlyConversations(otherID);
        getSupportActionBar().setTitle(name);
        getKeyboard();
        sendSeenStatus(otherID);
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
        //Call views
        keyboard = (CardView)findViewById(R.id.keyboard);
        toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        gifList = (RecyclerView)findViewById(R.id.sendGifs);
        conversation = (RecyclerView)findViewById(R.id.previousTalks);
        editText = (EditText)findViewById(R.id.searchGifs);
        progressBar = (ProgressBar) findViewById(R.id.progressInsideChat);
        errorPage = (LinearLayout)findViewById(R.id.nomessages_layout);
        nokeyboardError = (TextView)findViewById(R.id.errorMessage);

        //Create Arrays
        savedGifs = new ArrayList<>();
        earlyConversations = new ArrayList<>();

        //Create adapters
        conversationAdapter = new ConversationAdapter(earlyConversations,this);
        adapter = new ChatKeyboardAdapter(savedGifs,this,otherID);

        //Set Toolbar as action bar
        setSupportActionBar(toolbar);
        toolbar.getChildAt(0).setVisibility(View.GONE);

        //Horizontal list view attribute
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        //list specifications
        gifList.setLayoutManager(layoutManager);
        gifList.setAdapter(adapter);
        conversation.setLayoutManager(new LinearLayoutManager(this));
        conversation.setAdapter(conversationAdapter);

        //Defining filter for new message
        filter=new IntentFilter(getResources().getString(R.string.filter_string));
        receiver = new MessageReceiver();

        //object creations
        askSavedGIFs = new AskSavedGIFs(this);
        chatProcesses = new ChatProcesses(null,this);

    }

    public void searchTextChangingListener(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    makeInstantRequestForKeyboard(s.toString());
                }if(s.toString().length()==0){
                    getKeyboard();
                }
            }
        });
    }

    public void makeInstantRequestForKeyboard(String key){
        savedGifs.clear();
        adapter.notifyDataSetChanged();
        new AskingGIFProcess(ChatActivity.this).makeRequestToGetGifs(savedGifs,100,key);
    }

    public void makeCallForEarlyConversations(String id){
        chatProcesses.getMessages(id);
    }


    public void getKeyboard(){
        savedGifs.clear();
        askSavedGIFs.retrieveGIFs(savedGifs);
    }


    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        savedGifs.clear();
        savedGifs.addAll(gifsList);
        if(gifsList.size()<=0){
            nokeyboardError.setVisibility(View.VISIBLE);
            gifList.setVisibility(View.GONE);
        }else{
            if(nokeyboardError.getVisibility()==View.VISIBLE){
                nokeyboardError.setVisibility(View.GONE);
                gifList.setVisibility(View.VISIBLE);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void getConversation(List<JSONObject> jsonObjectList){
        earlyConversations.addAll(jsonObjectList);
        if(earlyConversations.size()>0){
            if(progressBar.isShown())
                progressBar.setVisibility(View.GONE);
            conversation.setVisibility(View.VISIBLE);
            conversationAdapter.notifyDataSetChanged();
            conversation.scrollToPosition(earlyConversations.size()-1);
        }else{
            if(progressBar.isShown())
                progressBar.setVisibility(View.GONE);
            conversation.setVisibility(View.GONE);
            errorPage.setVisibility(View.VISIBLE);
        }

    }

    public void addItem(JSONObject jsonObject){
        earlyConversations.add(jsonObject);
        errorPage.setVisibility(View.GONE);
        conversation.setVisibility(View.VISIBLE);
        conversationAdapter.notifyDataSetChanged();
        conversation.scrollToPosition(earlyConversations.size()-1);
    }
    public void sendSeenStatus(String otherID){
        chatProcesses.sendSeenStatus(otherID);
    }
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                chatProcesses.sendSeenStatus(otherID);
                earlyConversations.add(new JSONObject(intent.getExtras().getString("jsonObject")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            conversationAdapter.notifyDataSetChanged();
            conversation.scrollToPosition(earlyConversations.size()-1);
        }
    }
}
