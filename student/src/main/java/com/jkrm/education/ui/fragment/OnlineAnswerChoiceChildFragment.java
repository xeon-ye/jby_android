package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineAnswerChoiceAdapter;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.bean.rx.RxTurnpageType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 单选题 内侧(二级)
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 15:13
 */

public class OnlineAnswerChoiceChildFragment extends AwMvpFragment {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    Unbinder unbinder;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private SimilarResultBean.SubQuestionsBean mBean;
    private OnlineAnswerChoiceAdapter mQuestionBasketOptionsAdapter;
    private List<QuestionOptionBean> mMQuestionOptionBeanList;
    private SimilarResultBean mSimilarResultBean;
    private String mAnswer="";



    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlineanswer_choice_layout;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mBean = (SimilarResultBean.SubQuestionsBean) getArguments().getSerializable(Extras.SUB_QUESTION);
        mSimilarResultBean=(SimilarResultBean)getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        if(null!=mBean){
            mMathViewTitle.setText(mBean.getContent());
            AwMathViewUtil.setImgScan(mMathViewTitle);
        }
        if (mBean.getType() != null && "2".equals(mBean.getType().getIsOption()) && null != mBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
            mQuestionBasketOptionsAdapter = new OnlineAnswerChoiceAdapter();
            AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, mQuestionBasketOptionsAdapter, false);
            setChoiceListData(mBean, mQuestionBasketOptionsAdapter, mRcvData);
        } else {
            mRcvData.setVisibility(View.GONE);
        }
        mRcvData.setItemAnimator(null);
        //取消闪烁动画
       // ((DefaultItemAnimator)mRcvData.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(SimilarResultBean.SubQuestionsBean bean, OnlineAnswerChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        mMQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mMQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty(mMQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mRcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mMQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mQuestionBasketOptionsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<QuestionOptionBean> mQuestionOptionBeanList = (List<QuestionOptionBean>) adapter.getData();
                for (int i = 0; i < mQuestionOptionBeanList.size(); i++) {
                    if (i == position) {
                        mQuestionOptionBeanList.get(i).setSelect(true);
                        mAnswer=mQuestionOptionBeanList.get(i).getSerialNum();
                    } else {
                        mQuestionOptionBeanList.get(i).setSelect(false);
                    }
                }
                mQuestionBasketOptionsAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new RxTurnpageType(false,mSimilarResultBean.getId()));//翻页
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),true,mAnswer,mBean.getParentId()));
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

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        showMsg("提交");
    }
}
