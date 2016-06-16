package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
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
    List<JSONObject> list;
    UserProcesses userProcesses;
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

        //list initialization
        list = new ArrayList<>();

        //Class instances
        userProcesses = new UserProcesses(getContext(),RevealedProfilesFragment.this);

        userProcesses.getRevealedProfiles(Profile.getCurrentProfile().getId());
        return view;
    }
}
