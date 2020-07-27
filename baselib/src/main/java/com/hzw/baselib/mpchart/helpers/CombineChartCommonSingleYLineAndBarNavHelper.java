package com.hzw.baselib.mpchart.helpers;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hzw.baselib.R;
import com.hzw.baselib.mpchart.ChartWidthUtil;
import com.hzw.baselib.mpchart.formatters.CommonStrFormatter;
import com.hzw.baselib.mpchart.formatters.StringAxisValueFormatter;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hzw on 2019/6/20.
 */

public class CombineChartCommonSingleYLineAndBarNavHelper {

    private static final int OFFSET_LEFT = 15;
    private static final int OFFSET_TOP = 20;
    private static final int OFFSET_RIGTH = 15;
    private static final int OFFSET_BOTTOM = 10;

    /**
     * 设置柱线组合图样式，柱图依赖左侧y轴，线图依赖右侧y轴
     */
    public static void setCombineChart(CombinedChart combineChart, final List<String> xAxisValues, List<Float> barValues, List<Float> lineYAxisValue,
                                       String barTitle, String lineTitle,  int leftDotNum, String leftSuffix, int space) {

        removeDataSet(combineChart);//清除数据

        combineChart.setPinchZoom(true);//设置按比例放缩柱状图
        //设置一页最大显示个数为space，超出部分就滑动
        float ratio = (float) xAxisValues.size()/(float) space;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        combineChart.zoom(ratio,1f,0,0);

        //不显示makerview. 自己做点击后的处理
//        MPChartMarkerView markerView = new MPChartMarkerView(combineChart.getContext(), R.layout.custom_marker_view);
//        combineChart.setMarker(markerView);

        //设置绘制顺序，让线在柱的上层
        combineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.BAR
        });

        combineChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);
        combineChart.setHighlightFullBarEnabled(false);

        //x坐标轴设置
//        XAxis xAxis = combineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setGranularity(1f);
//        xAxis.setLabelCount(xAxisValues.size());
////        xAxis.setLabelCount(xAxisValues.size() + 2);
//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float v, AxisBase axisBase) {
//                if (v < 0 || v > (xAxisValues.size() - 1))//使得两侧柱子完全显示
//                    return "";
//                return xAxisValues.get((int) v);
//            }
//        });
//        xAxis.setTextSize(6);
//        xAxis.setTextColor(Color.parseColor("#999999"));
//        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。

        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValues, false);//设置自定义的x轴值格式化器
        XAxis xAxis = combineChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setLabelCount(xAxisValues.size());//设置标签显示的个数
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.parseColor("#999999"));

        //y轴设置
        YAxis leftAxis = combineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
        leftAxis.setGridColor(Color.parseColor("#e6e6e6"));  //网格线条的颜色
        leftAxis.setAxisLineWidth(0); //设置线宽度
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        leftAxis.setValueFormatter(new CommonStrFormatter(leftDotNum, leftSuffix));
        leftAxis.setTextSize(10f);
        leftAxis.setTextColor(Color.parseColor("#999999"));
        combineChart.getAxisRight().setEnabled(false);
        //设置坐标轴最大最小值
        Float yMax = null;
        try {
            yMax = Double.valueOf(Collections.max(barValues) * 1.1).floatValue();
            if(yMax < 6) {
                yMax = 10f;
            }
        } catch (Exception e) {
            e.printStackTrace();
            yMax = 10f;
        }
        leftAxis.setAxisMaximum(yMax);
//        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
//        Float yMin = Double.valueOf(Collections.min(barValues) * 0.9).floatValue();
//        leftAxis.setAxisMinimum(yMin);

        YAxis rightAxis = combineChart.getAxisRight();
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisLineWidth(0); //设置线宽度
        rightAxis.setAxisLineColor(Color.TRANSPARENT);
        rightAxis.setTextColor(Color.parseColor("#999999"));
//        Float yMaxBubble = null;
//        try {
//            yMaxBubble = Double.valueOf(Collections.max(lineYAxisValue) * 1.1).floatValue();
//            if(yMax < 6) {
//                yMaxBubble = 10f;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            yMaxBubble = 10f;
//        }
//        rightAxis.setAxisMinimum(0f);//设置Y轴最小值
//        rightAxis.setAxisMaximum(yMaxBubble);
        //图例设置
        Legend legend = combineChart.getLegend();
        legend.setEnabled(false);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setTextSize(10f);

        //TODO 展示位置暂时不好确定, 先隐藏.
        combineChart.getDescription().setEnabled(false);//设置描述
//        Description description = new Description();
//        description.setText("年级均分 60.00");
//        description.setTextColor(ContextCompat.getColor(combineChart.getContext(), R.color.color_ED7D31));
////        description.setPosition(combineChart.getCenter().getX(), xAxis.getYOffset());
//        combineChart.setDescription(description);

        //设置组合图数据
        CombinedData data = new CombinedData();
        data.setData(generateLineData(combineChart, lineYAxisValue, lineTitle));
        data.setData(generateBarData(combineChart, barValues, barTitle, xAxisValues.size()));

        combineChart.setData(data);//设置组合图数据源


        //使得两侧柱子完全显示
        xAxis.setAxisMinimum(combineChart.getCombinedData().getXMin() - 0.5f);
        xAxis.setAxisMaximum(combineChart.getCombinedData().getXMax() + 0.5f);

