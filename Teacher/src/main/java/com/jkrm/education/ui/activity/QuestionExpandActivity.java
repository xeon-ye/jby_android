package com.jkrm.education.ui.activity;

import android.widget.RelativeLayout;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionExtendViewPagerAdapter;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.QuestionSimilarPresent;
import com.jkrm.education.mvp.views.QuestionSimilarView;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;
import com.zzhoujay.richtext.RichText;

import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/24.
 */

public class QuestionExpandActivity extends AwMvpActivity<QuestionSimilarPresent> implements QuestionSimilarView.View {

    @BindView(R.id.scroll_indicator)
    FixedIndicatorView mScrollIndicator;
    @BindView(R.id.scroll_viewPager)
    SViewPager mScrollViewPager;
    @BindView(R.id.ic_noData)
    RelativeLayout mIcNoData;

    private QuestionExtendViewPagerAdapter mViewPagerAdapter;

    private String questionId = "";

    @Override
    protected QuestionSimilarPresent createPresenter() {
        return new QuestionSimilarPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question_extend;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("类题加练", null);
    }

    @Override
    protected void initData() {
        super.initData();

        questionId = getIntent().getStringExtra(Extras.COMMON_PARAMS);
        if(AwDataUtil.isEmpty(questionId)) {
            showDialogToFinish("题目id获取失败");
        }
        mPresenter.getSimilar(questionId);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    public void getSimilarSuccess(List<SimilarResultBean> result) {
        if(AwDataUtil.isEmpty(result)) {
            showView(mIcNoData, true);
            return;
        }
        showView(mIcNoData, false);
        setToolbarWithBackImg("类题加练（共" + result.size() + "题）", null);
        initScoreViewPager(result);
    }

    @Override
    public void getSimilarFail(String msg) {
        showView(mIcNoData, true);
    }

    private void initScoreViewPager(List<SimilarResultBean> list) {
        mScrollIndicator.setScrollBar(new ColorBar(mActivity, getResources().getColor(R.color.color_0093FF), AwDisplayUtil.dipToPix(mActivity, 2)));
        int selectColor = getResources().getColor(R.color.color_0093FF);
        int unSelectColor = getResources().getColor(R.color.black);
        mScrollIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor));
        mScrollViewPager.setCanScroll(true);
        mScrollViewPager.setOffscreenPageLimit(5);

        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mScrollIndicator, mScrollViewPager);
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        mViewPagerAdapter = new QuestionExtendViewPagerAdapter(getSupportFragmentManager(), mActivity, list);
        indicatorViewPager.setAdapter(mViewPagerAdapter);
    }


    @Override
    protected void onDestroy() {
        RichText.recycle();
        super.onDestroy();
    }
}
