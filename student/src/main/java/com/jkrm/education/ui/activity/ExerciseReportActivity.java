package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.RegexUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineExerciseReportNoGroupQuestionAdapter;
import com.jkrm.education.adapter.OnlineExerciseReportSubmitQuestionAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.BatchQuestionBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxReviewStatusType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ExerciseReportPresent;
import com.jkrm.education.mvp.views.ExerciseReportView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 练习报告
 */

public class ExerciseReportActivity extends AwMvpActivity<ExerciseReportPresent> implements ExerciseReportView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.btn_doagain)
    Button mBtnDoagain;
    private String mStrbatchId;
    private BatchBean mBatchBean;
    public static BatchQuestionBean mBatchQuestionBean;
    private boolean isGruopQuestion = false;//是否包含组题
    private List<BatchBean> mQuestionBeanList = new ArrayList<>();
    OnlineExerciseReportNoGroupQuestionAdapter mOnlineExerciseReportNoGroupQuestionAdapter;
    private OnlineExerciseReportSubmitQuestionAdapter mOnlineExerciseReportSubmitQuestionAdapter;

    @Override
    protected ExerciseReportPresent createPresenter() {
        return new ExerciseReportPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercise_report;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbar("练习报告", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        mOnlineExerciseReportNoGroupQuestionAdapter = new OnlineExerciseReportNoGroupQuestionAdapter(mActivity);
        mOnlineExerciseReportSubmitQuestionAdapter = new OnlineExerciseReportSubmitQuestionAdapter(mActivity);


    }

    @Override
    protected void initData() {
        super.initData();
        mStrbatchId = getIntent().getStringExtra(Extras.BATCHID);
        if (AwDataUtil.isEmpty(mStrbatchId)) {
            showDialogToFinish("获取报告失败");
            return;
        }
        mPresenter.getBatch(mStrbatchId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mOnlineExerciseReportNoGroupQuestionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AwSpUtil.clearOne(Extras.OUTSIDE_POS,Extras.OUTSIDE_POS);
                AwSpUtil.saveInt(Extras.OUTSIDE_POS,Extras.OUTSIDE_POS,position);
                toClass(AnswerAnalysisActivity.class, false, Extras.EXERCISEREPORT, (Serializable) mQuestionBeanList, Extras.OUTSIDE_POS, position);

            }
        });
        mOnlineExerciseReportSubmitQuestionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AwSpUtil.saveInt(Extras.OUTSIDE_POS,Extras.OUTSIDE_POS,position);
                toClass(AnswerAnalysisActivity.class, false, Extras.EXERCISEREPORT, (Serializable) mQuestionBeanList, Extras.OUTSIDE_POS, position);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @Override
    public void getBatchSuccess(List<BatchBean> data) {
        mQuestionBeanList = data;
        initRecyclerView();
    }

    private void initRecyclerView() {
        for (int i = 0; i < mQuestionBeanList.size(); i++) {
            if ("2".equals(mQuestionBeanList.get(i).getGroupQuestion())) {
                isGruopQuestion = true;
            }
        }
        mOnlineExerciseReportNoGroupQuestionAdapter.addAllData(mQuestionBeanList);

        mOnlineExerciseReportSubmitQuestionAdapter.addAllData(mQuestionBeanList);
        //有组题
        if (isGruopQuestion) {
            AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mOnlineExerciseReportSubmitQuestionAdapter, false);
        } else {
            AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvData, mOnlineExerciseReportNoGroupQuestionAdapter, 5);
        }
        mPresenter.getBatchQuestion(mStrbatchId);

    }

    @Override
    public void getBatchFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getBatchQuestionSuccess(BatchQuestionBean batchQuestionBean) {
        // showDialog(batchQuestionBean.toString());
        mBatchQuestionBean = batchQuestionBean;
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        String format = decimalFormat.format(Double.parseDouble(mBatchQuestionBean.getRightRate()));
        mTvContent.setText("共" + batchQuestionBean.getQuestNum() + "题,用时" + AwDateUtils.getDate(Integer.parseInt(batchQuestionBean.getUseTime())) + "正确率" + RegexUtil.calculatePercentage(batchQuestionBean.getRightRate()));

        AwLog.e(TAG, mBatchQuestionBean.toString());
    }

    @Override
    public void getBatchQuestionFail(String msg) {
        showMsg(msg);
    }

    @OnClick(R.id.btn_doagain)
    public void onViewClicked() {
        //类型转换
        ArrayList<SimilarResultBean> similarResultBeans = new ArrayList<>();
        for (BatchBean batchBean : mQuestionBeanList) {
            Gson gson = new Gson();
            SimilarResultBean similarResultBean = gson.fromJson(gson.toJson(batchBean), SimilarResultBean.class);
            similarResultBeans.add(similarResultBean);
        }
        toClass(OnlineAnswerActivity.class,true,Extras.KEY_SIMILAR_LIST,(Serializable)similarResultBeans,Extras.COURSE_ID,mQuestionBeanList.get(0).getCourseId());
        AwLog.e(TAG,similarResultBeans.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshState(RxReviewStatusType rxReviewStatusType){
        for (BatchBean batchBean : mQuestionBeanList) {
            if(rxReviewStatusType.getId().equals(batchBean.getId())){
                if(rxReviewStatusType.isRight()){
                    batchBean.setQuestStatus("1");
                }else{
                    batchBean.setQuestStatus("3");
                }
            }
        }
        mOnlineExerciseReportSubmitQuestionAdapter.notifyDataSetChanged();
        mOnlineExerciseReportNoGroupQuestionAdapter.notifyDataSetChanged();
    }
}
