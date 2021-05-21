package com.example.smartcalendar.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.smartcalendar.R;

public class Music extends Service {

    MediaPlayer mediaPlayer;
    int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Music", "Done");

        String nhan = intent.getExtras().getString("extra");
        Log.d("Music", "Key: " + nhan);

        if (nhan.equals("on"))
            id = 1;
        else if (nhan.equals("off"))
            id = 0;

        if (id == 1) {
            addNotification();
            mediaPlayer = MediaPlayer.create(this, R.raw.nhacchuong);
            mediaPlayer.start();
            id = 0;
        } else if (id == 0) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
        return START_NOT_STICKY;
    }

    private void addNotification() {
        String strTitle = "Smart Calendar";
        String strMsg = "It's time!!!";
        Intent notificationIntent = new Intent(this, NotificationDetailActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", strMsg);
        notificationIntent.putExtra("title", strTitle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Channel-001").setSmallIcon(R.drawable.ic_alarm).setContentTitle(strTitle).setContentText(strMsg).setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
