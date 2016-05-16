package com.teamspeaghetti.www.gifster.interiorapplication.commonclasses;

import android.app.Activity;
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
    public static void startFragment(Fragment fragment,FragmentManager fragmentManager){
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }
    public static void createSnackBar(View view, String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
