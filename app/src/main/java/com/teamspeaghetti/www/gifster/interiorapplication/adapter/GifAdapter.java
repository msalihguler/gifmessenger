package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
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
 * Created by Salih on 11.05.2016.
 */
public class GifAdapter extends RecyclerView.Adapter<GifAdapter.VHolder> {
    List<Gifs> gifsList;
    Context _context;

    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gif_single;
        public VHolder(View itemView) {
            super(itemView);
            gif_single = (ImageView)itemView.findViewById(R.id.gif_single);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Snackbar snackbar = Snackbar.make(view,String.valueOf(getPosition()),
                    Snackbar.LENGTH_SHORT).setAction("ADD TO FAVOURITES", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("view added","added");
                }
            });
            snackbar.show();
        }
    }
    public GifAdapter(List<Gifs> retrievedList){
        this.gifsList=retrievedList;

    }
    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_row, parent, false);

        return new VHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        String url = gifsList.get(position).getUrl();
        Log.e("url",url);
        Glide.with(_context)
                .load(Uri.parse(url))
                .asGif().placeholder(R.drawable.rotate_animation).crossFade()
                .into(holder.gif_single);
    }

    @Override
    public int getItemCount() {
        return gifsList.size();
    }


}
