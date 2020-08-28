package com.jkrm.education.ui.activity.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.jkrm.education.R;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.CorrectingPresent;
import com.jkrm.education.mvp.views.CorrectionView;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 批阅
 */
public class CorrectingActivity extends AwMvpActivity<CorrectingPresent> implements CorrectionView.View {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_studentId)
    TextView mTvStudentId;
    @BindView(R.id.tv_studentName)
    TextView mTvStudentName;
    @BindView(R.id.tv_questionInfo)
    TextView mTvQuestionInfo;
    @BindView(R.id.tv_commonUse)
    TextView mTvCommonUse;
    @BindView(R.id.iv_commonUse)
    ImageView mIvCommonUse;
    @BindView(R.id.tv_totalMarkPercent)
    TextView mTvTotalMarkPercent;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.ll_commonUse)
    LinearLayout mLlCommonUse;
    @BindView(R.id.tv_markByQuestion)
    TextView mTvMarkByQuestion;
    @BindView(R.id.tv_handleSwitch)
    TextView mTvHandleSwitch;
    @BindView(R.id.btn_seeOriginalQuestion)
    Button mBtnSeeOriginalQuestion;
    @BindView(R.id.btn_resetScore)
    Button mBtnResetScore;
    @BindView(R.id.btn_addSpecial)
    Button mBtnAddSpecial;
    @BindView(R.id.rl_layoutBottom)
    RelativeLayout mRlLayoutBottom;
    @BindView(R.id.iv_questionImg)
    CanvasImageViewWithScale mIvQuestionImg;
    @BindView(R.id.iv_lastQuestion)
    ImageView mIvLastQuestion;
    @BindView(R.id.iv_nextQuestion)
    ImageView mIvNextQuestion;
    @BindView(R.id.fl_imgFlLayout)
    FrameLayout mFlImgFlLayout;
    @BindView(R.id.rv_leftLayout)
    RelativeLayout mRvLeftLayout;
    @BindView(R.id.view_alpha)
    View mViewAlpha;
    @BindView(R.id.fl_leftLayout)
    FrameLayout mFlLeftLayout;
    private ReViewTaskBean.Bean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_correcting;
    }

    @Override
    protected CorrectingPresent createPresenter() {
        return new CorrectingPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mBean = (ReViewTaskBean.Bean) getIntent().getExtras().getSerializable(Extras.REVIEW_TASK_BEAN);
        mPresenter.getExamReadHeader(UserUtil.getAppUser().getTeacherId(),mBean.getExamId(),mBean.getPaperId(),mBean.getReadWay());
        mPresenter.getExamQuestions(UserUtil.getAppUser().getTeacherId(),mBean.getExamId(),mBean.getPaperId(),mBean.getReadWay(),mBean.getQuestionId());
    }

    @Override
    public void getExamReadHeaderSuccess(String data) {
        showMsg(data);
    }

    @Override
    public void getExamReadHeaderFail(String msg) {
        showMsg(msg);

    }

    @Override
    public void getExamQuestionsSuccess(String data) {
        showMsg(data);

    }

    @Override
    public void getExamQuestionsFail(String msg) {
        showMsg(msg);

    }
}