//        combineChart.setExtraTopOffset(30);
//        combineChart.setExtraBottomOffset(10);
        combineChart.animateX(1500);//数据显示动画，从左往右依次显示
        combineChart.invalidate();
    }

    /**
     * 生成气泡图数据
     */
    private static BubbleData generateBubbleData(CombinedChart combineChart, List<Float> bubblieValues, String lineTitle) {
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<>();

        for (int i = 0, n = bubblieValues.size(); i < n; ++i) {
            AwLog.d("CombinedChart bubble data: " + bubblieValues.get(i) + " ,i: " + i);
            //(float) (0.05 * bubblieValues.get(i)))
            bubbleEntries.add(new BubbleEntry(i, bubblieValues.get(i), 0.12f));
        }

        BubbleDataSet bubbleDataSet = new BubbleDataSet(bubbleEntries, lineTitle);
        bubbleDataSet.setColor(ContextCompat.getColor(combineChart.getContext(), R.color.color_ED7D31));
        bubbleDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
        bubbleDataSet.setDrawValues(false);//不绘制线的数据
        bubbleDataSet.setHighlightEnabled(false);
        bubbleDataSet.setHighLightColor(Color.TRANSPARENT);
        bubbleDataSet.setNormalizeSizeEnabled(false);
        bubbleDataSet.setForm(Legend.LegendForm.CIRCLE);

        BubbleData bubbleData = new BubbleData (bubbleDataSet);
        bubbleData.setDrawValues(false);
//        bubbleData.setValueTextSize(8f);
        bubbleData.setValueTextColor(Color.WHITE);
        bubbleData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 2);
            }
        });

        return bubbleData;
    }

    /**
     * 生成线图数据
     */
    private static LineData generateLineData(CombinedChart combineChart, List<Float> lineValues, String lineTitle) {
        ArrayList<Entry> lineEntries = new ArrayList<>();

        for (int i = 0, n = lineValues.size(); i < n; ++i) {
            lineEntries.add(new Entry(i, lineValues.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(lineEntries, lineTitle);
        lineDataSet.setColor(ContextCompat.getColor(combineChart.getContext(), R.color.color_ED7D31));
//        lineDataSet.setColor(Color.rgb(233, 196, 21));
        lineDataSet.setLineWidth(1f);//设置线的宽度
        lineDataSet.setDrawCircles(false);
        lineDataSet.setCircleColor(ContextCompat.getColor(combineChart.getContext(), R.color.color_ED7D31));//设置圆圈的颜色
        lineDataSet.setCircleColorHole(ContextCompat.getColor(combineChart.getContext(), R.color.color_ED7D31));//设置圆圈内部洞的颜色
        //        lineDataSet.setCircleColor(Color.rgb(244, 219, 100));//设置圆圈的颜色
        //        lineDataSet.setCircleColorHole(Color.WHITE);//设置圆圈内部洞的颜色
        //lineDataSet.setValueTextColor(Color.rgb(254,116,139));
//        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//设置线数据依赖于右侧y轴
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);//设置线数据依赖于左侧y轴
        lineDataSet.setHighlightEnabled(false);
        lineDataSet.setDrawValues(false);//不绘制线的数据
        lineDataSet.setForm(Legend.LegendForm.LINE);

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
     * 生成柱图数据
     *
     * @param barValues
     * @return
     */
    private static BarData generateBarData(CombinedChart combineChart, List<Float> barValues, String barTitle, int size) {

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        List<Integer> colors = new ArrayList<Integer>();

//        int green = combineChart.getContext().getResources().getColor(R.color.color_chart_669900);
//        int red = combineChart.getContext().getResources().getColor(R.color.color_chart_FF0000);
        for (int i = 0, n = barValues.size(); i < n; ++i) {
            AwLog.d("CombinedChart bar data: " + barValues.get(i) + " ,i: " + i);
            barEntries.add(new BarEntry(i, barValues.get(i)));
            // specific colors
//            if (barValues.get(i) >= 0)
//                colors.add(green);
//            else
//                colors.add(red);
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, barTitle);
        barDataSet.setColor(ContextCompat.getColor(combineChart.getContext(), R.color.color_40A0FF));//设置set1的柱的颜色
//        barDataSet.setColors(colors);
        barDataSet.setDrawValues(true);//隐藏柱子顶部文字或数字说明
        barDataSet.setValueTextColor(Color.parseColor("#999999"));
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setHighlightEnabled(false);

        BarData barData = new BarData(barDataSet);
        barData.setValueTextSize(10f);
        barData.setBarWidth(ChartWidthUtil.getBarWidth(size));
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 2);
            }
        });

        return barData;
    }

    public static void removeDataSet(CombinedChart combineChart) {//删除坐标线
        CombinedData data = combineChart.getData();
        if(data != null) {
            data.removeDataSet(data.getDataSetByIndex(data.getDataSetCount() - 1));
            combineChart.notifyDataSetChanged();
            combineChart.invalidate();
        }
    }
}
