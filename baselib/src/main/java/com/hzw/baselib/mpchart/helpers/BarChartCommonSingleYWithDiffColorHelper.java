package com.hzw.baselib.mpchart.helpers;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.hzw.baselib.R;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartWidthUtil;
import com.hzw.baselib.mpchart.MyBarDataSet;
import com.hzw.baselib.mpchart.NormalBarDataSet;
import com.hzw.baselib.mpchart.formatters.CommonStrFormatter;
import com.hzw.baselib.mpchart.formatters.StringAxisValueFormatter;
import com.hzw.baselib.mpchart.formatters.StringValueFormatter;
import com.hzw.baselib.mpchart.formatters.ValueFormatter;
import com.hzw.baselib.mpchart.makerviews.CommonStrEnd0MarkerView;
import com.hzw.baselib.mpchart.makerviews.CommonStrEnd2MarkerView;
import com.hzw.baselib.mpchart.makerviews.PercentMarkerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 通用单列柱图 barchart
 * Created by hzw on 2019/5/7.
 */

public class BarChartCommonSingleYWithDiffColorHelper {

    private static final int OFFSET_LEFT = 15;
    private static final int OFFSET_TOP = 0;
    private static final int OFFSET_RIGTH = 15;
    private static final int OFFSET_BOTTOM = 10;

    public static void setBarChart(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue, String title, int count, int leftDotNum, String leftSuffix, int topNum, String topSuffix) {

        removeDataSet(barChart);//清除数据

        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        //设置一页最大显示个数为20，超出部分就滑动
        float ratio = (float) xAxisValue.size()/(float) 20;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        barChart.zoom(ratio,1f,0,0);

        //设置自定义的markerView
        ValueFormatter xAxisValueFormatter = new StringValueFormatter(xAxisValue);
        if(AwBaseConstant.COMMON_SUFFIX_RATIO.equals(topSuffix)) {
            PercentMarkerView markerView = new PercentMarkerView(barChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter);
            barChart.setMarker(markerView);
        } else {
            if(topNum == 2) {
                CommonStrEnd2MarkerView markerView = new CommonStrEnd2MarkerView(barChart.getContext(), R.layout.custom_marker_view, topSuffix);
                barChart.setMarker(markerView);
            } else {
                CommonStrEnd0MarkerView markerView = new CommonStrEnd0MarkerView(barChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter, topSuffix);
                barChart.setMarker(markerView);
            }
        }

        barChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue, false);//设置自定义的x轴值格式化器
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setLabelCount(xAxisValue.size());//设置标签显示的个数
        if(count != 1){
            xAxis.setLabelRotationAngle(-60);
        }
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.parseColor("#999999"));

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴
        leftAxis.setAxisLineWidth(0); //设置线宽度
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
        leftAxis.setGridColor(Color.parseColor("#e6e6e6"));  //网格线条的颜色
        //设置坐标轴最大最小值
        Float yMax =  Double.valueOf(Collections.max(yAxisValue) * 1.1).floatValue();
        if(yMax < 6) {
            yMax = 10f;
        }
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setAxisMaximum(yMax);//设置Y轴最大值
        leftAxis.setValueFormatter(new CommonStrFormatter(leftDotNum, leftSuffix));
        leftAxis.setTextSize(10);
        leftAxis.setTextColor(Color.parseColor("#999999"));

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴

        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向

        legend.setForm(Legend.LegendForm.SQUARE);//图例窗体的形状
        legend.setFormSize(0f);//图例窗体的大小
        legend.setTextSize(12f);//图例文字的大小
        //legend.setYOffset(-2f);

        //设置柱状图数据
        setBarChartData(barChart, yAxisValue, title, xAxisValue.size());
        barChart.setScaleEnabled(false);//设置能否缩放
        barChart.setFitBars(true);//使两侧的柱图完全显示

