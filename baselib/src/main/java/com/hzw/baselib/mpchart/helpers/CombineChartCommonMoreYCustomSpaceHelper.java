package com.hzw.baselib.mpchart.helpers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hzw.baselib.R;
import com.hzw.baselib.mpchart.formatters.CommonStrFormatter;
import com.hzw.baselib.mpchart.formatters.StringValueFormatter;
import com.hzw.baselib.mpchart.formatters.ValueFormatter;
import com.hzw.baselib.mpchart.makerviews.CommonStrEnd0MarkerView;
import com.hzw.baselib.mpchart.makerviews.PercentMarkerView;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwLog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用组合多柱图线图
 * Created by hzw on 2019/5/7.
 */

public class CombineChartCommonMoreYCustomSpaceHelper {

    private static final int OFFSET_LEFT = 15;
    private static final int OFFSET_TOP = -30;
    private static final int OFFSET_RIGTH = 15;
    private static final int OFFSET_BOTTOM = 10;

    public static void setMoreBarChart(CombinedChart combineChart, List<String> xAxisValue, LinkedHashMap<String, List<Float>> map, LinkedHashMap<String, List<Float>> mapLine,
                                       List<Integer> colors, List<Integer> lineColors, boolean percentage, boolean rightPercentage,
                                       String topSuffix, int space, boolean showMarkerView, float leftYMax, float rightYMax) {
        combineChart.getDescription().setEnabled(false);//设置描述
        combineChart.setPinchZoom(true);//设置按比例放缩柱状图
        //设置一页最大显示个数，超出部分就滑动
        for (Map.Entry<String, List<Float>> entry : map.entrySet()) {
            List<Float> yAxisValue = entry.getValue();
            if(yAxisValue.size() * map.size() > space) { //一页超过20列, 设置滑动
                float ratio = (float) yAxisValue.size() * map.size()/(float) space;
                //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
                combineChart.zoom(ratio,1f,0,0);
                break;
            }
        }

        //设置自定义的markerView
        if(showMarkerView) {
            ValueFormatter xAxisValueFormatter = new StringValueFormatter(xAxisValue);
            if(percentage) {
                PercentMarkerView markerView = new PercentMarkerView(combineChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter);
                combineChart.setMarker(markerView);
            } else {
                CommonStrEnd0MarkerView markerView = new CommonStrEnd0MarkerView(combineChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter, topSuffix);
                combineChart.setMarker(markerView);
            }
            //        //设置自定义的markerView
            //        MPChartMarkerView markerView = new MPChartMarkerView(barChart.getContext(), R.layout.custom_marker_view);
            //        barChart.setMarker(markerView);

        }
        combineChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);

        //设置绘制顺序，让线在柱的上层
        combineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });

        //x坐标轴设置
        XAxis xAxis = combineChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setLabelCount(xAxisValue.size());//设置标签显示的个数
        xAxis.setCenterAxisLabels(true);//设置标签居中
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.parseColor("#999999"));

        //y轴设置
        YAxis leftAxis = combineChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLabels(true);//是否绘制y轴标签
        leftAxis.setDrawAxisLine(true);//是否绘制y轴
        leftAxis.setAxisLineWidth(0); //设置线宽度
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
        leftAxis.setGridColor(Color.parseColor("#e6e6e6"));  //网格线条的颜色
        if(percentage) {
            leftAxis.setValueFormatter(new CommonStrFormatter(2, "%"));
        }
        leftAxis.setTextSize(10);
        leftAxis.setTextColor(Color.parseColor("#999999"));

        //设置坐标轴最大最小值
        Float yMax = Double.valueOf(leftYMax * 1.3).floatValue();
        if(yMax < 6) {
            yMax = 10f;
        }
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setAxisMaximum(yMax);

        YAxis rightAxis = combineChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisLineWidth(0); //设置线宽度
        rightAxis.setAxisLineColor(Color.TRANSPARENT);
        rightAxis.setTextColor(Color.parseColor("#999999"));
        if(rightPercentage) {
            rightAxis.setValueFormatter(new CommonStrFormatter(2, "%"));
        }
        Float yMaxBubble = Double.valueOf(rightYMax * 1.3).floatValue();
        if(yMax < 6) {
            yMaxBubble = 100f;
        }
        rightAxis.setAxisMinimum(0f);//设置Y轴最小值
        rightAxis.setAxisMaximum(yMaxBubble);


        //图例设置
        Legend legend = combineChart.getLegend();
        legend.setEnabled(false);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        legend.setTextSize(10f);//设置文字大小
        legend.setForm(Legend.LegendForm.SQUARE);//正方形，圆形或线
        legend.setFormSize(10f); // 设置Form的大小
        legend.setFormLineWidth(10f);//设置Form的宽度
        legend.setXEntrySpace(5f);//设置距离饼图的距离，防止与饼图重合
        legend.setYEntrySpace(20f);//设置距离饼图的距离，防止与饼图重合
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        //设置组合图数据
        CombinedData data = new CombinedData();
        data.setData(generateBarData(combineChart, map, xAxisValue, colors));
        data.setData(setMoreLineChartData(mapLine, lineColors));

        combineChart.setData(data);//设置组合图数据源


        //使得两侧柱子完全显示
