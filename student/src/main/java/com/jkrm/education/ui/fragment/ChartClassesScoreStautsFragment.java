package com.jkrm.education.ui.fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.BarChartCommonSingleYWithCurrentDiffColorHelper;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYHelper;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ReportDurationsResultBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailType;
import com.jkrm.education.mvp.presenters.HomeworkDetailReportDurationPresent;
import com.jkrm.education.mvp.views.HomeworkDetailReportDurationlView;
import com.jkrm.education.ui.activity.homework.HomeworkDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 班级成绩分布
 * Created by hzw on 2019/5/10.
 */

public class ChartClassesScoreStautsFragment extends AwMvpFragment<HomeworkDetailReportDurationPresent> implements HomeworkDetailReportDurationlView.View {

    @BindView(R.id.barchart)
    BarChart mBarchart;
    private double mDouAverageGrade;

    @Override
    protected HomeworkDetailReportDurationPresent createPresenter() {
        return new HomeworkDetailReportDurationPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_detail_score_status;
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_loading));
        EventBus.getDefault().register(this);
        if(!AwDataUtil.isEmpty(HomeworkDetailActivity.mStrAverageGrade)){
            mDouAverageGrade=Double.parseDouble(HomeworkDetailActivity.mStrAverageGrade);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxHomeworkDetailType type) {
        AwLog.d("homeworkId ChartClassesScoreStautsFragment: " + type.getHomeworkId());
        mPresenter.report_durations(type.getHomeworkId());
    }


    @Override
    public void report_durationsSuccess(List<ReportDurationsResultBean> result) {
        mBarchart.fitScreen();
        if(AwDataUtil.isEmpty(result)) {
            AwLog.d("report 成绩分布 list is null");
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
            return;
        }
        AwLog.d("report 成绩分布 list size: " + result.size());
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(int i=result.size() - 1; i>=0; i--) {
            ReportDurationsResultBean temp = result.get(i);
            xAxisValues.add(temp.getDuration());
            yAxisValues.add(Float.valueOf(temp.getCnt()));
        }
      /*  BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart, xAxisValues, yAxisValues, "", null, 1, 0, "",
                0, AwBaseConstant.COMMON_SUFFIX_PERSON, 5, true, false, false);*/
        BarChartCommonSingleYWithCurrentDiffColorHelper.setBarChart(mBarchart,xAxisValues,yAxisValues,"",1,0,"",0,AwBaseConstant.COMMON_SUFFIX_PERSON,mDouAverageGrade);
        mBarchart.invalidate();

    }

    @Override
    public void report_durationsFail(String msg) {
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
