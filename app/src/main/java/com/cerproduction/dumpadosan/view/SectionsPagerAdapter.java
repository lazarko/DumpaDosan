package com.cerproduction.dumpadosan.view;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cerproduction.dumpadosan.R;
import com.cerproduction.dumpadosan.controller.GoalFragment;
import com.cerproduction.dumpadosan.controller.ProgressFragment;


/**
 * @author Lazar Cerovic (2020)
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    /**
     *
     * @param context
     * @param fm
     */
    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Switches between the two fragments. Takes in a tab position and returns a corresponding
     * fragment.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ProgressFragment.newInstance();
                break;
            case 1:
                fragment = GoalFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    /**
     * Returns the number of tab-pages (2)
     * @return int
     */
    @Override
    public int getCount() {
        // Show 2 total pages.
            return 2;
    }


}