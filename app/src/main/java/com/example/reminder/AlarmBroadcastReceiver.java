package com.example.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminder.Database.EventsDao;
import com.example.reminder.Database.EventsDatabase;
import com.example.reminder.Database.EventsModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String RECURRING = "RECURRING";
    public static final String TITLE = "TITLE";
    public EventsModel eventsModel;
    private String currentDate;




    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            String toastText = String.format("ALARM REBOOT");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            startAlarmService(context,intent);
        }
        else {
            String toastText = String.format("ALARM RECEIVED");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            if (!intent.getBooleanExtra(RECURRING, false)) {
                startAlarmService(context, intent);
            }  {
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent);
                } else{
                    Toast.makeText(context, "CHECK YOUR DATE", Toast.LENGTH_SHORT).show();
                }
            }

        }}





    private boolean alarmIsToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_MONTH);
        return true;
    }
    
    private boolean DateChecker(EventsModel eventsModel){
        Log.d("AlarmDate", "Show Date in Alarm Class");
     //if (eventsModel.getDateSelected().equals(currentDate));
     return true;
    }

    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }

    private void startRescheduleAlarmsService(Context context) {
        Intent intentService = new Intent(context, AlarmService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }
}
