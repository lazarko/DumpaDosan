package com.cerproduction.dumpadosan.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.cerproduction.dumpadosan.R;

/**
 * @author Lazar Cerovic (2020)
 * This class activity handles the About-section of the application.
 * For now it only displays text about "DumpaDosan", very simple at this stage.
 */
public class AboutActivity extends AppCompatActivity {

    /**
     * Standard method for activities
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}