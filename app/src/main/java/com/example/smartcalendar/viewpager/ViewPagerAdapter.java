package com.example.smartcalendar.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartcalendar.alarm.Alarm;
import com.example.smartcalendar.calendar.Calendar;
import com.example.smartcalendar.notes.Notes;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    final int PAGE_COUNT = 3;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Calendar();
            case 1:
                return new Alarm();
            case 2:
                return new Notes();
            default:
                return new Calendar();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
