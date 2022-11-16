package com.example.a19190859_fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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

    public void exportDB(){
        ModuleDBOpenHelper DBHelper = new ModuleDBOpenHelper(context, ModuleDBOpenHelper.DATABASE_NAME, null, ModuleDBOpenHelper.DATABASE_VERSION);
        File exportDirectory = new File(Environment.getExternalStorageDirectory(), "");
        if(!exportDirectory.exists()){
            exportDirectory.mkdirs();
        }
        File file;
        PrintWriter printWriter = null;
        try
        {
            file = new File(exportDirectory, "inputs.csv");
            file.createNewFile();
            printWriter = new PrintWriter(new FileWriter(file));
            SQLiteDatabase db = this.moduleDBOpenHelper.getReadableDatabase(););
            Cursor CSVcursor = db.rawQuery("SELECT * from inputs", null);

            printWriter.println("FIRST TABLE OF THE DATABASE");
            printWriter.println("ID,INPUT TIME,INPUT DURATION,INPUT PRESSURE,TIME BETWEEN TAPS");
            if( CSVcursor != null && CSVcursor.moveToFirst() ){
                while(CSVcursor.moveToNext())
                {
                    int sessionID = (int) CSVcursor.getInt(CSVcursor.getColumnIndex("_id"));
                    float inputTime = CSVcursor.getFloat(CSVcursor.getColumnIndex("input_time"));
                    float inputDuration = CSVcursor.getFloat(CSVcursor.getColumnIndex("input_duration"));
                    float inputPressure = CSVcursor.getFloat(CSVcursor.getColumnIndex("input_pressure"));
                    float timeBetweenTaps = CSVcursor.getFloat(CSVcursor.getColumnIndex("time_between_taps"));

                }
            }

            CSVcursor.close();
            db.close();

        }
        catch (Exception exception){

        }

        //incomplete - remember to continue working on this.
    }

    public String[] getAll() {

        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_SESSION_ID, KEY_INPUT_TIME, KEY_INPUT_DURATION, KEY_INPUT_PRESSURE, KEY_TIME_BETWEEN_TAPS};
        int sessionID;
        float inputTime;
        float inputDuration;
        float inputPressure;
        float timeBetweenTaps;

        float weight;
        float height;
        int age;
        String gender;
        int stepCount;

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;

        ModuleDBOpenHelper helperDB = new ModuleDBOpenHelper(context, ModuleDBOpenHelper.DATABASE_NAME, null, ModuleDBOpenHelper.DATABASE_VERSION);
        SQLiteDatabase db = helperDB.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);
        //
        boolean result = cursor.moveToFirst();
        while (result) {

            sessionID = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_SESSION_ID));
            inputTime = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_INPUT_TIME));
            inputDuration = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_INPUT_DURATION));
            inputPressure = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_INPUT_PRESSURE));
            timeBetweenTaps = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_TIME_BETWEEN_TAPS));

            outputArray.add(sessionID + " " + inputTime + " " + inputDuration + " " + inputPressure + " " +timeBetweenTaps);
            result = cursor.moveToNext();

        }return outputArray.toArray(new String[outputArray.size()]);
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
