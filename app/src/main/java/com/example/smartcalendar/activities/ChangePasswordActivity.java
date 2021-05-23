package com.example.smartcalendar.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartcalendar.R;

public class ChangePasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText currentPassword, newPassword, confirmPassword;
    TextView currentMessage, newMessage, confirmMessage;
    Button changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        toolbar = findViewById(R.id.toolbar);
        currentPassword = findViewById(R.id.currentPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        changeButton = findViewById(R.id.changeButton);
        currentMessage = findViewById(R.id.currentMessage);
        newMessage = findViewById(R.id.newMessage);
        confirmMessage = findViewById(R.id.confirmMessage);

        actionToolbar();

        if (currentPassword.requestFocus() || newPassword.requestFocus() || confirmPassword.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        currentPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !simplify(currentPassword).equals("sang")
                        && simplify(newPassword).equals(simplify(currentPassword))) {
                    currentMessage.setText("Failure!");
                } else {
                    currentMessage.setText("");
                }
            }
        });
        newPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && simplify(newPassword).equals(simplify(currentPassword))) {
                    newMessage.setText("Failure!");
                } else {
                    newMessage.setText("");
                }
            }
        });
        confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !simplify(newPassword).equals(simplify(confirmPassword))) {
                    confirmMessage.setText("Failure!");
                } else {
                    confirmMessage.setText("");
                }
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFail = false;
                if (!currentMessage.getText().toString().isEmpty()
                        || !newMessage.getText().toString().isEmpty()
                        || !confirmMessage.getText().toString().isEmpty()) {
                    isFail = true;
                }
                if (isFail || simplify(currentPassword).isEmpty()
                        || simplify(newPassword).isEmpty()
                        || simplify(confirmPassword).isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
                    finish();
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

    private String simplify(EditText editText) {
        return editText.getText().toString().trim();
    }
}
