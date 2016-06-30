package com.teamspeaghetti.www.gifster.interiorapplication.commonclasses;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.teamspeaghetti.www.gifster.R;
import com.wooplr.spotlight.SpotlightView;

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
    public static void startFragmentWithoutAnimation(Fragment fragment,FragmentManager fragmentManager){
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }
    public static void createSnackBar(View view, String message){
        Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    public static void createShowcase(Context context,View view,String title,String message,String key){
        new SpotlightView.Builder((Activity) context)
                .introAnimationDuration(400)
                .enableRevalAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(context.getResources().getColor(R.color.white))
                .headingTvSize(32)
                .headingTvText(title)
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText(message)
                .maskColor(context.getResources().getColor(R.color.bgspotlight))
                .target(view)
                .lineAnimDuration(400)
                .lineAndArcColor(context.getResources().getColor(R.color.colorAccent))
                .dismissOnTouch(true)
                .enableDismissAfterShown(true)
                .usageId(key)
                .show();
    }
    public static String checkNotificationType(String s){
        if(s.equals("Bir mesajınız var!"))
            return "message";
        else if(s.equals("Tienes un mensaje!"))
            return "message";
        else if(s.equals("You have a message!"))
            return "message";
        else if(s.equals("Bir eşleşmemiz var!"))
            return "match";
        else if(s.equals("Tienes un partido!"))
            return "match";
        else if(s.equals("You have a match!"))
            return "match";
        else if(s.equals("Birisi sizinle profilini paylaştı!"))
            return "reveal";
        else if(s.equals("Alguien reveló un perfil!"))
            return "reveal";
        else if(s.equals("Someone revealed a profile to you!"))
            return "reveal";
        return null;
    }
}
