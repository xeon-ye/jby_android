package com.jkrm.education.mpchart.helpers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 首页作业情况及其批阅情况
 * Created by hzw on 2019/5/7.
 */

public class ChartHomeworkScanResultHelper {


    private static final int OFFSET_LEFT = 0;
    private static final int OFFSET_TOP = -10;
    private static final int OFFSET_RIGTH = 0;
    private static final int OFFSET_BOTTOM = 0;
    private static final int[] COLOR_INNER = {Color.rgb(64, 160, 255), Color.rgb(76, 204, 203)};
    private static final int[] COLOR_OUT = {Color.rgb(147, 209, 254), Color.rgb(195, 234, 255), Color.rgb(76, 204, 203)};

    public static void setPieChartInner(PieChart pieChart, Map<String, Float> pieValues) {
        pieChart.setUsePercentValues(false);//设置使用百分比
        pieChart.getDescription().setEnabled(false);//设置描述
        pieChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);//饼图上下左右间距
        pieChart.setDrawCenterText(false);//是否绘制PieChart内部中心文本
        pieChart.setRotationAngle(270);//设置pieChart图表起始角度
        pieChart.setDrawHoleEnabled(false);//是否显示饼图中间空白区域，默认显示
        pieChart.setTransparentCircleRadius(0f);//设置PieChart内部透明圆的半径
        pieChart.setRotationEnabled(false);//设置pieChart图表是否可以手动旋转
        pieChart.setHighlightPerTapEnabled(false);//设置piecahrt图表点击Item高亮是否可用
        pieChart.setDrawEntryLabels(false);

        //图例设置
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        //设置饼图数据
        setPieChartData(pieChart, pieValues, 2);
//        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);//数据显示动画
    }

    public static void setPieChartOut(PieChart pieChart, Map<String, Float> pieValues) {
        pieChart.setUsePercentValues(false);//设置使用百分比
        pieChart.getDescription().setEnabled(false);//设置描述
        pieChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);//饼图上下左右间距
        pieChart.setDrawCenterText(false);//是否绘制PieChart内部中心文本
        pieChart.setRotationAngle(270);//设置pieChart图表起始角度
        pieChart.setDrawHoleEnabled(false);//是否显示饼图中间空白区域，默认显示
        pieChart.setTransparentCircleRadius(0f);//设置PieChart内部透明圆的半径
        pieChart.setRotationEnabled(false);//设置pieChart图表是否可以手动旋转
        pieChart.setHighlightPerTapEnabled(false);//设置piecahrt图表点击Item高亮是否可用
        pieChart.setDrawEntryLabels(false); //隐藏labels文字内容

        //图例设置
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        //设置饼图数据
        setPieChartData(pieChart, pieValues, 3);
        //        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);//数据显示动画
    }



    /**
     * 设置饼图数据源
     */
    private static void setPieChartData(PieChart pieChart, Map<String, Float> pieValues, int tag) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        Set<Map.Entry<String, Float>> set = pieValues.entrySet();
        Iterator<Map.Entry<String, Float>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Float> entry = it.next();
            entries.add(new PieEntry(Float.valueOf(entry.getValue().toString()), entry.getKey().toString()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0f);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        switch (tag) {
            case 2:
                dataSet.setColors(COLOR_INNER);//设置饼块的颜色
                break;
            case 3:
                dataSet.setColors(COLOR_OUT);//设置饼块的颜色
                break;
            default:

                break;
        }
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(1.5f);
        dataSet.setValueLineColor(Color.TRANSPARENT);//设置连接线的颜色, 透明不显示线
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(8f);
        dataSet.setValueTextColor(Color.TRANSPARENT); //不显示文字

        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
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
