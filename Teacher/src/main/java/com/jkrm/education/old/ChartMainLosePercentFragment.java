package com.jkrm.education.old;

import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.R;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.mvp.presenters.MainLosePresent;
import com.jkrm.education.mvp.views.MainLoseView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

/**
 * 原首页---失分率
 * Created by hzw on 2019/5/10.
 */

public class ChartMainLosePercentFragment extends AwMvpFragment<MainLosePresent> implements MainLoseView.View  {

    @BindView(R.id.barchart)
    BarChart mBarchart;

    @Override
    protected MainLosePresent createPresenter() {
        return new MainLosePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_main_lose_percent;
    }

    @Override
    protected void initData() {
        super.initData();
//        setBarchartLoseResult();
        mPresenter.getStatusErrorQuestionInSomeDay(MyApp.getInstance().getAppUser().getTeacherId());
    }

    @Override
    public void getStatusErrorQuestionInSomeDaySuccess(List<StatusErrorQuestionResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            AwLog.d("getStatusErrorQuestionInSomeDaySuccess list is null");
            return;
        }
        AwLog.d("getStatusErrorQuestionInSomeDaySuccess list size: " + list.size());
        Collections.sort(list, new Comparator<StatusErrorQuestionResultBean>() {
            @Override
            public int compare(StatusErrorQuestionResultBean o1, StatusErrorQuestionResultBean o2) {
                return (int) (Double.parseDouble(o1.getScoreRate())  * 100 - Double.parseDouble(o2.getScoreRate()) * 100);
            }
        });
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(StatusErrorQuestionResultBean temp : list) {
            String name = temp.getCatalogName();
            if(name.length() > 10) {
                name = name.substring(0, 10) + "...";
            }
            AwLog.d("name: " + name);
            xAxisValues.add(name);
            yAxisValues.add(Float.valueOf(temp.getScoreRate()) * 100);
        }
        BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart, xAxisValues, yAxisValues,
                "", null, 1, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 4,
                false, false, false);
        mBarchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showToastDialog(list.get((int) e.getX()).getCatalogName() + ":" + AwConvertUtil.double2String(e.getY(), 2) + AwBaseConstant.COMMON_SUFFIX_RATIO);
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setBarchartLoseResult() {
        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("函数及其表示");
        xAxisValues.add("函数的图像");
        xAxisValues.add("集合的概念与...");
        xAxisValues.add("函数与方程");

        List<Float> yAxisValues = new ArrayList<>();
        yAxisValues.add(15f);
        yAxisValues.add(35f);
        yAxisValues.add(10f);
        yAxisValues.add(40f);
        BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart, xAxisValues, yAxisValues,
                "", null, 1, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 3,
                false, false, false);
//        ChartLoseHelper.setBarChart(mBarchart, xAxisValues, yAxisValues,"",null,1);
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
                AwLog.d("onChartLongPressed x: " + me.getX() + " ,intX: " + (int)me.getX());
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
