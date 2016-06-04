package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ChatAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 3.06.2016.
 */
public class MessageFragment extends Fragment {
    RecyclerView chat_holder;
    ChatAdapter chatAdapter;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    List<People> matches = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages,null);
        chat_holder = (RecyclerView)rootView.findViewById(R.id.chat_holder);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressChat);
        chatAdapter=new ChatAdapter(getContext(),matches);
        layoutManager = new LinearLayoutManager(getContext());
        chat_holder.setLayoutManager(layoutManager);
        chat_holder.setAdapter(chatAdapter);

        return rootView;
    }
}
