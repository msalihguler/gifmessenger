package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.google.firebase.iid.FirebaseInstanceId;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.GPSTracker;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrievePeople;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Salih on 21.05.2016.
 */
public class SearchPeopleFragment extends Fragment implements View.OnClickListener,IRetrievePeople,View.OnTouchListener {

    //Variable declarations
    ImageView thumbup,thumbdown,profile_pic;
    TextView name,errormessage;
    CardView holder;
    List<People> peoples;
    UserProcesses user_instance;
    ProgressBar pbar;
    LinearLayout errorpage;
    SharedPreferences preferences;
    int lastPosition=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.searchpeople,null);
        user_instance = new UserProcesses(getContext(),this);
        registerUserToGIFsterServer();
        rootView = init(rootView);
        getPeopleFromServer();
        return rootView;
    }

    public View init(View rootView){

        //View initializations
        profile_pic = (ImageView)rootView.findViewById(R.id.profilepicture);
        thumbup = (ImageView)rootView.findViewById(R.id.thumbsup);
        thumbdown = (ImageView)rootView.findViewById(R.id.thumbsdown);
        name = (TextView)rootView.findViewById(R.id.name);
        errormessage = (TextView)rootView.findViewById(R.id.notfound);
        pbar = (ProgressBar)rootView.findViewById(R.id.pbar);
        holder=(CardView)rootView.findViewById(R.id.profilecard);
        errorpage = (LinearLayout)rootView.findViewById(R.id.noOneFound_message);

        //Shared preferences initialization
        preferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        //List initialization
        peoples = new ArrayList<People>();

        //Listeners for thumbs
        thumbdown.setOnClickListener(this);
        thumbup.setOnClickListener(this);
        thumbdown.setOnTouchListener(this);
        thumbup.setOnTouchListener(this);


        //rotate thumb image and change it's color
        Drawable drawableUp = getActivity().getResources().getDrawable(R.drawable.thumbup);
        Drawable drawableDown = getActivity().getResources().getDrawable(R.drawable.thumbup);
        drawableUp.setColorFilter(new
                PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
        drawableDown.setColorFilter(new
                PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
        thumbup.setImageDrawable(drawableUp);
        thumbdown.setImageDrawable(drawableDown);
        thumbdown.setRotationX(180);
        thumbdown.setRotationY(180);

        return rootView;
    }

    public void getPeopleFromServer(){
        if(holder.getVisibility()==View.VISIBLE)
            holder.setVisibility(View.GONE);
        if(errorpage.getVisibility()==View.VISIBLE)
            errorpage.setVisibility(View.GONE);
        pbar.setVisibility(View.VISIBLE);
        user_instance.getPeople(AccessToken.getCurrentAccessToken().getUserId());
    }

    @Override
    public void onClick(View v) {
        Log.e("count",String.valueOf(lastPosition));
        switch (v.getId()){
            case R.id.thumbsup:
                if(lastPosition>=peoples.size()-1) {
                    holder.startAnimation(createAnimationForLastElement("like"));
                    lastPosition=0;
                    holder.setVisibility(View.GONE);
                    errorpage.setVisibility(View.VISIBLE);
                }else{
                    holder.startAnimation(createAnimationForTopElements("like"));
                    user_instance.getInformation(peoples.get(lastPosition).getId(),peoples.get(lastPosition).getGender());
                }
                break;
            case R.id.thumbsdown:
                if(lastPosition>=peoples.size()-1) {
                    holder.startAnimation(createAnimationForLastElement("dislike"));
                    lastPosition=0;
                    holder.setVisibility(View.GONE);
                    errorpage.setVisibility(View.VISIBLE);
                }else {
                    holder.startAnimation(createAnimationForTopElements("dislike"));
                    user_instance.getInformation(peoples.get(lastPosition).getId(),peoples.get(lastPosition).getGender());
                }
                break;
        }
    }

    private void registerUserToGIFsterServer() {
        String gender = null;
        try {
            gender = getActivity().getIntent().getExtras().getString("gender");
        }catch (Exception e){
            gender = "null";
        }
        user_instance.sendRequest(AccessToken.getCurrentAccessToken().getUserId(),
                String.valueOf(new GPSTracker(getContext()).getLatitude()),
                String.valueOf(new GPSTracker(getContext()).getLongitude()), FirebaseInstanceId.getInstance().getToken(),gender);
    }

    @Override
    public void getRetrievedPeople(List<People> peopleList) {
            peoples.clear();
            peoples.addAll(peopleList);
        if(peoples.size()>0) {
            user_instance.getInformation(peoples.get(0).getId(),peoples.get(0).getGender());
        }else{
            pbar.setVisibility(View.GONE);
            holder.setVisibility(View.GONE);
            errorpage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void createList(People people) {
            if(people.getName()!=null) {
                String gender = preferences.getString("preferred", "female");
                if (people.getGender().equals(gender)){
                    name.setText(people.getFirst_name());
                Glide.with(getContext()).load(people.getProfile_url()).centerCrop()
                        .crossFade()
                        .into(profile_pic);
                holder.setVisibility(View.VISIBLE);
                if (pbar.isShown()) {
                    pbar.setVisibility(View.GONE);
                }
                }else{
                    lastPosition++;
                    if(lastPosition<peoples.size()){
                        user_instance.getInformation(peoples.get(lastPosition).getId(),peoples.get(lastPosition).getGender());
                    }else {
                        pbar.setVisibility(View.GONE);
                        holder.setVisibility(View.GONE);
                        errorpage.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    public Animation createAnimationForLastElement(String type){
        user_instance.sendLikeStatus(AccessToken.getCurrentAccessToken().getUserId(), peoples.get(peoples.size()-1).getId(), type);
        Animation animation = null;
        if(type.equals("dislike"))
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.move_left);
        else
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.move_right);
        animation.setFillAfter(true);
        return animation;
    }
    public Animation createAnimationForTopElements(String type) {
        user_instance.sendLikeStatus(AccessToken.getCurrentAccessToken().getUserId(), peoples.get(lastPosition).getId(), type);
        lastPosition++;
        Animation animation = null;
        if(type.equals("dislike"))
            animation =  AnimationUtils.loadAnimation(getContext(), R.anim.move_left);
        else
            animation = AnimationUtils.loadAnimation(getContext(), R.anim.move_right);
        return animation;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.thumbsdown:
                    thumbdown.getDrawable().setColorFilter(new
                            PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
                    break;
                case R.id.thumbsup:
                    thumbup.getDrawable().setColorFilter(new
                            PorterDuffColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY));
                    break;
            }
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            switch (v.getId()) {
                case R.id.thumbsdown:
                    thumbdown.getDrawable().setColorFilter(new
                            PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
                    break;
                case R.id.thumbsup:
                    thumbup.getDrawable().setColorFilter(new
                            PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
                    break;
            }
        }
        return false;
    }
}
