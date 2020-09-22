package com.cerproduction.dumpadosan.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.cerproduction.dumpadosan.R;
import com.cerproduction.dumpadosan.model.Goal;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author Lazar Cerovic (2020) Some parts are very much inspired from the course book
 * This class handles everything related to adding a goal such as adding sub-goals, date expected
 * of fulfillment, difficulty and even a picture. Right now not much is done with the picture except
 * for just saving the filepath. That may change in the future.
 */
public class AddGoalsActivity extends AppCompatActivity {

    /**
     * OnCreate method for AddGoal acitivity. Handles the UI and
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //UUID goalId = UUID.fromString(getIntent().getStringExtra("id_uuid_restore"));
        //mGoal = new Goal("", 0); //Initially
       // mGoal.update_ID(goalId.toString());
        //mGoal = mGoalSingle.get(this).getGoal(goalId);

        setContentView(R.layout.activity_add_goals);
        GoalSingleton mGoalSingle = GoalSingleton.get(this);
        Goal g = new Goal("", 1);
        mGoalSingle.get(this).addGoal(g);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AddFragment addFragment = AddFragment.newInstance(g.getID());
        ft.replace(R.id.placeholder_fragment, addFragment);
        ft.commit();



        //initVar();
        //collectData();
        //photoCollect();

/*
        mAddGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cb.isChecked()){
                    int year = date.getYear();
                    int month = date.getMonth()+1;
                    int day = date.getDayOfMonth();
                    mGoal.addDate(day+ "/" + month + "-" +year);

                }
                String[] part = new String[] {null, null, null};
                if(!mEditTextPart1.getText().toString().equals("")){
                    part[0] = mEditTextPart1.getText().toString();
                }
                if(!mEditTextPart2.getText().toString().equals("")){
                    part[1] = mEditTextPart2.getText().toString();
                }
                if(!mEditTextPart3.getText().toString().equals("")){
                    part[2] = mEditTextPart3.getText().toString();
                }

                mGoal.addPartGoal(part);
                GoalSingleton.get(getApplicationContext()).addGoal(mGoal);
                startActivity(new Intent(AddGoalsActivity.this, MainActivity.class));
            }
        });

 */

    }

    /**
     * Initializing all variables

    private void initVar(){
        mGoalSingle = GoalSingleton.get(this);
        //mGoal = new Goal("", 0); //Initially

        mDisplayPic = (ImageView) findViewById(R.id.iv_display_pic);
        mTakePicBtn = (ImageButton) findViewById(R.id.btn_take_pic);
        mAddGoalBtn = (Button) findViewById(R.id.btn_add_goal);
        mEditTextGoal = (EditText) findViewById(R.id.editTextGoal);
        mEditTextPart1 = (EditText) findViewById(R.id.editTextPartGoal1);
        mEditTextPart2 = (EditText) findViewById(R.id.editTextPartGoal2);
        mEditTextPart3 = (EditText) findViewById(R.id.editTextPartGoal3);
        mEditTextDifficulty = (EditText) findViewById(R.id.editTextDifficulty);
        date = (DatePicker) findViewById(R.id.datePicker2);
        cb = (CheckBox) findViewById(R.id.checkboxDatum);
        mAddGoalBtn.setEnabled(false);
        date.setMinDate(System.currentTimeMillis() - 1000);
        mPhotoFile = mGoalSingle.get(this).getPhotoFile(mGoal);
    }

     */

    /**
     * Code for collecting data through the listeners, activating the "add-goal-button" and
     * updating the goal object

    private void collectData(){

        TextWatcher tw = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count,
                                              int after) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before,
                                          int count) {
                    if (mEditTextGoal.getText().hashCode() == charSequence.hashCode())
                    {
                        first = true;
                        mGoal.modifyGoal(charSequence.toString());
                    }
                    else if (mEditTextDifficulty.getText().hashCode() == charSequence.hashCode())
                    {
                        second = true;
                        int tmp = 1;
                        if(!charSequence.toString().equals("")){
                            tmp = Integer.parseInt(charSequence.toString());
                        }

                        int difficulty = 1;
                        if(tmp > 10){
                            difficulty = 10;
                        }else if(tmp < 1){
                            difficulty = 1;
                        }else if(((tmp >= 1 ) && (tmp <= 10))){
                            difficulty = tmp;
                        }
                        mGoal.modifyDifficulty(difficulty);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(first && second){
                        mAddGoalBtn.setEnabled(true);
                    }
                }

        };

        mEditTextGoal.addTextChangedListener(tw);
        mEditTextDifficulty.addTextChangedListener(tw);

    }
     */

    /**
     * Method for scaling down the image captured and sets the image on the imageview (thumbnail)

    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mDisplayPic.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), this);
            mDisplayPic.setImageBitmap(bitmap);
        }
    }

    /**
     * This method contains everything related with photo capturing, including a button listener,
     * specifying where to save the image (with fileprovider).

    private void photoCollect(){
       // mPhotoFile = mGoalSingle.get(this).getPhotoFile(mGoal);
        ///Log.i(TAGOVER, "PATH 1: " + mPhotoFile);
        final Intent cImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i(TAGOVER, "File var:  " + mPhotoFile );

        PackageManager pm = this.getPackageManager();

        boolean canTakePhoto = mPhotoFile != null &&
                cImage.resolveActivity(pm) != null;
        mTakePicBtn.setEnabled(canTakePhoto);

        mTakePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mUri = FileProvider.getUriForFile(getApplicationContext(),
                        "com.cerproduction.dumpadosan.fileprovider",
                        mPhotoFile);
                cImage.putExtra(MediaStore.EXTRA_OUTPUT, mUri);

                List<ResolveInfo> cameraActivities = getPackageManager().queryIntentActivities(cImage,
                        PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    grantUriPermission(activity.activityInfo.packageName,
                            mUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(cImage, REQ_PHOTO);
            }
        });

        updatePhotoView();
    }
     */

    /**
     * Method using FileProvider to save the image captured and updates the photo view
     * Inspired from the course book
     * @param requestCode
     * @param resultCode
     * @param data

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQ_PHOTO) {
            Uri uri = FileProvider.getUriForFile(this,
                    "com.cerproduction.dumpadosan.fileprovider",
                    mPhotoFile);

            this.revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }
     */
    /**
     * Method used to restore the values of the variables before the acitivty stopped. Called on
     * AFTER the onCreate method

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
       // mUri = Uri.parse(savedInstanceState.getString("uri"));
        mGoal.modifyGoal(savedInstanceState.getString("goal_restore"));
        mGoal.update_ID(savedInstanceState.getString("id_uuid_restore"));
        mGoal.modifyDifficulty(savedInstanceState.getInt("difficulty_restore"));
        mGoal.addDate(savedInstanceState.getString("date_str_restore"));
        mGoal.addPartGoal(savedInstanceState.getStringArray("part_goals_restore"));
        mGoalSingle.get(this).updateGoal(mGoal);

    }
     */

    /**
     * Method used to save the state of the activity, preserved if activity were to be destroyed,
     * like eg rotation of the phone

    @Override public void onSaveInstanceState(Bundle outState) {
        //if (mUri !=null) {
           // outState.putString("uri", mUri.toString());
       // }
        //outState.putString("goal_restore", mGoal.getMainGoal());
        //outState.putInt("difficulty_restore", mGoal.getDifficulty());
        //outState.putString("id_uuid_restore", mGoal.getID().toString());
       // outState.putStringArray("part_goals_restore", mGoal.getPartGoals());
       // outState.putString("date_str_restore", mGoal.getDate());
        outState.putSerializable("id_uuid_restore", mGoal.getID());
        super.onSaveInstanceState(outState);

    }

    /*

    @Override
    protected void onPause() {
        super.onPause();
        mGoalSingle.get(this).updateGoal(mGoal);
    }
*/

}