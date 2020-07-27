package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.OnlineMultipleChoiceChildFragment;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/9 11:05
 */

public class OnlineMultipleChoiceChildPagerAdapter extends FragmentPagerAdapter {
    private List<SimilarResultBean> mList;
    List<SimilarResultBean.SubQuestionsBean> subQuestions;
    private Context mContext;

    public OnlineMultipleChoiceChildPagerAdapter(FragmentManager fm, List<SimilarResultBean.SubQuestionsBean> subQuestions, Context context) {
        super(fm);
        this.subQuestions = subQuestions;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        OnlineMultipleChoiceChildFragment onlineMultipleChoiceChildFragment = new OnlineMultipleChoiceChildFragment();
        Bundle bundle = new Bundle();
        SimilarResultBean.SubQuestionsBean subQuestionsBean = subQuestions.get(position);
        bundle.putSerializable(Extras.SUB_QUESTION, subQuestions.get(position));
        onlineMultipleChoiceChildFragment.setArguments(bundle);
        return onlineMultipleChoiceChildFragment;
    }

    @Override
    public int getCount() {
        return subQuestions.size();
    }
}
