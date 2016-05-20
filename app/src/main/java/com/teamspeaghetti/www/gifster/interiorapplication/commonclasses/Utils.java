package com.teamspeaghetti.www.gifster.interiorapplication.commonclasses;


import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.teamspeaghetti.www.gifster.R;

/**
 * Created by Salih on 16.05.2016.
 */
public class Utils {
    public static void startFragmentWithStack(Fragment fragment,FragmentManager fragmentManager,String stack){
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("fa").replace(R.id.flContent, fragment).commit();
    }
    public static void startFragment(Fragment fragment,FragmentManager fragmentManager){
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.flContent, fragment).commit();
    }
    public static void createSnackBar(View view, String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

}
