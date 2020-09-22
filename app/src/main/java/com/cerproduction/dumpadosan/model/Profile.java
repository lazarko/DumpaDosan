package com.cerproduction.dumpadosan.model;

/**
 * @author Lazar Cerovic (2020)
 * This class is an java object which represents the user profile, based on the init form.
 */
public class Profile {
    private int day_prillor;
    private int week_dosor;
    private int end_year;
    private int end_month;
    private int end_day;
    private int average_cost;


    /**
     * Public constructor for the object
     */
    public Profile(){
    }

    /**
     * Updates the date when the user has stopped taking snus [Year, Month, Day].
     * @param year
     * @param month
     * @param day
     */
    public void updateDate(int year, int month, int day){
        end_day = day;
        end_month = month;
        end_year = year;
    }

    /**
     * Updates the daily and weekly snus habit, as well as the cost for each snus box.
     * @param daily
     * @param weekly
     * @param cost
     */
    public void updateHabit(int daily, int weekly, int cost){
        day_prillor = daily;
        week_dosor = weekly;
        average_cost = cost;
    }

    /**
     * Get method for the day in which the last snus was taken
     * @return end_day
     */
    public int get_day(){ return end_day; }
    /**
     * Get method for the month in which the last snus was taken
     * @return end_month
     */
    public int get_month(){ return end_month; }

    /**
     * Get method for the average cost per each snus box.
     * @return average_cost
     */
    public int get_cost(){return average_cost; }
    /**
     * Get method for the year in which the last snus was taken
     * @return end_year
     */
    public int get_year(){ return end_year; }

    /**
     * Get method for the daily snus habit
     * @return day_prillor
     */
    public int get_day_prillor(){
        return day_prillor;
    }
    /**
     * Get method for the weekly snus habit
     * @return week_dosor
     */
    public int get_week_dosor(){ return week_dosor; }



}
