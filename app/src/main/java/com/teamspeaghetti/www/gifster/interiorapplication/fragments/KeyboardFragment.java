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

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.KeyboardAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskSavedGIFs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 18.05.2016.
 */
public class KeyboardFragment extends Fragment implements IRetrieveGIFs {

    RecyclerView recyclerView;
    List<Gifs> gifs;
    KeyboardAdapter adapter;
    AskSavedGIFs savedgifs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.keyboard_fragment,null);
        savedgifs = new AskSavedGIFs(this);
        gifs = new ArrayList<>();
        adapter = new KeyboardAdapter(gifs,getContext());
        recyclerView = (RecyclerView)rootView.findViewById(R.id.keyboard_holder);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        savedgifs.retrieveGIFs(gifs);
        return rootView;
    }

    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        gifs.clear();
        gifs.addAll(gifsList);
        adapter.notifyDataSetChanged();
    }
}
