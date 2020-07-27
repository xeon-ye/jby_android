package com.jkrm.education.ui.activity.login;

import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.R;
import com.jkrm.education.constants.MyConstant;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/5.
 */

public class GuideActivity extends AwBaseActivity {

    @BindView(R.id.splash_viewPager)
    ViewPager mSplashViewPager;
    @BindView(R.id.splash_indicator)
    FixedIndicatorView mSplashIndicator;

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private int[] images = {R.mipmap.img0, R.mipmap.img1, R.mipmap.img2};
    // 按返回键的判断
    private long[] mHits = new long[2];

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initData() {
        inflate = LayoutInflater.from(mActivity);
        indicatorViewPager = new IndicatorViewPager(mSplashIndicator, mSplashViewPager);
        indicatorViewPager.setAdapter(adapter);
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
                convertView = inflate.inflate(R.layout.inflate_layout_viewpager_splash_page_splash, container, false);
            }
            ImageView iv_res = (ImageView) convertView.findViewById(R.id.iv_res);
            Button btn_start = (Button) convertView.findViewById(R.id.btn_start);
            AwImgUtil.setImg(mActivity, iv_res, images[position]);
            //            iv_res.setBackground(getResources().getDrawable(images[position]));
            showView(btn_start,position == images.length - 1);
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AwSpUtil.saveBoolean(MyConstant.SPConstant.XML_APP_INFO, MyConstant.SPConstant.KEY_IS_FIRST, true);
                    toClass(PswLoginActivity.class, true);
                }
            });
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
            return images.length;
        }
    };

    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= SystemClock.uptimeMillis() - 2000) {
            finish();
        } else {
            showMsg(String.format(getString(R.string.more_click), mHits.length - 1));
        }
    }
}
