package com.pwittchen.eegreader.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.pwittchen.eegreader.database.controller.SignalTableController;
import com.pwittchen.eegreader.database.model.Signal;
import com.pwittchen.eegreader.generics.GenericApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import static com.pwittchen.eegreader.utils.LogUtils.LOGD;
import static com.pwittchen.eegreader.utils.LogUtils.LOGE;
import static com.pwittchen.eegreader.utils.LogUtils.LOGI;
import static com.pwittchen.eegreader.utils.LogUtils.makeLogTag;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String TAG = makeLogTag(DatabaseHandler.class);

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eeg_reader.db";
    public static final String TABLE_SIGNALS = "signals";
    public static final String KEY_ID = "_id";
    public static final String KEY_TYPE = "type";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_MILISECONDS = "miliseconds";

    private SignalTableController signalTableController;

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        signalTableController = new SignalTableController(this);
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        signalTableController = new SignalTableController(this);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_SIGNALS = "CREATE TABLE " + TABLE_SIGNALS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TYPE + " INTEGER,"
                + KEY_LEVEL + " INTEGER,"
                + KEY_MILISECONDS + " INTEGER"
                + ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_SIGNALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNALS);
        onCreate(sqLiteDatabase);
    }

    public String getDatabasePath() {
        File dataBaseFile = GenericApplication.getContext().getDatabasePath(DatabaseHandler.DATABASE_NAME);
        return dataBaseFile.toString();
    }

    public SignalTableController getSignalTableController() {
        return signalTableController;
    }

    public void logDebugInfo() {
        LOGI(TAG, "Database path: " + this.getDatabasePath());
        LOGD(TAG, "Reading all signals...");
        List<Signal> contacts = this.getSignalTableController().getAll();
        String signalLog = "";

        for (Signal signal : contacts) {
            signalLog = "Id: " + signal._id + " , Type: " + signal._type + " , Level: " + signal._level + " Miliseconds: " + signal._miliseconds;
            LOGD(TAG, signalLog);
        }
    }

    public boolean exportTableToCSVFile(Cursor csvCursor, String fileName) {
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, fileName);
        try {
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            csvWriter.writeNext(csvCursor.getColumnNames());
            if (csvCursor.getCount() > 0) {
                while (csvCursor.moveToNext()) {
                    String arrayOfStrings[] = {csvCursor.getString(0), csvCursor.getString(1), csvCursor.getString(2), csvCursor.getString(3)};
                    csvWriter.writeNext(arrayOfStrings);
                }
            } else {
                return false;
            }
            csvWriter.close();
            csvCursor.close();
            return true;
        } catch (SQLException sqlException) {
            LOGE(TAG, sqlException.getMessage(), sqlException);
            return false;
        } catch (IOException ioException) {
            LOGE(TAG, ioException.getMessage(), ioException);
            return false;
        }
    }

}
