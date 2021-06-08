package com.example.smartcalendar.calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;
import com.example.smartcalendar.calendar.database.DBOpenHelper;
import com.example.smartcalendar.calendar.database.DBStructure;
import com.example.smartcalendar.calendar.recyclerview.EventRecyclerAdapter;
import com.example.smartcalendar.calendar.recyclerview.Events;
import com.example.smartcalendar.calendar.recyclerview.GridAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CustomCalendarView extends LinearLayout {

    ImageButton nextButton, previousButton;
    TextView currentDate;
    GridView gridView;

    private static final int MAX_CALENDAR_DAYS = 42;

    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    SimpleDateFormat eventDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    GridAdapter gridAdapter;
    AlertDialog alertDialog;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList = new ArrayList<>();

    DBOpenHelper dbOpenHelper;

    public CustomCalendarView(Context context) {
        super(context);
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        InitializeLayout();
        SetupCalendar();

        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, -1);
                SetupCalendar();
            }
        });

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH, 1);
                SetupCalendar();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_event, null);
                EditText eventName = addView.findViewById(R.id.eventTitle);
                EditText eventLocation = addView.findViewById(R.id.eventLocation);
                TextView eventTime = addView.findViewById(R.id.eventTime);
                LinearLayout eventTimeLayout = addView.findViewById(R.id.eventTimeLayout);
                Button addEventButton = addView.findViewById(R.id.addEventButton);
                eventTimeLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                addView.getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        Calendar c = Calendar.getInstance();
                                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        c.set(Calendar.MINUTE, minute);
                                        c.setTimeZone(TimeZone.getDefault());
                                        SimpleDateFormat hformat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                        String EventTime = hformat.format(c.getTime());
                                        eventTime.setText(EventTime);
                                    }
                                }, hour, minute, true);
                        timePickerDialog.show();
                    }
                });

                String date = eventDateFormat.format(dates.get(position));
                String month = monthFormat.format(dates.get(position));
                String year = yearFormat.format(dates.get(position));

                addEventButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = eventName.getText().toString();
                        String location = eventLocation.getText().toString();
                        String time = eventTime.getText().toString();

                        if (!name.isEmpty() && !location.isEmpty() && !time.isEmpty()) {
                            SaveEvent(name, location, time, date, month, year);
                            SetupCalendar();
                            alertDialog.dismiss();
                        }
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String date = eventDateFormat.format(dates.get(position));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View showView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_events, null);
                RecyclerView recyclerView = showView.findViewById(R.id.eventsRecyclerView);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(showView.getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                EventRecyclerAdapter eventRecyclerAdapter = new EventRecyclerAdapter(showView.getContext(), CollectEventsByDate(date));
                recyclerView.setAdapter(eventRecyclerAdapter);
                eventRecyclerAdapter.notifyDataSetChanged();

                if (CollectEventsByDate(date).size() != 0) {
                    builder.setView(showView);
                    alertDialog = builder.create();
                    alertDialog.show();

                    alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            SetupCalendar();
                        }
                    });
                } else {
                    Toast.makeText(context, "No events on " + date, Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
    }

    private ArrayList<Events> CollectEventsByDate(String date) {
        ArrayList<Events> arrayList = new ArrayList<>();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEvents(date, database);
        while (cursor.moveToNext()) {
            String Event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String Location = cursor.getString(cursor.getColumnIndex(DBStructure.LOCATION));
            String Time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String Month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events Events = new Events(Event, Location, Time, Date, Month, Year);
            arrayList.add(Events);
        }
        cursor.close();
        dbOpenHelper.close();

        return arrayList;
    }

    public CustomCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void SaveEvent(String event, String location, String time, String date, String month, String year) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event, location, time, date, month, year, database);
        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();

    }

    private void InitializeLayout() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_calendar, this);

        nextButton = view.findViewById(R.id.nextButton);
        previousButton = view.findViewById(R.id.previousButton);
        currentDate = view.findViewById(R.id.currentDate);
        gridView = view.findViewById(R.id.gridView);
    }

    private void SetupCalendar() {
        String dateCurrent = dateFormat.format(calendar.getTime());
        currentDate.setText(dateCurrent);

        dates.clear();
        Calendar monthCalendar = (Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);
        CollectEventsPerMonth(monthFormat.format(calendar.getTime()), yearFormat.format(calendar.getTime()));

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        gridAdapter = new GridAdapter(context, dates, calendar, eventsList);
        gridView.setAdapter(gridAdapter);
    }

    private void CollectEventsPerMonth(String month, String year) {
        eventsList.clear();
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        Cursor cursor = dbOpenHelper.ReadEventsPerMonth(month, year, database);
        while (cursor.moveToNext()) {
            String Event = cursor.getString(cursor.getColumnIndex(DBStructure.EVENT));
            String Location = cursor.getString(cursor.getColumnIndex(DBStructure.LOCATION));
            String Time = cursor.getString(cursor.getColumnIndex(DBStructure.TIME));
            String Date = cursor.getString(cursor.getColumnIndex(DBStructure.DATE));
            String Month = cursor.getString(cursor.getColumnIndex(DBStructure.MONTH));
            String Year = cursor.getString(cursor.getColumnIndex(DBStructure.YEAR));
            Events Events = new Events(Event, Location, Time, Date, Month, Year);
            eventsList.add(Events);
        }
        cursor.close();
        dbOpenHelper.close();
    }
}
