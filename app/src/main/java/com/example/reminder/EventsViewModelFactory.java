package com.example.reminder;

import com.example.reminder.Database.EventsDatabase;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class EventsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final EventsDatabase eventsDatabase;
    private final int eventsId;

    public EventsViewModelFactory(EventsDatabase eventsDatabase, int eventsId) {
        this.eventsDatabase = eventsDatabase;
        this.eventsId = eventsId;
    }

    @Override
    public <T extends ViewModel> T create (Class<T> modelClass){
        return (T) new AddEventsViewModel(eventsDatabase,eventsId);
    }
}
