package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Salih on 9.06.2016.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.VHolder> {

    List<JSONObject> list;
    Context _context;

    public ConversationAdapter(List<JSONObject> _list,Context context){
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
        String url = null;
        String date = null;
        String sender =null;
        try {
            url = list.get(position).getString("message");
            date = list.get(position).getString("_id");
            sender = list.get(position).getString("sender_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int x =Integer.parseInt(date.substring(0,8), 16);
        String milliSeconds = String.valueOf(x)+"000";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        holder.date.setText(new SimpleDateFormat("dd-MM-yyyy HH:MM").format(calendar.getTime()));
        Glide.with(_context)
                .load(Uri.parse(url))
                .asGif().placeholder(R.drawable.rotate_animation).crossFade()
                .into(holder.gif);
        if(sender.equals(Profile.getCurrentProfile().getId()))
                holder.layout.setGravity(Gravity.RIGHT);
        else
                holder.layout.setGravity(Gravity.LEFT);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class VHolder extends RecyclerView.ViewHolder {
        TextView date;
        ImageView gif;
        LinearLayout layout;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            date = (TextView)itemView.findViewById(R.id.date);
            gif = (ImageView)itemView.findViewById(R.id.gif_to_show);
            layout = (LinearLayout)itemView.findViewById(R.id.messagelement);
        }
    }
}
