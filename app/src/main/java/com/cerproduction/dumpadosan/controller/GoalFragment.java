package com.cerproduction.dumpadosan.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cerproduction.dumpadosan.R;
import com.cerproduction.dumpadosan.model.Goal;

import java.io.File;
import java.util.List;

/**
 * @author Lazar Cerovic (2020) with inspiration from the book Android Programming:
 * The Big Nerd Ranch Guide by Bill Phillips & Brian Hardy
 * The following class is a fragment which handles the second tab that deals with displaying goals.
 * The goals are displayed with recycleView with the possibility of removing an item.
 */
public class GoalFragment extends Fragment {
    String TAGOVER = "TAGOVER";
    private GoalAdapter mAdapter;
    private RecyclerView mGoalsRecyclerView;

    /**
     * Constructor for this fragment
     */
    public GoalFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of the goal fragment. Auto generated.
     * @return GoalFragment
     */
    public static GoalFragment newInstance() {
        GoalFragment fragment = new GoalFragment();
        return fragment;
    }


    /**
     * Initializes the necessary UI items for the fragment, such as recycleView, which displays
     * the goals.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        mGoalsRecyclerView = (RecyclerView) view
                .findViewById(R.id.goals_recycler_view);
        mGoalsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshUI();
        return view;
    }

    /**
     * Method that updates the user interface
     */
    private void refreshUI() {
        GoalSingleton singleton = GoalSingleton.get(getActivity());
        List<Goal> list = singleton.getGoalList();
        if(mAdapter == null){
            mAdapter = new GoalAdapter(list);
            mGoalsRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.putGoals(list);
            mAdapter.notifyDataSetChanged();
        }
    }


    /**
     * Inner class used for initializing and displaying items in the recyclerview, which is called
     * a ViewHolder.
     */

    private class GoalHold extends RecyclerView.ViewHolder{

        private TextView mGoalTextDisplay, mDateTextView, mDifficultyTextView,
        mPartGoalOneDisplay, mPartGoalTwoDisplay, mPartGoalThreeDisplay;
        private ImageView mImageViewGoalPic;
        private Goal mGoal;
        private ImageButton mTrashButton;



        /**
         * Constructor method for the ViewHolder. Initializes the items in the itemlist, which
         * is used by the RecyclerView.
         * @param inflater
         * @param parent
         */
        public GoalHold(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_goal_item, parent, false));
            mPartGoalOneDisplay = (TextView) itemView.findViewById(R.id.part1_display);
            mPartGoalTwoDisplay = (TextView) itemView.findViewById(R.id.part2_display);
            mPartGoalThreeDisplay = (TextView) itemView.findViewById(R.id.part3_display);
            mDifficultyTextView = (TextView) itemView.findViewById(R.id.difficulty_display);
            mGoalTextDisplay = (TextView) itemView.findViewById(R.id.goal_display);
            mDateTextView = (TextView) itemView.findViewById(R.id.date_display);
            mTrashButton = (ImageButton) itemView.findViewById(R.id.trash_button);
            mImageViewGoalPic = (ImageView) itemView.findViewById(R.id.goal_pic);
        }


        /**
         * Binds the object information (Goal data) with the UI items on the RecyclerView
         * @param g
         */
        public void bind(Goal g) {
            mGoal = g;
            String[] parts = mGoal.getPartGoals();
            mGoalTextDisplay.setText(mGoal.getMainGoal());
            String s = "Svårighetsgrad: " + mGoal.getDifficulty();
            mDifficultyTextView.setText(s);
            mTrashButton.setImageResource(R.drawable.ic_delete);

            //String str = "/data/user/0/com.cerproduction.dumpadosan/files";
            File fileDir = getContext().getFilesDir();
            File file = new File(fileDir, mGoal.getPhotoFilename());
            if(file.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                mImageViewGoalPic.setImageBitmap(myBitmap);
            }

            if(mGoal.getDate() != null){
                mDateTextView.setText("Slutdatum: " + mGoal.getDate());
            }
            if(parts[0] != null){
                mPartGoalOneDisplay.setText("Delmål1: " + parts[0]);
            }
            if(parts[1] != null){
                mPartGoalTwoDisplay.setText("Delmål2: " + parts[1]);
            }
            if(parts[2] != null){
                mPartGoalThreeDisplay.setText("Delmål3: " + parts[2]);
            }

        }

    }

    //-------------------------------------------------------------------------------------------
    /**
     * Inner adapter class for the recyclerView. Binds from the app-specific data to the views
     * that are displayed within the RecycleView. Adds and removes items in the list of goals
     */
    private class GoalAdapter extends RecyclerView.Adapter<GoalHold>{

        private List<Goal> mGoalsList;

        /**
         * Initializes the adapter with the goal list. A constructor method
         * @param list
         */
        public GoalAdapter(List<Goal> list) {
            mGoalsList = list;
        }

        /**
         * This autogenerated method creates a new view holder when there are no existing
         * ViewHolders that the RecyclerView can reuse. It is similar to what ListView does.
         * @param parent
         * @param viewType
         * @return GoalHold
         */
        @NonNull
        @Override
        public GoalHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GoalHold(layoutInflater, parent);
        }

        /**
         * Another autogenerated method. Takes unused ViewHolders and fills them with data
         * to display. Also on scroll replaces old data with new.
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull GoalHold holder, final int position) {
            Goal g = mGoalsList.get(position);
            holder.bind(g);
            holder.mTrashButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final int var = position;
                    GoalSingleton.get(getContext()).deleteGoal(mGoalsList.get(var));
                    mGoalsList.remove(mGoalsList.get(var));
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, mGoalsList.size());
                }
            });

        }

        /**
         * Get method for the amount of elements displayed by the RecyclerView
         * @return listSize (int)
         */
        @Override
        public int getItemCount() {
            return mGoalsList.size();
        }


        /**
         * Assigns the list
         * @param gList
         */
        public void putGoals(List<Goal> gList) {
            mGoalsList = gList;
        }

    }

}