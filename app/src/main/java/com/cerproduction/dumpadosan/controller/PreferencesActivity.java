package com.cerproduction.dumpadosan.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.cerproduction.dumpadosan.R;

/**
 * @author Lazar Cerovic (2020)
 * This class represents a simple preferences activity. At the moment there is only one
 * function which this class serves: making it possible to redo the initial formular.
 */
public class PreferencesActivity extends AppCompatActivity {

    private Button mInitFormularBtn;
    private SharedPreferences mPref;

    /**
     * A overrided standard method for activities. This method initializes the button and
     * starts the InitActivity with setOnClickListener and resets the first time execution
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        mPref = getSharedPreferences("RUNONCE", Context.MODE_PRIVATE);
        mInitFormularBtn = findViewById(R.id.init_formular_button);
        mInitFormularBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = mPref.edit();
                ed.putBoolean("already_executed", false);
                ed.commit();
                startActivity(new Intent(PreferencesActivity.this, InitActivity.class));
                finish();
            }
        });
    }

}