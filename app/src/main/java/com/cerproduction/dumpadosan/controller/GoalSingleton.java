package com.cerproduction.dumpadosan.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cerproduction.dumpadosan.database.DatabaseParsing;
import com.cerproduction.dumpadosan.database.GoalCursorWrapper;
import com.cerproduction.dumpadosan.database.GoalReaderDbHelper;
import com.cerproduction.dumpadosan.model.Goal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.THIRD_PART_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.SECOND_PART_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.FIRST_PART_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.DATE;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.DIFFICULTY;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.GOAL_ID;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.MAIN_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.TABLE_NAME;


/**
 * @author Lazar Cerovic (2020)
 * This class is a singleton object for the goal object. This programming pattern is used to limit
 * the number of instances created.
 */
public class GoalSingleton {
    private static GoalSingleton sGoalCollection;
    private Context cnxt;
    private SQLiteDatabase mDB;


    /**
     * Public method, when called upon it will either create a new instance of the singleton
     * or return an already existing singleton.
     * @param context
     * @return
     */
    public static GoalSingleton get(Context context) {
        if (sGoalCollection == null) {
            sGoalCollection = new GoalSingleton(context);
        }
        return sGoalCollection;
    }

    /**
     * Private constructor which initializes the database
     * @param context
     */
    private GoalSingleton(Context context) {
        cnxt = context.getApplicationContext();
        GoalReaderDbHelper dbHelper = new GoalReaderDbHelper(cnxt);
        mDB = dbHelper.getWritableDatabase();

    }

    /**
     * Method used to delete a row (goal) from the database
     * @param goal
     */
    public void deleteGoal(Goal goal){
        String id = goal.getID().toString();
        mDB.delete(TABLE_NAME, GOAL_ID + "=?", new String[]{id});
    }

    /**
     * Adds the goal into the database table
     * @param goal
     */
    public void addGoal(Goal goal){
        ContentValues values = new ContentValues();
        values.put(GOAL_ID, goal.getID().toString());
        values.put(MAIN_GOAL, goal.getMainGoal());
        values.put(DIFFICULTY, ""+goal.getDifficulty());
        values.put(DATE, goal.getDate());
        if(goal.getPartGoals() != null) {
            values.put(FIRST_PART_GOAL, goal.getPartGoals()[0]);
            values.put(SECOND_PART_GOAL, goal.getPartGoals()[1]);
            values.put(THIRD_PART_GOAL, goal.getPartGoals()[2]);
        }else{
            values.put(FIRST_PART_GOAL, "");
            values.put(SECOND_PART_GOAL, "");
            values.put(THIRD_PART_GOAL, "");
        }
        mDB.insert(TABLE_NAME, null, values);
    }



    /**
     * Updates the goal in the table
     * @param goal
     */

    public void updateGoal(Goal goal) {
        String new_id = goal.getID().toString();
        ContentValues values = new ContentValues();
        values.put(GOAL_ID, goal.getID().toString());
        values.put(MAIN_GOAL, goal.getMainGoal());
        values.put(DIFFICULTY, goal.getDifficulty());
        values.put(DATE, goal.getDate());
        if(goal.getPartGoals() != null) {
            values.put(FIRST_PART_GOAL, goal.getPartGoals()[0]);
            values.put(SECOND_PART_GOAL, goal.getPartGoals()[1]);
            values.put(THIRD_PART_GOAL, goal.getPartGoals()[2]);
        }else{
            values.put(FIRST_PART_GOAL, "");
            values.put(SECOND_PART_GOAL, "");
            values.put(THIRD_PART_GOAL, "");
        }


        mDB.update(TABLE_NAME, values,
                GOAL_ID + " = ?",
                new String[] { new_id });
    }


    /**
     * Returns a list of goals by going through each row in the database (row = goal)
     * @return list
     */
    public List<Goal> getGoalList() {
        List<Goal> list = new ArrayList<>();
        GoalCursorWrapper cursor = qDatabase(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(cursor.getGoal());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return list;
    }

    public Goal getGoal(UUID id){
        GoalCursorWrapper cursor = qDatabase(
                GOAL_ID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getGoal();
        } finally {
            cursor.close();
        }
    }
    /**
     * Method used for wrapping a cursor and extracting information from it
     * @param whereClause
     * @param whereArgs
     * @return GoalCursorWrapper
     */
    private GoalCursorWrapper qDatabase(String whereClause, String[] whereArgs) {
        Cursor cursor = mDB.query(
                TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new GoalCursorWrapper(cursor);
    }

    /**
     * Get method for the filepath of the image
     * @param goal
     * @return File
     */
    public File getPhotoFile(Goal goal) {
        File filesDir = cnxt.getFilesDir();
        return new File(filesDir, goal.getPhotoFilename());
    }



}
