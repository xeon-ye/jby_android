package com.jkrm.education.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkrm.education.R;
import com.jkrm.education.ui.fragment.ChartClassesScoreStautsFragment;
import com.jkrm.education.ui.fragment.ChartQuestionScorePercentFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

/**
 * Created by hzw on 2019/5/10.
 */

public class HomeworkDetailViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private Context mContext;
    private String[] titles = {"成绩分布", "得分率"};
    private Fragment[] mFragments = {new ChartClassesScoreStautsFragment(), new ChartQuestionScorePercentFragment()};


    public HomeworkDetailViewPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(titles[position]);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return mFragments[position];
    }

}
