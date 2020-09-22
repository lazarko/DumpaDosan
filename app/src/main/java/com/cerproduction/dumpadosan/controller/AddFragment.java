package com.cerproduction.dumpadosan.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cerproduction.dumpadosan.R;
import com.cerproduction.dumpadosan.model.Goal;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {


    private Button mAddGoalBtn;
    private ImageButton mTakePicBtn;
    private EditText mEditTextGoal, mEditTextPart1, mEditTextPart2,
            mEditTextPart3, mEditTextDifficulty;
    private DatePicker date;
    private CheckBox cb;
    GoalSingleton mGoalSingle;
    private File mPhotoFile;
    private Goal mGoal;
    private ImageView mDisplayPic;
    private static final int REQ_PHOTO= 2;


    boolean first = false;
    boolean second = false;

    private String TAGOVER = "TAGOVER";


    public AddFragment() {
        // Required empty public constructor
    }


    /**
     * Creates a new instance of this fragment provided with the UUID parameter
     * @param goal_id
     * @return
     */
    public static AddFragment newInstance(UUID goal_id) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString("uuid_goal_restore", goal_id.toString());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onCreate method for this fragment. It
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            UUID goal_id = UUID.fromString(getArguments().getString("uuid_goal_restore"));
            mGoal = mGoalSingle.get(getActivity()).getGoal(goal_id);
            mPhotoFile = mGoalSingle.get(getActivity()).getPhotoFile(mGoal);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        initVar(v);

        if(mGoal == null){
            Log.i(TAGOVER, "YALLLA");
        }
        collectData();
        photoCollect();

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
                GoalSingleton.get(getActivity()).addGoal(mGoal);
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return v;
    }


    /**
     * Method for scaling down the image captured and sets the image on the imageview (thumbnail)
     */
    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mDisplayPic.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mDisplayPic.setImageBitmap(bitmap);
        }
    }

    /**
     * This method contains everything related with photo capturing, including a button listener,
     * specifying where to save the image (with fileprovider).
     */
    private void photoCollect(){
        // mPhotoFile = mGoalSingle.get(this).getPhotoFile(mGoal);
        ///Log.i(TAGOVER, "PATH 1: " + mPhotoFile);
        final Intent cImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i(TAGOVER, "File var:  " + mPhotoFile );

        PackageManager pm = getActivity().getPackageManager();

        boolean canTakePhoto = mPhotoFile != null &&
                cImage.resolveActivity(pm) != null;
        mTakePicBtn.setEnabled(canTakePhoto);

        mTakePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri mUri = FileProvider.getUriForFile(getActivity(),
                        "com.cerproduction.dumpadosan.fileprovider",
                        mPhotoFile);
                cImage.putExtra(MediaStore.EXTRA_OUTPUT, mUri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().
                        queryIntentActivities(cImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activity : cameraActivities) {
                    getActivity().grantUriPermission(activity.activityInfo.packageName,
                            mUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }

                startActivityForResult(cImage, REQ_PHOTO);
            }
        });

        updatePhotoView();
    }

    /**
     * Method using FileProvider to save the image captured and updates the photo view
     * Inspired from the course book
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQ_PHOTO) {
            Uri uri = FileProvider.getUriForFile(getActivity(),
                    "com.cerproduction.dumpadosan.fileprovider",
                    mPhotoFile);

            getActivity().revokeUriPermission(uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }
    }

    /**
     * Code for collecting data through the listeners, activating the "add-goal-button" and
     * updating the goal object
     */
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

    /**
     * Initializing all variables
     */
    private void initVar(View v){
        mDisplayPic = (ImageView) v.findViewById(R.id.iv_display_pic);
        mTakePicBtn = (ImageButton) v.findViewById(R.id.btn_take_pic);
        mAddGoalBtn = (Button) v.findViewById(R.id.btn_add_goal);
        mEditTextGoal = (EditText) v.findViewById(R.id.editTextGoal);
        mEditTextPart1 = (EditText) v.findViewById(R.id.editTextPartGoal1);
        mEditTextPart2 = (EditText) v.findViewById(R.id.editTextPartGoal2);
        mEditTextPart3 = (EditText) v.findViewById(R.id.editTextPartGoal3);
        mEditTextDifficulty = (EditText) v.findViewById(R.id.editTextDifficulty);
        date = (DatePicker) v.findViewById(R.id.datePicker2);
        cb = (CheckBox) v.findViewById(R.id.checkboxDatum);
        mAddGoalBtn.setEnabled(false);
        date.setMinDate(System.currentTimeMillis() - 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mGoalSingle.get(getActivity()).updateGoal(mGoal);
    }
}