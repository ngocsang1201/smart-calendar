package com.example.smartcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcalendar.R;
import com.example.smartcalendar.sql.SQLHelper;

public class SignIn extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        context = this;

        mapping();

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
                    Cursor dataSQL = sqlHelper.GetData("SELECT * FROM AccountData");
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
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                    } else {
                        Toast.makeText(context, "Username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "No one knows your password", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sql() {
        sqlHelper = new SQLHelper(this, "SQLite", null, 1);
        sqlHelper.QueryData("CREATE TABLE IF NOT EXISTS AccountData(Id INTEGER PRIMARY KEY AUTOINCREMENT, UserName VARCHAR(200), Password VARCHAR(200))");
    }

    private void mapping() {
        dangnhap = findViewById(R.id.dangnhap);
        dangky = findViewById(R.id.dangky);
        username_in = findViewById(R.id.username);
        password_in = findViewById(R.id.password);
        lost = findViewById(R.id.lost);
        remember = findViewById(R.id.remember);
    }
}