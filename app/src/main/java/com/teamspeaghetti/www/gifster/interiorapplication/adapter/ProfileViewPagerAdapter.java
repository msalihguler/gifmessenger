package com.teamspeaghetti.www.gifster.interiorapplication.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.KeyboardFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.RevealedProfilesFragment;

/**
 * Created by Salih on 18.05.2016.
 */
public class ProfileViewPagerAdapter extends FragmentPagerAdapter {
    int count=0;
    Context _context;
    public ProfileViewPagerAdapter(FragmentManager fm,int fragment_count,Context context) {
        super(fm);
        this.count=fragment_count;
        this._context=context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RevealedProfilesFragment();
            case 1:
                return new KeyboardFragment();

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return _context.getResources().getString(R.string.reveals);
            case 1:
                return _context.getResources().getString(R.string.gifboard);

        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
