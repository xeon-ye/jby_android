package com.jkrm.education.ui.activity.homework;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.QuestionSpecialResultBean;
import com.jkrm.education.constants.Extras;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/19.
 */

public class SeeSpecialActivity extends AwMvpActivity {

    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_currentStudent)
    TextView mTvCurrentStudent;
    @BindView(R.id.splash_viewPager)
    ViewPager mSplashViewPager;
    @BindView(R.id.splash_indicator)
    FixedIndicatorView mSplashIndicator;

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private List<QuestionSpecialResultBean> mSpecialBeanList = new ArrayList<>();

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_see_special;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("查看典型卷", null);
    }

    @Override
    protected void initData() {
        super.initData();
        mSpecialBeanList = (List<QuestionSpecialResultBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_SPECIAL_LIST);
        if(AwDataUtil.isEmpty(mSpecialBeanList)) {
            showDialogToFinish("典型题目列表不存在");
            return;
        }
        inflate = LayoutInflater.from(mActivity);
        indicatorViewPager = new IndicatorViewPager(mSplashIndicator, mSplashViewPager);
        indicatorViewPager.setAdapter(adapter);

        setText(mTvClasses, mSpecialBeanList.get(0).getClassName());
        setText(mTvCurrentStudent, mSpecialBeanList.get(0).getStudentName());
        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
            setText(mTvClasses, mSpecialBeanList.get(currentItem).getClassName());
            setText(mTvCurrentStudent, mSpecialBeanList.get(currentItem).getStudentName());
        });
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
            AwImgUtil.setImg(mActivity, iv_res, mSpecialBeanList.get(position).getScanImage());
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
            return mSpecialBeanList.size();
        }
    };
}
