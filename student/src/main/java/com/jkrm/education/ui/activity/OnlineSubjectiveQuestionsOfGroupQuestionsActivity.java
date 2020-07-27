package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineSubjectiveQuestionsOfGroupQuestionsAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineSubjectiveQuestionsOfGroupQuestionsPresent;
import com.jkrm.education.mvp.views.OnlineSubjectiveQuestionsOfGroupQuestionsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kexanie.library.MathView;

/**
 * 组题 主观题
 */
public class OnlineSubjectiveQuestionsOfGroupQuestionsActivity extends AwMvpActivity<OnlineSubjectiveQuestionsOfGroupQuestionsPresent> implements OnlineSubjectiveQuestionsOfGroupQuestionsView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_current)
    TextView mTvCurrent;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    String questionId;
    @BindView(R.id.math_view_title)
    MathView mMathViewTitle;
    @BindView(R.id.tv_ans_cur)
    TextView mTvAnsCur;

    private List<SimilarResultBean> mList = new ArrayList<>();
    private int postion;//当前第几题
    private Timer mTimer;//计时
    private long mTime;

    @Override
    protected OnlineSubjectiveQuestionsOfGroupQuestionsPresent createPresenter() {
        return new OnlineSubjectiveQuestionsOfGroupQuestionsPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTransparent();
        setToolbarWithBackImg("", null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_subjective_questions_of_group_questions;
    }

    @Override
    protected void initData() {
        super.initData();
        questionId = getIntent().getStringExtra(Extras.COMMON_PARAMS);
        if (AwDataUtil.isEmpty(questionId)) {
            showDialogToFinish("题目id获取失败");
        }
        mPresenter.getSimilar(questionId);
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if (AwDataUtil.isEmpty(result)) {
            showDialogToFinish("暂无类题加连");
            return;
        }
        mList = result;
        mTvSum.setText("/" + mList.size());
        initQuestion(mList.get(0), postion);
        initScoreViewPager(result);
    }

    @Override
    public void getSimilarFail(String msg) {
        showDialogToFinish("暂无类题加连");
    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    private void initScoreViewPager(List<SimilarResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            return;
        }
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        OnlineSubjectiveQuestionsOfGroupQuestionsAdapter mViewPagerAdapter = new OnlineSubjectiveQuestionsOfGroupQuestionsAdapter(getSupportFragmentManager(), list, mActivity);
        mViewpageer.setAdapter(mViewPagerAdapter);
        mTvSum.setText("/" + list.size());
    }

    private void initQuestion(SimilarResultBean similarResultBean, int postion) {
        mMathViewTitle.setText(similarResultBean.getContent());//题
        mTvCurrent.setText(postion + 1 + "");
        //initScoreViewPager(similarResultBean);
        initTimer();//开始计时
    }

    private void initScoreViewPager(SimilarResultBean similarResultBean) {

    }

    private void initTimer() {
        mTime = 0;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTime += 1000;
                        mToolbarCustom.setRightText(AwDateUtils.getmmssDateFormat(mTime));
                    }
                });
            }
        }, 0, 1000);
    }

    private void cancelTimer() {
        if (null != mTimer) {
            mTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    private void doNext() {
        if (postion < mList.size()) {
            postion++;
            initQuestion(mList.get(postion), postion);
        } else {
            showMsg("已是最后一题");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
