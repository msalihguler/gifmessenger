package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.KeyboardAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskSavedGIFs;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 18.05.2016.
 */
public class KeyboardFragment extends Fragment implements IRetrieveGIFs,View.OnClickListener {

    //Variable declarations
    RecyclerView recyclerView;
    List<Gifs> gifs;
    KeyboardAdapter adapter;
    AskSavedGIFs savedgifs;
    ProgressBar progressBar;
    LinearLayout errorHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.keyboard_fragment,null);
        rootView=init(rootView);
        getGIFsFromServer();
        return rootView;
    }

    public View init(View rootView){

        //Variable initialization
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_keyboard);
        errorHolder = (LinearLayout)rootView.findViewById(R.id.nogifs_layout);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.keyboard_holder);

        //List initialization
        gifs = new ArrayList<>();

        //Object initialization
        savedgifs = new AskSavedGIFs(this);
        adapter = new KeyboardAdapter(gifs,getContext());

        //listeners
        errorHolder.setOnClickListener(this);

        //recyclerview specification
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void getGIFsFromServer(){
        if(errorHolder.getVisibility()==View.VISIBLE)
            errorHolder.setVisibility(View.GONE);
        if(progressBar.getVisibility()==View.GONE)
            progressBar.setVisibility(View.VISIBLE);
        savedgifs.retrieveGIFs(gifs);
    }

    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        gifs.clear();
        gifs.addAll(gifsList);
        if(gifs.size()>0) {
            Log.e("url",gifs.get(0).getUrl());
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            errorHolder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Utils.startFragment(new GIFFragment(),getActivity().getSupportFragmentManager());
        ((MainActivity)getActivity()).notifyActivitySelected(3);
    }
}
