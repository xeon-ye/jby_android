package com.jkrm.education.ui.fragment;

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
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYHelper;
import com.hzw.baselib.mpchart.helpers.BarChartPositiveNegativeCommonHelper;
import com.hzw.baselib.mpchart.helpers.CombineChartCommonSingleYLineAndBarNavHelper;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.ReportQuestionScoreRateResultBean;
import com.jkrm.education.bean.result.ReportQuestionScoreResultBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailType;
import com.jkrm.education.bean.test.TestMarkClassesBean;
import com.jkrm.education.mvp.presenters.HomeworkDetailNavRatioPresent;
import com.jkrm.education.mvp.presenters.HomeworkDetailScorePresent;
import com.jkrm.education.mvp.views.HomeworkDetailNavRatioView;
import com.jkrm.education.mvp.views.HomeworkDetailScoreView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作业详情 得分率
 * * Created by hzw on 2019/5/10.
 */

public class HomeworkDetailNavRatioFragment extends AwMvpFragment<HomeworkDetailNavRatioPresent> implements HomeworkDetailNavRatioView.View {

    @BindView(R.id.barChartAverageRatio)
    BarChart mBarChartAverageRatio;

    @Override
    protected HomeworkDetailNavRatioPresent createPresenter() {
        return new HomeworkDetailNavRatioPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homework_detail_nav_ratio;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxHomeworkDetailType type) {
        mPresenter.report_questionRate(type.getHomeworkId(), MyApp.getInstance().getAppUser().getStudId());
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarChartAverageRatio, getString(com.hzw.baselib.R.string.common_loading));
        EventBus.getDefault().register(this);
    }

    @Override
    public void report_questionRateSuccess(List<ReportQuestionScoreRateResultBean> list) {
        mBarChartAverageRatio.fitScreen();
        if(AwDataUtil.isEmpty(list)) {
            ChartNoDataUtil.setBarChartNoDataStatus(mBarChartAverageRatio, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        List<String> xAxisValues = new ArrayList<>();
        //设置均差
        List<Float> yAxisValues = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            ReportQuestionScoreRateResultBean temp = list.get(i);
            xAxisValues.add(temp.getQuestionNum());
            if(!AwDataUtil.isEmpty(temp.getQuestSubScore())) {
                //yAxisValues.add(Float.valueOf(AwConvertUtil.double2String(Double.valueOf(temp.getQuestSubScore()), 2)));
                yAxisValues.add(Float.valueOf(temp.getQuestSubScore()));

            } else {
                yAxisValues.add(Float.valueOf("0"));
            }
        }

        BarChartPositiveNegativeCommonHelper.setPositiveNegativeBarChart(mBarChartAverageRatio, xAxisValues, yAxisValues, "");
    /*    mBarChartAverageRatio.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String subScore = AwDataUtil.isEmpty(list.get((int) e.getX()).getSubScore()) ? "0" : list.get((int) e.getX()).getSubScore();
                String questionScore = AwDataUtil.isEmpty(list.get((int) e.getX()).getQuestionScore()) ? "0" : list.get((int) e.getX()).getQuestionScore();
                String avgScore = AwDataUtil.isEmpty(list.get((int) e.getX()).getAvgScore()) ? "0" : list.get((int) e.getX()).getAvgScore();
                showToastDialog2("第" + list.get((int) e.getX()).getQuestionNum() + "题"
                        + "\n得分率差：" + Float.valueOf(AwConvertUtil.double2String(Double.valueOf(subScore), 2)) + AwBaseConstant.COMMON_SUFFIX_RATIO
                        + "\n个人得分：" + MyDateUtil.replace(AwConvertUtil.double2String(Double.valueOf(questionScore), 2)) + AwBaseConstant.COMMON_SUFFIX_SCORE
                        + "\n班级平均分: " + MyDateUtil.replace(AwConvertUtil.double2String(Double.valueOf(avgScore), 2)) + AwBaseConstant.COMMON_SUFFIX_SCORE);
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {
                dismissDialog();
            }
        });*/
    }

    @Override
    public void report_questionRateFail(String msg) {
        ChartNoDataUtil.setBarChartNoDataStatus(mBarChartAverageRatio, getString(com.hzw.baselib.R.string.common_no_data));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
