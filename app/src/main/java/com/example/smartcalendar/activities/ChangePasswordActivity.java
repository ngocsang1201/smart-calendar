package com.example.smartcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcalendar.R;
import com.example.smartcalendar.sql.SQLHelper;

public class ChangePasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText currentPassword, newPassword, confirmPassword;
    Button changeButton;
    SQLHelper sqlHelper;
    String UserSQL, PassSQL, NameSQL, MailSQL;
    String current, newPass, confirmPass;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context = this;

        toolbar = findViewById(R.id.toolbar);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        changeButton = findViewById(R.id.changeButton);

        actionToolbar();

        Intent i = getIntent();
        String item1 = i.getStringExtra("key3");
        String item2 = i.getStringExtra("key4");

        sqlHelper = new SQLHelper(this, "SQLite", null, 1);
        Cursor dataSQL = sqlHelper.GetData("SELECT * FROM AccountDataVer1");
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = currentPassword.getText().toString().trim();
                newPass = newPassword.getText().toString().trim();
                confirmPass = confirmPassword.getText().toString().trim();
                if (current.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(context, "Please enter full information", Toast.LENGTH_SHORT).show();
                } else if (current.equals(item2) && newPass.equals(confirmPass)) {
                    while (dataSQL.moveToNext()) {
                        PassSQL = dataSQL.getString(2);
                        UserSQL = dataSQL.getString(1);
                        int ID = dataSQL.getInt(0);
                        if (item1.equals(UserSQL) && item2.equals(PassSQL)) {
                            UserSQL = dataSQL.getString(1);
                            NameSQL = dataSQL.getString(3);
                            MailSQL = dataSQL.getString(4);
                            sqlHelper.QueryData("REPLACE INTO AccountDataVer1 VALUES ('" + ID + "', '" + UserSQL + "', '" + newPass + "', '" + NameSQL + "', '" + MailSQL + "')");
                        }
                    }
                    Toast.makeText(context, "Change password successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, "Something wrong!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Change Password");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
