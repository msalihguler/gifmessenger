package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamspeaghetti.www.gifster.R;

/**
 * Created by Salih on 24.06.2016.
 */
public class NetworkErrorPageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.network_error,null);
        return rootview;
    }
}
