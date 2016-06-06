package com.teamspeaghetti.www.gifster.interiorapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.teamspeaghetti.www.gifster.R;

/**
 * Created by Salih on 6.06.2016.
 */
public class ChatActivity extends AppCompatActivity {
    Button toggle;
    Boolean toogled=true;
    CardView keyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(getIntent().getExtras().getString("name"));
        final Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);

        final Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        slide_up.setFillAfter(true);
        slide_down.setFillAfter(true);
        keyboard = (CardView)findViewById(R.id.keyboard);
        toggle = (Button)findViewById(R.id.toggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toogled){
                    keyboard.startAnimation(slide_down);
                    toogled=false;
                }else{
                    keyboard.startAnimation(slide_up);
                    toogled=true;
                }
            }
        });

    }
}
