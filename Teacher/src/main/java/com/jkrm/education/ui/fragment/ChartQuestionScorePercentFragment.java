package com.jkrm.education.ui.fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYWithDiffColorHelper;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.QuestionScoreBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailRatioType;

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

public class ChartQuestionScorePercentFragment extends AwMvpFragment {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_score_percent;
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, "加载中...");
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxHomeworkDetailRatioType type) {
        mBarchart.fitScreen();
        if(null == type) {
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        List<QuestionScoreBean> list = type.getList();
        if(AwDataUtil.isEmpty(list)) {
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            QuestionScoreBean temp = list.get(i);
            xAxisValues.add(temp.getClassScoredRatio().getQuestionNum());
            yAxisValues.add(Float.valueOf(AwConvertUtil.double2String(Double.parseDouble(temp.getClassScoredRatio().getRatio() == null ? "0" : temp.getClassScoredRatio().getRatio()) * 100, 2)));
        }
        BarChartCommonSingleYWithDiffColorHelper.setBarChart(mBarchart, xAxisValues, yAxisValues, "", 1, 0, AwBaseConstant.COMMON_SUFFIX_RATIO, 2, AwBaseConstant.COMMON_SUFFIX_RATIO);
        mBarchart.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
