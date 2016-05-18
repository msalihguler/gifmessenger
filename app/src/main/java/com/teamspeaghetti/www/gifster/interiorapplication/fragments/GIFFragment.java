package com.teamspeaghetti.www.gifster.interiorapplication.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.adapter.GifAdapter;
import com.teamspeaghetti.www.gifster.interiorapplication.interfaces.IRetrieveGIFs;
import com.teamspeaghetti.www.gifster.interiorapplication.model.Gifs;
import com.teamspeaghetti.www.gifster.interiorapplication.presenters.AskingGIFProcess;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Salih on 16.05.2016.
 */
public class GIFFragment extends Fragment implements IRetrieveGIFs {

    ProgressBar progressBar;
    EditText gifsearch;
    ImageView giflogo;
    RecyclerView gifholder;
    List<Gifs> real_gif_list = new ArrayList<Gifs>();
    GifAdapter adapter;
    AskingGIFProcess askingGIFProcess;
    LinearLayoutManager linearLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.gif_content,null);
        gifsearch = (EditText)rootView.findViewById(R.id.gif_search);
        giflogo = (ImageView)rootView.findViewById(R.id.giflogo);
        gifholder = (RecyclerView)rootView.findViewById(R.id.gif_holder);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        askingGIFProcess = new AskingGIFProcess(this);
        adapter = new GifAdapter(real_gif_list,askingGIFProcess);
        linearLayoutManager = new LinearLayoutManager(getContext());
        gifholder.setLayoutManager(linearLayoutManager);
        gifholder.setAdapter(adapter);
        gifsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    progressBar.setVisibility(View.VISIBLE);
                    askingGIFProcess.getGIFS(gifsearch.getText().toString(),real_gif_list,true);
                    return true;
                }else{
                    return true;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void retrieveGIFs(List<Gifs> gifsList) {
        real_gif_list.clear();
        real_gif_list.addAll(gifsList);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}
