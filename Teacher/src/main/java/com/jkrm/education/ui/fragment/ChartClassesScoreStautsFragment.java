package com.jkrm.education.ui.fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYWithDiffColorHelper;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.HomeworkDuratBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailDurationType;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYHelper;

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

public class ChartClassesScoreStautsFragment extends AwMvpFragment {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_score_status;
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, "加载中...");
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxHomeworkDetailDurationType type) {
        mBarchart.fitScreen();
        if(null == type) {
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        List<HomeworkDuratBean> list = type.getList();
       if(AwDataUtil.isEmpty(list)) {
           AwLog.d("report 成绩分布 list is null");
           ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
           return;
       }
        AwLog.d("report 成绩分布 list size: " + list.size());
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(int i=list.size() - 1; i>=0; i--) {
            HomeworkDuratBean temp = list.get(i);
            xAxisValues.add(temp.getDuration());
            yAxisValues.add(Float.valueOf(temp.getCnt()));
        }
   /*     BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart, xAxisValues, yAxisValues, "", null, 1, 0, "", 0, AwBaseConstant.COMMON_SUFFIX_PERSON,
                5, true, false, false);*/
        BarChartCommonSingleYWithDiffColorHelper.setBarChartSingle(mBarchart, xAxisValues, yAxisValues, "", 1, 0, "", 0, AwBaseConstant.COMMON_SUFFIX_PERSON);
//        ChartClassesScoreHelper.setBarChart(mBarchart, xAxisValues, yAxisValues,"",null,1);
        mBarchart.invalidate();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
