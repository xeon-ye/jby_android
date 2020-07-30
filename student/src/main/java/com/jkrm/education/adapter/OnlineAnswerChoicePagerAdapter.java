package com.jkrm.education.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.OnlineAnswerChoiceChildFragment;
import com.jkrm.education.ui.fragment.OnlineAnswerChoiceFragment;
import com.jkrm.education.ui.fragment.OnlineChoiceOfGroupQuestionsFragment;
import com.jkrm.education.ui.fragment.OnlineFiveOutOfSevenFragment;
import com.jkrm.education.ui.fragment.OnlineMultipleChoiceFragment;
import com.jkrm.education.ui.fragment.OnlineNonGroupSubjectiveQuestionsFragment;
import com.jkrm.education.ui.fragment.OnlineSubjectiveQuestionsOfGroupQuestionsFragment;
import com.jkrm.education.ui.fragment.OnlineSubmitQuestionFragment;
import com.jkrm.education.ui.fragment.OnlineTrueOrFalseFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 在线答题
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 15:57
 */

public class OnlineAnswerChoicePagerAdapter extends FragmentStatePagerAdapter {
    private List<SimilarResultBean> mList;
    private Context mContext;
    private ArrayList<Fragment.SavedState> mSavedState = new ArrayList<Fragment.SavedState>();
    public OnlineAnswerChoicePagerAdapter(FragmentManager fm, List<SimilarResultBean> list, Context context) {
        super(fm);
        mList = list;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        SimilarResultBean similarResultBean = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR, mList.get(position));
        bundle.putSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR_LIST,(Serializable) mList);
        Fragment fragment = new Fragment();
        //最后一个界面是提交界面
        if(position==mList.size()-1){
            fragment=new OnlineSubmitQuestionFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        //选择题（非主观题）
        if("2".equals(similarResultBean.getType().getIsOption())){
            if(similarResultBean.isGroupQuestion()|| !AwDataUtil.isEmpty(similarResultBean.getSubQuestions())){
                //组题
                fragment= new OnlineSubjectiveQuestionsOfGroupQuestionsFragment();
            }
            //非组题
            else {
                if("1".equals(similarResultBean.getType().getTotalid())||"单选题".equals(similarResultBean.getType().getName())){
                    //单选
                    fragment=new OnlineAnswerChoiceFragment();
                }else if("2".equals(similarResultBean.getType().getTotalid())||"多选题".equals(similarResultBean.getType().getName())){
                    //多选
                    fragment=new OnlineMultipleChoiceFragment();
                }else if("5".equals(similarResultBean.getType().getTotalid())||"七选五".equals(similarResultBean.getType().getName())){
                    //七选五  (组选中才会出现)
                    //fragment=new OnlineFiveOutOfSevenFragment();
                }else if("6".equals(similarResultBean.getType().getTotalid())||"判断题".equals(similarResultBean.getType().getName())){
                    fragment=new OnlineTrueOrFalseFragment();
                }
            }

        }else{

            //主观题
            if (similarResultBean.isGroupQuestion()){
                //组题
                fragment= new OnlineSubjectiveQuestionsOfGroupQuestionsFragment();
            }else{
                //非组题
                fragment=new OnlineNonGroupSubjectiveQuestionsFragment();
            }
        }

        fragment.setArguments(bundle);
       /* OnlineAnswerChoiceFragment onlineAnswerChoiceFragment=new OnlineAnswerChoiceFragment();

        onlineAnswerChoiceFragment.setArguments(bundle);*/
        return fragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        // Never maintain any states from the base class, just null it out
        bundle.putParcelableArray("states", null);
        return bundle;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
