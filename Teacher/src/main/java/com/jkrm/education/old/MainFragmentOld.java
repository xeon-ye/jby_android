package com.jkrm.education.old;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.github.mikephil.charting.charts.PieChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.util.AwAnimationUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.hzw.baselib.widgets.AwViewCircleImage;
import com.jkrm.education.R;
import com.jkrm.education.adapter.MainLoseViewPagerAdapter;
import com.jkrm.education.adapter.TeacherTodoListAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mpchart.helpers.ChartHomeworkScanResultHelper;
import com.jkrm.education.mvp.presenters.MainFragmentPresent;
import com.jkrm.education.mvp.views.MainFragmentView;
import com.jkrm.education.ui.activity.ScanActivity;
import com.jkrm.education.ui.activity.mark.MarkDetailActivity;
import com.jkrm.education.util.DataUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 原首页
 * Created by hzw on 2019/5/5.
 */

public class MainFragmentOld extends AwMvpFragment<MainFragmentPresent> implements MainFragmentView.View {

    @BindView(R.id.iv_scan)
    ImageView mIvScan;
    @BindView(R.id.iv_scanPoint)
    ImageView mIvScanPoint;
    @BindView(R.id.iv_scanNormal)
    ImageView mIvScanNormal;
    @BindView(R.id.iv_scanPointNormal)
    ImageView mIvScanPointNormal;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_todoTxt)
    TextView mTvTodoTxt;
    @BindView(R.id.tv_homeworkSubmitNo)
    TextView mTvTHomeworkSubmitNo;
    @BindView(R.id.tv_homeworkSubmitYes)
    TextView mTvTHomeworkSubmitYes;
    @BindView(R.id.tv_homeworkCorrectYes)
    TextView mTvTHomeworkCorrectYes;
    @BindView(R.id.tv_homeworkCorrectNo)
    TextView mTvTHomeworkCorrectNo;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.civ_avatar)
    AwViewCircleImage mCivAvatar;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.nsv_scrollview)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.piechart_homeworkScanInner)
    PieChart mPieChartHomeworkScanInner;
    @BindView(R.id.piechart_homeworkScanOut)
    PieChart mPieChartHomeworkScanOut;
    @BindView(R.id.scroll_indicator)
    FixedIndicatorView mScrollIndicator;
    @BindView(R.id.scroll_viewPager)
    SViewPager mScrollViewPager;
    @BindView(R.id.rl_todoHasData)
    RelativeLayout mRlTodoHasData;
    @BindView(R.id.rl_todoNoData)
    RelativeLayout mRlTodoNoData;

    private TeacherTodoListAdapter mTeacherTodoListAdapter;

    private IndicatorViewPager indicatorViewPager;
    private MainLoseViewPagerAdapter mLoseViewPagerAdapter;

    @Override
    protected MainFragmentPresent createPresenter() {
        return new MainFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 接收到扫描信息推送
     * @param cPushMessage
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(CPushMessage cPushMessage) {
        if(cPushMessage != null) {
            if("扫描开始".equals(cPushMessage.getTitle())) {
                showView(mIvScanPoint, true);
                showView(mIvScanPointNormal, true);
                AwAnimationUtil.startAlphaAnimation(mIvScanPoint);
                AwAnimationUtil.startAlphaAnimation(mIvScanPointNormal);
                AwLog.d("alipush 扫描开始...动画闪烁");
            } else {
                mIvScanPoint.clearAnimation();
                mIvScanPointNormal.clearAnimation();
                showView(mIvScanPoint, false);
                showView(mIvScanPointNormal, false);
                AwLog.d("alipush 扫描结束...动画闪烁结束");
            }
            AwLog.d("alipush MainFragment 接收到全局监听到推送信息 messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        setMargins(mRlTitle, 0, 0 - AwDisplayUtil.dip2px(mActivity, 48), 0, 0);
//        mRlTitle.animate().translationY(0 - AwDisplayUtil.dip2px(mActivity, 48));
        //填充nested
        AwImgUtil.setImgAvatar(mActivity, mCivAvatar, "");

        //待办事项
        mTeacherTodoListAdapter = new TeacherTodoListAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mTeacherTodoListAdapter, false);
        initLoseViewPager();
        mTvName.setText(MyApp.getInstance().getAppUser().getNickName());

        mPresenter.getStatusMarkBeforeDawn(MyApp.getInstance().getAppUser().getTeacherId());
        mPresenter.getTeacherTodoList(MyApp.getInstance().getAppUser().getTeacherId(), 0);
    }

    private void initLoseViewPager() {
        mScrollIndicator.setScrollBar(new ColorBar(getActivity(), getResources().getColor(R.color.color_0093FF), AwDisplayUtil.dipToPix(mActivity, 2)));
        int selectColor = getResources().getColor(R.color.color_0093FF);
        int unSelectColor = getResources().getColor(R.color.black);
        mScrollIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor));
        mScrollViewPager.setCanScroll(false);
        mScrollViewPager.setOffscreenPageLimit(2);

        indicatorViewPager = new IndicatorViewPager(mScrollIndicator, mScrollViewPager);
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        mLoseViewPagerAdapter = new MainLoseViewPagerAdapter(getChildFragmentManager(), mActivity);
        indicatorViewPager.setAdapter(mLoseViewPagerAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvScan.setOnClickListener(v -> toClass(ScanActivity.class, false));
        mIvScanNormal.setOnClickListener(v -> toClass(ScanActivity.class, false));
        mNestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY >= AwDisplayUtil.dip2px(mActivity, 48)) {
                    setMargins(mRlTitle, 0, 0, 0, 0);
//                mRlTitle.animate().translationY(0);
            } else {
                    setMargins(mRlTitle, 0, 0 - mRlTitle.getHeight(), 0, 0);
//                mRlTitle.animate().translationY(0 - mRlTitle.getHeight());
            }
        });
        mTeacherTodoListAdapter.setOnItemClickListener((adapter, view, position) -> {
            TeacherTodoBean todoBean = mTeacherTodoListAdapter.getItem(position);
            if(todoBean.isAllowOperate()) {
                //数据组装, 供批阅详情使用
                RowsHomeworkBean rowsHomeworkBean = new RowsHomeworkBean();
                RowsHomeworkBean.ClassesBean classesBean = new RowsHomeworkBean.ClassesBean();
                classesBean.setId(todoBean.getClassId());
                classesBean.setName(todoBean.getClassName());
                classesBean.setPopulation(todoBean.getPopulation());
                RowsHomeworkBean.StatisticsBean statisticsBean = new RowsHomeworkBean.StatisticsBean();
                statisticsBean.setSubmitted(todoBean.getSubmitted());
                rowsHomeworkBean.setId(todoBean.getHomeworkId());
                rowsHomeworkBean.setClasses(classesBean);
                rowsHomeworkBean.setStatistics(statisticsBean);
                toClass(MarkDetailActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, rowsHomeworkBean);
//                EventBus.getDefault().postSticky(new RxMainViewpagerType(1));
            } else {
                showMsg("请在PC端操作");
            }
        });
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private void setPieChartHomeworkScanResult() {
        int submitYes = RandomValueUtil.getNum(30, 60);
        int submitNot = RandomValueUtil.getNum(5, 20);

        int correctYes = RandomValueUtil.getNum(20, submitYes);
        int correctNot = submitYes - correctYes;

        setText(mTvTHomeworkSubmitNo, "未提交：" + submitNot);
        setText(mTvTHomeworkSubmitYes, "已提交：" + submitYes);
        setText(mTvTHomeworkCorrectNo, "未批阅：" + correctNot);
        setText(mTvTHomeworkCorrectYes, "已批阅：" + correctYes);


        Map<String,Float> pieValues2 =new LinkedHashMap<>();
        pieValues2.put("1", Float.valueOf(submitYes + ""));
        pieValues2.put("2", Float.valueOf(submitNot + ""));
        ChartHomeworkScanResultHelper.setPieChartInner(mPieChartHomeworkScanInner, pieValues2);
//
        Map<String,Float> pieValues3 =new LinkedHashMap<>();
        pieValues3.put("3", Float.valueOf(correctYes + ""));
        pieValues3.put("4", Float.valueOf(correctNot + ""));
        pieValues3.put("5", Float.valueOf(submitNot + ""));
        ChartHomeworkScanResultHelper.setPieChartOut(mPieChartHomeworkScanOut, pieValues3);
    }

    @Override
    public void getTeacherTodoListSuccess(String data, List<TeacherTodoBean> list, int total, int size, int pages, int current) {
        if(AwDataUtil.isEmpty(list)) {
            mTeacherTodoListAdapter.clearData();
            mRcvData.removeAllViews();
            mTeacherTodoListAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            showTopTodoView(false, 0);
            return;
        }

        if(list.size() > 4) {
            list = list.subList(0, 4);
        }
        showTopTodoView(true, list.size());
        mTeacherTodoListAdapter.addAllData(DataUtil.convertTeacherTodoBeanList(list));
        mTeacherTodoListAdapter.loadMoreComplete();
        mTeacherTodoListAdapter.setEnableLoadMore(false);
        mTeacherTodoListAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getTeacherTodoListFaile(String msg) {
        mTeacherTodoListAdapter.clearData();
        mRcvData.removeAllViews();
        mTeacherTodoListAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        showTopTodoView(false, 0);
    }

    @Override
    public void getStatusMarkBeforeDawnSuccess(StatusHomeworkScanResultBean bean) {
        if(bean == null) {
            AwLog.d("getStatusMarkBeforeDawnSuccess bean is null");
            return;
        }
        int submitYes = Integer.parseInt(bean.getSubmitted());
        int submitNot = Integer.parseInt(bean.getMissing());

        int correctYes = Integer.parseInt(bean.getProgress());
        int correctNot = Integer.parseInt(bean.getUnprogress());

        setText(mTvTHomeworkSubmitNo, "未提交：" + submitNot);
        setText(mTvTHomeworkSubmitYes, "已提交：" + submitYes);
        setText(mTvTHomeworkCorrectNo, "未批阅：" + correctNot);
        setText(mTvTHomeworkCorrectYes, "已批阅：" + correctYes);


        Map<String,Float> pieValues2 =new LinkedHashMap<>();
        pieValues2.put("1", Float.valueOf(submitYes + ""));
        pieValues2.put("2", Float.valueOf(submitNot + ""));
        ChartHomeworkScanResultHelper.setPieChartInner(mPieChartHomeworkScanInner, pieValues2);
        //
        Map<String,Float> pieValues3 =new LinkedHashMap<>();
        pieValues3.put("3", Float.valueOf(correctYes + ""));
        pieValues3.put("4", Float.valueOf(correctNot + ""));
        pieValues3.put("5", Float.valueOf(submitNot + ""));
        ChartHomeworkScanResultHelper.setPieChartOut(mPieChartHomeworkScanOut, pieValues3);
    }

    @Override
    public void getStatusErrorQuestionInSomeDaySuccess(List<StatusErrorQuestionResultBean> list) {

    }

    @Override
    public void getStatusErrorQuestionInSomeDayFail(String msg) {

    }

    private void showTopTodoView(boolean isShowListView, int todoListSize) {
        mRlTodoHasData.setVisibility(isShowListView ? View.VISIBLE : View.GONE);
        mRlTodoNoData.setVisibility(isShowListView ? View.GONE : View.VISIBLE);
        if(isShowListView){
            mTvTodoTxt.setText(AwDateUtils.getDateHourString() + "！您有" + todoListSize + "条待办事项如下：");
        } else {
            mTvTodoTxt.setText(AwDateUtils.getDateHourString() + "！您目前没有待办事项。");
        }
    }
}
