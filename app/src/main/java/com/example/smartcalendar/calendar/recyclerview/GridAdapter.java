package com.example.smartcalendar.calendar.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.smartcalendar.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridAdapter extends ArrayAdapter {

    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater layoutInflater;

    public GridAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events) {
        super(context, R.layout.layout_single_cell);

        this.dates = dates;
        this.currentDate = currentDate;
        this.events = events;
        layoutInflater = LayoutInflater.from(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Date monthDate = dates.get(position);
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(monthDate);

        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH) + 1;
        int displayYear = dateCalendar.get(Calendar.YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_single_cell, null);
        }

        TextView dayNumber = convertView.findViewById(R.id.calendarDay);
        TextView eventNumber = convertView.findViewById(R.id.eventsNumber);
        dayNumber.setText(String.valueOf(dayNo));

        if (displayMonth == currentMonth && displayYear == currentYear) {
            dayNumber.setTextColor(Color.parseColor("#333333"));
//            if (dayNo == currentDay ) {
//                dayNumber.setTextColor(Color.parseColor("#FFFFFF"));
//                eventNumber.setTextColor(Color.parseColor("#FFFFFF"));
//                convertView.setBackgroundResource(R.drawable.circle_calender);
//            }
        } else {
            dayNumber.setTextColor(Color.parseColor("#CCCCCC"));
        }

        Calendar eventCalendar = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            eventCalendar.setTime(ConvertStringToDate(events.get(i).getDATE()));
            if (dayNo == eventCalendar.get(Calendar.DAY_OF_MONTH) && (displayMonth == eventCalendar.get(Calendar.MONTH) + 1)
                    && displayYear == eventCalendar.get(Calendar.YEAR)) {
                arrayList.add(events.get(i).getEVENT());
                eventNumber.setText(arrayList.size() + " Events");
            }
        }

        return convertView;
    }

    private Date ConvertStringToDate(String eventDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(eventDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
