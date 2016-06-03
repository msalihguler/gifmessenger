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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
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
    ProgressBar pbar;
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
        pbar = (ProgressBar)rootView.findViewById(R.id.pbar);
        holder=(CardView)rootView.findViewById(R.id.profilecard);
        peoples = new ArrayList<People>();
        thumbdown.setOnClickListener(this);
        thumbup.setOnClickListener(this);
        holder.setVisibility(View.GONE);
        pbar.setVisibility(View.VISIBLE);
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
                if(lastPosition>=peoples.size()) {
                    lastPosition=0;
                    Animation animation =AnimationUtils.loadAnimation(getContext(), R.anim.move_right);
                    animation.setFillAfter(true);
                    holder.startAnimation(animation);

                }else{
                    user_instance.sendLikeStatus(AccessToken.getCurrentAccessToken().getUserId(),peoples.get(lastPosition).getId(),"like");
                    holder.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.move_right));
                    user_instance.getInformation(peoples.get(lastPosition).getId());
                    lastPosition++;
                }
                break;
            case R.id.thumbsdown:
                if(lastPosition>=peoples.size()) {
                    lastPosition=0;

                }else {
                    user_instance.sendLikeStatus(AccessToken.getCurrentAccessToken().getUserId(),peoples.get(lastPosition).getId(),"dislike");
                    holder.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.move_left));
                    user_instance.getInformation(peoples.get(lastPosition).getId());
                    lastPosition++;
                }
                break;
        }
    }

    @Override
    public void getRetrievedPeople(List<People> peopleList) {
            peoples.clear();
            peoples.addAll(peopleList);
        if(peoples.size()>0) {
            user_instance.getInformation(peoples.get(0).getId());
            lastPosition = 1;
        }else{
            pbar.setVisibility(View.GONE);
            Utils.createSnackBar(getView(),"There is no one new");
        }
    }

    @Override
    public void createList(People people) {
            if(people.getName()!=null) {
                name.setText(people.getName());
                Glide.with(getContext()).load(people.getProfile_url())
                        .crossFade()
                        .into(profile_pic);
                holder.setVisibility(View.VISIBLE);
                if(pbar.isShown()) {
                    pbar.setVisibility(View.GONE);

                }
            }
        }

}
