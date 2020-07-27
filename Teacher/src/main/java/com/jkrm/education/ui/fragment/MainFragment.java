package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.CombineChartCommonSingleYBubbleHelper;
import com.hzw.baselib.util.AwAnimationUtil;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.AwToastUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.hzw.baselib.widgets.AwViewCircleImage;
import com.jkrm.education.R;
import com.jkrm.education.adapter.TeacherTodoListAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.result.TeacherTodoBean;
import com.jkrm.education.bean.rx.RxAlipushDataResultType;
import com.jkrm.education.bean.rx.RxUnConnectType;
import com.jkrm.education.bean.rx.RxUpdateUserBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MainFragmentPresent;
import com.jkrm.education.mvp.views.MainFragmentView;
import com.jkrm.education.ui.activity.ScanActivity;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.ui.activity.mark.MarkDetailActivity;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 首页
 * Created by hzw on 2019/5/5.
 */

public class MainFragment extends AwMvpLazyFragment<MainFragmentPresent> implements MainFragmentView.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.iv_scan)
    ImageView mIvScan;
    @BindView(R.id.iv_scanPoint)
    ImageView mIvScanPoint;
    @BindView(R.id.iv_scanNormal)
    ImageView mIvScanNormal;
    @BindView(R.id.iv_scanQrCode)
    ImageView mIvScanQrCode;
    @BindView(R.id.iv_scanQrCodeNormal)
    ImageView mIvScanQrCodeNormal;
    @BindView(R.id.iv_scanPointNormal)
    ImageView mIvScanPointNormal;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_todoTxt)
    TextView mTvTodoTxt;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.civ_avatar)
    AwViewCircleImage mCivAvatar;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.nsv_scrollview)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.rl_todoHasData)
    RelativeLayout mRlTodoHasData;
    @BindView(R.id.rl_todoNoData)
    RelativeLayout mRlTodoNoData;
    @BindView(R.id.barchart)
    BarChart mBarchart;
    @BindView(R.id.combinedChart)
    CombinedChart mCombinedChart;

    private TeacherTodoListAdapter mTeacherTodoListAdapter;

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
        // TODO: 2020/5/9 用户信息格式不一样需要重新登录
        if(AwDataUtil.isEmpty(AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_ACC,""))){
            ReLoginUtil.reLogin(mActivity);
        }
    }

    /**
     * 接收到扫描信息(消息)推送
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

    /**
     * 接收到扫描信息(通知)推送
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxAlipushDataResultType type) {
        if(type == null) {
            return;
        }
        Map<String, String> extraMap = type.getExtraMap();
        if(extraMap != null && null != extraMap.get("type") && "resolve".equals(extraMap.get("type"))) {
            showView(mIvScanPoint, true);
            showView(mIvScanPointNormal, true);
            AwAnimationUtil.startAlphaAnimation(mIvScanPoint);
            AwAnimationUtil.startAlphaAnimation(mIvScanPointNormal);
            AwLog.d("alipush 扫描开始...动画闪烁");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxUpdateUserBean type) {
        if(type == null)
            return;
        if(!AwDataUtil.isEmpty(type.getAvatar())) {
            AwImgUtil.setImgAvatar(mActivity, mCivAvatar, type.getAvatar());
        }
        if(!AwDataUtil.isEmpty(type.getNickName())) {
            setText(mTvName, type.getNickName());
        }
        UserUtil.updateUserInfo(type);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxUnConnectType type) {
        if(type == null)
            return;
       getData();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        EventBus.getDefault().register(this);
        setMargins(mRlTitle, 0, 0 - AwDisplayUtil.dip2px(mActivity, 48), 0, 0);
//        mRlTitle.animate().translationY(0 - AwDisplayUtil.dip2px(mActivity, 48));
        //填充nested

        //待办事项
        mTeacherTodoListAdapter = new TeacherTodoListAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mTeacherTodoListAdapter, false);
        mTvName.setText(AwDataUtil.isEmpty(UserUtil.getAppUser().getRealName()) ? "暂无姓名" : UserUtil.getAppUser().getRealName());
        AwImgUtil.setImgAvatar(mActivity, mCivAvatar, UserUtil.getAvatar());

        getData();

//        testCombineChartBubble();
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
        mPresenter.getTeacherTodoList(UserUtil.getTeacherId(), 0);
//        mPresenter.getStatusErrorQuestionInSomeDay(UserUtil.getTeacherId());
    }


    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mIvScan.setOnClickListener(v -> {
            //进入作业扫描进度页面, 关闭本页的红点效果, 类似微信等信息点进去
            mIvScanPoint.clearAnimation();
            mIvScanPointNormal.clearAnimation();
            showView(mIvScanPoint, false);
            showView(mIvScanPointNormal, false);
            MainFragment.this.toClass(ScanActivity.class, false);
        });
        mIvScanNormal.setOnClickListener(v -> {
            //进入作业扫描进度页面, 关闭本页的红点效果, 类似微信等信息点进去
            mIvScanPoint.clearAnimation();
            mIvScanPointNormal.clearAnimation();
            showView(mIvScanPoint, false);
            showView(mIvScanPointNormal, false);
            MainFragment.this.toClass(ScanActivity.class, false);
        });
        mIvScanQrCode.setOnClickListener(v -> AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsCamera, new IPermissionListener() {
            @Override
            public void granted() {
                toClass(ScanQrcodeActivity.class, false);
            }

            @Override
            public void shouldShowRequestPermissionRationale() {
                showDialog("扫码需开启相机权限");
            }

            @Override
            public void needToSetting() {
                showDialog("扫码需开启相机权限");
            }
        }));
        mIvScanQrCodeNormal.setOnClickListener(v -> AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsCamera, new IPermissionListener() {
            @Override
            public void granted() {
                toClass(ScanQrcodeActivity.class, false);
            }

            @Override
            public void shouldShowRequestPermissionRationale() {
                showDialog("扫码需开启相机权限");
            }

            @Override
            public void needToSetting() {
                showDialog("扫码需开启相机权限");
            }
        }));

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
            if(todoBean.isAllowOperate() && todoBean.isHandle()) {
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
                showMsg("处理中，请稍后查看");
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


    @Override
    public void getTeacherTodoListSuccess(String data, List<TeacherTodoBean> list, int total, int size, int pages, int current) {
        mSflLayout.setRefreshing(false);
        if(AwDataUtil.isEmpty(list)) {
            mTeacherTodoListAdapter.clearData();
            mRcvData.removeAllViews();
            mTeacherTodoListAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            showTopTodoView(false, 0);
            return;
        }

        if(list.size() > 10) {
            list = list.subList(0, 10);
        }
        showTopTodoView(true, list.size());
        //TODO ui调整, 取消原有多色数值背景了, 使用统一蓝底白字.
//        mTeacherTodoListAdapter.addAllData(DataUtil.convertTeacherTodoBeanList(list));
        mTeacherTodoListAdapter.addAllData(list);
        mTeacherTodoListAdapter.loadMoreComplete();
        mTeacherTodoListAdapter.setEnableLoadMore(false);
        mTeacherTodoListAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getTeacherTodoListFaile(String msg) {
        mSflLayout.setRefreshing(false);
        mTeacherTodoListAdapter.clearData();
        mRcvData.removeAllViews();
        mTeacherTodoListAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        showTopTodoView(false, 0);
    }

    @Override
    public void getStatusMarkBeforeDawnSuccess(StatusHomeworkScanResultBean bean) {

    }

    @Override
    public void getStatusErrorQuestionInSomeDaySuccess(List<StatusErrorQuestionResultBean> list) {
        mSflLayout.setRefreshing(false);
        if(AwDataUtil.isEmpty(list)) {
            AwLog.d("getStatusErrorQuestionInSomeDaySuccess list is null");
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChart, null);
            return;
        }
        AwLog.d("getStatusErrorQuestionInSomeDaySuccess list size: " + list.size());
        Collections.sort(list, (o1, o2) -> (int) (Double.parseDouble(o1.getScoreRate())  * 100 - Double.parseDouble(o2.getScoreRate()) * 100));
        showComchart(list);

    }

    @Override
    public void getStatusErrorQuestionInSomeDayFail(String msg) {
        mSflLayout.setRefreshing(false);
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChart, null);
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

    private void showComchart(List<StatusErrorQuestionResultBean> list) {
        List<String> xAxisValues = new ArrayList<>();
        List<Float> barYAxisValues = new ArrayList<>();
        List<Float> bubbleYAxisValue = new ArrayList<>();
        for(StatusErrorQuestionResultBean temp : list) {
            String name = temp.getCatalogName();
            if(name.length() > 10) {
                name = name.substring(0, 10) + "...";
            }
            AwLog.d("name: " + name);
            xAxisValues.add(name);
            barYAxisValues.add(Float.valueOf(temp.getScoreRate()) * 100);
            bubbleYAxisValue.add(Float.valueOf(temp.getErrorCnt()));
        }
        CombineChartCommonSingleYBubbleHelper.setCombineChart(mCombinedChart, xAxisValues, barYAxisValues, bubbleYAxisValue, "得分率", "题数", 2, AwBaseConstant.COMMON_SUFFIX_RATIO,4);
        mCombinedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showToastDialog(list.get((int) e.getX()).getCatalogName() + "\n得分率：" + AwConvertUtil.double2String(e.getY(), 2) + AwBaseConstant.COMMON_SUFFIX_RATIO
                        + "\n总题数：" + list.get((int) e.getX()).getErrorCnt());
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {
                dismissDialog();
            }
        });
    }
}
