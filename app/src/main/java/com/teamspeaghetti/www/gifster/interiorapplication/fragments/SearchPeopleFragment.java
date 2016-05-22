package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrievePeople;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 21.05.2016.
 */
public class SearchPeopleFragment extends Fragment implements View.OnClickListener,IRetrievePeople {
    ImageView thumbup,thumbdown;
    CardView holder;
    List<People> peoples;
    int lastPosition=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchpeople,null);
        new UserProcesses(getContext()).getPeople(Profile.getCurrentProfile().getId());
        return init(rootView);
    }

    public View init(View rootView){
        thumbup = (ImageView)rootView.findViewById(R.id.thumbsup);
        thumbdown = (ImageView)rootView.findViewById(R.id.thumbsdown);
        thumbdown.setOnClickListener(this);
        thumbup.setOnClickListener(this);
        peoples = new ArrayList<People>();
        holder=(CardView)rootView.findViewById(R.id.profilecard);
        Drawable mDrawable = getActivity().getResources().getDrawable(R.drawable.thumbup);
        mDrawable.setColorFilter(new
                PorterDuffColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY));
        thumbup.setImageDrawable(mDrawable);
        thumbdown.setImageDrawable(mDrawable);
        thumbdown.setRotationX(180);
        thumbdown.setRotationY(180);
        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.thumbsup:
                if(lastPosition==peoples.size()) {

                }else{
                    holder.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.move_right));
                    lastPosition++;
                }


                break;
            case R.id.thumbsdown:
                if(lastPosition==peoples.size()) {

                }else {
                    holder.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.move_left));
                    lastPosition++;
                }
                break;
        }
    }

    @Override
    public void getRetrievedPeople(List<People> peopleList) {
            peoples= (peopleList);
            new UserProcesses().getInformation(peoples.get(0).getId());

    }
}
