package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.squareup.picasso.Picasso;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.ChatActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.CircleTransform;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;

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
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView profile_pic,reveal_profile;
        TextView name;
        RelativeLayout to_chat;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            profile_pic=(ImageView)itemView.findViewById(R.id.mini_profile_photo);
            reveal_profile = (ImageView)itemView.findViewById(R.id.revealProfile);
            name = (TextView)itemView.findViewById(R.id.mini_profile_name);
            to_chat = (RelativeLayout)itemView.findViewById(R.id.person_info_line);
            to_chat.setOnClickListener(this);
            reveal_profile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.revealProfile:
                    new UserProcesses(_context).revealProfile(_matches.get(getPosition()).getId());
                    break;
                case R.id.person_info_line:
                    Intent intent = new Intent((MainActivity) _context, ChatActivity.class);
                    intent.putExtra("name", _matches.get(getPosition()).getFirst_name());
                    intent.putExtra("id", _matches.get(getPosition()).getId());
                    _context.startActivity(intent);
                    break;
            }
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
        holder.name.setText(_matches.get(position).getFirst_name());
        Picasso.with(_context).load(_matches.get(position).getProfile_url()).resize(75,75).transform(new CircleTransform()).into(holder.profile_pic);
    }

    @Override
    public int getItemCount() {
        return _matches.size();
    }
}
