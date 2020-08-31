package com.jkrm.education.ui.activity.exam;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkCommonUseScoreAdapter;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.CorrectingPresent;
import com.jkrm.education.mvp.views.CorrectionView;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;
import com.jkrm.education.widget.ChoseQuestionNumberDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    /**
     * 是否正在编辑常用分数
     */
    private boolean isEditCommonUse = false;
    MarkCommonUseScoreAdapter mCommonUseScoreAdapter;
    private List<ExamReadHeaderBean> mQuestionNumList=new ArrayList<>();

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
        setStatusTxtDark();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getNotchParams();
        }
        mPresenter.getExamReadHeader(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay());
        mPresenter.getExamQuestions(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay(), mBean.getQuestionId());
    }

    @Override
    protected void initData() {
        super.initData();
        mCommonUseScoreAdapter = new MarkCommonUseScoreAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCommonUseScoreAdapter, true);
    }

    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();

        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                if (null == displayCutout) {
                    return;
                }
                AwLog.d("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                AwLog.d("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                AwLog.d("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                AwLog.d("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());

                List<Rect> rects = displayCutout.getBoundingRects();
                if (rects == null || rects.size() == 0) {
                    AwLog.d("TAG", "不是刘海屏");
                } else {
                    AwLog.d("TAG", "刘海屏数量:" + rects.size());
                    for (Rect rect : rects) {
                        AwLog.d("TAG", "刘海屏区域：" + rect);
                    }
                }

                mFlImgFlLayout.setPadding(displayCutout.getSafeInsetLeft(), 0, 0, 0);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(displayCutout.getSafeInsetLeft(),0,0,0);//4个参数按顺序分别是左上右下
//                mFlImgFlLayout.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public void getExamReadHeaderSuccess(List<ExamReadHeaderBean> data) {
        mQuestionNumList = data;
    }

    @Override
    public void getExamReadHeaderFail(String msg) {
        showMsg(msg);

    }

    @Override
    public void getExamQuestionsSuccess(List<ExamQuestionsBean> data) {
        Glide.with(mActivity).load(data.get(0).getRawScan()).into(mIvQuestionImg);
    }


    @Override
    public void getExamQuestionsFail(String msg) {
        showMsg(msg);

    }

    @Override
    protected void onPause() {
        super.onPause();
        AwLog.d("drawScore... onPause");
        mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_ONPAUSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AwLog.d("drawScore... onResume");
        mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_ONRESUME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_studentId, R.id.tv_studentName, R.id.iv_questionImg, R.id.iv_lastQuestion, R.id.iv_nextQuestion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_studentId:
                break;
            case R.id.tv_studentName:
                ChoseQuestionNumberDialogFragment choseQuestionNumberDialogFragment=new ChoseQuestionNumberDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Extras.KEY_QUESTION_NUM_LIST, (Serializable) mQuestionNumList);
                choseQuestionNumberDialogFragment.setArguments(bundle);
                choseQuestionNumberDialogFragment.show(getSupportFragmentManager(),"");
                choseQuestionNumberDialogFragment.setOnItemClickListener(new ChoseQuestionNumberDialogFragment.onItemClickListener() {
                    @Override
                    public void onItemChickListener(ExamReadHeaderBean bean) {
                        showDialog(bean.getQuestionNum());
                    }
                });
                break;
            case R.id.iv_questionImg:
                break;
            case R.id.iv_lastQuestion:
                break;
            case R.id.iv_nextQuestion:
                break;
        }
    }
}
