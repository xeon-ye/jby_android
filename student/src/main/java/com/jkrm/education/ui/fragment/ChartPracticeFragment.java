package com.jkrm.education.ui.fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.mpchart.ChartNoDataUtil;
import com.hzw.baselib.mpchart.helpers.BarChartCommonMoreYHelper;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.rx.RxHomeworkDetailType;
import com.jkrm.education.bean.rx.RxPracticeNumsType;
import com.jkrm.education.mvp.presenters.ChartPracticeFragmentPresent;
import com.jkrm.education.mvp.views.ChartPracticeFragmentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.CheckedOutputStream;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/10.
 */

public class ChartPracticeFragment extends AwMvpFragment<ChartPracticeFragmentPresent> implements ChartPracticeFragmentView.View {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected ChartPracticeFragmentPresent createPresenter() {
        return new ChartPracticeFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_practice;
    }

    @Override
    protected void initData() {
        super.initData();
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxPracticeNumsType type) {
        mPresenter.getPracticeDataTable(MyApp.getInstance().getAppUser().getStudId(), type.getStartDate(), type.getEndDate());
    }


    private void setBarchartData() {
        List<String> xAxisValues = new ArrayList<>();
        int totalColumn = 6;

        for(int i=1; i<totalColumn + 1; i++) {
            xAxisValues.add("6月" + i + "日");
        }

        List<Float> yAxisValues1 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues2 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues3 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues3.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues4 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues4.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues5 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues5.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues6 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues6.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        map.put("语文", yAxisValues1);
        map.put("数学", yAxisValues2);
        map.put("英语", yAxisValues3);
        map.put("物理", yAxisValues4);
        map.put("化学", yAxisValues5);
        map.put("历史", yAxisValues6);
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), false, "");
    }

    @Override
    public void getPracticeDataTableSuccess(List<PracticeTableResult> list) {
        if(AwDataUtil.isEmpty(list)) {
            AwLog.d("getPracticeDataTableSuccess list isEmpty");
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
            return;
        }
        AwLog.d("getPracticeDataTableSuccess list size: " + list.size());
        try {
            List<String> courseList = new ArrayList<>();
            List<String> xAxisValues = new ArrayList<>();
            List<Float> leftList = new ArrayList<>();
            LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
            for(PracticeTableResult temp : list) {
                //先迭代获取所有日期和所有学科
                if(!xAxisValues.contains(temp.getCreateTime())) {
                    xAxisValues.add(temp.getCreateTime());
                }
                if(!courseList.contains(temp.getCourseName())) {
                    courseList.add(temp.getCourseName());
                }
                List<Float> yAxisValues = new ArrayList<>();
                yAxisValues.add(Float.valueOf(temp.getNums()));
                leftList.add(Float.valueOf(temp.getNums()));
            }
            List<String> coureseCopyList = new ArrayList<>();
            coureseCopyList.addAll(courseList);
            for(String tempCourse : courseList) {//语文 数学 历史
                List<Float> yAxisValues = new ArrayList<>();
                for(String tempDate : xAxisValues) { //712 713 714
                    boolean isExist = false;
                    for(PracticeTableResult tempAll : list) {
                        if(tempAll.getCreateTime().equals(tempDate) && tempAll.getCourseName().equals(tempCourse)) {
                            isExist = true;
                        }
                    }
                    //如果存在直接添加, 不存在则默认0
                    if(isExist) {
                        for(PracticeTableResult tempAll : list) {
                            if(tempAll.getCreateTime().equals(tempDate) && tempAll.getCourseName().equals(tempCourse)) {
                                yAxisValues.add(Float.valueOf(tempAll.getNums()));
                            }
                        }
                    } else {
                        yAxisValues.add(Float.valueOf("0"));
                    }
                }
                //插入map
                map.put(tempCourse, yAxisValues);
            }
            float maxLeftValue = Collections.max(leftList);
            BarChartCommonMoreYHelper.setMoreBarChartWithLeftYMax(mBarchart, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), false, "", maxLeftValue);
        } catch (Exception e) {
            e.printStackTrace();
            ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
        }
    }

    @Override
    public void getPracticeDataTableFail(String msg) {
        ChartNoDataUtil.setBarChartNoDataStatus(mBarchart, getString(com.hzw.baselib.R.string.common_no_data));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
