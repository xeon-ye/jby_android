package com.jkrm.education.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwMathViewUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.RegexUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalyChoiceAdapter;
import com.jkrm.education.adapter.AnswerAnalyQuestionsOfGroupChildPagerAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.AnswerAnalysisPresent;
import com.jkrm.education.mvp.views.AnswerAnalysisView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.kexanie.library.MathView;

/**
 * @Description:
 * @Author: xiangqian
 * @CreateDate: 2020/05/28
 */

public class AnswerAnalyChoiceChildFragment extends AwMvpFragment<AnswerAnalysisPresent> implements AnswerAnalysisView.View {


    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_answer_state)
    TextView mTvAnswerState;
    @BindView(R.id.tv_answer)
    TextView mTvAnswer;
    @BindView(R.id.tv_analysis)
    TextView mTvAnalysis;
    @BindView(R.id.math_view_analysis)
    MathView mMathViewAnalysis;
    Unbinder unbinder;
    @BindView(R.id.tv_question_num)
    TextView mTvQuestionNum;
    private ArrayList<QuestionOptionBean> mQuestionOptionBeanList;
    private AnswerAnalyChoiceAdapter mQuestionBasketOptionsAdapter;
    private BatchBean.SubQuestionsBean mBatchBean;

    @Override
    protected AnswerAnalysisPresent createPresenter() {
        return new AnswerAnalysisPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_answeranaly_choice_child_layout;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mBatchBean = (BatchBean.SubQuestionsBean) getArguments().getSerializable(Extras.BATCHBEAN_SUB);
        if (null == mBatchBean) {
            return;
        }
        //外部大题 请求 类题
        BatchBean batchBean = AnswerAnalyQuestionsOfGroupChildPagerAdapter.getBatchBean();
        //题干
        mMathViewTitle.setText(mBatchBean.getContent());
        AwMathViewUtil.setImgScan(mMathViewTitle);
        //解析
        mMathViewAnalysis.setText(mBatchBean.getAnswerExplanation());
        AwMathViewUtil.setImgScan(mMathViewAnalysis);
        if (AwDataUtil.isEmpty(mBatchBean.getAnswerExplanation())) {
            showView(mTvAnalysis, false);
        }
        if(!AwDataUtil.isEmpty(mBatchBean.getQuestionNum())){
            mTvQuestionNum.setText("第"+mBatchBean.getQuestionNum()+"题");
        }
        if (AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            mTvAnswerState.setText("未作答");
            mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
            //正确答案
            // mTvAnswer.setText("正确答案："+Html.fromHtml(mBatchBean.getAnswer()));
        } else {
            if (AwDataUtil.isEmpty(mBatchBean.getStudAnswer()) || AwDataUtil.isEmpty(mBatchBean.getAnswer())) {
                return;
            }
            int allRight = RegexUtil.isAllRight(Html.fromHtml(mBatchBean.getAnswer()).toString(), mBatchBean.getStudAnswer());
            if (0 == allRight) {
                mTvAnswerState.setText("回答正确");
                mTvAnswerState.setTextColor(getResources().getColor(R.color.black));
                //正确答案
                mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()));
            } else if (1 == allRight) {
                //半对
                mTvAnswerState.setText("回答不全");
                mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
                //正确答案
                mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()) + "我的答案：" + mBatchBean.getStudAnswer());
            } else if (2 == allRight) {
                //错误
                mTvAnswerState.setText("回答错误");
                mTvAnswerState.setTextColor(getResources().getColor(R.color.red));
                //正确答案
                mTvAnswer.setText("正确答案：" + Html.fromHtml(mBatchBean.getAnswer()) + "我的答案：" + mBatchBean.getStudAnswer());
            }

        }


        if ("2".equals(mBatchBean.getIsOption()) || mBatchBean.getType() != null && "2".equals(mBatchBean.getType().getIsOption()) && null != mBatchBean.getOptions()) {
            mRcvData.setVisibility(View.VISIBLE);
            mQuestionBasketOptionsAdapter = new AnswerAnalyChoiceAdapter();
            AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, mQuestionBasketOptionsAdapter, false);
            setChoiceListData(mBatchBean, mQuestionBasketOptionsAdapter, mRcvData);
        } else {
            mRcvData.setVisibility(View.GONE);
        }
        mRcvData.setItemAnimator(null);
        changeAnswerState();
        //取消闪烁动画
        // ((DefaultItemAnimator)mRcvData.getItemAnimator()).setSupportsChangeAnimations(false);*/
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(BatchBean.SubQuestionsBean bean, AnswerAnalyChoiceAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        mQuestionOptionBeanList = new ArrayList<>();

        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty((Serializable) mQuestionOptionBeanList)) {
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

    private void changeAnswerState() {
        if (AwDataUtil.isEmpty(mBatchBean.getAnswer())) {
            return;
        }
        char[] right = Html.fromHtml(mBatchBean.getAnswer()).toString().toCharArray();//html 读取 因为有新罗马字体
        char[] custom = new char[]{};
        if (null == mQuestionOptionBeanList || mQuestionOptionBeanList.size() == 0) {
            return;
        }
        if (!AwDataUtil.isEmpty(mBatchBean.getStudAnswer())) {
            custom = mBatchBean.getStudAnswer().toCharArray();
        }
        for (char c : custom) {
            String s = String.valueOf(c);
            for (QuestionOptionBean questionOptionBean : mQuestionOptionBeanList) {
                if (s.equals(questionOptionBean.getSerialNum())) {
                    questionOptionBean.setChoose(1);
                }
            }
        }
        for (char c : right) {
            String s = String.valueOf(c);

            for (QuestionOptionBean questionOptionBean : mQuestionOptionBeanList) {
                if (s.equals(questionOptionBean.getSerialNum())) {
                    questionOptionBean.setChoose(2);
                }
            }
        }
        if (null != mQuestionBasketOptionsAdapter) {
            mQuestionBasketOptionsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
       /* mQuestionBasketOptionsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
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
        });*/

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
    public void collectQuestionSuccess(WatchLogBean request) {
        showMsg("收藏成功");
    }

    @Override
    public void collectQuestionFail(String msg) {
        showMsg("收藏失败");
    }

    @Override
    public void removeCollectQuestionSuccess(WatchLogBean request) {
        showMsg("移除收藏成功");
    }

    @Override
    public void removeCollectQuestionFail(String msg) {
        showMsg("移除收藏失败");
    }

    @Override
    public void readOverQuestionSuccess(WatchLogBean request) {
        showMsg("批阅成功");
    }

    @Override
    public void readOverQuestionFail(String msg) {
        showMsg("批阅失败");
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {

    }

    @Override
    public void getSimilarFail(String msg) {

    }

}
