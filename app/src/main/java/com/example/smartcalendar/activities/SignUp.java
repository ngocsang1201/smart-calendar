package com.example.smartcalendar.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcalendar.R;
import com.example.smartcalendar.sql.SQLHelper;

public class SignUp extends SignIn {

    Context context;
    EditText name;
    EditText pass;
    EditText confirm;
    Button done;
    TextView can;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        context = this;

        mapping2();

        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignIn();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item1 = name.getText().toString().trim();
                String item2 = pass.getText().toString().trim();
                String item3 = confirm.getText().toString().trim();

                if (TextUtils.isEmpty(item1) || TextUtils.isEmpty(item2) || TextUtils.isEmpty(item3)) {
                    Toast.makeText(context, "Please enter full information", Toast.LENGTH_SHORT).show();
                }
                if (item1.length() < 8 || item2.length() < 8) {
                    Toast.makeText(context, "At least 8 characters", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.equals(item2, item3)) {
                        sqlHelper.QueryData("INSERT INTO AccountData VALUES (null, '" + item1 + "', '" + item2 + "')");
                        Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show();
                        goToSignIn();
                    } else {
                        Toast.makeText(context, "The password does not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void goToSignIn(){
        finish();
        startActivity(new Intent(getApplicationContext(), SignIn.class));
    }

    private void mapping2() {
        name = findViewById(R.id.name_in);
        pass = findViewById(R.id.pass_in);
        confirm = findViewById(R.id.pass_con);
        done = findViewById(R.id.done);
        can = findViewById(R.id.cancel);
    }
}