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
import com.example.smartcalendar.activities.ChangePasswordActivity;
import com.example.smartcalendar.activities.ThemeActivity;

public class SettingsFragment extends Fragment {

    TextView nameMail, nameLock, nameTheme, nameNoti, nameFont;
    SwitchCompat switchFb, switchGoogle, switchTwit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameMail = view.findViewById(R.id.nameMail);
        nameLock = view.findViewById(R.id.nameLock);
        nameTheme = view.findViewById(R.id.nameTheme);
        switchFb = view.findViewById(R.id.switchFb);
        switchGoogle = view.findViewById(R.id.switchGoogle);
        switchTwit = view.findViewById(R.id.switchTwit);

        nameMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nameLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        nameTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemeActivity.class));
            }
        });

        SwitchCompat[] switchAccounts = {switchFb, switchGoogle, switchTwit};
        String[] uriString = {"https://www.facebook.com", "https://www.google.com", "https://twitter.com"};
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
