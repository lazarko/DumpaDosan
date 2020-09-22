package com.cerproduction.dumpadosan.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.UUID;

/**
 * @author Lazar Cerovic (2020)
 * This class represent the Goal-object. Any relevant attributes of the object
 * are declared here.
 */
public class Goal {

    private UUID id_number;
    int difficulty;
    String[] part_goals;
    String main_goal;
    String date_expected;
    /**
     * Public constructor for the Goal object. Generates also an unique id number for each object
     * @param main
     * @param rate
     */
    public Goal(String main, int rate){
        id_number = UUID.randomUUID();
        main_goal = main;
        difficulty = rate;
        date_expected = null; //INIT
    }

    /**
     * Method for changing the main goal-string.
     * @param goal
     */
    public void modifyGoal(String goal){
        main_goal = goal;
    }

    /**
     * Method for changing the self-evaluated goal difficulty
     * @param assesment
     */
    public void modifyDifficulty(int assesment){
        difficulty = assesment;
    }

    /**
     * It is optional to use date. If no date is selected it is null. Method adds date
     * @param date
     */
    public void addDate(String date){
        date_expected = date;
    }

    /**
     * Updates the id number from input string
     * @param id
     */
    public void update_ID(String id){
        id_number = UUID.fromString(id);
    }

    /**
     * Adds partial goals. It is optional to use them. If not used it will be null.
     * @param part
     */
    public void addPartGoal(String[] part){
        part_goals = part;
    }

    /**
     * Get method for the main goal.
     * @return main_goal
     */
    public String getMainGoal(){
        return main_goal;
    }

    /**
     * Get method which returns a unique filename for the picture
     * @return filename
     */
    public String getPhotoFilename() {
        return "IMG_" + getID() + ".jpg";
    }

    /**
     * Get method for the partial goals setup by the user
     * @return part_goals
     */
    public String[] getPartGoals(){
        return part_goals;
    }

    /**
     * Get method for the difficulty
     * @return difficulty
     */
    public int getDifficulty(){
        return difficulty;
    }

    /**
     * Get method for the date the goal is expected to be fulfilled
     * @return date_expected
     */
    public String getDate(){
        return date_expected;
    }

    /**
     * Get method for the identifier number of each goal
     * @return id_number
     */
    public UUID getID(){
        return id_number;
    }


}
