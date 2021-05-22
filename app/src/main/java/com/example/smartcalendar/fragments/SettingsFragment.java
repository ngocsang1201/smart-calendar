package com.example.smartcalendar.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.smartcalendar.R;
import com.example.smartcalendar.activities.ChangePassword;
import com.example.smartcalendar.activities.Theme;

public class SettingsFragment extends Fragment {

    TextView nameMail, nameLock, nameTheme, nameNoti, nameFont;
    SwitchCompat switchFb, switchInsta, switchTwit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameMail = view.findViewById(R.id.nameMail);
        nameLock = view.findViewById(R.id.nameLock);
        nameTheme = view.findViewById(R.id.nameTheme);
        switchFb = view.findViewById(R.id.switchFb);
        switchInsta = view.findViewById(R.id.switchInsta);
        switchTwit = view.findViewById(R.id.switchTwit);

        nameMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nameLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });
        nameTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Theme.class));
            }
        });

        SwitchCompat[] switchAccounts = {switchFb, switchInsta, switchTwit};
        String[] uriString = {"https://www.facebook.com/", "https://www.instagram.com/", "https://twitter.com/"};
        for (int i = 0; i < switchAccounts.length; i++) {
            int _i = i;
            switchAccounts[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Uri uri = Uri.parse(uriString[_i]);
                        startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    }
                }
            });
        }
        return view;
    }
}
