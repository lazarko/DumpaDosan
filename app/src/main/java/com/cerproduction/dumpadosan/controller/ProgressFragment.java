package com.cerproduction.dumpadosan.controller;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cerproduction.dumpadosan.R;
import com.cerproduction.dumpadosan.model.Profile;
import com.cerproduction.dumpadosan.model.Time;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Lazar Cerovic (2020)
 * This fragment class handles the features of one (of two tabs) tab.
 * Main focus is on two things: 1) Displaying time since the last snus was taken
 * 2) Displaying how much money that was saved
 */
public class ProgressFragment extends Fragment {

    private String PREFERENCES_NAME = "PROFILE-OBJECT";
    private Profile mProfile;
    private TextView display_time, display_money;

    SharedPreferences mPrefs;
    Time mTime;

    /**
     * Constructor method
     */
    public ProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of fragment ProgressFragment
     * @return A new instance of fragment ProgressFragment.
     */
    public static ProgressFragment newInstance() {
        ProgressFragment fragment = new ProgressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Standard method for fragments. Initializes object variables and setups the restoration
     * of data saved in the InitFragment.java.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mPrefs = this.getActivity().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        mProfile = new Profile();
        restoreData();
    }


    /**
     * Standard method for fragments. In this method the
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_progress, container, false);
        display_time = v.findViewById(R.id.display_tv_id);
        display_money = v.findViewById(R.id.money_tv_id);
        Time mTime = new Time(mProfile.get_year(), mProfile.get_month(), mProfile.get_day());

        display_time.setText(mTime.getTimeSince());
        display_money.setText(mTime.getMoneySaved(mProfile.get_week_dosor(), mProfile.get_cost()));

        return v;
    }

    /**
     * Restoring all data method and updates the object variable
     */
    public void restoreData(){
        int tmp_year = mPrefs.getInt("YEAR", 0);
        int tmp_month = mPrefs.getInt("MONTH", 0);
        int tmp_day = mPrefs.getInt("DAY", 0);
        int tmp_prillor = mPrefs.getInt("PRILLOR", 0);
        int tmp_dosor = mPrefs.getInt("DOSOR", 0);
        int tmp_cost = mPrefs.getInt("COST", 0);

        mProfile.updateDate(tmp_year, tmp_month, tmp_day);
        mProfile.updateHabit(tmp_prillor, tmp_dosor, tmp_cost);

    }
}