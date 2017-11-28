package com.example.musab.samplecalender;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String SELECTED_DATE = "SELECTED_DATE";
    CompactCalendarView calendarView;
    TextView monthName;
    Date selectedDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = findViewById(R.id.calendarView);
        monthName = findViewById(R.id.monthName);
        final LinearLayout list = findViewById(R.id.list);

        Date date = null;
        calendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss", Locale.ENGLISH);
        String dateInString = "29-11-2017 10:20:56";
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        calendarView.addEvent(new Event(Color.BLUE, date.getTime(), "Brown Fox"), true);
        calendarView.addEvent(new Event(Color.BLUE, date.getTime(), "jumps over"), true);
        calendarView.addEvent(new Event(Color.BLUE, date.getTime(), "lazy dog"), true);
        calendarView.addEvent(new Event(Color.BLUE, date.getTime(), "comma"), true);

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_DATE)) {
            selectedDate = (Date) savedInstanceState.getSerializable(SELECTED_DATE);
            calendarView.setCurrentDate(selectedDate);
        }

        monthName.setText(new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(calendarView.getFirstDayOfCurrentMonth().getTime()));

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                selectedDate = dateClicked;
                list.removeAllViews();
                for (Event event : calendarView.getEvents(dateClicked)) {
                    TextView row = new TextView(MainActivity.this);
                    row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    row.setText(((String) event.getData()));
                    list.addView(row);
                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                monthName.setText(new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(calendarView.getFirstDayOfCurrentMonth().getTime()));
            }
        });

    }

    public void onPrevClicked(View view) {
        calendarView.showPreviousMonth();
        selectedDate = calendarView.getFirstDayOfCurrentMonth();
    }

    public void onNextClicked(View view) {
        calendarView.showNextMonth();
        selectedDate = calendarView.getFirstDayOfCurrentMonth();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (selectedDate != null)
            outState.putSerializable(SELECTED_DATE, selectedDate);
        super.onSaveInstanceState(outState);
    }
}
