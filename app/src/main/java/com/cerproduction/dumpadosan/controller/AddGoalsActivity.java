package com.cerproduction.dumpadosan.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.cerproduction.dumpadosan.R;

import java.util.UUID;

/**
 * @author Lazar Cerovic (2020)
 * This activitiy takes a goal id and passes it to the fragment, which is used for adding goals.
 */
public class AddGoalsActivity extends AppCompatActivity {

    /**
     * Reads the arguments and puts it in a intent, to be accessed in onCreate.
     * @param packageContext
     * @param goal_id
     * @return intent
     */
    public static Intent addIntent(Context packageContext, UUID goal_id) {
        Intent intent = new Intent(packageContext, AddGoalsActivity.class);
        intent.putExtra("goal_id_put", goal_id);
        return intent;
    }

    /**
     * OnCreate method for AddGoal activity. Creates a new fragment which handles everything
     * concerning adding goals to the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_goals);
        UUID goal_id = (UUID) getIntent()
                .getSerializableExtra("goal_id_put");

        Fragment fragment = AddFragment.newInstance(goal_id);
        androidx.fragment.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.fragment_container_id, fragment);
        transaction.commit();
    }
}