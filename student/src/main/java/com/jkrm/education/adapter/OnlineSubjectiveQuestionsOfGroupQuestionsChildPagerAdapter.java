package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.OnlineAnswerChoiceChildFragment;
import com.jkrm.education.ui.fragment.OnlineAnswerChoiceFragment;
import com.jkrm.education.ui.fragment.OnlineFiveOutOfSevenFragment;
import com.jkrm.education.ui.fragment.OnlineMultipleChoiceChildFragment;
import com.jkrm.education.ui.fragment.OnlineMultipleChoiceFragment;
import com.jkrm.education.ui.fragment.OnlineNonGroupSubjectiveQuestionsChildFragment;
import com.jkrm.education.ui.fragment.OnlineNonGroupSubjectiveQuestionsFragment;
import com.jkrm.education.ui.fragment.OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment;
import com.jkrm.education.ui.fragment.OnlineSubjectiveQuestionsOfGroupQuestionsFragment;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/11 15:49
 */

public class OnlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter extends FragmentPagerAdapter {
    private List<SimilarResultBean> mList;
    List<SimilarResultBean.SubQuestionsBean> subQuestions;
    private Context mContext;
    private SimilarResultBean mBean;

    public SimilarResultBean getBean() {
        return mBean;
    }

    public void setBean(SimilarResultBean bean) {
        mBean = bean;
    }

    public OnlineSubjectiveQuestionsOfGroupQuestionsChildPagerAdapter(FragmentManager fm, List<SimilarResultBean.SubQuestionsBean> subQuestions, Context context) {
        super(fm);
        this.subQuestions = subQuestions;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        SimilarResultBean.SubQuestionsBean similarResultBean = subQuestions.get(position);
        Fragment fragment=new Fragment();
        //选择题（非主观题）
        if("2".equals(similarResultBean.getType().getIsOption())){
            if("1".equals(similarResultBean.getType().getTotalId())||"单选题".equals(similarResultBean.getType().getName())||"4".equals(similarResultBean.getType().getTotalId())){
                fragment=new OnlineAnswerChoiceChildFragment();
                //多选
            }else if("2".equals(similarResultBean.getType().getTotalId())||"多选题".equals(similarResultBean.getType().getName())){
                fragment=new OnlineMultipleChoiceChildFragment();
            }else if("5".equals(similarResultBean.getType().getTotalId())||"七选五".equals(similarResultBean.getType().getName())){
                //七选五
                fragment=new OnlineFiveOutOfSevenFragment();
            }else{
                fragment=new OnlineAnswerChoiceChildFragment();
            }
        }else{
            //拍照上传
            fragment=new OnlineNonGroupSubjectiveQuestionsChildFragment();
        }
        OnlineAnswerChoiceChildFragment onlineAnswerChoiceChildFragment=new OnlineAnswerChoiceChildFragment();
        OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment onlineMultipleChoiceChildFragment = new OnlineSubjectiveQuestionsOfGroupQuestionsChildFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable(Extras.SUB_QUESTION,subQuestions.get(position));
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR,mBean);
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public int getCount() {
        return subQuestions.size();
    }
}
