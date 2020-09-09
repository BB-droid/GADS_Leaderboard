package com.bbapps.gadsleaderboard.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bbapps.gadsleaderboard.Fragments.LeaderBoardFragment;
import com.bbapps.gadsleaderboard.Fragments.SkilliqFragment;
import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;
import com.bbapps.gadsleaderboard.R;

import java.util.ArrayList;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private ArrayList<LeadersModel> leadersModels;
    private ArrayList<LeadersSkillIQModel> skillIQModels;

    public SectionsPagerAdapter(Context context, FragmentManager fm,
                                ArrayList<LeadersModel> leadersModel,
                                ArrayList<LeadersSkillIQModel> skillIQModels) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        this.skillIQModels = skillIQModels;
        this.leadersModels = leadersModel;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LeaderBoardFragment.newInstance(leadersModels);
        } else {
            return SkilliqFragment.newInstance(skillIQModels);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}