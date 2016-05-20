package com.teamspeaghetti.www.gifster.interiorapplication.adapter;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.fragments.FullScreenGIFsFragment;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import java.util.List;

/**
 * Created by Salih on 20.05.2016.
 */
public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.VHolder> {
    List<Gifs> list;
    Context _context;
    public KeyboardAdapter(List<Gifs> temp,Context context){
        this.list=temp;
        this._context=context;
    }
    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.keyboard_item, parent, false);

        return new VHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        String url = list.get(position).getUrl();
        String temp = url.substring(0,url.length()-6);
        temp = temp+"_s.gif";
        Glide.with(_context).load(temp)
                .thumbnail(0.5f)
                .crossFade()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        public VHolder(View itemView) {
            super(itemView);
            thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FullScreenGIFsFragment alertDialog = new FullScreenGIFsFragment(list,getPosition());
                    Utils.startFragmentWithStack(alertDialog,((FragmentActivity)_context).getSupportFragmentManager(),"FullScreen");
                }
            });
        }
    }
}
