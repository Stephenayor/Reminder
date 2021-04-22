package com.example.reminder;

import android.app.Application;

import com.example.reminder.Database.EventsDatabase;
import com.example.reminder.Database.EventsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<EventsModel>> eventsModel;

    public MainViewModel(@NonNull Application application) {
        super(application);
        EventsDatabase database = EventsDatabase.getInstance(this.getApplication());
        eventsModel = database.eventsDao().loadAllEvents();
    }

    public LiveData<List<EventsModel>> getEventsModel(){
        return eventsModel;
    }
}
