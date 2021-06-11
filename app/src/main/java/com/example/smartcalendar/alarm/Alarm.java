package com.example.smartcalendar.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.smartcalendar.R;
import com.example.smartcalendar.activities.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Alarm extends Fragment {

    ImageView hengio;
    Switch alarmSwitch, unavailable;
    RelativeLayout alarmLayout;
    TextView hienthi, txtView, alarmContent;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    TextView txtProgress;
    ProgressBar progressBar;

    int updateHour, updateMinute;

    private MainActivity mainActivity;
    TimePickerDialog timePickerDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        txtProgress = view.findViewById(R.id.taim);
        progressBar = view.findViewById(R.id.progressBar);
        hengio = view.findViewById(R.id.settime);
        alarmSwitch = view.findViewById(R.id.alarmSwitch);
        unavailable = view.findViewById(R.id.switchUnavailable);
        alarmLayout = view.findViewById(R.id.alarmLayout);
        hienthi = view.findViewById(R.id.textt);
        txtView = view.findViewById(R.id.txtView);
        alarmContent = view.findViewById(R.id.alarmContent);
        calendar = Calendar.getInstance();

        mainActivity = (MainActivity) getActivity();
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);

        timePickerInit();
        actionProgressBar();
        createNotificationChannel();

        hengio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
        hienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hengio.performClick();
            }
        });
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    alarmManager.cancel(pendingIntent);
                    intent.putExtra("extra", "off");
                    getActivity().sendBroadcast(intent);
                    txtView.setText("No alarms on");
                } else {
                    setAlarm();
                }
            }
        });
        unavailable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getContext(), "not available at the moment", Toast.LENGTH_SHORT).show();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unavailable.setChecked(false);
                        }
                    }, 500);
                }
            }
        });
        return view;
    }

    private void timePickerInit() {
        Calendar c = Calendar.getInstance();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMinute = c.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(
                mainActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        updateHour = hourOfDay;
                        updateMinute = minute;
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        hienthi.setText(f24Hours.format(calendar.getTime()));
                        alarmLayout.setVisibility(View.VISIBLE);
                        alarmSwitch.setChecked(true);

                        setAlarm();
                    }
                }, currentHour, currentMinute, true
        );
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        timePickerDialog.updateTime(currentHour, currentMinute);
    }

    private void setAlarm() {
        txtView.setText("1 alarm on");

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("extra", "on");
        pendingIntent = PendingIntent.getBroadcast(mainActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        addNotification();
    }

    private void actionProgressBar() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                final Calendar now = Calendar.getInstance();
                final int h = now.get(Calendar.HOUR_OF_DAY);
                final int m = now.get(Calendar.MINUTE);
                final int s = now.get(Calendar.SECOND);

                String hh = String.valueOf(h > 9 ? h : "0" + h);
                String mm = String.valueOf(m > 9 ? m : "0" + m);

                txtProgress.setText(hh + ":" + mm);
                progressBar.setProgress(s);
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void addNotification() {
        String strTitle = "Alarm";
        String strMsg = hienthi.getText().toString().trim() + " " + alarmContent.getText().toString().trim();
        Intent notificationIntent = new Intent(getActivity(), NotificationDetailActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", strMsg);
        notificationIntent.putExtra("title", strTitle);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "Channel-001")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(strTitle)
                .setContentText(strMsg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Channel-001", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
