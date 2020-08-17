package com.jkrm.education.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerAnalysisPagerAdapter;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.rx.RxLastBean;
import com.jkrm.education.bean.rx.RxNextpageType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.AnswerAnalysisPresent;
import com.jkrm.education.mvp.views.AnswerAnalysisView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作答分析
 */
public class AnswerAnalysisActivity extends AwMvpActivity<AnswerAnalysisPresent> implements AnswerAnalysisView.View {

    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_current)
    TextView mTvCurrent;
    @BindView(R.id.tv_sum)
    TextView mTvSum;
    @BindView(R.id.ll_of_online_answer)
    LinearLayout mLlOfOnlineAnswer;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    private List<BatchBean> mQuestionBeanList = new ArrayList<>();
    private int outSidePos, inSidePos;
    private String mStrType = "", mStrTime = "";
    private boolean isError;
    public static String mStrLastQueID = "";
    private boolean mIsFromSituation;

    @Override
    protected AnswerAnalysisPresent createPresenter() {
        return new AnswerAnalysisPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_analysis;
    }


    @Override
    protected void initData() {
        super.initData();
        setToolbarWithBackImg("题目详情", null);
        setStatusTxtDark();
        mQuestionBeanList = (List<BatchBean>) getIntent().getSerializableExtra(Extras.EXERCISEREPORT);
        outSidePos = getIntent().getIntExtra(Extras.OUTSIDE_POS, 0);
        inSidePos = getIntent().getIntExtra(Extras.INGSIDE_POS, 0);
        mStrType = getIntent().getStringExtra(Extras.KEY_QUESTION_TYPE);
        mStrTime = getIntent().getStringExtra(Extras.KEY_QUESTION_TIME);
        mIsFromSituation = getIntent().getBooleanExtra(Extras.KEY_ANSWER_SITUATION, false);
        //类型和时间不为空 修改头布局
        if (!AwDataUtil.isEmpty(mStrType) || !AwDataUtil.isEmpty(mStrTime)) {
            mTvType.setText(mStrType);
            mTvCurrent.setText(mStrTime);
            isError = true;
        }
        //答题卡进来的
        if (mIsFromSituation) {
            mLlTitle.setVisibility(View.GONE);
            mTvType.setText("题目详情");
            Drawable drawable = this.getResources().getDrawable(R.mipmap.icon_back);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            mTvType.setCompoundDrawables(drawable, null, null, null);
            mTvType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            showView(mTvCurrent,false);
            showView(mTvSum,false);
        }
        AnswerAnalysisPagerAdapter answerAnalysisPagerAdapter = new AnswerAnalysisPagerAdapter(getSupportFragmentManager(), mQuestionBeanList, mActivity);
        answerAnalysisPagerAdapter.setInSidePos(inSidePos);
        //是否是从错题本进来的
        answerAnalysisPagerAdapter.setError(isError);
        mViewpageer.setAdapter(answerAnalysisPagerAdapter);
        mViewpageer.setOffscreenPageLimit(3);
        outSidePos = AwSpUtil.getInt(Extras.OUTSIDE_POS, Extras.OUTSIDE_POS, 0);
        mViewpageer.setCurrentItem(outSidePos);
        mTvSum.setText("/" + (mQuestionBeanList.size()));
        mStrLastQueID = mQuestionBeanList.get(mQuestionBeanList.size() - 1).getId();
    }

    @Override
    protected void initListener() {
        super.initListener();

        mViewpageer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
             /*   if(position==mBatchQuestionBean.getList().size()-1){
                    showView(mLlOfOnlineAnswer,false);
                }else{
                    showView(mLlOfOnlineAnswer,true);
                }*/
                if (AwDataUtil.isEmpty(mStrTime)) {
                    mTvCurrent.setText(position + 1 + "");
                } else {
                    mTvSum.setText(AwDateUtils.getyyyyMMddHHmmss(Long.parseLong(mStrTime)));
                    showView(mTvCurrent, false);
                }
               /* if (position + 1 == mViewpageer.getChildCount()) {
                    EventBus.getDefault().post(new RxLastBean(true));
                } else {
                    EventBus.getDefault().post(new RxLastBean(false));
                }*/
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
    public void collectQuestionSuccess(WatchLogBean request) {

    }

    @Override
    public void collectQuestionFail(String msg) {

    }

    @Override
    public void removeCollectQuestionSuccess(WatchLogBean request) {

    }

    @Override
    public void removeCollectQuestionFail(String msg) {

    }

    @Override
    public void readOverQuestionSuccess(WatchLogBean request) {

    }

    @Override
    public void readOverQuestionFail(String msg) {

    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {

    }

    @Override
    public void getSimilarFail(String msg) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNextPageType(RxNextpageType type) {
        if (null != type && type.isOutSide()) {
            mViewpageer.setCurrentItem(mViewpageer.getCurrentItem() + 1);
        }
    }

}
