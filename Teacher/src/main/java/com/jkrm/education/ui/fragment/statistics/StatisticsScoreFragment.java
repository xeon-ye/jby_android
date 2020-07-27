package com.jkrm.education.ui.fragment.statistics;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.CombineChartCommonMoreYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.CombineChartCommonSingleYLineAndBarNavHelper;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.api.APIService;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;
import com.jkrm.education.bean.result.StatisticsScoreAverageResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionRankResultBean;
import com.jkrm.education.bean.result.StatisticsScorePositionScoreResultBean;
import com.jkrm.education.bean.rx.RxStatisticsCourseType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.StatisticsScorePresent;
import com.jkrm.education.mvp.views.StatisticsScoreView;
import com.jkrm.education.ui.activity.HomeworkChoiceActiviity;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.RequestUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/29.
 */

public class StatisticsScoreFragment extends AwMvpLazyFragment<StatisticsScorePresent> implements StatisticsScoreView.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.ll_courseChoice)
    LinearLayout mLlCourseChoice;
    @BindView(R.id.tv_courseName)
    TextView mTvCourseName;
    @BindView(R.id.tv_gradeAverage)
    TextView mTvGradeAverage;
    @BindView(R.id.barchart)
    BarChart mBarchart;
    @BindView(R.id.nsv_scrollview)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.combinedChartAverageDev)
    CombinedChart mCombinedChartAverageDev;
    @BindView(R.id.combinedChartScore)
    CombinedChart mCombinedChartScore;
    @BindView(R.id.combinedChartRank)
    CombinedChart mCombinedChartRank;
    @BindView(R.id.ic_legendAverage)
    LinearLayout mIcLegendAverage;
    @BindView(R.id.ic_legendScore)
    LinearLayout mIcLegendScore;
    @BindView(R.id.ic_legendRank)
    LinearLayout mIcLegendRank;
    @BindView(R.id.rg_score)
    RadioGroup mRgScore;
    @BindView(R.id.rg_rank)
    RadioGroup mRgRank;

    private RowsStatisticsHomeworkResultBean mBean = null;
    private String currentHomeworkId = "";
    private boolean isAllowShowChartToast = true;
    private String currentScoreRange = "80-100";
    private String currentRankange = "1-10";

    @Override
    protected StatisticsScorePresent createPresenter() {
        return new StatisticsScorePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_score;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvCourseName.setTypeface(CustomFontStyleUtil.getNewRomenType());
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        EventBus.getDefault().register(this);
        mPresenter.getStatisticsScoreHomeworkList(MyApp.getInstance().getAppUser().getTeacherId(), RequestUtil.getStatisticsScoreHomeworkList(1, APIService.COMMON_PAGE_SIZE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        if (mBean != null) {
            mPresenter.getStatisticsScoreAverage(mBean.getId());
        }
        if (!AwDataUtil.isEmpty(currentHomeworkId)) {
            mPresenter.getStatisticsScorePositionRank(currentHomeworkId, RequestUtil.getStatisticsScorePositionRank(currentRankange));
            mPresenter.getStatisticsScorePositionScore(currentHomeworkId, RequestUtil.getStatisticsScorePositionScore(currentScoreRange));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxStatisticsCourseType type) {
        if (type == null)
            return;
        if (mBean.getId().equals(type.getHomeworkBean().getId())) {
            //选择后的课时和之前相同, 不做处理
            return;
        }
        mBean = type.getHomeworkBean();
        currentHomeworkId = mBean.getId();
        setText(mTvCourseName, mBean.getHwName());
        mPresenter.getStatisticsScoreAverage(mBean.getId());
        mPresenter.getStatisticsScorePositionRank(currentHomeworkId, RequestUtil.getStatisticsScorePositionRank(currentRankange));
        mPresenter.getStatisticsScorePositionScore(currentHomeworkId, RequestUtil.getStatisticsScorePositionScore(currentScoreRange));

    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mLlCourseChoice.setOnClickListener(v -> toClass(HomeworkChoiceActiviity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mBean));
        mRgScore.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rBtn_socre1:
                    currentScoreRange = "80-100";
                    break;
                case R.id.rBtn_socre2:
                    currentScoreRange = "60-79";
                    break;
                case R.id.rBtn_socre3:
                    currentScoreRange = "40-59";
                    break;
                case R.id.rBtn_socre4:
                    currentScoreRange = "20-39";
                    break;
                case R.id.rBtn_socre5:
                    currentScoreRange = "0-19";
                    break;
            }
            mPresenter.getStatisticsScorePositionScore(currentHomeworkId, RequestUtil.getStatisticsScorePositionScore(currentScoreRange));
        });
        mRgRank.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rBtn_rank1:
                    currentRankange = "1-10";
                    break;
                case R.id.rBtn_rank2:
                    currentRankange = "11-50";
                    break;
                case R.id.rBtn_rank3:
                    currentRankange = "51-100";
                    break;
                case R.id.rBtn_rank4:
                    currentRankange = "101-200";
                    break;
                case R.id.rBtn_rank5:
                    currentRankange = "201-300";
                    break;
            }
            mPresenter.getStatisticsScorePositionRank(currentHomeworkId, RequestUtil.getStatisticsScorePositionRank(currentRankange));
        });
        mNestedScrollView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //按下
                    isAllowShowChartToast = false;
                    break;
                case MotionEvent.ACTION_UP:
                    //松开
                    isAllowShowChartToast = true;
                    break;
            }
            return false;
        });
    }

    @Override
    public void getStatisticsScoreHomeworkListSuccess(AnswerSheetDataResultBean data, List<RowsStatisticsHomeworkResultBean> list, int total, int size, int pages, int current) {
        mSflLayout.setRefreshing(false);
        if (AwDataUtil.isEmpty(list)) {
            showView(mIcLegendAverage, false);
            showView(mIcLegendScore, false);
            showView(mIcLegendRank, false);
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartAverageDev, getString(com.hzw.baselib.R.string.common_no_data));
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartScore, getString(com.hzw.baselib.R.string.common_no_data));
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartRank, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        mBean = list.get(0);
        currentHomeworkId = mBean.getId();
        setText(mTvCourseName, mBean.getHwName());
        mPresenter.getStatisticsScoreAverage(mBean.getId());
        mPresenter.getStatisticsScorePositionRank(currentHomeworkId, RequestUtil.getStatisticsScorePositionRank(currentRankange));
        mPresenter.getStatisticsScorePositionScore(currentHomeworkId, RequestUtil.getStatisticsScorePositionScore(currentScoreRange));
    }

    @Override
    public void getStatisticsScoreHomeworkListFail(String msg) {
        mSflLayout.setRefreshing(false);
        showView(mIcLegendAverage, false);
        showView(mIcLegendScore, false);
        showView(mIcLegendRank, false);
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartAverageDev, getString(com.hzw.baselib.R.string.common_no_data));
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartScore, getString(com.hzw.baselib.R.string.common_no_data));
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartRank, getString(com.hzw.baselib.R.string.common_no_data));
    }

    @Override
    public void getStatisticsScoreAverageSuccess(List<StatisticsScoreAverageResultBean> list) {
        mSflLayout.setRefreshing(false);
        if (AwDataUtil.isEmpty(list) || AwDataUtil.isEmpty(list.get(0).getAvgScore())) {
            showView(mIcLegendAverage, false);
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartAverageDev, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        showView(mIcLegendAverage, true);
        AwLog.d("getStatisticsScoreAverageSuccess legend visible1? ---> " + (mIcLegendAverage.getVisibility() == View.VISIBLE));
        Collections.sort(list, new Comparator<StatisticsScoreAverageResultBean>() {
            @Override
            public int compare(StatisticsScoreAverageResultBean o1, StatisticsScoreAverageResultBean o2) {
                if (Double.parseDouble(o1.getAvgScore()) - Double.parseDouble(o2.getAvgScore()) >= 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        Collections.sort(list, new Comparator<StatisticsScoreAverageResultBean>() {
            @Override
            public int compare(StatisticsScoreAverageResultBean statisticsScoreAverageResultBean, StatisticsScoreAverageResultBean t1) {
                if (statisticsScoreAverageResultBean.isLeader()) {
                    return 1;
                } else {
                    return -1;

                }
            }
        });
        List<String> xAxisValues = new ArrayList<>();
        //设置均分
        List<Float> yAxisValues = new ArrayList<>();
        //设置均差
        List<Float> yAxisValues2 = new ArrayList<>();
        //组合图线形
        List<Float> yAxisValues3 = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            StatisticsScoreAverageResultBean temp = list.get(i);
            xAxisValues.add(temp.getClassName());
            yAxisValues.add(Float.valueOf(temp.getAvgScore()));
            yAxisValues2.add(Float.valueOf(temp.getDevScore()));
            yAxisValues3.add(Float.valueOf(0));
        }
        mBarchart.fitScreen();
        mCombinedChartAverageDev.fitScreen();
        BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart, xAxisValues, yAxisValues, "", null, 1,
                0, "", 2, AwBaseConstant.COMMON_SUFFIX_SCORE, 6, false, false, true);

//        BarChartPositiveNegativeCommonHelper.setPositiveNegativeBarChart(mBarchart2, xAxisValues, yAxisValues2, "");
        String classAvg = list.get(0).getClassAvg();
        if (AwDataUtil.isEmpty(classAvg)) {
            setText(mTvGradeAverage, "年级均分");
        } else {
            setText(mTvGradeAverage, "年级均分(" + AwConvertUtil.double2String(Double.parseDouble(classAvg), 2) + ")");
        }
        CombineChartCommonSingleYLineAndBarNavHelper.setCombineChart(mCombinedChartAverageDev, xAxisValues, yAxisValues2, yAxisValues3, "班级均差", "年级均分(" + AwConvertUtil.double2String(Double.parseDouble(list.get(0).getClassAvg()), 2) + ")",
                0, "", 6);
//        AwLog.d("description x: " + mCombinedChartAverageDev.getDescription().getPosition().getX() + " ,y: " + mCombinedChartAverageDev.getDescription().getPosition().getY());
    }

    @Override
    public void getStatisticsScoreAverageFail(String msg) {
        mSflLayout.setRefreshing(false);
        showView(mIcLegendAverage, false);
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
    }

    @Override
    public void getStatisticsScorePositionRankSuccess(List<StatisticsScorePositionRankResultBean> list) {
        mSflLayout.setRefreshing(false);
        //重置表格缩放等数据
        mCombinedChartRank.fitScreen();

        if (AwDataUtil.isEmpty(list)) {
            showView(mIcLegendRank, false);
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartRank, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        showView(mIcLegendRank, true);
        List<Float> leftList = new ArrayList<>();
        List<Float> rightList = new ArrayList<>();
        for (StatisticsScorePositionRankResultBean temp : list) {
            if (Float.parseFloat(temp.getDurationNum()) <= Float.parseFloat(temp.getDurationTotalNum())) {
                leftList.add(Float.valueOf(temp.getDurationTotalNum()));
            } else {
                leftList.add(Float.valueOf(temp.getDurationNum()));
            }

            if (Float.parseFloat(temp.getDurationRate()) <= Float.parseFloat(temp.getDurationTotalRate())) {
                rightList.add(Float.valueOf(temp.getDurationTotalRate()));
            } else {
                rightList.add(Float.valueOf(temp.getDurationRate()));
            }
        }
        float maxLeftValue = Collections.max(leftList);
        float maxRightValue = Collections.max(rightList) * 100;

        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues1 = new ArrayList<>();
        List<Float> yAxisValues2 = new ArrayList<>();
        List<Float> yAxisValuesLine1 = new ArrayList<>();
        List<Float> yAxisValuesLine2 = new ArrayList<>();
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        LinkedHashMap<String, List<Float>> mapLine = new LinkedHashMap<>();
        for (StatisticsScorePositionRankResultBean temp : list) {
            xAxisValues.add(temp.getClassName());

            yAxisValues1.add(Float.valueOf(temp.getDurationNum()));
            yAxisValues2.add(Float.valueOf(temp.getDurationTotalNum()));

            yAxisValuesLine1.add(Float.valueOf(temp.getDurationRate()) * 100);
            yAxisValuesLine2.add(Float.valueOf(temp.getDurationTotalRate()) * 100);
        }
        map.put("本段人数", yAxisValues1);
        map.put("累计人数", yAxisValues2);
        mapLine.put("本段占比", yAxisValuesLine1);
        mapLine.put("累计占比", yAxisValuesLine2);
        CombineChartCommonMoreYCustomSpaceHelper.setMoreBarChart(mCombinedChartRank, xAxisValues, map, mapLine,
                MyDateUtil.getChartColorsList2(mActivity), MyDateUtil.getChartLineColorsList(mActivity),
                false, true, "", 8, false, maxLeftValue, maxRightValue);
        mCombinedChartRank.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (isAllowShowChartToast) {
                    int x = (int) Math.floor(e.getX());
                    AwLog.d("Entry x: " + x);
                    StatisticsScorePositionRankResultBean bean = list.get(x);
                    showToastDialog2(bean.getClassName() +
                            "\n本段人数：" + bean.getDurationNum() + AwBaseConstant.COMMON_SUFFIX_PERSON
                            + "\n累计人数：" + bean.getDurationTotalNum() + AwBaseConstant.COMMON_SUFFIX_PERSON
                            + "\n本段占比: " + AwConvertUtil.double2String(Double.parseDouble(bean.getDurationRate()) * 100, 2) + AwBaseConstant.COMMON_SUFFIX_RATIO
                            + "\n累计占比: " + AwConvertUtil.double2String(Double.parseDouble(bean.getDurationTotalRate()) * 100, 2) + AwBaseConstant.COMMON_SUFFIX_RATIO);
                    AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
                } else {
                    AwLog.d("Entry 不允许点击提示");
                }

            }

            @Override
            public void onNothingSelected() {
                AwLog.d("Entry onNothingSelected");
                dismissDialog();
            }
        });
    }

    @Override
    public void getStatisticsScorePositionRankFail(String msg) {
        mSflLayout.setRefreshing(false);
        showView(mIcLegendRank, false);
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartRank, getString(com.hzw.baselib.R.string.common_no_data));
    }

    @Override
    public void getStatisticsScorePositionScoreSuccess(List<StatisticsScorePositionScoreResultBean> list) {
        mSflLayout.setRefreshing(false);
        if (AwDataUtil.isEmpty(list)) {
            showView(mIcLegendScore, false);
            ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartScore, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        showView(mIcLegendScore, true);
        //重置表格缩放等数据
        mCombinedChartScore.fitScreen();
        List<Float> leftList = new ArrayList<>();
        List<Float> rightList = new ArrayList<>();
        for (StatisticsScorePositionScoreResultBean temp : list) {
            if (Float.parseFloat(temp.getNowCount()) <= Float.parseFloat(temp.getTotalCount())) {
                leftList.add(Float.valueOf(temp.getTotalCount()));
            } else {
                leftList.add(Float.valueOf(temp.getNowCount()));
            }

            if (Float.parseFloat(temp.getNowProp()) <= Float.parseFloat(temp.getTotalProp())) {
                rightList.add(Float.valueOf(temp.getTotalProp()));
            } else {
                rightList.add(Float.valueOf(temp.getNowProp()));
            }
        }
        float maxLeftValue = Collections.max(leftList);
        float maxRightValue = Collections.max(rightList) * 100;

        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues1 = new ArrayList<>();
        List<Float> yAxisValues2 = new ArrayList<>();
        List<Float> yAxisValuesLine1 = new ArrayList<>();
        List<Float> yAxisValuesLine2 = new ArrayList<>();
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        LinkedHashMap<String, List<Float>> mapLine = new LinkedHashMap<>();
        for (StatisticsScorePositionScoreResultBean temp : list) {
            xAxisValues.add(temp.getClassName());

            yAxisValues1.add(Float.valueOf(temp.getNowCount()));
            yAxisValues2.add(Float.valueOf(temp.getTotalCount()));

            yAxisValuesLine1.add(Float.valueOf(temp.getNowProp()) * 100);
            yAxisValuesLine2.add(Float.valueOf(temp.getTotalProp()) * 100);
        }
        map.put("本段人数", yAxisValues1);
        map.put("累计人数", yAxisValues2);
        mapLine.put("本段占比", yAxisValuesLine1);
        mapLine.put("累计占比", yAxisValuesLine2);
        CombineChartCommonMoreYCustomSpaceHelper.setMoreBarChart(mCombinedChartScore, xAxisValues, map, mapLine,
                MyDateUtil.getChartColorsList2(mActivity), MyDateUtil.getChartLineColorsList(mActivity),
                false, true, "", 8, false, maxLeftValue, maxRightValue);
        mCombinedChartScore.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (isAllowShowChartToast) {
                    int x = (int) Math.floor(e.getX());
                    AwLog.d("Entry x: " + x);
                    StatisticsScorePositionScoreResultBean bean = list.get(x);
                    showToastDialog2(bean.getClassName() +
                            "\n本段人数：" + bean.getNowCount() + AwBaseConstant.COMMON_SUFFIX_PERSON
                            + "\n累计人数：" + bean.getTotalCount() + AwBaseConstant.COMMON_SUFFIX_PERSON
                            + "\n本段占比: " + AwConvertUtil.double2String(Double.parseDouble(bean.getNowProp()) * 100, 2) + AwBaseConstant.COMMON_SUFFIX_RATIO
                            + "\n累计占比: " + AwConvertUtil.double2String(Double.parseDouble(bean.getTotalProp()) * 100, 2) + AwBaseConstant.COMMON_SUFFIX_RATIO);
                    AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
                } else {
                    AwLog.d("Entry 不允许点击提示");
                }

            }

            @Override
            public void onNothingSelected() {
                AwLog.d("Entry onNothingSelected");
                dismissDialog();
            }
        });
    }

    @Override
    public void getStatisticsScorePositionScoreFail(String msg) {
        mSflLayout.setRefreshing(false);
        showView(mIcLegendScore, false);
        ChartNoDataUtil.setCombinedChartNoDataStatus(mCombinedChartScore, getString(com.hzw.baselib.R.string.common_no_data));
    }
}
