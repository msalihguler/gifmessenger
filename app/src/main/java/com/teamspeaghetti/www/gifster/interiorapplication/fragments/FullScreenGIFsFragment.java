package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.ViewPagerAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskSavedGIFs;

import java.util.List;

/**
 * Created by Salih on 20.05.2016.
 */
@SuppressLint("ValidFragment")
public class FullScreenGIFsFragment extends Fragment implements View.OnClickListener,IRetrieveGIFs {
    ViewPager viewpager;
    ViewPagerAdapter adapter;
    List<Gifs> list;
    int pos;
    ImageView delete,send;

    @SuppressLint("ValidFragment")
    public FullScreenGIFsFragment(List<Gifs> gifs,int _pos){
        this.list = gifs;
        this.pos = _pos;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.keyboard_fullscreen, container, false);
        viewpager = (ViewPager)v.findViewById(R.id.fullgifs);
        delete = (ImageView)v.findViewById(R.id.delete);
        send = (ImageView)v.findViewById(R.id.send);
        delete.setOnClickListener(this);
        send.setOnClickListener(this);
        adapter= new ViewPagerAdapter(getContext(),list);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(pos);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getResources().getString(R.string.deletetitle));
                builder.setMessage(getResources().getString(R.string.deletemessage));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = viewpager.getCurrentItem();
                     new AskSavedGIFs(FullScreenGIFsFragment.this).deleteGIF(list.get(position).getUrl(),list,position);
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.show();
                break;
            case R.id.send:
                break;
        }

    }

    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        list.clear();
        list.addAll(gifsList);
        adapter.notifyDataSetChanged();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
