package com.example.reminder.Database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.reminder.AlarmBroadcastReceiver;

import java.util.Calendar;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "events")
public class EventsModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String eventName;
    private String timeSelected;
    private  String dateSelected;
    private int hour, minute;


    @Ignore
    public EventsModel(String eventName, String timeSelected, String dateSelected,int hour, int minute) {
        this.eventName = eventName;
        this.timeSelected = timeSelected;
        this.dateSelected = dateSelected;
        this.hour = hour;
        this.minute = minute;
    }

    public EventsModel(int id, String eventName, String timeSelected, String dateSelected, int hour, int minute) {
        this.id = id;
        this.eventName = eventName;
        this.timeSelected = timeSelected;
        this.dateSelected = dateSelected;
        this.hour = hour;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTimeSelected() {
        return timeSelected;
    }

    public void setTimeSelected(String timeSelected) {
        this.timeSelected = timeSelected;
    }

    public String getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(String dateSelected) {
        this.dateSelected = dateSelected;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}

