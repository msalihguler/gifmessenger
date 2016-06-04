package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;

import java.util.List;

/**
 * Created by Salih on 4.06.2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VHolder> {

    Context _context;
    List<People> _matches;
    public ChatAdapter(Context context, List<People> matches){
        this._context=context;
        this._matches=matches;
    }
    public class VHolder extends RecyclerView.ViewHolder{
        ImageView profile_pic;
        TextView name;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            profile_pic=(ImageView)itemView.findViewById(R.id.mini_profile_photo);
            name = (TextView)itemView.findViewById(R.id.mini_profile_name);
        }
    }
    @Override
    public ChatAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _context=parent.getContext();
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_list_item, parent, false);
            return new VHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.VHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return _matches.size();
    }
}