//        xAxis.setAxisMinimum(combineChart.getCombinedData().getXMin() - 2f);
//        xAxis.setAxisMaximum(combineChart.getCombinedData().getXMax() + 2f);

        //        combineChart.setExtraTopOffset(30);
        //        combineChart.setExtraBottomOffset(10);
        combineChart.animateX(1500);//数据显示动画，从左往右依次显示
        combineChart.setScaleEnabled(false);//设置能否缩放
//        barChart.animateX(1500);//数据显示动画，从左往右依次显示
        combineChart.invalidate();
    }

    /**
     * 生成柱图数据
     *
     * @return
     */
    private static BarData generateBarData(CombinedChart combineChart, LinkedHashMap<String, List<Float>> map, List<String> xAxisValue, List<Integer> colors) {
        float groupSpace = 0.2f; //0.04
        //        float barSpace = 0.1f; //0.03
        //        float barWidth = 0.3f; //0.45

        float barSpace = 0.05f;
        float barWidth = (1-groupSpace) / map.size() - barSpace;
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        int currentPosition = 0;//用于柱状图颜色集合的index
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        for (Map.Entry<String, List<Float>> entry : map.entrySet()) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            String title =  entry.getKey();
            List<Float> yAxisValue = entry.getValue();//4个
            AwLog.d("迭代title: " + title);
            for (int i=0; i < yAxisValue.size(); i++) {
                AwLog.d("迭代title y: " + yAxisValue.get(i));
                entries.add(new BarEntry(i, yAxisValue.get(i)));
            }
            BarDataSet dataset = new BarDataSet(entries, title);
            dataset.setColor(colors.get(currentPosition));
            dataset.setDrawValues(false);//隐藏柱子顶部文字或数字说明
            dataSets.add(dataset);
            currentPosition++;
        }
        float max = (dataSets.size() * (barWidth + barSpace) + groupSpace) * xAxisValue.size();
        BarData data = new BarData(dataSets);
