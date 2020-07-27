package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.OnlineAnswerChoiceFragment;
import com.jkrm.education.ui.fragment.OnlineNonGroupSubjectiveQuestionsFragment;
import com.jkrm.education.ui.fragment.OnlineSubjectiveQuestionsOfGroupQuestionsFragment;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/29 15:50
 */

public class OnlineNonGroupSubjectiveQuestionsAdapter extends FragmentPagerAdapter {
    private List<SimilarResultBean> mList;
    private Context mContext;

    public OnlineNonGroupSubjectiveQuestionsAdapter(FragmentManager fm, List<SimilarResultBean> list, Context context) {
        super(fm);
        mList = list;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        OnlineNonGroupSubjectiveQuestionsFragment onlineSubjectiveQuestionsOfGroupQuestionsFragment=new OnlineNonGroupSubjectiveQuestionsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR, mList.get(position));
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR_LIST,(Serializable) mList);
        onlineSubjectiveQuestionsOfGroupQuestionsFragment.setArguments(bundle);
        return onlineSubjectiveQuestionsOfGroupQuestionsFragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
