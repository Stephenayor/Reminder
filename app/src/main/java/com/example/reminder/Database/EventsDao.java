package com.example.reminder.Database;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EventsDao {

    @Query("SELECT * FROM events")
    LiveData<List<EventsModel>> loadAllEvents();


    @Insert()
    void insertEvents(EventsModel eventsModel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvents(EventsModel eventsModel);

    @Delete
    void deleteTask(EventsModel eventsModel);

    @Query("SELECT * FROM events WHERE id = :id")
    LiveData<EventsModel> loadTaskById(int id);

}
