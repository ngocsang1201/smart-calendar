package com.example.smartcalendar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcalendar.R;
import com.example.smartcalendar.recyclerview.share.ShareAdapter;
import com.example.smartcalendar.recyclerview.share.ShareItem;

import java.util.ArrayList;

public class ShareFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ShareItem> shareItems;
    ShareAdapter shareAdapter;

    String[] titles = {
            "Facebook", "LinkedIn", "Twitter",
            "Pinterest","Quora", "Google Plus",
            "Weibo", "Instagram", "Dribbble",
            "SoundCloud", "Reddit", "Blogger",
            "Wechat", "Line", "Whatsapp"
    };
    int[] icons = {
            R.drawable.facebook, R.drawable.linkedin, R.drawable.twitter,
            R.drawable.pinterest, R.drawable.quora, R.drawable.google,
            R.drawable.weibo, R.drawable.instagram, R.drawable.dribbble,
            R.drawable.soundcloud, R.drawable.reddit, R.drawable.blogger,
            R.drawable.wechat, R.drawable.line, R.drawable.whatsapp
    };
    int[] colors = {
            R.color.colorFacebook, R.color.colorLinkedIn, R.color.colorTwitter,
            R.color.colorPinterest, R.color.colorQuora, R.color.colorGoogle,
            R.color.colorWeibo, R.color.colorInstagram, R.color.colorDribbble,
            R.color.colorSoundCloud, R.color.colorReddit, R.color.colorBlogger,
            R.color.colorWechat, R.color.colorLine, R.color.colorWhatsapp,
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        recyclerView = view.findViewById(R.id.shareRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        shareItems = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            shareItems.add(new ShareItem(titles[i], icons[i], colors[i]));
        }
        shareAdapter = new ShareAdapter(getContext(), shareItems);
        recyclerView.setAdapter(shareAdapter);

        return view;
    }
}
