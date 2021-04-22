package com.example.reminder;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.se.omapi.Channel;

import com.example.reminder.Database.EventsModel;
import com.example.reminder.R;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmService extends Service {

    private static final String EVENTNAME = "eventName";

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private EventsModel eventsModel;

    @Override
    public void onCreate() {
        super.onCreate();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, AddEvents.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String notificationTitle = String.format("%s Alarm", intent.getStringExtra("hello"));
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"ALARM_SERVICE_CHANNEL")
                .setSmallIcon(R.drawable.reminder_icon)
                .setContentTitle(notificationTitle)
                .setContentText("You have an Upcoming Event")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        startForeground(1, notificationBuilder.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        //Notification id is a unique int for each notification that you must define
        int notificationId = 1;
        notificationManager.notify(notificationId, notificationBuilder.build());


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}