package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.wooplr.spotlight.SpotlightView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Salih on 17.06.2016.
 */
public class RevealedProfileAdapter extends RecyclerView.Adapter<RevealedProfileAdapter.VHolder> {

    Context _context;
    List<JSONObject> _reveals;
    public RevealedProfileAdapter(Context context, List<JSONObject> reveals){
        this._context=context;
        this._reveals=reveals;
    }
    public class VHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView profilePicture;
        TextView  profileName;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            profilePicture = (ImageView)itemView.findViewById(R.id.revealed_profile_picture);
            profileName    = (TextView)itemView.findViewById(R.id.revealed_profile_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent browserIntent = null;
            try {
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(_reveals.get(getPosition()).getString("link")));
                _context.startActivity(browserIntent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        _context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.revealed_profile_item, parent, false);
        return new VHolder(itemView,viewType);
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {
        try {
            holder.profileName.setText(_reveals.get(position).getString("name"));
            Picasso.with(_context)
                    .load(_reveals.get(position).getString("pic_link")).into(holder.profilePicture);
            if(position==0) {
                Utils.createShowcase(_context,holder.profileName,_context.getResources().getString(R.string.reveals),
                        _context.getResources().getString(R.string.profilecardexplanation),_context.getResources().getString(R.string.profilecardkey));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return _reveals.size();
    }
}
