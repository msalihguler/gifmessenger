package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ChatAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveMatches;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrievePeople;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.ChatProcesses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 3.06.2016.
 */
public class MessageFragment extends Fragment implements IRetrievePeople {
    RecyclerView chat_holder;
    ChatAdapter chatAdapter;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    List<People> matches = new ArrayList<>();
    ChatProcesses chatInstance;
    LinearLayout nomatchesLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages,null);
        chat_holder = (RecyclerView)rootView.findViewById(R.id.chat_holder);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressChat);
        nomatchesLayout = (LinearLayout)rootView.findViewById(R.id.nomatches_layotu);
        chatAdapter=new ChatAdapter(getContext(),matches);
        chatInstance = new ChatProcesses(MessageFragment.this,getContext());
        layoutManager = new LinearLayoutManager(getContext());
        chat_holder.setLayoutManager(layoutManager);
        chat_holder.setAdapter(chatAdapter);
        getMatchesFromServer();
        return rootView;
    }
    public void getMatchesFromServer(){
        if(nomatchesLayout.getVisibility()==View.VISIBLE)
            nomatchesLayout.setVisibility(View.GONE);
        chatInstance.getMatches();
    }
    @Override
    public void getRetrievedPeople(List<People> peopleList) {
    }

    @Override
    public void createList(People people) {
        if(people==null){
            progressBar.setVisibility(View.GONE);
            nomatchesLayout.setVisibility(View.VISIBLE);
        }else {
            matches.add(people);
            chatAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            chat_holder.setVisibility(View.VISIBLE);
        }
    }
}
