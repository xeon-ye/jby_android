package com.jkrm.education.old;

import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.mpchart.helpers.BarChartCommonMoreYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonMoreYHelper;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.mvp.presenters.MainLosePresent;
import com.jkrm.education.mvp.views.MainLoseView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 原首页---错题数
 * Created by hzw on 2019/5/10.
 */

public class ChartMainLoseCountFragment extends AwMvpFragment<MainLosePresent> implements MainLoseView.View {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected MainLosePresent createPresenter() {
        return new MainLosePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_main_lose_count;
    }

    @Override
    protected void initData() {
        super.initData();
//        setBarchartLoseCountResult();
        mPresenter.getStatusErrorQuestionInSomeDay(MyApp.getInstance().getAppUser().getTeacherId());
    }

    @Override
    public void getStatusErrorQuestionInSomeDaySuccess(List<StatusErrorQuestionResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            AwLog.d("getStatusErrorQuestionInSomeDaySuccess list is null");
            return;
        }
        AwLog.d("getStatusErrorQuestionInSomeDaySuccess list size: " + list.size());
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues1 = new ArrayList<>();
        List<Float> yAxisValues2 = new ArrayList<>();
        for(StatusErrorQuestionResultBean temp : list) {
            String name = temp.getCatalogName();
            if(name.length() > 10) {
                name = name.substring(0, 10) + "...";
            }
            AwLog.d("name: " + name);
            xAxisValues.add(name);
            yAxisValues1.add(Float.valueOf(temp.getErrorCnt()));
            yAxisValues2.add(Float.valueOf(temp.getAllCnt()));
        }

        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        map.put("失分题数", yAxisValues1);
        map.put("总题数", yAxisValues2);
        BarChartCommonMoreYCustomSpaceHelper.setMoreBarChart(mBarchart, xAxisValues, map, Arrays.asList(
                mActivity.getResources().getColor(R.color.color_4CCCCB), mActivity.getResources().getColor(R.color.color_40A0FF)
        ), false, "", 8, false);
        mBarchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int x = (int)e.getX();
                showToastDialog(list.get((int) e.getX()).getCatalogName() + " ：" + AwConvertUtil.double2String(e.getY(), 0));
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setBarchartLoseCountResult() {
        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("函数及其表示");
        xAxisValues.add("函数的图像");
        xAxisValues.add("集合的概念与...");
        xAxisValues.add("函数与方程");

        List<Float> yAxisValues1 = new ArrayList<>();
        yAxisValues1.add(580f);
        yAxisValues1.add(450f);
        yAxisValues1.add(250f);
        yAxisValues1.add(193f);
        List<Float> yAxisValues2 = new ArrayList<>();
        yAxisValues2.add(600f);
        yAxisValues2.add(800f);
        yAxisValues2.add(400f);
        yAxisValues2.add(200f);
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        map.put("失分题数", yAxisValues1);
        map.put("总题数", yAxisValues2);
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart, xAxisValues, map, Arrays.asList(
                mActivity.getResources().getColor(R.color.color_4CCCCB), mActivity.getResources().getColor(R.color.color_40A0FF)
        ), false, "");
//        ChartLose2BarHelper.setTwoBarChart(mBarchart, xAxisValues, yAxisValues1, yAxisValues2, "失分题数", "总题数");
        mBarchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mBarchart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
//                showMsg("长按了");
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }


}
