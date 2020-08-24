package com.jkrm.education.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hzw.baselib.base.AwBaseApplication;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.UpdateBean;
import com.hzw.baselib.util.AwAPPUtils;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwUpdateUtil;
import com.hzw.baselib.util.AwVersionUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.rx.RxMainViewpagerType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.download.DownLoadService;
import com.jkrm.education.mvp.presenters.MainPresent;
import com.jkrm.education.mvp.views.MainView;
import com.jkrm.education.ui.activity.login.ChoiceLearningSectionActivity;
import com.jkrm.education.ui.fragment.BookExercisesFramgment;
import com.jkrm.education.ui.fragment.ErrorQuestionFragment;
import com.jkrm.education.ui.fragment.ErrorQuestionNewFragment;
import com.jkrm.education.ui.fragment.MeMainFragment;
import com.jkrm.education.ui.fragment.MicrolessonFragment;
import com.jkrm.education.ui.fragment.QuestionBasketFragment;
import com.jkrm.education.ui.fragment.ScoreMainFragment;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.UserUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends AwMvpActivity<MainPresent> implements MainView.View {

    @BindView(R.id.svp_main)
    SViewPager mSvpMain;
    @BindView(R.id.fiv_indicator)
    FixedIndicatorView mFivIndicator;
    private List<Fragment> mFragmentList = new ArrayList<>();
    // 按返回键的判断
    private long[] mHits = new long[2];


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        mFragmentList.add(new ScoreMainFragment());
         //mFragmentList.add(new ErrorQuestionFragment());
        mFragmentList.add(new ErrorQuestionNewFragment());
        // mFragmentList.add(new QuestionBasketFragment());
        mFragmentList.add(new MicrolessonFragment());//微课
        // TODO: 2020/6/18 取消图书习题
        //mFragmentList.add(new BookExercisesFramgment());
        mFragmentList.add(new MeMainFragment());
        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mFivIndicator, mSvpMain);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        mSvpMain.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        mSvpMain.setOffscreenPageLimit(5);

        setStatusBlue();
        mPresenter.getVersionInfo();
        //取消fir更新方式
//        AwFirUtil.checkUpdateInfo(FirUpdateBean.API_STUDENT_ID, updateBean -> runOnUiThread(() -> {
//            if(updateBean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), updateBean.getVersion()) > 0) {
//                if(AwBaseApplication.netWatchdog.hasNet(mActivity)) {
//                    AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {});
//                }
//            }
//        }));
        //取消指针监听
        //        indicatorViewPager.setOnIndicatorPageChangeListener((preItem, currentItem) -> {
        //            switch (currentItem) {
        //                case 0:
        //                case 1:
        //                    setStatusBlue();
        //                    break;
        //                default:
        //                    setStatusTransparentFitsWindow();
        //                    break;
        //            }
        //        });
        //后台服务批量下载文件
        Intent intent= new Intent(this, DownLoadService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getApplicationContext().startForegroundService(intent);
        } else {
            getApplicationContext().startService(intent);
        }
        if(null== UserUtil.getAppUser().getSchool()|| AwDataUtil.isEmpty(UserUtil.getAppUser().getSchool().getId())){
            toClass(ChoiceLearningSectionActivity.class,true, Extras.KEY_REGISTER_ID,UserUtil.getAppUser().getId());
        }
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
    protected void onResume() {
        super.onResume();
        mPresenter.getUserJudge();
    }

    @Override
    public void needReLogin() {
        showDialog("账号已失效，请重新登录", v -> ReLoginUtil.reLogin(mActivity));
    }

    @Override
    public void getVersionInfoSuccess(VersionResultBean bean) {
        if (bean != null && AwVersionUtil.compareVersions(AwAPPUtils.getAppVersionInfo(mActivity, AwAPPUtils.TYPE_VERSION.TYPE_VERSION_NAME), bean.getVersion()) > 0) {
            if (AwBaseApplication.netWatchdog.hasNet(mActivity)) {
                UpdateBean updateBean = new UpdateBean();
                updateBean.setVersion(bean.getVersion());
                updateBean.setUpdateContent(bean.getUpdateContent());
                updateBean.setUrl(bean.getUrl());
                AwUpdateUtil.getInstance(mActivity).handleUpdate(updateBean, () -> {
                });
            }
        }
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
      /*     private String[] tabNames = {"作业", "错题本", "图书习题", "我的"};
       private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
            R.drawable.maintab_4_selector};*/

        // TODO: 2020/6/18  取消图书习题
/*        private String[] tabNames = {"作业", "错题本", "我的"};
        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector,
                R.drawable.maintab_4_selector};*/
               private String[] tabNames = {"作业", "错题本", "微课",  "我的"};
               private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_5_selector,
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
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= SystemClock.uptimeMillis() - 2000) {
            finish();
            ExitAppForced();
        } else {
            showMsg(String.format(getString(R.string.more_click), mHits.length - 1));
        }
    }

    @Override
    protected void reLogin() {
        ReLoginUtil.reLogin(mActivity);
    }

    /** 保存MyTouchListener接口的列表 */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<>();

    /** 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法 */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /** 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法 */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove( listener );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
    public interface MyTouchListener {
        /** onTOuchEvent的实现 */
        boolean onTouchEvent(MotionEvent event);
    }
}
