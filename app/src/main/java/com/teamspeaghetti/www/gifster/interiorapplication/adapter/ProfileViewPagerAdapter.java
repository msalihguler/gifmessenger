package com.teamspeaghetti.www.gifster.interiorapplication.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamspeaghetti.www.gifster.interiorapplication.fragments.KeyboardFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.NotificationFragment;

/**
 * Created by Salih on 18.05.2016.
 */
public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    int count=0;
    public ProfileViewPagerAdapter(FragmentManager fm,int fragment_count) {
        super(fm);
        this.count=fragment_count;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NotificationFragment();
            case 1:
                return new KeyboardFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Notifications";
            case 1:
                return "Keyboard";
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
