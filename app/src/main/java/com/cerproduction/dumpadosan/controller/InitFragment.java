package com.cerproduction.dumpadosan.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cerproduction.dumpadosan.R;
import com.cerproduction.dumpadosan.model.Profile;
import com.cerproduction.dumpadosan.view.SpinnerChoice;


import static android.content.Context.MODE_PRIVATE;


/**
 * @author Lazar Cerovic (2020)
 * This fragment class represents the initial feature on first time launch. The feature constist of
 * a form, which the user honestly should write. The fragment class handles the form as well as
 * updating the UI and switching to MainActivity.java
 */
public class InitFragment extends Fragment {
    private String PREFERENCES_NAME = "PROFILE-OBJECT";
    private Profile mProfile;
    private SpinnerChoice mSpinnerChoice;
    private EditText mInputAverageCost;
    private SharedPreferences mPref;

    private Button mNextButton;
    SharedPreferences mPrefs;

    /**
     * Standard method for fragments. Initialize the essential components of the fragment, as well
     * as checking if its the first time launched (if the form has been written before). If it isn't
     * then go directly to MainActivity.java
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPref = this.getActivity().getSharedPreferences("RUNONCE", Context.MODE_PRIVATE);
        if(mPref.getBoolean("already_executed", false)){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            SharedPreferences.Editor ed = mPref.edit();
            ed.putBoolean("already_executed", true);
            ed.commit();
        }
        mProfile = new Profile();

    }

    /**
     * Standard method for fragment. Method draws the fragment user interface initially and makes
     * the fragment visibile to the activity. Handles the user interface content.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return v
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_init, container, false);
        mSpinnerChoice = new SpinnerChoice(v, this);
        final DatePicker dp = (DatePicker) v.findViewById(R.id.datePicker1);
        dp.setMaxDate(System.currentTimeMillis());
        mNextButton = (Button) v.findViewById(R.id.nextBtn_id);
        mInputAverageCost = (EditText) v.findViewById(R.id.add_average_cost);

        mPrefs = this.getActivity().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int c = Integer.parseInt(mInputAverageCost.getText().toString());
            mProfile.updateDate(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
            mProfile.updateHabit(mSpinnerChoice.spinner_prillor.getSelectedItemPosition(),
                    mSpinnerChoice.spinner_dosor.getSelectedItemPosition(), c);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            mSpinnerChoice.spinner_prillor.getId();
            saveData();
            startActivity(intent);
            getActivity().finish();
            }
        });

        return v;
    }

    /**
     * Method for saving all the data from the form. Saved with SharedPreferences.
     */
    public void saveData(){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt("YEAR", mProfile.get_year());
        editor.putInt("MONTH", mProfile.get_month()+1); //FOR SOME REASON IT STARTS ON 0
        editor.putInt("DAY", mProfile.get_day());
        editor.putInt("PRILLOR", mProfile.get_day_prillor());
        editor.putInt("DOSOR", mProfile.get_week_dosor());
        editor.putInt("COST", mProfile.get_cost());
        editor.commit();

    }

}