//        data.setHighlightEnabled(false);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.parseColor("#999999"));
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 0);
            }
        });
        combineChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        combineChart.getXAxis().setAxisMaximum(max);
        data.setBarWidth(barWidth);
        data.groupBars(0, groupSpace, barSpace);
        return data;
    }

    /**
     * 生成线图数据
     */
    private static LineData generateLineData(List<Float> lineValues, String lineTitle, int colorRes) {
        ArrayList<Entry> lineEntries = new ArrayList<>();

        for (int i = 0, n = lineValues.size(); i < n; ++i) {
            lineEntries.add(new Entry(i + 0.5f, lineValues.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntries, lineTitle);
        lineDataSet.setColor(colorRes);
        lineDataSet.setLineWidth(2.5f);//设置线的宽度
        lineDataSet.setCircleColor(colorRes);//设置圆圈的颜色
        lineDataSet.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
        //lineDataSet.setValueTextColor(Color.rgb(254,116,139));
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
        lineDataSet.setDrawValues(false);//不绘制线的数据

        LineData lineData = new LineData(lineDataSet);
        lineData.setValueTextSize(10f);
        lineData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 2);
            }
        });

        return lineData;
    }


    /**
     * 设置柱状图数据源
     */
    private static void setMoreBarChartData(BarChart barChart, List<String> xAxisValue, LinkedHashMap<String, List<Float>> map, List<Integer> colors) {
        float groupSpace = 0.2f; //0.04
//        float barSpace = 0.1f; //0.03
//        float barWidth = 0.3f; //0.45

        float barSpace = 0.02f;
        float barWidth = (1-groupSpace) / map.size() - barSpace;
        // (0.45 + 0.03) * 2 + 0.04 = 1，即一个间隔为一组，包含两个柱图 -> interval per "group"

        int currentPosition = 0;//用于柱状图颜色集合的index
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        for (Map.Entry<String, List<Float>> entry : map.entrySet()) {
            ArrayList<BarEntry> entries = new ArrayList<>();
            String title =  entry.getKey();
            List<Float> yAxisValue = entry.getValue();//4个
            AwLog.d("迭代title: " + title);
            for (int i=0; i < yAxisValue.size(); i++) {
                AwLog.d("迭代title y: " + yAxisValue.get(i));
                entries.add(new BarEntry(i, yAxisValue.get(i)));
            }
            BarDataSet dataset = new BarDataSet(entries, title);
            dataset.setColor(colors.get(currentPosition));
            dataset.setDrawValues(false);//隐藏柱子顶部文字或数字说明
            dataSets.add(dataset);
            currentPosition++;
        }
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 2);
            }
        });

        barChart.setData(data);
        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * xAxisValue.size() + 0);
        barChart.groupBars(0, groupSpace, barSpace);
    }

    private static LineData setMoreLineChartData(LinkedHashMap<String, List<Float>> map, List<Integer> colors) {
        int currentPosition = 0;//用于柱状图颜色集合的index
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (Map.Entry<String, List<Float>> entry : map.entrySet()) {
            ArrayList<Entry> entries = new ArrayList<>();
            String title =  entry.getKey();
            List<Float> yAxisValue = entry.getValue();//4个
            AwLog.d("迭代title: " + title);
            for (int i=0; i < yAxisValue.size(); i++) {
                AwLog.d("迭代title y: " + yAxisValue.get(i));
                entries.add(new Entry(i + 0.5f, yAxisValue.get(i)));
            }
            LineDataSet dataset = new LineDataSet(entries, title);
            dataset.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
            dataset.setDrawValues(false);//隐藏柱子顶部文字或数字说明
            dataset.setColor(colors.get(currentPosition));
            dataset.setCircleColor(colors.get(currentPosition));
            dataset.setLineWidth(1f);//设置线宽  1
            dataset.setCircleRadius(3f);//设置焦点圆心的大小
            dataset.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            dataset.setHighlightLineWidth(3f);//设置点击交点后显示高亮线宽
            dataset.setHighlightEnabled(false);//是否禁用点击高亮线
            dataset.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            dataset.setValueTextSize(9f);//设置显示值的文字大小   9   0不显示数字
            dataset.setDrawFilled(false);//设置禁用范围背景填充
            dataset.setDrawCircles(true);//图表上的数据点是否用小圆圈表示
            dataset.setMode(LineDataSet.Mode.LINEAR);  //CUBIC_BEZIER曲线  HORIZONTAL_BEZIER水平线  LINEAR线性  STEPPED走线
            dataset.setForm(Legend.LegendForm.LINE);
            dataSets.add(dataset);
            currentPosition++;
        }
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData(dataSets);
        data.setValueTextSize(10f);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 2);
            }
        });
        return data;
    }

    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(false);
        //        barDataSet.setValueTextSize(10f);
        //        barDataSet.setValueTextColor(color);
    }
}
