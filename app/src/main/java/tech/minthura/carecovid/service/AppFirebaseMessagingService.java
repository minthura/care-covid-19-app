package tech.minthura.carecovid.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import tech.minthura.carecovid.LaunchActivity;
import tech.minthura.carecovid.R;
import tech.minthura.caresdk.Session;
import tech.minthura.caresdk.model.NotificationMessageEvent;

public class AppFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(this.getClass().getSimpleName(), "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        Map<String, String> data = remoteMessage.getData();
        if (data.size() > 0) {
            Log.d(this.getClass().getSimpleName(), "Message data payload: " + data);
            Bundle bundle = new Bundle();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                bundle.putString(entry.getKey(), entry.getValue());
            }
            String title = bundle.getString("TITLE", "CareCovid19");
            String body = bundle.getString("BODY", "Thank you for using this app.");
            if (Session.getSession().getState() == Session.State.CLOSED){
                Session.getSession().saveLastNotification(title, body);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                final String NOTIFICATION_CHANNEL_ID = getString(R.string.default_notification_channel_id);
                Intent intent = new Intent(this, LaunchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_virus_notification)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "CARECOVID_NOTIFICATION_CHANNEL", importance);
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                notificationManager.notify(0, builder.build());

            } else {
                EventBus.getDefault().post(new NotificationMessageEvent(title, body));
            }
        }

        /*// Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            EventBus.getDefault().post(new NotificationMessageEvent(title, body));
            Log.d(this.getClass().getSimpleName(), "Message Notification Body: " + body);
        }*/
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(this.getClass().getSimpleName(), "Refreshed token: " + token);
        Session.getSession().registerDevice(token);
    }
}
