package com.jkrm.education.ui.activity.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ReviewPresent;
import com.jkrm.education.mvp.views.ReviewView;
import com.jkrm.education.util.UserUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回评检测
 */
public class ReviewActivity   extends AwMvpActivity<ReviewPresent> implements ReviewView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private String EXAM_ID,PAPER_ID,READ_WAY,QUESTION_ID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_review;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImg("回评检查", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        EXAM_ID= (String) getIntent().getExtras().get(Extras.EXAM_ID);
        PAPER_ID= (String) getIntent().getExtras().get(Extras.PAPER_ID);
        READ_WAY= (String) getIntent().getExtras().get(Extras.READ_WAY);
        QUESTION_ID= (String) getIntent().getExtras().get(Extras.QUESTION_ID);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getExamReviewScore(UserUtil.getAppUser().getTeacherId(),EXAM_ID,PAPER_ID,READ_WAY,QUESTION_ID);
    }

    @Override
    protected ReviewPresent createPresenter() {
        return new ReviewPresent(this);
    }


    @Override
    public void getExamReviewScoreSuccess(String data) {
        showMsg(data);
    }

    @Override
    public void getExamReviewScoreFail(String msg) {
        showMsg(msg);

    }

    @Override
    public void getExamReviewListSuccess(String data) {
        showMsg(data);

    }

    @Override
    public void getExamReviewListFail(String msg) {
        showMsg(msg);

    }
}
