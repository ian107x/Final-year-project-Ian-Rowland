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
import java.util.ArrayList;

public class InfoDB {


    public static final String KEY_ID = "id";
    public static final String KEY_GAME_ENJOYED = "game_enjoyed";
    public static final String KEY_HOW_MUCH_CONTROL = "how_much_control";
    public static final String KEY_GAINED_OR_LOST_CONTROL = "gained_or_lost_control";

    private Context context;
    private ModuleDBOpenHelper moduleDBOpenHelper;

    public InfoDB(Context context){
        this.context = context;
        moduleDBOpenHelper = new ModuleDBOpenHelper(context, ModuleDBOpenHelper.DATABASE_NAME, null, ModuleDBOpenHelper.DATABASE_VERSION);

        if(getAll().size() == 0)
        {
            addAnswers("1", "0", "0");
            addAnswers("0", "1", "0");
            addAnswers("0", "0", "1");
            addAnswers("0", "1", "0");
            addAnswers("1", "0", "0");
            addAnswers("0", "1", "0");
        }

    }

    /*public void addInput(boolean pct, float inputTime, float duration, float pressure, float timeBetween){
        ContentValues newInput = new ContentValues();

        newInput.put(KEY_PCT, pct);
        newInput.put(KEY_INPUT_TIME, inputTime);
        newInput.put(KEY_INPUT_DURATION, duration);
        newInput.put(KEY_INPUT_PRESSURE, pressure);

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.insert(ModuleDBOpenHelper.DATABASE_TABLE, null, newInput);
    }*/

    public void clearAnswers()
    {
        String where = null;
        String whereArgs[] = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.delete(ModuleDBOpenHelper.DATABASE_TABLE, where, whereArgs);
    }

    public void addAnswers(String ans1, String ans2, String ans3)
    {
        //clearAnswers();
        ContentValues newAnswers = new ContentValues();
        //newAnswers.put(KEY_ID, 1);
        newAnswers.put(KEY_GAME_ENJOYED, ans1);
        newAnswers.put(KEY_HOW_MUCH_CONTROL, ans2);
        newAnswers.put(KEY_GAINED_OR_LOST_CONTROL, ans3);

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        db.insert(ModuleDBOpenHelper.DATABASE_TABLE, null, newAnswers);

    }

    public void exportDB(){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        try
        {
            File bfile = new File(dir, "answers.txt");
            FileWriter myWriter = new FileWriter(bfile);
            ArrayList<String> dbToString = getAll();
            for (int i = 0; i < dbToString.size() ; i++)
            {
                myWriter.write(dbToString.get(i));
            }
            myWriter.close();
        }
        catch (Exception exception){

        }
    }

    public String[] getAnswersByID(Integer id)
    {
        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_GAME_ENJOYED, KEY_HOW_MUCH_CONTROL, KEY_GAINED_OR_LOST_CONTROL};

        String gameEnjoyed;
        String howmuchcontrol;
        String gainedorlost;
        String where = KEY_ID + "= ?";
        String whereArgs[] = {id.toString()};
        String groupBy = null;
        String having = null;
        String order = null;

        SQLiteDatabase db = moduleDBOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ModuleDBOpenHelper.DATABASE_TABLE,
                result_columns, where,
                whereArgs, groupBy, having, order);

        boolean result = cursor.moveToFirst();
        if(result) {
            gameEnjoyed = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GAME_ENJOYED));
            howmuchcontrol = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HOW_MUCH_CONTROL));
            gainedorlost = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GAINED_OR_LOST_CONTROL));

            outputArray.add("Was Game enjoyed:" + " " + gameEnjoyed + "\n " + "How much control" +  " " + howmuchcontrol + "\n " +
                    "Control gained or lost" + " " + gainedorlost);

        }
        return outputArray.toArray(new String[outputArray.size()]);
    }

    public ArrayList<String> getAll() {

        //ArrayList<ArrayList> outputArray = new ArrayList<ArrayList>();
        ArrayList<String> outputArray = new ArrayList<String>();
        String[] result_columns = new String[]{
                KEY_ID, KEY_GAME_ENJOYED, KEY_HOW_MUCH_CONTROL, KEY_GAINED_OR_LOST_CONTROL};

        int id;
        String gameEnjoyed;
        String howMuchControl;
        String gainedOrLostControl;

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
        outputArray.add("ID, " + " Game enjoyed, " + " How much control, " + " Gained or lost control " + "\n");
        while (result) {

            id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID));
            gameEnjoyed = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GAME_ENJOYED));
            howMuchControl = cursor.getString(cursor.getColumnIndexOrThrow(KEY_HOW_MUCH_CONTROL));
            gainedOrLostControl = cursor.getString(cursor.getColumnIndexOrThrow(KEY_GAINED_OR_LOST_CONTROL));


            outputArray.add(id + ", " + gameEnjoyed + ", " + howMuchControl + ", " + gainedOrLostControl + "\n");
            result = cursor.moveToNext();

        }

        cursor.close();
        return outputArray;
    }

    private static class ModuleDBOpenHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "inputDatabase.db";
        private static final String DATABASE_TABLE = "inputs";
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_CREATE = "create table " +
                DATABASE_TABLE + " (" + KEY_ID +
                " integer primary key autoincrement, " +
                KEY_GAME_ENJOYED + " string not null, " +
                KEY_HOW_MUCH_CONTROL + " string not null, " +
                KEY_GAINED_OR_LOST_CONTROL + " string not null) ";

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
