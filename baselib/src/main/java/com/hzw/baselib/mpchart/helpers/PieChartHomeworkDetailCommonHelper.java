package com.hzw.baselib.mpchart.helpers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.hzw.baselib.mpchart.formatters.CommonStrFormatter;
import com.hzw.baselib.util.MyDateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 通用饼图(作业详情使用 单独色值)
 * Created by hzw on 2019/5/7.
 */

public class PieChartHomeworkDetailCommonHelper {


    private static final int OFFSET_LEFT = 20;
    private static final int OFFSET_TOP = -10;
    private static final int OFFSET_RIGTH = 20;
    private static final int OFFSET_BOTTOM = 5;
    public static final int[] COLORS = {
            Color.rgb(64, 160, 255),
            Color.rgb(76, 204, 203),
            Color.rgb(88, 203, 116),
            Color.rgb(252, 212, 75),
            Color.rgb(244, 102, 124)};


    public static void setPieChartCommon(PieChart pieChart, Map<String, Float> pieValues) {
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
        legend.setEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setWordWrapEnabled(true);
        legend.setXEntrySpace(10f);//设置距离饼图的距离，防止与饼图重合
        legend.setYEntrySpace(10f);//设置距离饼图的距离，防止与饼图重合
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        //设置饼图数据
        setPieChartData(pieChart, pieValues);
        //        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);//数据显示动画
    }



    /**
     * 设置饼图数据源
     */
    private static void setPieChartData(PieChart pieChart, Map<String, Float> pieValues) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        Set<Map.Entry<String, Float>> set = pieValues.entrySet();
        Iterator<Map.Entry<String, Float>> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Float> entry = it.next();
            entries.add(new PieEntry(Float.valueOf(entry.getValue().toString()), entry.getKey().toString()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(0.5f);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离
        dataSet.setColors(MyDateUtil.getChartColorsArrayHomework(pieChart.getContext()));//设置饼块的颜色
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(1.5f);
        dataSet.setValueLineColor(Color.BLUE);//设置连接线的颜色, 透明不显示线
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new CommonStrFormatter(0,"人"));
        pieData.setValueTextSize(8f);
        dataSet.setValueTextColor(Color.BLACK); //不显示文字

        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

}
