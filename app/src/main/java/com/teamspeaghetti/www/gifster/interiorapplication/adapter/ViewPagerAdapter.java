package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import java.util.List;

/**
 * Created by Salih on 20.05.2016.
 */
public class ViewPagerAdapter extends PagerAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<Gifs> list;
    public ViewPagerAdapter(Context _context,List<Gifs> _list){
        this.context = _context;
        this.list=_list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.keyboard_full_screen_item, container, false);
        ImageView image = (ImageView)view.findViewById(R.id.image_preview);
        Glide.with(context).load(list.get(position).getUrl())
                .crossFade()
                .into(image);
        container.addView(view);
        Log.e("list",list.get(position).getUrl());
        return view;
    }
    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
