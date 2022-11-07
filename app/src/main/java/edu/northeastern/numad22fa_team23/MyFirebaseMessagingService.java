package edu.northeastern.numad22fa_team23;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import android.os.Handler;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final Map<Integer, Integer> emoji = new HashMap<>();


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        System.out.println("From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            System.out.println("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //sendNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
        //sendNotification(remoteMessage.getNotification().getBody());
        showNotification(remoteMessage.getNotification(), Integer.parseInt(Objects.requireNonNull(remoteMessage.getData().get("image_id"))));
    }

    private void showNotification(RemoteMessage.Notification remoteMessageNotification, int imageId) {
        System.out.println("start processing show notification");
        Intent intent = new Intent(this, SendingMessage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        Notification notification;
        NotificationCompat.Builder builder;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("CHANNEL_DESCRIPTION");
        notificationManager.createNotificationChannel(notificationChannel);
        builder = new NotificationCompat.Builder(this, "CHANNEL_ID");

        System.out.println("notification title: " + remoteMessageNotification.getTitle());

        emoji.put(R.id.image01, R.drawable.image01);
        emoji.put(R.id.image02, R.drawable.image02);
        emoji.put(R.id.image03, R.drawable.image03);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), emoji.get(imageId));
        notification = builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessageNotification.getTitle())
                .setContentText(remoteMessageNotification.getBody())
                .setLargeIcon(bm)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bm)
                        .bigLargeIcon(null))
                .addAction(R.mipmap.ic_launcher, "Emoji", pendingIntent)
                .build();
        notificationManager.notify(0, notification);

    }

    private void sendNotification(String from, String body) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MyFirebaseMessagingService.this.getApplicationContext(), from + " " + body, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE);

        String channelId = "my channel id";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_stat_notification)
                        .setContentTitle("new test message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
