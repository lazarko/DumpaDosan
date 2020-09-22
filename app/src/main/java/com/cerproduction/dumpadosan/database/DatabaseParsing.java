package com.cerproduction.dumpadosan.database;

import android.provider.BaseColumns;

/**
 * @author Lazar Cerovic (2020)
 * This class is used to define the database
 */
public class DatabaseParsing {

    public static final String TABLE_NAME = "goalTable";
    /**
     * Constructor made private so that it isn't accidentally instantiated the contract class
     */
    private DatabaseParsing(){
    }

    /**
     * Inner class that defines the table contents of the database
     */
    public final class GoalEntry implements BaseColumns {
        public static final String GOAL_ID = "id";
        public static final String MAIN_GOAL = "goal";
        public static final String DIFFICULTY = "difficulty";
        public static final String DATE = "date";
        public static final String FIRST_PART_GOAL = "one";
        public static final String SECOND_PART_GOAL = "two";
        public static final String THIRD_PART_GOAL = "three";

    }
}
