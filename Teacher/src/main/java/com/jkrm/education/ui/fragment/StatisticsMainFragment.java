package com.jkrm.education.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.R;
import com.jkrm.education.ui.fragment.statistics.StatisticsHomeworkFragment;
import com.jkrm.education.ui.fragment.statistics.StatisticsScoreFragment;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

import butterknife.BindView;

/**
 * 统计
 * Created by hzw on 2019/5/5.
 */

public class StatisticsMainFragment extends AwMvpLazyFragment {

    @BindView(R.id.scroll_indicator)
    FixedIndicatorView mScrollIndicator;
    @BindView(R.id.scroll_viewPager)
    SViewPager mScrollViewPager;

    private IndicatorViewPager indicatorViewPager;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();

        float unSelectSize = 16;
        float selectSize = unSelectSize * 1.2f;

        int selectColor = getResources().getColor(R.color.white);
        int unSelectColor = getResources().getColor(R.color.color_B2D8FF);
        mScrollIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        mScrollViewPager.setCanScroll(false);
        mScrollViewPager.setOffscreenPageLimit(3);

        indicatorViewPager = new IndicatorViewPager(mScrollIndicator, mScrollViewPager);
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private Fragment[] fragments = {new StatisticsHomeworkFragment(), new StatisticsScoreFragment()};
        private String[] tabs = {"作业统计", "成绩统计"};

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.tab_top_blue, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabs[position]);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return fragments[position];
        }
    }
}
