package com.example.smartcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcalendar.R;

public class RegisterActivity extends LoginActivity {

    Context context;
    EditText name,pass;
    Button done;
    TextView can;

    TextView textView;
    ImageView imgLogo2;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;

        mapping2();
        animation();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item1 = name.getText().toString().trim();
                String item2 = pass.getText().toString().trim();

                if (TextUtils.isEmpty(item1) || TextUtils.isEmpty(item2)) {
                    Toast.makeText(context, "Please enter full information", Toast.LENGTH_SHORT).show();
                }
                if (item1.length() < 6 || item2.length() < 6) {
                    Toast.makeText(context, "At least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    sqlHelper.QueryData("INSERT INTO AccountData VALUES (null, '" + item1 + "', '" + item2 + "')");
                    Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show();
                    can.performClick();
                }
            }
        });
    }

    private void mapping2() {
        name = findViewById(R.id.name_in);
        pass = findViewById(R.id.pass_in);
        done = findViewById(R.id.done);
        can = findViewById(R.id.cancel);

        textView = findViewById(R.id.textView);
        imgLogo2 = findViewById(R.id.imgLogo2);
    }
    private void animation() {
        textView.setTranslationX(-300);
        imgLogo2.setTranslationX(300);

        textView.setAlpha(v);
        imgLogo2.setAlpha(v);

        textView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        imgLogo2.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
    }

    public void onLoginClick(View view){
        finish();
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

    }
}
