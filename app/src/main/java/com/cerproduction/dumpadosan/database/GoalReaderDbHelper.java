package com.cerproduction.dumpadosan.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoalReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GoalReader.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseParsing.TABLE_NAME + " (" +
                    DatabaseParsing.GoalEntry._ID + " INTEGER PRIMARY KEY, " +
                    DatabaseParsing.GoalEntry.GOAL_ID + " TEXT, " +
                    DatabaseParsing.GoalEntry.MAIN_GOAL + " TEXT, " +
                    DatabaseParsing.GoalEntry.DIFFICULTY + " INTEGER, " +
                    DatabaseParsing.GoalEntry.DATE + " TEXT, " +
                    DatabaseParsing.GoalEntry.FIRST_PART_GOAL + " TEXT, " +
                    DatabaseParsing.GoalEntry.SECOND_PART_GOAL + " TEXT, " +
                    DatabaseParsing.GoalEntry.THIRD_PART_GOAL + " TEXT)";

    /**
     * Constructor used for creating a new instance of the database
     * @param context
     */
    public GoalReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Autogenerated method. Creates the table
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Autogenerated method
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
