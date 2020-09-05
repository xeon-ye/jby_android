package com.jkrm.education.ui.activity.exam;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.ExamGroupAdapter;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ClassesChoicePresent;
import com.jkrm.education.mvp.presenters.ExamTaskPresent;
import com.jkrm.education.mvp.views.ClassesChoiceView;
import com.jkrm.education.mvp.views.ExamTaskView;
import com.jkrm.education.util.UserUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 阅卷任务
 */
public class ExamTaskActivity extends AwMvpActivity<ExamTaskPresent> implements ExamTaskView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private String EXAM_ID, PAPER_ID,READ_WAY,QUESTION_ID;
    private ExamGroupAdapter mExamGroupAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_task;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImg("阅卷任务", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        EXAM_ID = getIntent().getExtras().getString(Extras.EXAM_ID);
        PAPER_ID = getIntent().getExtras().getString(Extras.PAPER_ID);
        mExamGroupAdapter = new ExamGroupAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mExamGroupAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getReviewTaskList(UserUtil.getAppUser().getTeacherId(), EXAM_ID, PAPER_ID);

    }

    @Override
    protected ExamTaskPresent createPresenter() {
        return new ExamTaskPresent(this);
    }


    @Override
    public void getReviewTaskListSuccess(List<ReViewTaskBean> data) {
        mExamGroupAdapter.addAllData(data);
    }

    @Override
    public void getReviewTaskListFail(String msg) {
        showMsg(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
