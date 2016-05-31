package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.animation.Animator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
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
    ImageView thumbup,thumbdown,profile_pic;
    TextView name;
    CardView holder;
    List<People> peoples;
    UserProcesses user_instance;
    int lastPosition=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchpeople,null);
        user_instance = new UserProcesses(getContext(),this);
        user_instance.getPeople(AccessToken.getCurrentAccessToken().getUserId());
        return init(rootView);
    }

    public View init(View rootView){
        profile_pic = (ImageView)rootView.findViewById(R.id.profilepicture);
        name = (TextView)rootView.findViewById(R.id.name);
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
                if(lastPosition>peoples.size()) {
                    lastPosition=0;
                }else{
                    holder.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.move_right));
                    lastPosition++;
                }


                break;
            case R.id.thumbsdown:
                if(lastPosition==peoples.size()) {
                    lastPosition=0;
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
            user_instance.getInformation(peoples.get(0).getId());

    }

    @Override
    public void createList(List<People> peopleList) {
        People people = peopleList.get(0);
        name.setText(people.getName());
        Glide.with(getContext()).load(people.getProfile_url())
                .thumbnail(0.5f)
                .crossFade()
                .into(profile_pic);
    }
}
