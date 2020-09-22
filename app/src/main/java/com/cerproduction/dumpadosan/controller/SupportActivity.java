package com.cerproduction.dumpadosan.controller;

import androidx.appcompat.app.AppCompatActivity;
import com.cerproduction.dumpadosan.R;
import android.os.Bundle;

/**
 * @author Lazar Cerovic (2020)
 * Just a simple activity to display the support-side.
 */
public class SupportActivity extends AppCompatActivity {

    /**
     * Standard method for activities
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
    }
}