package com.teamspeaghetti.www.gifster.interiorapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.Profile;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.commonclasses.Utils;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRequestHolder;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskingGIFProcess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
    public void onBindViewHolder(final VHolder holder, int position) {
        try {
            if(position==gifsList.size()){
                holder.loadmore.setVisibility(View.VISIBLE);
                holder.pBar.setVisibility(View.GONE);
                holder.loadmore.setText(_context.getResources().getString(R.string.loadmore));
            }else{
                holder.loading.setVisibility(View.VISIBLE);
                String url = gifsList.get(position).getUrl();
                Glide.with(_context)
                        .load(Uri.parse(url))
                        .asGif().crossFade().listener(new RequestListener<Uri, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Uri model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.loading.setVisibility(View.GONE);
                        return false;
                    }
                })
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
        ProgressBar pBar,loading;
        public VHolder(View itemView,int viewtype) {
            super(itemView);
            if(viewtype==VIEW_TYPE_FOOTER) {
                loadmore = (Button) itemView.findViewById(R.id.loadmorebutton);
                pBar = (ProgressBar)itemView.findViewById(R.id.loadmoreprogress);
                loadmore.setOnClickListener(this);
            }else if(viewtype==VIEW_TYPE_CELL) {
                gif_single = (ImageView) itemView.findViewById(R.id.gif_single);
                loading = (ProgressBar)itemView.findViewById(R.id.loading);
                gif_single.setOnClickListener(this);
            }

        }
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.gif_single:
                    final View v_send = view;
                    snackbar = Snackbar.make(view, "",
                            Snackbar.LENGTH_INDEFINITE).setAction(_context.getResources().getString(R.string.tofavourites), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addToKeyBoard(gifsList.get(getPosition()).getUrl(),v_send);
                        }
                    });
                    snackbar.show();
                    break;
                case R.id.loadmorebutton:
                    view.setVisibility(View.GONE);
                    pBar.setVisibility(View.VISIBLE);
                    askingGIFProcess.getGIFS("",gifsList,false);
                    break;
            }

        }
    }
    public void addToKeyBoard(String url, final View view){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(_context.getResources().getString(R.string.serverurl)).addConverterFactory(GsonConverterFactory.create()).build();
        IRequestHolder requestInterface =retrofit.create(IRequestHolder.class);
        Call<ResponseBody> call = requestInterface.addToKeyBoard(url, Profile.getCurrentProfile().getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if(!jsonObject.getBoolean("error")){
                        Utils.createSnackBar(view,jsonObject.getString("message"));
                    }else{
                        Utils.createSnackBar(view,jsonObject.getString("message"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
