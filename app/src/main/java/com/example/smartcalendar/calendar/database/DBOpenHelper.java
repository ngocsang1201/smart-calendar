package com.example.smartcalendar.calendar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String CREATE_EVENTS_TABLE = "CREATE TABLE "
            + DBStructure.EVENTS_TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DBStructure.EVENT + " TEXT, "
            + DBStructure.LOCATION + " LOCATION, "
            + DBStructure.TIME + " TEXT, "
            + DBStructure.DATE + " TEXT, "
            + DBStructure.MONTH + " TEXT, "
            + DBStructure.YEAR + " TEXT)";
    private static final String DROP_EVENTS_TABLE = "DROP TABLE IF EXISTS " + DBStructure.EVENTS_TABLE_NAME;


    public DBOpenHelper(@Nullable Context context) {
        super(context, DBStructure.DB_NAME, null, DBStructure.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EVENTS_TABLE);
        onCreate(db);
    }

    public void SaveEvent(String event, String location, String time, String date, String month, String year, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBStructure.EVENT, event);
        contentValues.put(DBStructure.LOCATION, location);
        contentValues.put(DBStructure.TIME, time);
        contentValues.put(DBStructure.DATE, date);
        contentValues.put(DBStructure.MONTH, month);
        contentValues.put(DBStructure.YEAR, year);
        database.insert(DBStructure.EVENTS_TABLE_NAME, null, contentValues);
    }

    public Cursor ReadEvents(String date, SQLiteDatabase database) {
        String[] Projections = {DBStructure.EVENT, DBStructure.LOCATION, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.DATE + "=?";
        String[] SelectionArgs = {date};

        return database.query(DBStructure.EVENTS_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }

    public Cursor ReadEventsPerMonth(String month, String year, SQLiteDatabase database) {
        String[] Projections = {DBStructure.EVENT, DBStructure.LOCATION, DBStructure.TIME, DBStructure.DATE, DBStructure.MONTH, DBStructure.YEAR};
        String Selection = DBStructure.MONTH + "=? and " + DBStructure.YEAR + "=?";
        String[] SelectionArgs = {month, year};

        return database.query(DBStructure.EVENTS_TABLE_NAME, Projections, Selection, SelectionArgs, null, null, null);
    }

    public void DeleteEvent(String event, String location, String date, String time, SQLiteDatabase database) {
        String selection = DBStructure.EVENT + "=? and " + DBStructure.LOCATION + "=? and " + DBStructure.DATE + "=? and " + DBStructure.TIME + "=?";
        String[] selectionArg = {event, location, date, time};
        database.delete(DBStructure.EVENTS_TABLE_NAME, selection, selectionArg);
    }
}
