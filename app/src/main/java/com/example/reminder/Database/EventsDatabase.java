package com.example.reminder.Database;

import android.content.Context;
import android.util.Log;

import com.example.reminder.DateConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {EventsModel.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class EventsDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "EventsList";
    private static final String LOG_TAG = EventsDatabase.class.getSimpleName();
    private static  EventsDatabase eventsInstance;

    public static EventsDatabase getInstance(Context context){
        if (eventsInstance == null){
            synchronized (LOCK){
                eventsInstance = Room.databaseBuilder(context.getApplicationContext(),EventsDatabase.class,EventsDatabase.DATABASE_NAME)
                        //Queries are been done in a separate thread to avoid locking the UI
                        .build();
        }
    }

        return eventsInstance;
}
    public abstract EventsDao eventsDao();
}
