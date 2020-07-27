package com.jkrm.education.ui.fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYHelper;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.ReportQuestionScoreResultBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailType;
import com.jkrm.education.mvp.presenters.HomeworkDetailScorePresent;
import com.jkrm.education.mvp.views.HomeworkDetailScoreView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作业详情 得分
 * * Created by hzw on 2019/5/10.
 */

public class ChartQuestionScoreFragment extends AwMvpFragment<HomeworkDetailScorePresent> implements HomeworkDetailScoreView.View {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected HomeworkDetailScorePresent createPresenter() {
        return new HomeworkDetailScorePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_detail_score;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxHomeworkDetailType type) {
        AwLog.d("homeworkId ChartQuestionScoreFragment: " + type.getHomeworkId());
        mPresenter.report_question(type.getHomeworkId(), MyApp.getInstance().getAppUser().getStudId());
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
        EventBus.getDefault().register(this);
    }

    @Override
    public void report_questionSuccess(List<ReportQuestionScoreResultBean> result) {
        mBarchart.fitScreen();
        if(AwDataUtil.isEmpty(result)) {
            AwLog.d("report 得分 list is null");
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
            return;
        }
        AwLog.d("report 得分 list size: " + result.size());
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(ReportQuestionScoreResultBean temp : result) {
            xAxisValues.add(temp.getQuestionNum());
            yAxisValues.add(Float.valueOf(temp.getScore()));
        }
        AwLog.d("report 得分 x size: " + xAxisValues.size());
        for(int i=0; i<xAxisValues.size(); i++) {
            AwLog.d("report 得分 x content: " + xAxisValues.get(i));
        }
        BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart, xAxisValues, yAxisValues, "", null, 1, 0, "",
                0, AwBaseConstant.COMMON_SUFFIX_SCORE, 5, true, false, false);
        mBarchart.invalidate();
    }

    @Override
    public void report_questionFail(String msg) {
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
