package com.example.smartcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.smartcalendar.R;
import com.example.smartcalendar.sql.SQLHelper;

public class LoginActivity extends AppCompatActivity {
    
    Context context;
    Button dangnhap;
    TextView dangky;
    EditText username_in;
    EditText password_in;
    TextView lost;
    CheckBox remember;
    SharedPreferences sharedPreferences;
    SQLHelper sqlHelper;

    String UserSQL;
    String PassSQL;

    TextView textWelcome;
    ImageView imgLogo, imgFb, imgGoogle, imgTwitter;

    float v = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("night", 0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode", false);
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        context = this;

        mapping();
        animation();
        sql();

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        username_in.setText(sharedPreferences.getString("username_out", ""));
        password_in.setText(sharedPreferences.getString("password_out", ""));

        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item1 = username_in.getText().toString().trim();
                String item2 = password_in.getText().toString().trim();
                boolean flag = false;

                if (TextUtils.isEmpty(item1) || TextUtils.isEmpty(item2)) {
                    Toast.makeText(context, "Username or password is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor dataSQL = sqlHelper.GetData("SELECT * FROM AccountDataVer1");
                    while (dataSQL.moveToNext()) {
                        UserSQL = dataSQL.getString(1);
                        PassSQL = dataSQL.getString(2);
//                        int ID = dataSQL.getInt(0);
                        if (item1.equals(UserSQL) && item2.equals(PassSQL)) {
                            flag = true;
                        }
                    }

                    if (flag) {
                        finish();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("key1", item1);
                        i.putExtra("key2", item2);
                        if (remember.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("username_out", item1);
                            editor.putString("password_out", item2);
                            editor.putBoolean("checked", true);
                            editor.apply();
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.remove("username_out");
                            editor.remove("password_out");
                            editor.remove("checked");
                            editor.apply();
                        }
                        startActivity(i);
                    } else {
                        Toast.makeText(context, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "No one knows your password \nLet's create new account", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView[] socials = {imgFb, imgGoogle, imgTwitter};
        String[] uriString = {"https://www.facebook.com", "https://accounts.google.com/servicelogin", "https://twitter.com/login"};
        for (int i = 0; i < socials.length; i++) {
            int _i = i;
            socials[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(uriString[_i]);
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
            });
        }
    }

    private void sql() {
        sqlHelper = new SQLHelper(this, "SQLite", null, 1);
        sqlHelper.QueryData("CREATE TABLE IF NOT EXISTS AccountDataVer1(Id INTEGER PRIMARY KEY AUTOINCREMENT, UserName VARCHAR(200), Password VARCHAR(200), FullName VARCHAR(200), UserMail VARCHAR(200))");
    }

    private void mapping() {
        dangnhap = findViewById(R.id.dangnhap);
        dangky = findViewById(R.id.dangky);
        username_in = findViewById(R.id.username);
        password_in = findViewById(R.id.password);
        lost = findViewById(R.id.lost);
        remember = findViewById(R.id.remember);

        textWelcome = findViewById(R.id.textWelcome);
        imgLogo = findViewById(R.id.imgLogo);
        imgFb = findViewById(R.id.imgFb);
        imgGoogle = findViewById(R.id.imgGoogle);
        imgTwitter = findViewById(R.id.imgTwitter);
    }

    private void animation() {
        textWelcome.setTranslationX(-300);
        imgLogo.setTranslationX(300);
        imgFb.setTranslationY(400);
        imgGoogle.setTranslationY(400);
        imgTwitter.setTranslationY(400);

        textWelcome.setAlpha(v);
        imgLogo.setAlpha(v);
        imgFb.setAlpha(v);
        imgGoogle.setAlpha(v);
        imgTwitter.setAlpha(v);

        textWelcome.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        imgLogo.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();
        imgFb.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(800).start();
        imgGoogle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
        imgTwitter.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
    }

    public void onLoginClick(View View) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}