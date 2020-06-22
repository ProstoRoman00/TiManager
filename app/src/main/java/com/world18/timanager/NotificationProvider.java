package com.world18.timanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

public class NotificationProvider {
    private Context context;
    public NotificationProvider(Context context) {
        this.context=context;
    }

    public void sendNotification(String title, String body, int notificationRequestCode) {
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //PendingIntent pi = PendingIntent.getActivity(this, 0 /* Request code */, i, PendingIntent.FLAG_ONE_SHOT);
        PendingIntent pi = PendingIntent.getActivity(context, notificationRequestCode, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sound)
                .setContentIntent(pi);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }
}