package com.teamspeaghetti.www.gifster.interiorapplication.commonclasses;

import android.app.Application;

import com.teamspeaghetti.www.gifster.interiorapplication.receiver.ConnectivityReceiver;

/**
 * Created by Salih on 24.06.2016.
 */
public class MainApplication extends Application {
    private static MainApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
