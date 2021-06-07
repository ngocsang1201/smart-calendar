package com.example.smartcalendar.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.smartcalendar.R;
import com.example.smartcalendar.fragments.HelpFragment;
import com.example.smartcalendar.fragments.HomeFragment;
import com.example.smartcalendar.fragments.NotificationsFragment;
import com.example.smartcalendar.fragments.RatingFragment;
import com.example.smartcalendar.fragments.SettingsFragment;
import com.example.smartcalendar.fragments.ShareFragment;
import com.example.smartcalendar.sql.SQLHelper;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView drawerNavigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    AlertDialog aboutDialog;
    TextView drawerName, drawerMail;

    SQLHelper sqlHelper;
    String item1_send, item2_send, username_send, password_send;
    String NameSQL;
    String MailSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerNavigationView = findViewById(R.id.drawerNav);

        View headerView = drawerNavigationView.getHeaderView(0);
        drawerName = headerView.findViewById(R.id.drawerName);
        drawerMail = headerView.findViewById(R.id.drawerMail);

        Intent i = getIntent();
        String item1 = i.getStringExtra("key1");
        String item2 = i.getStringExtra("key2");
        setItem1(item1);
        setItem2(item2);
        String UserSQL, PassSQL;

        sqlHelper = new SQLHelper(this, "SQLite", null, 1);
        Cursor dataSQL = sqlHelper.GetData("SELECT * FROM AccountDataVer1");
        while (dataSQL.moveToNext()) {
            UserSQL = dataSQL.getString(1);
            PassSQL = dataSQL.getString(2);
//                        int ID = dataSQL.getInt(0);
            if (item1.equals(UserSQL) && item2.equals(PassSQL)) {
                NameSQL = dataSQL.getString(3);
                MailSQL = dataSQL.getString(4);
            }
        }
        drawerName.setText(NameSQL);
        drawerMail.setText(MailSQL);

        actionToolbar();
        actionDrawer();
        setupFragment();

    }

    private void setItem2(String item2) {
        item2_send = item2;
    }

    private String getItem2() {
        return item2_send;
    }

    private void setItem1(String item1) {
        item1_send = item1;
    }

    private String getItem1() {
        return item1_send;
    }

    public String getMailSQL() {
        return MailSQL;
    }

    private void setupFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new HomeFragment());
        fragmentTransaction.commit();
    }

    private void actionDrawer() {
        drawerNavigationView.setNavigationItemSelectedListener(this);
        drawerNavigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.optionAbout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View view = LayoutInflater.from(getApplicationContext()).inflate(
                            R.layout.layout_about,
                            (ViewGroup) findViewById(R.id.layoutAbout)
                    );
                    builder.setView(view);
                    aboutDialog = builder.create();
                    if (aboutDialog.getWindow() != null) {
                        aboutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    aboutDialog.show();
                }
                return true;
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.drawerHome:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new HomeFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle(R.string.app_name);
                break;
            case R.id.drawerNotifications:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new NotificationsFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Notifications");
                break;
            case R.id.drawerSettings:
                sendDataToSettings();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new SettingsFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Settings");
                break;
            case R.id.drawerLogOut:
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.drawerHelp:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new HelpFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Help Center");
                break;
            case R.id.drawerShare:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new ShareFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Social Share");
                break;
            case R.id.drawerRate:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new RatingFragment());
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Rate Us");
                break;
            default:
                Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public String getUsername_send() {
        return username_send;
    }

    public String getPassword_send() {
        return password_send;
    }

    private void sendDataToSettings() {
        username_send = getItem1();
        password_send = getItem2();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }
}