package com.example.smartcalendar.alarm;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.smartcalendar.R;

import java.util.Calendar;

public class Alarm extends Fragment {

    Button hengio, huybo;
    TextView hienthi;
    TimePicker dongho;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        hengio = view.findViewById(R.id.settime);
        huybo = view.findViewById(R.id.btncancel);
        hienthi = view.findViewById(R.id.textt);
        dongho = view.findViewById(R.id.clockk);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        dongho.setIs24HourView(true);

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);

        createNotificationChannel();

        hengio.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, dongho.getCurrentHour());
                calendar.set(Calendar.MINUTE, dongho.getCurrentMinute());

                int gio = dongho.getCurrentHour();
                int phut = dongho.getCurrentMinute();

                String string_gio = gio > 9 ? String.valueOf(gio) : "0" + gio;
                String string_phut = phut > 9 ? String.valueOf(phut) : "0" + phut;

                intent.putExtra("extra", "on");
                pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                hienthi.setText("Giờ bạn đã đặt là " + string_gio + ":" + string_phut);
                addNotification();
            }
        });

        huybo.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                hienthi.setText("Đã dừng");
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra", "off");
                getActivity().sendBroadcast(intent);
            }
        });
        return view;
    }

    private void addNotification() {
        String strTitle = "Smart Calendar";
        String strMsg = hienthi.getText().toString().trim();
        Intent notificationIntent = new Intent(getActivity(), NotificationDetailActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("message", strMsg);
        notificationIntent.putExtra("title", strTitle);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "Channel-001").setSmallIcon(R.drawable.ic_alarm).setContentTitle(strTitle).setContentText(strMsg).setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent).setAutoCancel(true);
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
