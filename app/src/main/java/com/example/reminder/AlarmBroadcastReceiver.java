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
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String RECURRING = "RECURRING";
    public static final String TITLE = "TITLE";
    private EventsModel eventsModel;
    // eventsModel = new EventsModel(Alarm,"","",14,30);
    private String eventName = "";
    private String dateSelected = "";
    private String timeSelected = "";
    private int timeHour = 0;
    private int timeMinute = 0;
    private String currentDate;
    private TimePicker timePicker;
    private EventsDatabase eventsDatabase;
    private String[] mDateSplit;
    private String[] mTimeSplit;
    private int mYear, mMonth, mHour, mMinute, mDay, mReceivedID;



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
            } {

//            eventsDatabase = EventsDatabase.getInstance(context);
//                     Calendar calendar = Calendar.getInstance();
//                     AlarmService AlarmReceiver = new AlarmService();
//
//                     LiveData<List<EventsModel>> modelList = eventsDatabase.eventsDao().loadAllEvents();
//
//            if (modelList!=null){
//
//            dateSelected = eventsModel.getDateSelected();
//            timeSelected = eventsModel.getTimeSelected();
//
//            mDateSplit = dateSelected.split("-");
//            mTimeSplit = timeSelected.split("-");
//
//            mDay = Integer.parseInt(mDateSplit[2]);
//            mMonth = Integer.parseInt(mDateSplit[1]);
//            mYear = Integer.parseInt(mDateSplit[0]);
//            mHour = Integer.parseInt(mTimeSplit[0]);
//            mMinute = Integer.parseInt(mTimeSplit[1]);
//
//            calendar.set(Calendar.MONTH, --mMonth);
//            calendar.set(Calendar.YEAR, mYear);
//            calendar.set(Calendar.DAY_OF_MONTH, mDay);
//            calendar.set(Calendar.HOUR_OF_DAY, mHour);
//            calendar.set(Calendar.MINUTE, mMinute);
//            calendar.set(Calendar.SECOND, 0);
//
//            startAlarmService(context,intent);}
        }}}



    private boolean alarmIsToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_MONTH);

       // if (eventsModel.getDateSelected().equals(currentDate));
        Log.d("Today's Date", "The value of getDateSelected is showing");
       return true;
    }
    
    private boolean DateChecker(EventsModel eventsModel){
     if (eventsModel.getDateSelected().equals(currentDate));
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
