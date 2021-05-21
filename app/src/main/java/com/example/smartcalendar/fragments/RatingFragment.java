package com.example.smartcalendar.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.smartcalendar.R;

public class RatingFragment extends Fragment {

    TextView reviewNumber;
    TextView rate1, rate2, rate3, rate4, rate5;
    TextView rateExcellent, rateVeryGood, rateGood, rateBad, rateNotBad, rateEnjoy;
    EditText comment;
    Button rateButton;

    int rateNumber = 0;
    String rateString = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        reviewNumber = view.findViewById(R.id.reviewNumber);
        rate1 = view.findViewById(R.id.rate1);
        rate2 = view.findViewById(R.id.rate2);
        rate3 = view.findViewById(R.id.rate3);
        rate4 = view.findViewById(R.id.rate4);
        rate5 = view.findViewById(R.id.rate5);
        rateExcellent = view.findViewById(R.id.rateExcellent);
        rateVeryGood = view.findViewById(R.id.rateVeryGood);
        rateGood = view.findViewById(R.id.rateGood);
        rateBad = view.findViewById(R.id.rateBad);
        rateNotBad = view.findViewById(R.id.rateNotBad);
        rateEnjoy = view.findViewById(R.id.rateEnjoy);
        comment = view.findViewById(R.id.comment);
        rateButton = view.findViewById(R.id.rateButton);

        TextView[] stars = {rate1, rate2, rate3, rate4, rate5};
        TextView[] rates = {rateExcellent, rateVeryGood, rateGood, rateBad, rateNotBad, rateEnjoy};

        for (int i = 0; i< stars.length; i++) {
            int index = i;
            stars[index].setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < 5; j++) {
                        stars[j].setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorSearchBackground));
                        stars[j].setTextColor(Color.parseColor("#333333"));
                    }
                    stars[index].setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
                    stars[index].setTextColor(Color.parseColor("#FFFFFF"));
                    rateNumber = index + 1;
                }
            });
        }

        for (TextView rate : rates) {
            rate.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < 6; i++) {
                        rates[i].setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorSearchBackground));
                        rates[i].setTextColor(Color.parseColor("#333333"));
                    }
                    rate.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorAccent));
                    rate.setTextColor(Color.parseColor("#FFFFFF"));
                    rateString = rate.getText().toString();
                }
            });
        }

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = comment.getText().toString();
                int number = Integer.parseInt(reviewNumber.getText().toString());
                if (!commentText.equals("")) {
                    number++;
                    reviewNumber.setText(String.valueOf(number));
                    comment.getText().clear();
                }
                if (rateNumber == 0 && rateString.equals("")){}
                else if (rateNumber < 3 || rateString.equals("Bad")) {
                    Toast.makeText(getContext(), "Sorry", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thank you", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
