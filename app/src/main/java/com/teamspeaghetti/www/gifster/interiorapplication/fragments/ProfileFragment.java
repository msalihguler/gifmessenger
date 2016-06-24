package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ProfileViewPagerAdapter;

/**
 * Created by Salih on 18.05.2016.
 */
public class ProfileFragment extends Fragment {

    //Varable declarations
    TabLayout profileTabs;
    ViewPager profileFragmentHolder;
    public static int fragment_count=2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_page,null);

        //Variable initializations
        profileTabs = (TabLayout)rootView.findViewById(R.id.profiletabs);
        profileFragmentHolder = (ViewPager)rootView.findViewById(R.id.fragment_holder_profile);

        //Set adapter to view pager
        profileFragmentHolder.setAdapter(new ProfileViewPagerAdapter(getChildFragmentManager(),fragment_count,getContext()));

        //Customize tabs
        profileTabs.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        profileTabs.setTabTextColors(getResources().getColor(R.color.whitefaded),getResources().getColor(R.color.white));
        profileTabs.post(new Runnable() {
            @Override
            public void run() {
                profileTabs.setupWithViewPager(profileFragmentHolder);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
