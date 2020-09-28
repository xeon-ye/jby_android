package com.jkrm.education.ui.activity;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwUpdateUtil;
import com.hzw.baselib.util.AwVersionUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.rx.RxAlipushRegisterResultType;
import com.jkrm.education.bean.rx.RxMainViewpagerType;
import com.jkrm.education.mvp.presenters.MainPresent;
import com.jkrm.education.mvp.views.MainView;
import com.jkrm.education.ui.fragment.ErrorQuestionFragment;
import com.jkrm.education.ui.fragment.MainFragment;
import com.jkrm.education.ui.fragment.MarkMainFragment;
import com.jkrm.education.ui.fragment.MeMainFragment;
import com.jkrm.education.ui.fragment.StatisticsMainFragment;
import com.jkrm.education.ui.fragment.exam.ExamFragment;
import com.jkrm.education.util.ReLoginUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/5.
 */

public class MainActivity extends AwMvpActivity<MainPresent> implements MainView.View {

    @BindView(R.id.svp_main)
    SViewPager mSvpMain;
    @BindView(R.id.fiv_indicator)
    FixedIndicatorView mFivIndicator;

    private List<Fragment> mFragmentList = new ArrayList<>();
    // 按返回键的判断
    private long[] mHits = new long[2];
    private MarkMainFragment mMarkMainFragment;
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * 已登录过的账号, 全局监听阿里云推送注册服务, 注册成功绑定当前登录的teacherId为阿里云推送acccount
     * @param type
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxAlipushRegisterResultType type) {
        if(type != null && type.isRegisterSuccess()) {
            //进入首页, 必获得当前教师用户信息了. 此时进行推送绑定账号操作.
            MyApp.getInstance().bindAliPushAccount(false);
        } else {
            showDialog("绑定推送服务失败，将无法实时接收扫描信息，请知悉");
        }

    }

    @Override
    protected void initData() {
        super.initData();
        mMarkMainFragment = new MarkMainFragment();
       // mFragmentList.add(new MainFragment());
        mFragmentList.add(mMarkMainFragment);
        mFragmentList.add(new ExamFragment());
        mFragmentList.add(new ErrorQuestionFragment());
        mFragmentList.add(new StatisticsMainFragment());
//        mFragmentList.add(new TestFragment());
        mFragmentList.add(new MeMainFragment());
        indicatorViewPager = new IndicatorViewPager(mFivIndicator, mSvpMain);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        mSvpMain.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        mSvpMain.setOffscreenPageLimit(4);

        setStatusBlue();

        mPresenter.getVersionInfo();
        //Fir方式更新
//        AwFirUtil.checkUpdateInfo(FirUpdateBean.API_TEACHER_ID, updateBean -> runOnUiThread(() -> {
//            if(updateBean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), updateBean.getVersion()) > 0) {
//                if(AwBaseApplication.netWatchdog.hasNet(mActivity)) {
//                    AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {});
//                }
//            }
//        }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxMainViewpagerType type) {
       mSvpMain.setCurrentItem(type.getIndex());
    }

    @Override
    protected MainPresent createPresenter() {
        return new MainPresent(this);
    }

    @Override
    public void getVersionInfoSuccess(VersionResultBean bean) {
        if(bean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), bean.getVersion()) > 0) {
            if(AwBaseApplication.netWatchdog.hasNet(mActivity)) {
                UpdateBean updateBean = new UpdateBean();
                updateBean.setVersion(bean.getVersion());
                updateBean.setUpdateContent(bean.getUpdateContent());
                updateBean.setUrl(bean.getUrl());
                AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {});
            }
        }
    }


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
  /*      private String[] tabNames = {"首页", "作业", "错题本","统计", "我的"};
        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_5_selector,R.drawable.maintab_3_selector,
                R.drawable.maintab_4_selector};*/
        private String[] tabNames = { "作业","考试", "错题本","统计", "我的"};
        private int[] tabIcons = {R.drawable.maintab_2_selector, R.drawable.maintab_6_selector, R.drawable.maintab_5_selector,R.drawable.maintab_3_selector,
                R.drawable.maintab_4_selector};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return mFragmentList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if(1 == indicatorViewPager.getCurrentItem() && mMarkMainFragment.isDrawerLayoutShowing()) {
            mMarkMainFragment.closeDrawerLayout();
            return;
        }
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= SystemClock.uptimeMillis() - 2000) {
            finish();
            finishAffinity();
            ExitAppForced();
        } else {
            showMsg(String.format(getString(R.string.more_click), mHits.length - 1));
        }
    }

    @Override
    protected void reLogin() {
        ReLoginUtil.reLogin(mActivity);
    }
}
