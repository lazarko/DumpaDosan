package com.cerproduction.dumpadosan.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.cerproduction.dumpadosan.model.Goal;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.THIRD_PART_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.SECOND_PART_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.FIRST_PART_GOAL;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.DATE;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.DIFFICULTY;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.GOAL_ID;
import static com.cerproduction.dumpadosan.database.DatabaseParsing.GoalEntry.MAIN_GOAL;

/**
 * @author Lazar Cerovic (2020) Inspiration from the course book
 * Wraps cursors and adds more methods, primarily used to minimize the code.
 */
public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Constructor
     * @param cursor
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * This method extracts the meta data from the cursor and adds it to object (goal)
     * @return goal
     */
    public Goal getGoal() {
        String id = getString(getColumnIndexOrThrow(GOAL_ID));
        String main_goal = getString(getColumnIndexOrThrow(MAIN_GOAL));
        int diff = getInt(getColumnIndexOrThrow(DIFFICULTY));
        String date = getString(getColumnIndexOrThrow(DATE));
        String[] part_goal = new String[] {getString(getColumnIndexOrThrow(FIRST_PART_GOAL)),
                getString(getColumnIndexOrThrow(SECOND_PART_GOAL)),
                getString(getColumnIndexOrThrow(THIRD_PART_GOAL))};
        Goal goal = new Goal(main_goal, diff);
        goal.update_ID(id);
        goal.addDate(date);
        goal.addPartGoal(part_goal);
        return goal;
    }
}
