package com.example.reminder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.reminder.Database.EventsDatabase;
import com.example.reminder.Database.EventsModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEvents extends AppCompatActivity {
    private static final String LOG_TAG = AddEvents.class.getSimpleName();
    public static final String EXTRA_EVENT_ID = "extraEventId";
    private static final int DEFAULT_EVENT_ID = -1;
    private static final String EVENTNAME = "eventsName";
    private TextInputLayout eventNameInput;
    private TextInputEditText eventNameEditText;
    private TextInputLayout selectTimeInput;
    private TextInputEditText selectTimeEditText;
    private static TextView textView;
    private Button datePickerButton;
    private Button addEventsButton;
    private EventsDatabase eventsDatabase;
    private String eventName;
    private String timeSelected;
    private String dateSelected;
    private int eventsId = DEFAULT_EVENT_ID;
    public  EventsModel eventsModel;
    public AlarmManager alarmManager;
    private PendingIntent alarmIntent;
    private TimePicker timePicker;
    private static Calendar calendar = Calendar.getInstance();
    private static int year;
    private static int month;
    private static int day;






    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);
        eventNameInput = findViewById(R.id.eventName_text_input);
        eventNameEditText = findViewById(R.id.eventName_edit_text);
        textView = findViewById(R.id.display_date_text);
        datePickerButton = findViewById(R.id.pick_date_button);
        addEventsButton = findViewById(R.id.add_events_button);
        eventsDatabase = EventsDatabase.getInstance(getApplicationContext());
        timePicker = findViewById(R.id.time_picker);


//        selectTimeEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                final Calendar myCalender = Calendar.getInstance();
////                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
////                int minute = myCalender.get(Calendar.MINUTE);
////
////
////                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
////                    @Override
////                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////                        String am_pm;
////                        if (hourOfDay >= 12) {
////                            am_pm = "PM";
////                        } else {
////                            am_pm = "AM";
////                        }
////                        selectTimeEditText.setText(hourOfDay + ":" + minute + "" + am_pm);
////
////
////                        if (view.isShown()) {
////                            myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
////                            myCalender.set(Calendar.MINUTE, minute);
////
////                        }
////                    }
////                };
////                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvents.this,
////                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, hour, minute, true);
////                timePickerDialog.setTitle("Choose hour:");
////                timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
////                timePickerDialog.show();
//                timePicker.is24HourView();
//
//            }
//
//
//        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_EVENT_ID)) {
            addEventsButton.setText("Update");
            textView.getText();

            if (eventsId == DEFAULT_EVENT_ID) {
                eventsId = intent.getIntExtra(EXTRA_EVENT_ID, DEFAULT_EVENT_ID);

                EventsViewModelFactory eventsViewModelFactory = new EventsViewModelFactory(eventsDatabase, eventsId);
                final AddEventsViewModel viewModel = ViewModelProviders.of(this, eventsViewModelFactory).
                        get(AddEventsViewModel.class);
                viewModel.getEventsModelLiveData().observe(this, new Observer<EventsModel>() {
                    @Override
                    public void onChanged(EventsModel eventsModel) {
                        viewModel.getEventsModelLiveData().removeObserver(this);
                        PopulateUI(eventsModel);

                    }
                });
            }

        }


    }

    private void PopulateUI(EventsModel eventsModel) {
        if (eventsModel == null) {
            return;
        }

        eventNameEditText.setText(eventsModel.getEventName());
        //selectTimeEditText.setText(eventsModel.getTimeSelected());
        Log.d("Date values", "Date return value");
        datePickerButton.setText(eventsModel.getDateSelected());

    }



    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar calendar = Calendar.getInstance();
          year = calendar.get(Calendar.YEAR);
          //month = Integer.valueOf(calendar.get(Calendar.MONTH) + 1);
          month = calendar.get(Calendar.MONTH);
          day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        int Month = month+1;
        textView.setText( + year + "-" + month + "-" + day);

    }

}

    public void showDatePickerDialog(View v) {
        datePickerButton.setVisibility(View.GONE);
        DialogFragment newFragment = new AddEvents.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClickAddEvents(View view) {
        eventName = eventNameEditText.getText().toString();
        timeSelected = selectTimeEditText.getText().toString();
        dateSelected = textView.getText().toString();

        saveEvents();
    }

    private void saveEvents() {
         eventsModel = new EventsModel(eventName, timeSelected, dateSelected,
                TimePickerUtil.getTimePickerHour(timePicker), TimePickerUtil.getTimePickerMinute(timePicker));
        EventsExecutor.getInstance().diskIO().execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                if (eventsId == DEFAULT_EVENT_ID) {
                    //Save new events
                    Log.d("Date Checker", "check if date values are saved" );
                    eventsDatabase.eventsDao().insertEvents(eventsModel);
                } else {
                    eventsModel.setId(eventsId);
                    eventsDatabase.eventsDao().updateEvents(eventsModel);
                }

                finish();
                scheduleAlarm(getApplicationContext());
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void scheduleAlarm(Context context)  {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(EVENTNAME, eventName);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
         final Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(System.currentTimeMillis());
            // The date selected by the user
            String selectedDate = eventsModel.getDateSelected();

          String[] chosenDate = selectedDate.split("-");
          String year = chosenDate[0];
          String month = chosenDate[1];
          String day = chosenDate[2];
          int chosenYear =Integer.parseInt(year);
          int chosenMonth = Integer.parseInt(month);
          int chosenDay = Integer.parseInt(day);


        calendar.set(Calendar.YEAR,chosenYear);
        calendar.set(Calendar.MONTH,chosenMonth);
        calendar.set(Calendar.DAY_OF_WEEK,chosenDay);
        calendar.set(Calendar.HOUR_OF_DAY, TimePickerUtil.getTimePickerHour(timePicker));
        calendar.set(Calendar.MINUTE, TimePickerUtil.getTimePickerMinute(timePicker));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                alarmIntent
        );

    }
    //TODO :
    // 1. The notification should display the alarm name
    // 2. Make the timepicker attractive
    // 3. Check the date again
    // 4. Clean the code more
    // 5. Use MVVM
}

