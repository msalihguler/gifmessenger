package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
        public VHolder(View itemView,int viewtype) {
            super(itemView);
        }
    }
    @Override
    public ChatAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.VHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return _matches.size();
    }
}
