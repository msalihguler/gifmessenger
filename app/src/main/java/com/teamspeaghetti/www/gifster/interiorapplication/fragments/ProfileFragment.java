package com.teamspeaghetti.www.gifster.interiorapplication.fragments;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ProfileViewPagerAdapter;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

/**
 * Created by Salih on 18.05.2016.
 */
public class ProfileFragment extends Fragment {

    //Varable declarations
    TabLayout profileTabs;
    ViewPager profileFragmentHolder;
    LinearLayout tab1,tab2;
    public static int fragment_count=2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_page,null);

        //Variable initializations
        profileTabs = (TabLayout)rootView.findViewById(R.id.profiletabs);
        profileFragmentHolder = (ViewPager)rootView.findViewById(R.id.fragment_holder_profile);
        tab1 = (LinearLayout)rootView.findViewById(R.id.tab1);
        tab2 = (LinearLayout)rootView.findViewById(R.id.tab2);

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
        createSpotLight();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void createSpotLight(){
        new SpotlightView.Builder((Activity) getContext())
                .introAnimationDuration(400)
                .enableRevalAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(getContext().getResources().getColor(R.color.white))
                .headingTvSize(32)
                .headingTvText(getContext().getResources().getString(R.string.profiletitle))
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText(getContext().getResources().getString(R.string.profilesexplanation))
                .maskColor(getContext().getResources().getColor(R.color.bgspotlight))
                .target(tab1)
                .lineAnimDuration(400)
                .lineAndArcColor(getContext().getResources().getColor(R.color.colorAccent))
                .dismissOnTouch(true)
                .enableDismissAfterShown(true)
                .usageId(getContext().getResources().getString(R.string.profilekey)).setListener(new SpotlightListener() {
            @Override
            public void onUserClicked(String s) {
                new SpotlightView.Builder((Activity) getContext())
                        .introAnimationDuration(400)
                        .enableRevalAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .headingTvColor(getContext().getResources().getColor(R.color.white))
                        .headingTvSize(32)
                        .headingTvText(getContext().getResources().getString(R.string.gifboard))
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText(getContext().getResources().getString(R.string.gifboardexplanation))
                        .maskColor(getContext().getResources().getColor(R.color.bgspotlight))
                        .target(tab2)
                        .lineAnimDuration(400)
                        .lineAndArcColor(getContext().getResources().getColor(R.color.colorAccent))
                        .dismissOnTouch(true)
                        .enableDismissAfterShown(true)
                        .usageId(getContext().getResources().getString(R.string.gifboardkey))
                        .show();
            }
        })//UNIQUE ID
                .show();
    }
}
