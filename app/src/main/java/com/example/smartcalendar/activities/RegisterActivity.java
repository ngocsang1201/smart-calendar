package com.example.smartcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcalendar.R;

public class RegisterActivity extends LoginActivity {

    Context context;
    EditText username, pass, name, mail;
    Button done;
    TextView can;

    TextView textView;
    ImageView imgLogo2;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();

        context = this;

        mapping2();
        animation();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item1 = username.getText().toString().trim();
                String item2 = pass.getText().toString().trim();
                String item3 = name.getText().toString().trim();
                String item4 = mail.getText().toString().trim();

                if (TextUtils.isEmpty(item1) || TextUtils.isEmpty(item2) || TextUtils.isEmpty(item3) || TextUtils.isEmpty(item4)) {
                    Toast.makeText(context, "Please enter full information", Toast.LENGTH_SHORT).show();
                }
                if (item1.length() < 6 || item2.length() < 6) {
                    Toast.makeText(context, "At least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    sqlHelper.QueryData("INSERT INTO AccountDataVer1 VALUES (null, '" + item1 + "', '" + item2 + "', '" + item3 + "', '" + item4 + "')");
                    Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show();
                    can.performClick();
                }
            }
        });
    }

    private void mapping2() {
        username = findViewById(R.id.name_in);
        pass = findViewById(R.id.pass_in);
        done = findViewById(R.id.done);
        can = findViewById(R.id.cancel);
        name = findViewById(R.id.edittextName);
        mail = findViewById(R.id.editTextMail);

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

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
    }

    public void onLoginClick(View view) {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);

    }
}
