package com.teamspeaghetti.www.gifster.interiorapplication.commonclasses;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Salih on 20.05.2016.
 */
public class KeyboardItems extends RelativeLayout {

    public KeyboardItems(Context context) {
        super(context);
    }

    public KeyboardItems(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardItems(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyboardItems(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set a square layout.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
