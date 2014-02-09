package com.pwittchen.eegreader.database.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pwittchen.eegreader.database.DatabaseHandler;
import com.pwittchen.eegreader.database.model.Signal;

import java.util.ArrayList;
import java.util.List;

public class SignalTableController {

    private SQLiteOpenHelper sqLiteOpenHelper;

    public SignalTableController(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    public void add(Signal signal) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.KEY_TYPE, signal._type);
        contentValues.put(DatabaseHandler.KEY_LEVEL, signal._level);
        contentValues.put(DatabaseHandler.KEY_MILISECONDS, new java.util.Date().getTime());
        sqLiteDatabase.insert(DatabaseHandler.TABLE_SIGNALS, null, contentValues);
        sqLiteDatabase.close();
    }

    public Signal getItem(int id) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(
                DatabaseHandler.TABLE_SIGNALS,
                new String[]{DatabaseHandler.KEY_ID,
                        DatabaseHandler.KEY_TYPE,
                        DatabaseHandler.KEY_LEVEL,
                        DatabaseHandler.KEY_MILISECONDS},
                DatabaseHandler.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Signal signal = new Signal(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
        return signal;
    }

    public List<Signal> getAll() {

        List<Signal> signalList = new ArrayList<Signal>();
        String selectQuery = "SELECT  * FROM " + DatabaseHandler.TABLE_SIGNALS;
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Signal signal = new Signal();
                signal._id = Integer.parseInt(cursor.getString(0));
                signal._type = Integer.parseInt(cursor.getString(1));
                signal._level = Integer.parseInt(cursor.getString(2));
                signal._miliseconds = Long.parseLong(cursor.getString(3));
                signalList.add(signal);
            } while (cursor.moveToNext());
        }
        return signalList;
    }

    public Cursor getAllCursor() {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DatabaseHandler.TABLE_SIGNALS;
        return sqLiteDatabase.rawQuery(selectQuery, null);
    }

    public int getCount() {
        String countQuery = "SELECT * FROM " + DatabaseHandler.TABLE_SIGNALS;
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(countQuery, null);
        return cursor.getCount();
    }

    public int update(Signal signal) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_LEVEL, signal._level);
        values.put(DatabaseHandler.KEY_TYPE, signal._type);
        values.put(DatabaseHandler.KEY_MILISECONDS, signal._miliseconds);
        return sqLiteDatabase.update(DatabaseHandler.TABLE_SIGNALS, values, DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(signal._id)});
    }

    public void delete(Signal signal) {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseHandler.TABLE_SIGNALS, DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(signal._id)});
        sqLiteDatabase.close();
    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.delete(DatabaseHandler.TABLE_SIGNALS, "", new String[]{});
        sqLiteDatabase.close();
    }
}
