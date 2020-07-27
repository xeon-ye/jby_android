package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.bean.OriginalPagerResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.SeeOriginalPagerPresent;
import com.jkrm.education.mvp.views.SeeOriginalPagerView;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.UserUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 查看原卷
 */
public class OriginPaperActivity extends AwMvpActivity<SeeOriginalPagerPresent> implements SeeOriginalPagerView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_currentStudent)
    TextView mTvCurrentStudent;
    @BindView(R.id.splash_viewPager)
    ViewPager mSplashViewPager;
    @BindView(R.id.splash_indicator)
    FixedIndicatorView mSplashIndicator;
    @BindView(R.id.drawerLayout_studentSwitch)
    DrawerLayout mDrawerLayoutStudentSwitch;
    private LayoutInflater inflate;
    private List<String> mOriginalPagerResultBeanList = new ArrayList<>();
    private IndicatorViewPager indicatorViewPager;
    private String mHomeworkId;
    private RowsHomeworkBean mRowsHomeworkBean;

    @Override
    protected SeeOriginalPagerPresent createPresenter() {
        return new SeeOriginalPagerPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_origin_paper;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("查看原卷", null);
    }

    @Override
    protected void initData() {
        super.initData();
        inflate = LayoutInflater.from(mActivity);
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        if (mRowsHomeworkBean == null) {
            showDialogToFinish("获取作业数据失败");
            return;
        }
        mTvClasses.setTypeface(CustomFontStyleUtil.getNewRomenType());
        mTvClasses.setText(mRowsHomeworkBean.getHomeworkName());//
        mHomeworkId = mRowsHomeworkBean.getHomeworkId();
        indicatorViewPager = new IndicatorViewPager(mSplashIndicator, mSplashViewPager);
        indicatorViewPager.setAdapter(adapter);
        mPresenter.getStudentOriginalQuestionAnswer(mHomeworkId,UserUtil.getStudId());
    }

    @Override
    public void getStudentOriginalQuestionAnswerSuccess(OriginalPagerResultBean bean) {
        if (bean != null) {
            mOriginalPagerResultBeanList = bean.getRawScan();
            AwLog.d("getStudentOriginalQuestionAnswerSuccess list size: " + mOriginalPagerResultBeanList.size());
            indicatorViewPager.notifyDataSetChanged();
            mSplashViewPager.setCurrentItem(0);
        } else {
            showDialog("获取原卷失败");
        }
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.inflate_layout_viewpager_point_splash, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.inflate_layout_viewpager_see_original_pager, container, false);
            }
            ImageView iv_res = (ImageView) convertView.findViewById(R.id.iv_res);
            AwImgUtil.setImg(mActivity, iv_res, mOriginalPagerResultBeanList.get(position));
            //            iv_res.setBackground(getResources().getDrawable(images[position]));
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mOriginalPagerResultBeanList.size();
        }
    };
}
