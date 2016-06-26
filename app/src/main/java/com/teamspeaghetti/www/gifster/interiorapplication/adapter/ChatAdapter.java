package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AlertDialog;
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
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestHolder;
import com.teamspeaghetti.www.gifster.interiorapplication.model.People;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.UserProcesses;
import com.wooplr.spotlight.SpotlightView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Salih on 4.06.2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.VHolder> {

    //Variable Declarations
    Context _context;
    List<People> _matches;

    public ChatAdapter(Context context, List<People> matches){
        this._context=context;
        this._matches=matches;
    }
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //ViewHolder variable declarations
        ImageView profile_pic,reveal_profile,message_count;
        TextView name;
        RelativeLayout to_chat;

        public VHolder(View itemView,int viewtype) {
            super(itemView);

            //Initialize ViewHolder variables
            profile_pic=(ImageView)itemView.findViewById(R.id.mini_profile_photo);
            reveal_profile = (ImageView)itemView.findViewById(R.id.revealProfile);
            message_count=(ImageView)itemView.findViewById(R.id.message_count);
            name = (TextView)itemView.findViewById(R.id.mini_profile_name);
            to_chat = (RelativeLayout)itemView.findViewById(R.id.person_info_line);

            //Onlisteners
            to_chat.setOnClickListener(this);
            reveal_profile.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.revealProfile:
                    AlertDialog.Builder builder = new AlertDialog.Builder(_context);
                    builder.setMessage(R.string.reveal_message)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new UserProcesses(_context).revealProfile(_matches.get(getPosition()).getId());
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    // Create the AlertDialog object and return it
                    Dialog alert = builder.create();
                    alert.show();
                    break;
                case R.id.person_info_line:
                    Intent intent = new Intent((MainActivity) _context, ChatActivity.class);
                    intent.putExtra("name", _matches.get(getPosition()).getFirst_name());
                    intent.putExtra("id", _matches.get(getPosition()).getId());
                    _context.startActivity(intent);
                    message_count.setVisibility(View.GONE);
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
    public void onBindViewHolder(final ChatAdapter.VHolder holder, int position) {
        holder.name.setText(_matches.get(position).getFirst_name());
        Picasso.with(_context).load(_matches.get(position).getProfile_url()).resize(75,75).transform(new CircleTransform()).into(holder.profile_pic);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.getNumber(Profile.getCurrentProfile().getId(),_matches.get(position).getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    int count = Integer.parseInt(response.body().string());
                    if(count==0){
                        holder.message_count.setVisibility(View.GONE);
                    }
                    else if(count==1){
                        holder.message_count.setVisibility(View.VISIBLE);
                        holder.message_count.setImageResource(R.drawable.one_indicator);
                    }else if(count==2){
                        holder.message_count.setVisibility(View.VISIBLE);
                        holder.message_count.setImageResource(R.drawable.two_indicator);
                    }else if(count==3){
                        holder.message_count.setVisibility(View.VISIBLE);
                        holder.message_count.setImageResource(R.drawable.three_indicator);
                    }else if(count==4){
                        holder.message_count.setVisibility(View.VISIBLE);
                        holder.message_count.setImageResource(R.drawable.four_indicator);
                    }else if(count==5){
                        holder.message_count.setVisibility(View.VISIBLE);
                        holder.message_count.setImageResource(R.drawable.five_indicator);
                    }else{
                        holder.message_count.setVisibility(View.VISIBLE);
                        holder.message_count.setImageResource(R.drawable.more_indicator);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        if(position==0){
            Utils.createShowcase(_context,holder.reveal_profile,_context.getResources().getString(R.string.messagelisttitle)
                    ,_context.getResources().getString(R.string.messagelistexplanation),_context.getResources().getString(R.string.revealkey));
        }
    }

    @Override
    public int getItemCount() {
        return _matches.size();
    }
}
