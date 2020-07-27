package com.jkrm.education.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.BatchQuestionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.AnswerAnalyChoiceFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyNonGroupSubjectiveQuestionsFragment;
import com.jkrm.education.ui.fragment.AnswerAnalyQuestionsOfGroupQuestionsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/28 19:08
 */

public class AnswerAnalysisPagerAdapter extends FragmentPagerAdapter {
    private List<BatchBean> mList=new ArrayList<>();
    private Context mContext;
    private static int inSidePos;
    private static boolean isError;

    public static boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setInSidePos(int inSidePos) {
            inSidePos = inSidePos;
    }

    public static int getInSidePos() {
        return inSidePos;
    }

    public AnswerAnalysisPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public AnswerAnalysisPagerAdapter(FragmentManager fm, List<BatchBean> list, Context context) {
        super(fm);
        mList = list;
        mContext = context;
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=new Fragment();
        BatchBean batchBean = mList.get(position);
        Bundle bundle=new Bundle();
        bundle.putString(Extras.KEY_QUESTION_TYPE,batchBean.getErrorTypeName());
        bundle.putSerializable(Extras.BATCHBEAN,batchBean);
        Log.e( "getItem: ",batchBean.toString()+"" );
        //组题目
        if(batchBean.isGroupQuestion()){
            fragment=new  AnswerAnalyQuestionsOfGroupQuestionsFragment();
        }else{
            //非组题 选择题
            if("2".equals(batchBean.getIsOption())||"2".equals(batchBean.getType().getIsOption())){
                fragment=new AnswerAnalyChoiceFragment();
            }else{
                //非组题 非选择题
                fragment=new AnswerAnalyNonGroupSubjectiveQuestionsFragment();
            }
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
