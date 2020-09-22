package com.cerproduction.dumpadosan.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cerproduction.dumpadosan.R;

import java.util.concurrent.TimeUnit;


/**
 * @author Lazar Cerovic (2020)
 * Activity class which hosts the fragment InitFragment.java, which handles the initial form.
 */
public class InitActivity extends AppCompatActivity {

    /**
     * Standard method for activities. Not much else to add.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
          }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

    }
}