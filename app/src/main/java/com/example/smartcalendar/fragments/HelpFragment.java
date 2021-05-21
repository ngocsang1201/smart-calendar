package com.example.smartcalendar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;
import com.example.smartcalendar.recyclerview.help.HelpAdapter;
import com.example.smartcalendar.recyclerview.help.HelpItem;

import java.util.ArrayList;

public class HelpFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<HelpItem> helpItems;
    HelpAdapter helpAdapter;

    String[] titles = {"Documentation", "Training", "Community Forums", "System Status", "Release Notes", "Developers"};
    String[] descs = {"Browse getting started guides", "Sign up for a workshop", "Submit new ideas, communicate", "See the current system status", "Learn about the latest product", "Get access to comprehensive"};
    int[] icons = {R.drawable.ic_document, R.drawable.ic_cap, R.drawable.ic_chat, R.drawable.ic_drawer_notification, R.drawable.ic_campaign, R.drawable.ic_handyman};
    int[] colors = {R.color.colorHelp1, R.color.colorHelp2, R.color.colorHelp3, R.color.colorHelp4, R.color.colorHelp5, R.color.colorHelp6};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        recyclerView = view.findViewById(R.id.helpRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        helpItems = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            helpItems.add(new HelpItem(titles[i], descs[i], icons[i], colors[i]));
        }
        helpAdapter = new HelpAdapter(getContext(), helpItems);
        recyclerView.setAdapter(helpAdapter);

        return view;
    }
}
