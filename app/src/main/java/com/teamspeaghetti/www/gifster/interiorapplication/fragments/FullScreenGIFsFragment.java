package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ViewPagerAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import java.util.List;

/**
 * Created by Salih on 20.05.2016.
 */
public class FullScreenGIFsFragment extends Fragment implements View.OnClickListener {
    ViewPager viewpager;
    ViewPagerAdapter adapter;
    List<Gifs> list;
    int pos;
    ImageView delete,send;
    public FullScreenGIFsFragment(){
    }
    @SuppressLint("ValidFragment")
    public FullScreenGIFsFragment(List<Gifs> gifs,int _pos){
        this.list = gifs;
        this.pos = _pos;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.keyboard_fullscreen, container, false);
        viewpager = (ViewPager)v.findViewById(R.id.fullgifs);
        delete = (ImageView)v.findViewById(R.id.delete);
        send = (ImageView)v.findViewById(R.id.send);
        adapter= new ViewPagerAdapter(getContext(),list);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(pos);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.send){

        }else if(v.getId()==R.id.delete){

        }
    }
}
