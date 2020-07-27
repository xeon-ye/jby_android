package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.OnlineMultipleChoiceFragment;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/23 13:58
 */

public class OnlineMultipleChoicePagerAdapter extends FragmentPagerAdapter {
    private List<SimilarResultBean> mList;
    private Context mContext;

    public OnlineMultipleChoicePagerAdapter(FragmentManager fm, List<SimilarResultBean> list, Context context) {
        super(fm);
        mList = list;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        OnlineMultipleChoiceFragment onlineAnswerChoiceFragment=new OnlineMultipleChoiceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR, mList.get(position));
        onlineAnswerChoiceFragment.setArguments(bundle);
        return onlineAnswerChoiceFragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
