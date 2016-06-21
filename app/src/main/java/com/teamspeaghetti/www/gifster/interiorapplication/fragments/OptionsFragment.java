package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;

/**
 * Created by Salih on 17.06.2016.
 */
public class OptionsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    SharedPreferences preferences;
    RadioGroup genderPreferences;
    CardView rateApplication,deleteAccount;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.options_layout,null);
        preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        genderPreferences = (RadioGroup)rootView.findViewById(R.id.gender_settings);
        rateApplication = (CardView)rootView.findViewById(R.id.rate_application);
        deleteAccount   = (CardView)rootView.findViewById(R.id.delete_account);
        rateApplication.setOnClickListener(this);
        deleteAccount.setOnClickListener(this);
        String gender = preferences.getString("preferred","female");
        if(gender.equals("female"))
            genderPreferences.check(R.id.women_selected);
        else
            genderPreferences.check(R.id.men_selected);
        genderPreferences.setOnCheckedChangeListener(this);
        return rootView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.women_selected:
                preferences.edit().putString("preferred","female").commit();
                break;
            case R.id.men_selected:
                preferences.edit().putString("preferred","male").commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rate_application:
                Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Utils.createSnackBar(getView(),"Activity not found");
                }
                break;
            case R.id.delete_account:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.dialog_message)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new UserProcesses(getContext(),OptionsFragment.this).deleteProfile(Profile.getCurrentProfile().getId());
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                // Create the AlertDialog object and return it
                Dialog alert = builder.create();
                alert.show();
                break;
        }
    }
}
