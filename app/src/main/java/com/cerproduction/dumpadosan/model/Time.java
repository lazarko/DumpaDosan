package com.cerproduction.dumpadosan.model;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.Weeks;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Lazar Cerovic (2020)
 * Class which represents a object, which keeps the time since last snus was taken, as well as
 * keeping track of how much money was saved (in Swedish crowns).
 */
public class Time {
    private int last_snus_year;
    private int last_snus_month;
    private int last_snus_day;


    private final String TITLE_STR_TIME = "TID: ";
    private final String TITLE_STR_YEAR = " ÅR, ";
    private final String TITLE_STR_MONTH = " MÅNADER, ";
    private final String TITLE_STR_DAYS = " DAGAR";

    /**
     * Public constructor method for this object. Updates the date (year, month, day) in which the
     * last snus was taken
     * @param year
     * @param month
     * @param day
     */
    public Time(int year, int month, int day){
        last_snus_year = year;
        last_snus_month = month;
        last_snus_day = day;
    }

    /**
     * Calculates the time difference between the current date and the date marked at last day of
     * taking a snus. This is done with the help of the joda-time library
     * @return (String) difference
     */
    public String getTimeSince(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String[] str = formatter.format(date).split("/");
        int currentYear = Integer.parseInt(str[2]);
        int currentMonth = Integer.parseInt(str[1]);
        int currentDay = Integer.parseInt(str[0]);
        DateTime startTime = new DateTime(last_snus_year, last_snus_month, last_snus_day,
                0, 0, 0, 0);

        DateTime currentDate = new DateTime(currentYear, currentMonth, currentDay,
                0 ,0, 0, 0);
        Period period = new Period(startTime, currentDate);
        int year = period.getYears();
        int month = period.getMonths();
        int days = period.getDays();
        String diff = TITLE_STR_TIME + year + TITLE_STR_YEAR + month + TITLE_STR_MONTH +
                days + TITLE_STR_DAYS;
        return diff;
    }

    /**
     * Method for calculating the total amount of money saved since the last consumption of snus.
     * Is calculated by number of weeks, so it may give a little bit lower than the actual number.
     * @param weekly_usage
     * @param average_cost
     * @return cost_str
     */
    public String getMoneySaved(int weekly_usage, int average_cost){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String[] str = formatter.format(date).split("/");
        int currentYear = Integer.parseInt(str[2]);
        int currentMonth = Integer.parseInt(str[1]);
        int currentDay = Integer.parseInt(str[0]);
        DateTime startTime = new DateTime(last_snus_year, last_snus_month, last_snus_day,
                0, 0, 0, 0);

        DateTime currentDate = new DateTime(currentYear, currentMonth, currentDay,
                0 ,0, 0, 0);
        int weeks_since = Weeks.weeksBetween(startTime, currentDate).getWeeks();
        int total_cost = (weekly_usage+1) * average_cost * weeks_since;
        String cost_str = "SPARAT MER ÄN " + total_cost + " KRONOR";
        return cost_str;
    }


}
