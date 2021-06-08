package com.example.smartcalendar.calendar.recyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;
import com.example.smartcalendar.calendar.database.DBOpenHelper;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<Events> eventsArrayList;
    DBOpenHelper dbOpenHelper;

    public EventRecyclerAdapter(Context context, ArrayList<Events> eventsArrayList) {
        this.context = context;
        this.eventsArrayList = eventsArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contain_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events events = eventsArrayList.get(position);
        holder.name.setText(events.getEVENT());
        holder.location.setText(events.getLOCATION());
        holder.date.setText(events.getDATE());
        holder.time.setText(events.getTIME());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCalendarEvent(events.getEVENT(), events.getLOCATION(), events.getDATE(), events.getTIME());
                eventsArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, name, time, location;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.eventNameRow);
            date = itemView.findViewById(R.id.eventDateRow);
            location = itemView.findViewById(R.id.eventLocationRow);
            time = itemView.findViewById(R.id.eventTimeRow);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    private void deleteCalendarEvent(String event, String location, String date, String time) {
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.DeleteEvent(event, location, date, time, database);
        dbOpenHelper.close();
    }
}
