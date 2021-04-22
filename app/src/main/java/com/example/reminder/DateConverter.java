package com.example.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.DialogFragment;
import androidx.room.TypeConverter;

public class DateConverter {
    @TypeConverter
    public static Date toDate(String stringDate){
        return stringDate == null ? null : new Date(stringDate);
    }

    @TypeConverter
    public static String toTimeStamp(Date date){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateTimeFormat.format(date);
    }
}
//public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Use the current date as the default date in the picker
//        final Calendar calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = Integer.valueOf(calendar.get(Calendar.MONTH) + 1);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        // Create a new instance of DatePickerDialog and return it
//        return new DatePickerDialog(getActivity(), this, year, month, day);
//    }
//
//    public void onDateSet(DatePicker view, int year, int month, int day) {
//        final Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR,year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH,day);
//        int Month = month+1;
//        textView.setText( + year + "-" + Month + "-" + day);
//
//    }
//
//}
//
//    public void showDatePickerDialog(View v) {
//        datePickerButton.setVisibility(View.GONE);
//        DialogFragment newFragment = new AddEvents.DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
//
//    }