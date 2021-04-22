package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.reminder.Database.EventsDatabase;
import com.example.reminder.Database.EventsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventsAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private List<EventsModel> eventsModelList;
    private EventsDatabase eventsDatabase;
    private EventsAdapter eventsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.events_recycler_view);
        eventsDatabase = EventsDatabase.getInstance(getApplicationContext());


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventsAdapter = new EventsAdapter(eventsModelList,this,this);
        recyclerView.setAdapter(eventsAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent startAddEvents = new Intent(MainActivity.this,AddEvents.class);
               startActivity(startAddEvents);
            }
    });

              /*
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                    EventsExecutor.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            int position = viewHolder.getAdapterPosition();
                            List<EventsModel> events = eventsAdapter.getEvents();
                            eventsDatabase.eventsDao().deleteTask(events.get(position));
                        }
                    });

            }

        }).attachToRecyclerView(recyclerView);
        setupViewModel();



}

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getEventsModel().observe(this, new Observer<List<EventsModel>>() {
            @Override
            public void onChanged(List<EventsModel> modelList) {
                eventsAdapter.setEvents(modelList);
            }
        });
    }



    @Override
    public void onItemClickListener(int eventId){
        Intent intent = new Intent(MainActivity.this,AddEvents.class);
        intent.putExtra(AddEvents.EXTRA_EVENT_ID,eventId);
        startActivity(intent);
    }
}