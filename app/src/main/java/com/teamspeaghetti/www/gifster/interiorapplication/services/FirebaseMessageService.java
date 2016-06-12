package com.teamspeaghetti.www.gifster.interiorapplication.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.teamspeaghetti.www.gifster.R;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.ChatActivity;
import com.teamspeaghetti.www.gifster.interiorapplication.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Salih on 10.06.2016.
 */
public class FirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("From", "From: " + remoteMessage.getFrom());
        Log.d("Body", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        if(ChatActivity.active) {
            try {
                Intent intent = new Intent();
                intent.setAction("com.teamspaghetti.gifster.newmessage");
                intent.putExtra("jsonObject",remoteMessage.getData().get("message"));
                sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        sendNotification(remoteMessage.getNotification().getBody());
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.chat)
                .setContentTitle(getApplicationContext().getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
