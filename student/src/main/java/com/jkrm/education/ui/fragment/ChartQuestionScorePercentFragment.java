package com.jkrm.education.ui.fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYWithDiffColorHelper;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.ReportQuestionScoreRateResultBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailType;
import com.jkrm.education.mvp.presenters.HomeworkDetailScoreRatePresent;
import com.jkrm.education.mvp.views.HomeworkDetailScoreRateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作业详情 得分率
 * Created by hzw on 2019/5/10.
 */

public class ChartQuestionScorePercentFragment extends AwMvpFragment<HomeworkDetailScoreRatePresent> implements HomeworkDetailScoreRateView.View  {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected HomeworkDetailScoreRatePresent createPresenter() {
        return new HomeworkDetailScoreRatePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_detail_score_percent;
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxHomeworkDetailType type) {
        AwLog.d("homeworkId ChartQuestionScorePercentFragment : " + type.getHomeworkId());
        mPresenter.report_questionRate(type.getHomeworkId(), MyApp.getInstance().getAppUser().getStudId());
    }

    @Override
    public void report_questionRateSuccess(List<ReportQuestionScoreRateResultBean> result) {
        mBarchart.fitScreen();
        if(AwDataUtil.isEmpty(result)) {
            AwLog.d("report 得分率 list is null");
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
            return;
        }
        AwLog.d("report 得分率 list size: " + result.size());
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(ReportQuestionScoreRateResultBean temp : result) {
            xAxisValues.add(temp.getQuestionNum());
            yAxisValues.add(Float.valueOf(temp.getRatio()) * 100);
        }
        AwLog.d("report 得分率 x size: " + xAxisValues.size());
        BarChartCommonSingleYWithDiffColorHelper.setBarChart(mBarchart, xAxisValues, yAxisValues, "", 1, 0, AwBaseConstant.COMMON_SUFFIX_RATIO, 2, AwBaseConstant.COMMON_SUFFIX_RATIO);
        mBarchart.invalidate();
    }

    @Override
    public void report_questionRateFail(String msg) {
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
