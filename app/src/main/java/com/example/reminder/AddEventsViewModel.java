package com.example.reminder;

import com.example.reminder.Database.EventsDatabase;
import com.example.reminder.Database.EventsModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddEventsViewModel extends ViewModel {

    private LiveData<EventsModel> eventsModelLiveData;

    public AddEventsViewModel(EventsDatabase database, int eventsId) {
        eventsModelLiveData = database.eventsDao().loadTaskById(eventsId);
    }

    public LiveData<EventsModel> getEventsModelLiveData(){
        return eventsModelLiveData;
    }

}
