package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.ChatProcesses;

import java.util.List;

/**
 * Created by Salih on 6.06.2016.
 */
public class ChatKeyboardAdapter extends RecyclerView.Adapter<ChatKeyboardAdapter.VHolder> {

    List<Gifs> _list;
    Context _context;
    String opponentID;
    public ChatKeyboardAdapter (List<Gifs> list, Context context,String id){
        this._list=list;
        this._context=context;
        this.opponentID = id;
    }
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gifs;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            gifs = (ImageView)itemView.findViewById(R.id.gif_chat_single);
            gifs.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.gif_chat_single:
                    new ChatProcesses(null,_context).sendMessage(opponentID,_list.get(getPosition()).getUrl());
                    break;
            }
        }
    }
    @Override
    public ChatKeyboardAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inner_chat_keyboard_item, parent, false);
        return new VHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(ChatKeyboardAdapter.VHolder holder, int position) {
        String url = _list.get(position).getUrl();
        Glide.with(_context)
                .load(Uri.parse(url))
                .asGif().placeholder(R.drawable.rotate_animation).crossFade()
                .into(holder.gifs);
    }

    @Override
    public int getItemCount() {
        return _list.size();
    }
}
