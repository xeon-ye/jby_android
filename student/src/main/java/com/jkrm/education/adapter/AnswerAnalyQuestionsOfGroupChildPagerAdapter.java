package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.activity.ErrorQuestionClassifyActivity;
import com.jkrm.education.ui.fragment.AnswerAnalyChoiceChildFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyChoiceFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyExpandFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyNonGroupSubjectiveQuestionsChildFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyNonGroupSubjectiveQuestionsFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyQuestionsOfGroupQuestionsFragment;
import com.jkrm.education.ui.fragment.OnlineAnswerChoiceChildFragment;
import com.jkrm.education.ui.fragment.OnlineFiveOutOfSevenFragment;
import com.jkrm.education.ui.fragment.OnlineMultipleChoiceChildFragment;
import com.jkrm.education.ui.fragment.OnlineNonGroupSubjectiveQuestionsChildFragment;
import com.jkrm.education.ui.fragment.OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/11 15:49
 */

public class AnswerAnalyQuestionsOfGroupChildPagerAdapter extends FragmentPagerAdapter {
    List<BatchBean.SubQuestionsBean> mList;
    private Context mContext;
    public static BatchBean mBatchBean;

    public static BatchBean getBatchBean() {
        return mBatchBean;
    }

    public void setBatchBean(BatchBean batchBean) {
        mBatchBean = batchBean;
    }

    public AnswerAnalyQuestionsOfGroupChildPagerAdapter(FragmentManager fm, List<BatchBean.SubQuestionsBean> list, Context context) {
        super(fm);
        this.mList = list;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();
        BatchBean batchBean = AnswerAnalyQuestionsOfGroupQuestionsFragment.getmBatchBean();
        if (AwDataUtil.isEmpty(batchBean)) {
            batchBean = ErrorQuestionClassifyActivity.getmBatchBean();
        }
        boolean hasExpend = AnswerAnalysisPagerAdapter.isError() && !AwDataUtil.isEmpty(batchBean) && position == mList.size() - 1;
        //错题本进来的
        //if (AnswerAnalysisPagerAdapter.isError()&& !AwDataUtil.isEmpty(batchBean)&&!AwDataUtil.isEmpty(batchBean.getQuestionVideo())||"1".equals(batchBean.getSimilar())) {
        if (hasExpend) {
            if (position == mList.size() - 1) {
                bundle.putSerializable(Extras.BATCHBEAN, batchBean);
                fragment = new AnswerAnalyExpandFragment();
                fragment.setArguments(bundle);
                return fragment;
            }

        }
        //组题选择题
        BatchBean.SubQuestionsBean subQuestionsBean = mList.get(position);
        if ("2".equals(subQuestionsBean.getIsOption())||null != subQuestionsBean.getType() && "2".equals(subQuestionsBean.getType().getIsOption())) {
            fragment = new AnswerAnalyChoiceChildFragment();
        } else {
            fragment = new AnswerAnalyNonGroupSubjectiveQuestionsChildFragment();
        }
        bundle.putSerializable(Extras.BATCHBEAN_SUB, subQuestionsBean);
        //bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR,mBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
