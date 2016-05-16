package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskingGIFProcess;

import java.util.List;


/**
 * Created by Salih on 11.05.2016.
 */
public class GifAdapter extends RecyclerView.Adapter<GifAdapter.VHolder> {

    List<Gifs> gifsList;
    Context _context;
    Snackbar snackbar;
    AskingGIFProcess askingGIFProcess;
    public static int VIEW_TYPE_FOOTER=1;
    public static int VIEW_TYPE_CELL=0;

    public GifAdapter(List<Gifs> retrievedList, AskingGIFProcess askingGIFProcess){
        this.gifsList=retrievedList;
        this.askingGIFProcess = askingGIFProcess;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == gifsList.size()&&position!=0) ? VIEW_TYPE_FOOTER : VIEW_TYPE_CELL;
    }
    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _context=parent.getContext();
        if (viewType == VIEW_TYPE_CELL) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gif_list_row, parent, false);
            return new VHolder(itemView,viewType);
        }else if(viewType == VIEW_TYPE_FOOTER){
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.load_more, parent, false);
            return new VHolder(itemView,viewType);
        }else
            return null;
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        try {
            if(position==gifsList.size()){
                holder.loadmore.setVisibility(View.VISIBLE);
                holder.pBar.setVisibility(View.GONE);
                holder.loadmore.setText("LOAD MORE...");
            }else{
                String url = gifsList.get(position).getUrl();
                Glide.with(_context)
                        .load(Uri.parse(url))
                        .asGif().placeholder(R.drawable.rotate_animation).crossFade()
                        .into(holder.gif_single);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public int getItemCount() {
            return gifsList.size()+1;
    }

    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gif_single;
        Button loadmore;
        ProgressBar pBar;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            if(viewtype==VIEW_TYPE_FOOTER) {
                loadmore = (Button) itemView.findViewById(R.id.loadmorebutton);
                pBar = (ProgressBar)itemView.findViewById(R.id.loadmoreprogress);
                loadmore.setOnClickListener(this);
            }else if(viewtype==VIEW_TYPE_CELL) {
                gif_single = (ImageView) itemView.findViewById(R.id.gif_single);
                gif_single.setOnClickListener(this);
            }

        }
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.gif_single:
                    snackbar = Snackbar.make(view, String.valueOf(getPosition()),
                            Snackbar.LENGTH_INDEFINITE).setAction("ADD TO FAVOURITES", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.setText("Tıklandı");
                        }
                    });
                    snackbar.show();
                    break;
                case R.id.loadmorebutton:
                    view.setVisibility(View.GONE);
                    pBar.setVisibility(View.VISIBLE);
                    askingGIFProcess.getGIFS("whatever",gifsList,false);
                    break;
            }

        }
    }

}