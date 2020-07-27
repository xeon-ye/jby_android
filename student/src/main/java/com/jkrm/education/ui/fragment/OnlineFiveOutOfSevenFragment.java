package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineAnswerChoiceAdapter;
import com.jkrm.education.adapter.OnlineAnswerFiveOutSevenChoiceAdapter;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxFiveOutSevenType;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.bean.rx.RxTurnpageType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description: 七选五
 * @Author: xiangqian
 * @CreateDate: 2020/5/12 19:06
 */

public class OnlineFiveOutOfSevenFragment extends AwMvpFragment {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    Unbinder unbinder;
    private SimilarResultBean.SubQuestionsBean mBean;
    private OnlineAnswerFiveOutSevenChoiceAdapter mQuestionBasketOptionsAdapter;
    private List<QuestionOptionBean> mMQuestionOptionBeanList;
    private boolean isAnswer=false;
    private String mAnswer="";
    private SimilarResultBean mOutSideBean;



    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlineanswer_fiveout_of_seven_layout;
    }


    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        mBean = (SimilarResultBean.SubQuestionsBean) getArguments().getSerializable(Extras.SUB_QUESTION);
        mOutSideBean=(SimilarResultBean)getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR);
        if (mBean == null) {
            return;
        }
        mMathViewTitle.setText(mBean.getContent());
/*        mMathViewTitle.setText(mBean.getContent());
        List<SimilarResultBean.SubQuestionsBean> subQuestions = mBean.getSubQuestions();
        mOnlineMultipleChoiceChildPagerAdapter = new OnlineMultipleChoiceChildPagerAdapter(getChildFragmentManager(), subQuestions, getContext());
        mViewpager.setAdapter(mOnlineMultipleChoiceChildPagerAdapter);
        mViewpager.setOffscreenPageLimit(5);*/
        if(mBean.getType() != null && "2".equals(mBean.getType().getIsOption()) && null != mBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
            mQuestionBasketOptionsAdapter = new OnlineAnswerFiveOutSevenChoiceAdapter();
            AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, mQuestionBasketOptionsAdapter, false);
            setChoiceListData(mBean, mQuestionBasketOptionsAdapter, mRcvData);
        } else {
            mRcvData.setVisibility(View.GONE);
        }
        mRcvData.setItemAnimator(null);
          //((SimpleItemAnimator)mRcvData.getItemAnimator()).setSupportsChangeAnimations(false);

    }
    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(SimilarResultBean.SubQuestionsBean bean, OnlineAnswerFiveOutSevenChoiceAdapter adapter, RecyclerView rcvData) {
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
                        //默认 变 选中
                        if(0==mQuestionOptionBeanList.get(i).getChoose()){
                            mQuestionOptionBeanList.get(i).setChoose(1);
                            //通知其他页面置灰
                            EventBus.getDefault().post(new RxFiveOutSevenType(mBean.getId(),mQuestionOptionBeanList.get(i).getSerialNum(),2));
                            //作答 后 翻页
                            EventBus.getDefault().post(new RxTurnpageType(false,mOutSideBean.getId()));
                        }else if(1==mQuestionOptionBeanList.get(i).getChoose()){
                            //选中 变 置灰
                            mQuestionOptionBeanList.get(i).setChoose(2);
                        }else if(2==mQuestionOptionBeanList.get(i).getChoose()){
                            //置灰 变 默认
                            mQuestionOptionBeanList.get(i).setChoose(0);
                        }
                    }else{
                        //已选中的话恢复至默认
                        if(1==mQuestionOptionBeanList.get(i).getChoose()){
                            mQuestionOptionBeanList.get(i).setChoose(0);
                        }
                    }
                }
                mQuestionBasketOptionsAdapter.notifyDataSetChanged();
                mAnswer="";
                //通知是否作答
                for (QuestionOptionBean questionOptionBean : mMQuestionOptionBeanList) {
                    if(1==questionOptionBean.getChoose()){
                        isAnswer=true;
                        mAnswer=questionOptionBean.getSerialNum();
                    }
                }
                //小题是否作答
                EventBus.getDefault().post(new RxQuestionBean(mBean.getId(),isAnswer,mAnswer,mBean.getParentId()));
                //fragment 复用置空
                isAnswer=false;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshChoceState(RxFiveOutSevenType bean) {
        //修改七选五状态
        List<QuestionOptionBean> data = mQuestionBasketOptionsAdapter.getData();
        for (QuestionOptionBean datum : mMQuestionOptionBeanList) {
            if(!bean.getId().equals(mBean.getId())&&bean.getChoice().equals(datum.getSerialNum())){
                //如果未选中的话 置灰
                if(1!=datum.getChoose()){
                    datum.setChoose(2);
                }
            }
        }
        mQuestionBasketOptionsAdapter.notifyDataSetChanged();
    }
}
