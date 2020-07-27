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
import org.greenrobot.eventbus.EventBus;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 单选题 外侧
 * @Author: xiangqian
 * @CreateDate: 2020/4/22 15:13
 */

public class OnlineAnswerChoiceFragment extends AwMvpFragment {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    Unbinder unbinder;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    private SimilarResultBean mBean;
    private OnlineAnswerChoiceAdapter mQuestionBasketOptionsAdapter;
    private List<QuestionOptionBean> mMQuestionOptionBeanList;
    private List<SimilarResultBean> mList=new ArrayList<>();
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
        mBean = (SimilarResultBean) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        mList = (List<SimilarResultBean>) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR_LIST);
        if (mBean == null) {
            return;
        }
       /* if (mBean.getId().equals(mList.get(mList.size() - 1).getId())) {
            showView(mTvSubmit,true);
        }*/
        mTvTitle.setText(mBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        mMathViewTitle.setText(mBean.getContent());
        mQuestionBasketOptionsAdapter = new OnlineAnswerChoiceAdapter();
        if (mBean.getType() != null && mBean.getType().isChoiceQuestion() && null != mBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
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
    private void setChoiceListData(SimilarResultBean bean, OnlineAnswerChoiceAdapter adapter, RecyclerView rcvData) {
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
                //作答
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),true,mAnswer,mBean.getParentId()));
                EventBus.getDefault().post(new RxTurnpageType(true,mBean.getId()));
            }
        });

    }



    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        showMsg("提交");
    }
}