//        barChart.animateX(1500);//数据显示动画，从左往右依次显示
    }

    public static void setBarChartSingle(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue, String title, int count, int leftDotNum, String leftSuffix, int topNum, String topSuffix) {

        removeDataSet(barChart);//清除数据

        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        //设置一页最大显示个数为20，超出部分就滑动
        float ratio = (float) xAxisValue.size()/(float) 20;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        barChart.zoom(ratio,1f,0,0);

        //设置自定义的markerView
        ValueFormatter xAxisValueFormatter = new StringValueFormatter(xAxisValue);
        if(AwBaseConstant.COMMON_SUFFIX_RATIO.equals(topSuffix)) {
            PercentMarkerView markerView = new PercentMarkerView(barChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter);
            barChart.setMarker(markerView);
        } else {
            if(topNum == 2) {
                CommonStrEnd2MarkerView markerView = new CommonStrEnd2MarkerView(barChart.getContext(), R.layout.custom_marker_view, topSuffix);
                barChart.setMarker(markerView);
            } else {
                CommonStrEnd0MarkerView markerView = new CommonStrEnd0MarkerView(barChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter, topSuffix);
                barChart.setMarker(markerView);
            }
        }

        barChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue, false);//设置自定义的x轴值格式化器
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setLabelCount(xAxisValue.size());//设置标签显示的个数
        if(count != 1){
            xAxis.setLabelRotationAngle(-60);
        }
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.parseColor("#999999"));

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLabels(true);//禁止绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴
        leftAxis.setAxisLineWidth(0); //设置线宽度
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
        leftAxis.setGridColor(Color.parseColor("#e6e6e6"));  //网格线条的颜色
        //设置坐标轴最大最小值
        Float yMax =  Double.valueOf(Collections.max(yAxisValue) * 1.1).floatValue();
        if(yMax < 6) {
            yMax = 10f;
        }
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setAxisMaximum(yMax);//设置Y轴最大值
        leftAxis.setValueFormatter(new CommonStrFormatter(leftDotNum, leftSuffix));
        leftAxis.setTextSize(10);
        leftAxis.setTextColor(Color.parseColor("#999999"));

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴

        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向

        legend.setForm(Legend.LegendForm.SQUARE);//图例窗体的形状
        legend.setFormSize(0f);//图例窗体的大小
        legend.setTextSize(12f);//图例文字的大小
        //legend.setYOffset(-2f);

        //设置柱状图数据
        setBarChartDataSingle(barChart, yAxisValue, title, xAxisValue.size());
        barChart.setScaleEnabled(false);//设置能否缩放
        barChart.setFitBars(true);//使两侧的柱图完全显示

//        barChart.animateX(1500);//数据显示动画，从左往右依次显示
    }
    private static void setBarChartDataSingle(BarChart barChart, List<Float> yAxisValue, String title, int size) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0, n = yAxisValue.size(); i < n; ++i) {
            entries.add(new BarEntry(i, yAxisValue.get(i)));
        }

        NormalBarDataSet set1;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (NormalBarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new NormalBarDataSet(entries, title);
            set1.setColors(new int[]{Color.parseColor("#40A0FF")});
            set1.setDrawValues(false);//隐藏柱子顶部文字或数字说明
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(ChartWidthUtil.getBarWidth(size));
            data.setValueFormatter(new CommonStrFormatter(2, ""));
            barChart.setData(data);
        }
    }
    private static void setBarChartData(BarChart barChart, List<Float> yAxisValue, String title, int size) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0, n = yAxisValue.size(); i < n; ++i) {
            entries.add(new BarEntry(i, yAxisValue.get(i)));
        }

        MyBarDataSet set1;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (MyBarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new MyBarDataSet(entries, title);
            set1.setColors(new int[]{Color.parseColor("#FAB843"),Color.parseColor("#40A0FF")});
            set1.setDrawValues(false);//隐藏柱子顶部文字或数字说明
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(ChartWidthUtil.getBarWidth(size));
            data.setValueFormatter(new CommonStrFormatter(2, ""));
            barChart.setData(data);
       }
    }

    public static void removeDataSet(BarChart barChart) {//删除坐标线

        BarData data = barChart.getData();
        if(data != null) {
            data.removeDataSet(data.getDataSetByIndex(data.getDataSetCount() - 1));
            barChart.notifyDataSetChanged();
            barChart.invalidate();
        }
    }
}
