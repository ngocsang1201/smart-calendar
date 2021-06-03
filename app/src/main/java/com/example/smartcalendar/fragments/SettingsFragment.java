package com.example.smartcalendar.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smartcalendar.R;
import com.example.smartcalendar.activities.ChangePasswordActivity;
import com.example.smartcalendar.activities.FontActivity;
import com.example.smartcalendar.activities.MainActivity;
import com.example.smartcalendar.activities.ThemeActivity;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class SettingsFragment extends Fragment {

    private MainActivity mainActivity;

    TextView nameMail, nameLock, nameTheme, nameNoti, nameFont;
    Switch switchFb, switchGoogle, switchTwitter;

    SharedPreferences sharedPreferences = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        nameMail = view.findViewById(R.id.nameMail);
        nameLock = view.findViewById(R.id.nameLock);
        nameTheme = view.findViewById(R.id.nameTheme);
        nameNoti = view.findViewById(R.id.nameNoti);
        nameFont = view.findViewById(R.id.nameFont);
        switchFb = view.findViewById(R.id.switchFb);
        switchGoogle = view.findViewById(R.id.switchGoogle);
        switchTwitter = view.findViewById(R.id.switchTwitter);
        mainActivity = (MainActivity) getActivity();

        sharedPreferences = getActivity().getSharedPreferences("facebook", 0);
        sharedPreferences = getActivity().getSharedPreferences("google", 0);
        sharedPreferences = getActivity().getSharedPreferences("twitter", 0);
        switchFb.setChecked(sharedPreferences.getBoolean("facebook", false));
        switchGoogle.setChecked(sharedPreferences.getBoolean("google", false));
        switchTwitter.setChecked(sharedPreferences.getBoolean("twitter", false));

        nameMail.setText(mainActivity.getMailSQL());
        String item1 = mainActivity.getUsername_send();
        String item2 = mainActivity.getPassword_send();

        nameMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nameLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChangePasswordActivity.class);
                i.putExtra("key3", item1);
                i.putExtra("key4", item2);
                startActivity(i);
            }
        });
        nameTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThemeActivity.class));
            }
        });
        nameFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FontActivity.class));
            }
        });

        Switch[] switchAccounts = {switchFb, switchGoogle, switchTwitter};
        String[] uriString = {"https://www.facebook.com", "https://www.google.com", "https://twitter.com"};
        for (int i = 0; i < switchAccounts.length; i++) {
            int _i = i;
            switchAccounts[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    switch (_i) {
                        case 0: {
                            editor.putBoolean("facebook", isChecked);
                            break;
                        }
                        case 1: {
                            editor.putBoolean("google", isChecked);
                            break;
                        }
                        case 2: {
                            editor.putBoolean("twitter", isChecked);
                            break;
                        }
                    }
                    editor.commit();

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
