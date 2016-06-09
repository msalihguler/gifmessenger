package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Salih on 9.06.2016.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.VHolder> {

    List<HashMap<String,Gifs>> list;
    Context _context;

    public ConversationAdapter(List<HashMap<String,Gifs>> _list,Context context){
            this.list = _list;
            this._context = context;
    }
    @Override
    public ConversationAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_item, parent, false);
        return new VHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter.VHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class VHolder extends RecyclerView.ViewHolder {
        TextView date;
        ImageView gif;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            gif = (ImageView)itemView.findViewById(R.id.gif_to_show);
        }
    }
}
