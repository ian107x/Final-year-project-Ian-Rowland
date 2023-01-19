package com.example.a19190859_fyp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class infoDB {


    public static final String KEY_PCT = "_pct";
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

    public void addInput(boolean pct, float inputTime, float duration, float pressure, float timeBetween){
        ContentValues newInput = new ContentValues();

        newInput.put(KEY_PCT, pct);
        newInput.put(KEY_INPUT_TIME, inputTime);
        newInput.put(KEY_INPUT_DURATION, duration);
        newInput.put(KEY_INPUT_PRESSURE, pressure);
        newInput.put(KEY_TIME_BETWEEN_TAPS, timeBetween);

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.insert(ModuleDBOpenHelper.DATABASE_TABLE, null, newInput);
    }

    public void exportDB(){
        ModuleDBOpenHelper DBHelper = new ModuleDBOpenHelper(context, ModuleDBOpenHelper.DATABASE_NAME, null, ModuleDBOpenHelper.DATABASE_VERSION);
        File exportDirectory = new File(Environment.getExternalStorageDirectory(), "");
        String path = "/storage/sdcard0/inputs.txt";

        if(!exportDirectory.exists()){
            exportDirectory.mkdirs();
        }
        //File file;
        //PrintWriter printWriter = null;
        try
        {
            /*file = new File(exportDirectory, "inputs.csv");
            file.createNewFile();
            printWriter = new PrintWriter(new FileWriter(file));*/

            File bfile = new File(path);
            FileWriter myWriter = new FileWriter(bfile);
            String[] dbToString = getAll();

            bfile.createNewFile();

            //if (bfile.createNewFile()) {
            //    myWriter.write("Input id" + ", " + "input time" + ", " + "input duration" + ", " + "input pressure" + ", " + " time since previous input");
            //}
            if(bfile.length() == 0)
            {
                myWriter.write("Input id" + ", " + "input time" + ", " + "input duration" + ", " + "input pressure" + ", " + " time since previous input");
            }

            for (int i = 0; i < dbToString.length ; i++)
            {
                myWriter.write(dbToString[i]);
            }
            myWriter.close();

        }
        catch (Exception exception){

        }

        //incomplete - remember to continue working on this.
    }

    public String[] getAll() {

        //ArrayList<ArrayList> outputArray = new ArrayList<ArrayList>();
        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_PCT, KEY_INPUT_TIME, KEY_INPUT_DURATION, KEY_INPUT_PRESSURE, KEY_TIME_BETWEEN_TAPS};
        boolean pct;
        float inputTime;
        float inputDuration;
        float inputPressure;
        float timeBetweenTaps;

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

            pct = cursor.getExtras().getBoolean(String.valueOf(cursor.getColumnIndexOrThrow(KEY_PCT)));
            inputTime = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_INPUT_TIME));
            inputDuration = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_INPUT_DURATION));
            inputPressure = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_INPUT_PRESSURE));
            timeBetweenTaps = cursor.getFloat(cursor.getColumnIndexOrThrow(KEY_TIME_BETWEEN_TAPS));

            //ArrayList row = new ArrayList<>();

            /*row.add(sessionID);
            row.add(inputTime);
            row.add(inputDuration);
            row.add(inputPressure);
            row.add(timeBetweenTaps+"\n");

            outputArray.add(row);*/

            outputArray.add(pct + ", " + inputTime + ", " + inputDuration + ", " + inputPressure + ", " +timeBetweenTaps + "\n");
            result = cursor.moveToNext();

        }return outputArray.toArray(new String[outputArray.size()]);
    }

    public String[][] convertTo2dArray(/*String[] db*/)
    {
        String[] db = getAll();
        int numCols = (db[0].split(", ")).length;
        String[][] dbList = new String[db.length][numCols];
        for(int i = 0; i < db.length; i++)
        {
            String[] row = db[i].split(" ");
            for(int y = 0; y < row.length; y++)
            {
                dbList[i][y] = row[y];
            }

        }
        return dbList;
    }



    private static class ModuleDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "inputDatabase.db";
        private static final String DATABASE_TABLE = "inputs";
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_PCT +
                " boolean primary key, " +
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
