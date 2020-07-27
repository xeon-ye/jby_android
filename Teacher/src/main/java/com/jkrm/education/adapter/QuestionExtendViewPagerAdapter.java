package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzw.baselib.util.AwNumToChineseUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.QuestionExtendFragment;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.List;

/**
 * Created by hzw on 2019/5/10.
 */

public class QuestionExtendViewPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private List<SimilarResultBean> mList;
    private Context mContext;


    public QuestionExtendViewPagerAdapter(FragmentManager fragmentManager, Context context, List<SimilarResultBean> list) {
        super(fragmentManager);
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tab_top, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText("题目" + AwNumToChineseUtil.intToChinese(position + 1));
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        QuestionExtendFragment fragment = new QuestionExtendFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR, mList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

}
