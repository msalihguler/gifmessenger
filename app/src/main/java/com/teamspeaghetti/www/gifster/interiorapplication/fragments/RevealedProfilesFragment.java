package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.RevealedProfileAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 16.06.2016.
 */
public class RevealedProfilesFragment extends Fragment {
    //Variable declaration
    RecyclerView revealed_list;
    ProgressBar progressBar;
    LinearLayout errorMessage;
    List<JSONObject> r_list;
    UserProcesses userProcesses;
    RevealedProfileAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView= inflater.inflate(R.layout.fragment_revealed_profile,null);
            rootView = initViews(rootView);
            return rootView;
    }

    public View initViews(View view){
        //view initialization
        revealed_list = (RecyclerView)view.findViewById(R.id.revealed_profile_holder);
        progressBar = (ProgressBar)view.findViewById(R.id.progressRevealed);
        errorMessage = (LinearLayout)view.findViewById(R.id.no_revealed_photos);

        //list initialization
        r_list = new ArrayList<>();

        //adapter initializatin
        adapter = new RevealedProfileAdapter(getContext(),r_list);

        //Class instances
        userProcesses = new UserProcesses(getContext(),RevealedProfilesFragment.this);

        //Specification of Recyclerview
        revealed_list.setLayoutManager(new LinearLayoutManager(getContext()));
        revealed_list.setAdapter(adapter);

        //Calling methods
        userProcesses.getRevealedProfiles(Profile.getCurrentProfile().getId());
        return view;
    }

    public void getResponseForRevealedProfiles(List<JSONObject> list){
        r_list.clear();
        r_list.addAll(list);
        if(r_list.size()>0){
            progressBar.setVisibility(View.GONE);
            revealed_list.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }else{
            progressBar.setVisibility(View.GONE);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }
}
