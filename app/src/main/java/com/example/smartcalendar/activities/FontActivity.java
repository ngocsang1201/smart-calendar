package com.example.smartcalendar.activities;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcalendar.R;

public class FontActivity extends AppCompatActivity {

    Toolbar toolbar;
    SeekBar seekbarFont;
    TextView textSample, changeLanguage;
    RadioButton radioFont;

    AlertDialog aboutDialog;

    int[] fontSize = {R.dimen.size12, R.dimen.size14, R.dimen.size16, R.dimen.size18, R.dimen.size20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);

        toolbar = findViewById(R.id.toolbar);
        textSample = findViewById(R.id.textSample);
        changeLanguage = findViewById(R.id.changeLanguage);
        seekbarFont = findViewById(R.id.seekbarFont);
        radioFont = findViewById(R.id.radioFont);

        actionToolbar();

        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FontActivity.this);
                View view = LayoutInflater.from(getApplicationContext()).inflate(
                        R.layout.layout_change_language,
                        (ViewGroup) findViewById(R.id.layoutChangeLanguage)
                );
                builder.setView(view);
                aboutDialog = builder.create();
                if (aboutDialog.getWindow() != null) {
                    aboutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }

//                RadioButton radioL1 = view.findViewById(R.id.radioL1);
//                RadioButton radioL2 = view.findViewById(R.id.radioL2);
//                RadioButton radioL3 = view.findViewById(R.id.radioL3);
                RadioButton[] radioBtns = {view.findViewById(R.id.radioL1), view.findViewById(R.id.radioL2), view.findViewById(R.id.radioL3)};
                radioBtns[1].setChecked(true);

                for (RadioButton radioBtn : radioBtns) {
                    radioBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (RadioButton btn : radioBtns) {
                                btn.setChecked(false);
                            }
                            radioBtn.setChecked(true);
                        }
                    });
                }

                aboutDialog.show();
            }
        });

        seekbarFont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSample.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(fontSize[progress]));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @SuppressLint("ResourceType")
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioFont.setChecked(true);

    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Language & Font");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
