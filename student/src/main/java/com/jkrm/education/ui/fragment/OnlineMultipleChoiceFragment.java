package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineAnswerChoiceAdapter;
import com.jkrm.education.adapter.OnlineMultipleChoiceChildPagerAdapter;
import com.jkrm.education.adapter.QuestionBasketOptionsAdapter;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 在线多选题 答题
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 15:13
 */

public class OnlineMultipleChoiceFragment extends AwMvpFragment {

    Unbinder unbinder;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.ll)
    LinearLayout mLl;
    @BindView(R.id.handler)
    ImageButton mHandler;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.math_view)
    MathView mMathView;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private SimilarResultBean mBean;
    private OnlineAnswerChoiceAdapter mQuestionBasketOptionsAdapter;
    private OnlineMultipleChoiceChildPagerAdapter mOnlineMultipleChoiceChildPagerAdapter;
    private List<SimilarResultBean> mList = new ArrayList<>();
    private boolean isAnswer=false;//是否作答
    private String mAnswer;


    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlinemultiple_choice_layout;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (SimilarResultBean) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        if (mBean == null) {
            return;
        }
        mMathView.setText(mBean.getContent());
        AwMathViewUtil.setImgScan(mMathView);
/*        mMathViewTitle.setText(mBean.getContent());
        List<SimilarResultBean.SubQuestionsBean> subQuestions = mBean.getSubQuestions();
        mOnlineMultipleChoiceChildPagerAdapter = new OnlineMultipleChoiceChildPagerAdapter(getChildFragmentManager(), subQuestions, getContext());
        mViewpager.setAdapter(mOnlineMultipleChoiceChildPagerAdapter);
        mViewpager.setOffscreenPageLimit(5);*/
        if(mBean.getType() != null && mBean.getType().isChoiceQuestion() && null != mBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
            mQuestionBasketOptionsAdapter = new OnlineAnswerChoiceAdapter();
            AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, mQuestionBasketOptionsAdapter, false);
            setChoiceListData(mBean, mQuestionBasketOptionsAdapter, mRcvData);
        } else {
            mRcvData.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initListener() {
        super.initListener();
    /*    mQuestionBasketOptionsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<QuestionOptionBean> mQuestionOptionBeanList = ( List<QuestionOptionBean>) adapter.getData();
                showMsg(mQuestionOptionBeanList.get(position).getSerialNum());
            }
        });*/
        mQuestionBasketOptionsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<QuestionOptionBean> mQuestionOptionBeanList = (List<QuestionOptionBean>) adapter.getData();
                for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                    if (i == position) {
                        if(mQuestionOptionBeanList.get(i).isSelect()){
                            mQuestionOptionBeanList.get(i).setSelect(false);
                        }else{
                            mQuestionOptionBeanList.get(i).setSelect(true);
                        }
                    }
                }
                //是否作答
                mQuestionBasketOptionsAdapter.notifyDataSetChanged();
                for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                    if(mQuestionOptionBeanList.get(i).isSelect()){
                        isAnswer=true;
                        break;
                    }
                    isAnswer=false;

                }
                //拼接答案
                mAnswer="";
                for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                    if(mQuestionOptionBeanList.get(i).isSelect()){
                        mAnswer+=mQuestionOptionBeanList.get(i).getSerialNum();
                    }
                }
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),isAnswer,mAnswer,mBean.getParentId()));
            }
        });

        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTvNum.setText(position + 1 + "");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(SimilarResultBean bean, OnlineAnswerChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean> mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty(mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mRcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }
}
