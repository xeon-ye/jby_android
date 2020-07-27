package com.jkrm.education.ui.fragment.statistics;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.CombineChartCommonSingleYBubbleHelper;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.StatisticsHomeworkSubmitAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionKnowledgeResultBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.rx.RxStatisticsClassesType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.StatisticsHomeworkPresent;
import com.jkrm.education.mvp.views.StatisticsHomeworkView;
import com.jkrm.education.ui.activity.ClassesChoiceActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hzw on 2019/5/29.
 */

public class StatisticsHomeworkFragment extends AwMvpLazyFragment<StatisticsHomeworkPresent> implements StatisticsHomeworkView.View,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.ll_classes)
    LinearLayout mLlClass;
    @BindView(R.id.tv_classesNames)
    TextView mTvClassesNames;
    @BindView(R.id.combinedChart)
    CombinedChart mCombinedChart;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.nestscrview)
    NestedScrollView mNestscrview;
    Unbinder unbinder;
    private int index = 1;
    private int totalPages = Integer.MAX_VALUE;
    List<StatisticsHomeworkSubmitTableResultBeanNew.RowsBean> mSubmitTableResultBeanList = new ArrayList<>();

    private List<TeacherClassBean> mTeacherClassBeanList = new ArrayList<>();

    private StatisticsHomeworkSubmitAdapter mAdapter;
    private String mClassesIds;

    @Override
    protected StatisticsHomeworkPresent createPresenter() {
        return new StatisticsHomeworkPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_homework;
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mAdapter = new StatisticsHomeworkSubmitAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, true);
        mAdapter.setEnableLoadMore(false);
        mRcvData.setHasFixedSize(true);
        EventBus.getDefault().register(this);
        mPresenter.getTeacherClassList(MyApp.getInstance().getAppUser().getTeacherId());
        mTeacherClassBeanList = MyApp.mTeacherClassHomeworkBeanList;
        if (AwDataUtil.isEmpty(mTeacherClassBeanList)) {
            //再获取一次作业列表接口, 从该接口获取作业班级(api无单独的接口可供调用)
            mPresenter.getAnswerSheets(MyApp.getInstance().getAppUser().getTeacherId(),
                    AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-7))),
                    AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis())), "", "", 1);
        } else {
            refreshData();
        }

    }


    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxStatisticsClassesType type) {
        if (type == null)
            return;
        if (MyConstant.LocalConstant.TAG_STATISTICS_HOMEWORK == type.getTag()) {
            //TODO 更新报表数据
            mTeacherClassBeanList = type.getTeacherList();
            AwLog.d("全局通知, 更新作业统计报表数据, 当前班级数量: " + mTeacherClassBeanList.size());
            refreshData();
        } else {
            AwLog.d("全局通知, 不更新作业统计报表数据, 非作业统计页面进入选择班级");
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRcvData);
        mLlClass.setOnClickListener(v -> toClass(ClassesChoiceActiviity.class, false,
                Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_HOMEWORK,
                Extras.KEY_BEAN_TEACHER_CLASSES_LIST, mTeacherClassBeanList));
        mRcvData.setNestedScrollingEnabled(false);
        mNestscrview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //判断是否滑到的底部
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    if (index < totalPages) {
                        index++;
                        getData(false);
                    }
                }
            }

        });
    }

    private void refreshData() {
        if (!AwDataUtil.isEmpty(mTeacherClassBeanList)) {
            StringBuffer sb = new StringBuffer();
            StringBuffer sbClassNames = new StringBuffer();
            for (TeacherClassBean tempBean : mTeacherClassBeanList) {
                sb.append(tempBean.getClassId()).append(",");
                sbClassNames.append((tempBean.getClassName())).append(",");
            }
            mClassesIds = sb.substring(0, sb.toString().length() - 1);
            String classNames = sbClassNames.substring(0, sbClassNames.toString().length() - 1);
            setText(mTvClassesNames, classNames);
            AwLog.d("统计, 筛选班级: " + classNames + " ,id: " + mClassesIds);
            String teacherId = MyApp.getInstance().getAppUser().getTeacherId();

            //获取作业提交统计
            //mPresenter.getStatisticsHomeworkSubmitTable(MyApp.getInstance().getAppUser().getTeacherId(), RequestUtil.getStatisticsHomeworkSubmitTable(mClassesIds,index));
            getData(true);
            //获取错题知识点 20190923需求隐藏知识点模块
//            mPresenter.getStatisticsHomeworkErrorQuestionKnowledge(MyApp.getInstance().getAppUser().getTeacherId(),
//                    RequestUtil.getStatisticsHomeworkErrorQuestionKnowledge("", "", "", classesIds));

        } else {
            mSflLayout.setRefreshing(false);
            AwLog.d("迭代教师作业班级 is null");
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChart, getString(com.hzw.baselib.R.string.common_no_data));
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        }
    }

    @Override
    public void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {
        if (data != null) {
            MyApp.mTeacherClassHomeworkBeanList = data.getClassList();
            mTeacherClassBeanList = data.getClassList();
            refreshData();
        }
    }

    @Override
    public void getAnswerSheetsFail(String msg) {
        refreshData();
    }

    @Override
    public void getStatisticsHomeworkSubmitTableSuccess(List<StatisticsHomeworkSubmitTableResultBean> list) {
        mSflLayout.setRefreshing(false);
      /*  if (AwDataUtil.isEmpty(list)) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mAdapter.loadMoreEnd(false);
            return;
        }
        mSubmitTableResultBeanList.addAll(list);
        mAdapter.addAllData(mSubmitTableResultBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.setEnableLoadMore(true);
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);*/
    }

    @Override
    public void getStatisticsHomeworkSubmitTableFail(String msg) {
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        //mAdapter.loadMoreEnd(true);
    }

    @Override
    public void getStatisticesHomeworkSubmitRatioSuccess(List<StatisticsHomeworkSubmitRatioResultBean> list) {

    }

    @Override
    public void getStatisticsHomeworkMisstakeRatioSuccess(List<StatisticsErrorQuestionRatioResultBean> list) {

    }

    @Override
    public void getStatisticsHomeworkErrorQuestionKnowledgeSuccess(List<StatisticsErrorQuestionKnowledgeResultBean> list) {
        mSflLayout.setRefreshing(false);
        if (AwDataUtil.isEmpty(list)) {
            AwLog.d("getStatusErrorQuestionInSomeDaySuccess list is null");
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChart, null);
            return;
        }
        AwLog.d("getStatusErrorQuestionInSomeDaySuccess list size: " + list.size());
        Collections.sort(list, (o1, o2) -> (int) (Double.parseDouble(o1.getScoreRate()) * 100 - Double.parseDouble(o2.getScoreRate()) * 100));
        showComchart(list);
    }

    @Override
    public void getStatisticsHomeworkErrorQuestionKnowledgeFail(String msg) {
        mSflLayout.setRefreshing(false);
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChart, null);
    }

    @Override
    public void getStatisticsHomeworkSubmitTableNewSuccess(StatisticsHomeworkSubmitTableResultBeanNew statisticsHomeworkSubmitTableResultBeanNew) {
        mSflLayout.setRefreshing(false);
        totalPages = Integer.parseInt(statisticsHomeworkSubmitTableResultBeanNew.getPages());
        if (AwDataUtil.isEmpty(statisticsHomeworkSubmitTableResultBeanNew.getRows())) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mAdapter.loadMoreEnd(true);
            return;
        }
        mSubmitTableResultBeanList.addAll(statisticsHomeworkSubmitTableResultBeanNew.getRows());
        mAdapter.addAllData(mSubmitTableResultBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getStatisticsHomeworkSubmitTableNewFail(String msg) {
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        mAdapter.loadMoreEnd(false);
    }

    @Override
    public void getTeacherClassListSuccess(List<TeacherClassBean> list) {
        mTeacherClassBeanList = list;
        refreshData();
    }

    @Override
    public void getTeacherClassListFail(String msg) {

    }

    private void showComchart(List<StatisticsErrorQuestionKnowledgeResultBean> list) {
        List<String> xAxisValues = new ArrayList<>();
        List<Float> barYAxisValues = new ArrayList<>();
        List<Float> bubbleYAxisValue = new ArrayList<>();
        CombineChartCommonSingleYBubbleHelper.setCombineChart(mCombinedChart, xAxisValues, barYAxisValues, bubbleYAxisValue, "得分率", "题数", 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 4);
        for (StatisticsErrorQuestionKnowledgeResultBean temp : list) {
            String name = temp.getCatalogName();
            if (name.length() > 10) {
                name = name.substring(0, 10) + "...";
            }
            AwLog.d("name: " + name);
            xAxisValues.add(name);
            barYAxisValues.add(Float.valueOf(temp.getScoreRate()) * 100);
            bubbleYAxisValue.add(Float.valueOf(temp.getErrorCnt()));
        }
        CombineChartCommonSingleYBubbleHelper.setCombineChart(mCombinedChart, xAxisValues, barYAxisValues, bubbleYAxisValue, "得分率", "题数", 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 4);
        mCombinedChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showToastDialog(list.get((int) e.getX()).getCatalogName() + "\n得分率：" + AwConvertUtil.double2String(e.getY(), 2) + AwBaseConstant.COMMON_SUFFIX_RATIO
                        + "\n总题数：" + list.get((int) e.getX()).getErrorCnt());
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        if (index < totalPages) {
            index++;
            getData(false);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    private void getData(boolean needResetIndex) {
        if (needResetIndex) {
            index = 1;
            mSubmitTableResultBeanList = new ArrayList<>();
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.getStatisticsHomeworkSubmitTableNew(MyApp.getInstance().getAppUser().getTeacherId(), mClassesIds, index, 10);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
