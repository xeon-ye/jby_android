package com.jkrm.education.ui.activity.me;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.ui.fragment.MeMainFragment;
import com.jkrm.education.ui.fragment.MyOrderFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends AwBaseActivity {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.viewpageer)
    ViewPager mViewpageer;
    List<String> list = Arrays.asList("全部", "待支付", "已支付", "已取消");
    ArrayList<String> mList = new ArrayList<String>(list);
    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("我的订单", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        //全部订单
        MyOrderFragment AllOrderFragment = new MyOrderFragment();
        Bundle AllOrderBundle=new Bundle();
        AllOrderBundle.putString(Extras.KEY_STEP,"");
        AllOrderFragment.setArguments(AllOrderBundle);
        //待支付
        MyOrderFragment ToBePaidOrderFragment = new MyOrderFragment();
        Bundle ToBePaidBundle=new Bundle();
        ToBePaidBundle.putString(Extras.KEY_STEP,"1");
        ToBePaidOrderFragment.setArguments(ToBePaidBundle);
        //已支付
        MyOrderFragment PaidOrderFragment = new MyOrderFragment();
        Bundle PaidOrderBundle=new Bundle();
        PaidOrderBundle.putString(Extras.KEY_STEP,"2");
        PaidOrderFragment.setArguments(PaidOrderBundle);
        //已取消
        MyOrderFragment CanceledOrderFragment = new MyOrderFragment();
        Bundle CanceleOrderBundle=new Bundle();
        CanceleOrderBundle.putString(Extras.KEY_STEP,"3");
        CanceledOrderFragment.setArguments(CanceleOrderBundle);
        fragments.add(AllOrderFragment);
        fragments.add(ToBePaidOrderFragment);
        fragments.add(PaidOrderFragment);
        fragments.add(CanceledOrderFragment);
        mViewpageer.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {

                return mList.get(position);
            }
        });
        mTabLayout.setupWithViewPager(mViewpageer);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                //setIndicator(tabLayout,60,60);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
