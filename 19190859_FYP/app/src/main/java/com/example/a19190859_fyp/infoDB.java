package com.example.a19190859_fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class infoDB {

    public static final String KEY_SESSION_ID = "_id";
    public static final String KEY_INPUT_TIME = "input_time";
    public static final String KEY_INPUT_DURATION = "input_duration";
    public static final String KEY_INPUT_PRESSURE = "input_pressure";
    public static final String KEY_TIME_BETWEEN_TAPS = "time_between_taps";

    private Context context;
    private ModuleDBOpenHelper moduleDBOpenHelper;

    public infoDB(Context context){
        this.context = context;
        moduleDBOpenHelper = new ModuleDBOpenHelper(context, ModuleDBOpenHelper.DATABASE_NAME, null, ModuleDBOpenHelper.DATABASE_VERSION);
    }

    public void addInput(int sessionID, float inputTime, float duration, float pressure, float timeBetween){
        ContentValues newInput = new ContentValues();

        newInput.put(KEY_SESSION_ID, sessionID);
        newInput.put(KEY_INPUT_TIME, inputTime);
        newInput.put(KEY_INPUT_DURATION, duration);
        newInput.put(KEY_INPUT_PRESSURE, pressure);
        newInput.put(KEY_TIME_BETWEEN_TAPS, timeBetween);
    }



    private static class ModuleDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "inputDatabase.db";
        private static final String DATABASE_TABLE = "inputs";
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_SESSION_ID +
                " integer primary key, " +
                KEY_INPUT_TIME + " float, " +
                KEY_INPUT_DURATION + " float, " +
                KEY_TIME_BETWEEN_TAPS + " float, " +
                KEY_INPUT_PRESSURE + " float) ";

        public ModuleDBOpenHelper(Context context, String name,
                                  SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {

            Log.w("TaskDBAdapter", "Upgrading from version " +
                    oldVersion + " to " +
                    newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);


        }

}
}
