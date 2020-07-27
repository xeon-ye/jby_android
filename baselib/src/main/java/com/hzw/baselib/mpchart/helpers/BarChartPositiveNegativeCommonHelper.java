package com.hzw.baselib.mpchart.helpers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hzw.baselib.R;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.ChartWidthUtil;
import com.hzw.baselib.mpchart.formatters.CommonStrFormatter;
import com.hzw.baselib.mpchart.formatters.StringValueFormatter;
import com.hzw.baselib.mpchart.formatters.ValueFormatter;
import com.hzw.baselib.mpchart.makerviews.CommonStrEnd0MarkerView;
import com.hzw.baselib.mpchart.makerviews.CommonStrEnd2MarkerView;
import com.hzw.baselib.mpchart.makerviews.PercentMarkerView;
import com.hzw.baselib.util.AwConvertUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzw on 2019/6/27.
 */

public class BarChartPositiveNegativeCommonHelper {

    private static final int OFFSET_LEFT = 15;
    private static final int OFFSET_TOP = 0;
    private static final int OFFSET_RIGTH = 15;
    private static final int OFFSET_BOTTOM = 10;

    /**
     * 设置正负值在0轴上下方的柱图
     *
     * @param barChart
     * @param xAxisValue x轴的值。必须与yAxisValue的值个数相同
     * @param yAxisValue y轴的值。必须与xAxisValue的值个数相同
     * @param title
     */
    public static void setPositiveNegativeBarChart(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue, String title) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);//设置描述
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        //设置一页最大显示个数为20，超出部分就滑动
        float ratio = (float) xAxisValue.size() / (float) 10;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        barChart.zoom(ratio, 1f, 0, 0);
        //设置自定义的markerView
        ValueFormatter xAxisValueFormatter = new StringValueFormatter(xAxisValue);
        CommonStrEnd0MarkerView markerView = new CommonStrEnd0MarkerView(barChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter, "分");
        barChart.setMarker(markerView);
        // scaling can now only be done on x- and y-axis separately
        barChart.setDrawGridBackground(false);
        barChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);
//        MPChartMarkerView markerView = new MPChartMarkerView(barChart.getContext(), R.layout.custom_marker_view);
//        barChart.setMarker(markerView);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xAxisValue.size());
        xAxis.setLabelCount(xAxisValue.size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setTextSize(10);
        xAxis.setTextColor(Color.parseColor("#999999"));

        YAxis left = barChart.getAxisLeft();
        left.setDrawLabels(true);
//        left.setSpaceTop(25f);
//        left.setSpaceBottom(25f);
        left.setDrawAxisLine(true);
//        left.setDrawGridLines(false);
        left.setAxisLineWidth(0); //设置线宽度
        left.setAxisLineColor(Color.TRANSPARENT);
        left.setDrawZeroLine(true); // draw a zero line
        left.setZeroLineColor(Color.DKGRAY);
        left.setZeroLineWidth(1f);
        left.enableGridDashedLine(10f, 10f, 0f);  //设置Y轴网格线条的虚线，参1 实线长度，参2 虚线长度 ，参3 周期
        left.setGridColor(Color.parseColor("#e6e6e6"));  //网格线条的颜色
        left.setTextSize(10);
        left.setTextColor(Color.parseColor("#999999"));
        //left.setValueFormatter(new CommonStrFormatter(2,  AwBaseConstant.COMMON_SUFFIX_RATIO));
        barChart.getAxisRight().setEnabled(false);

        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setFormSize(0f);
        legend.setTextSize(16f);
        legend.setYOffset(-2f);


        // THIS IS THE ORIGINAL DATA YOU WANT TO PLOT
        final List<Data> data = new ArrayList<>();
        for (int i = 0, n = xAxisValue.size(); i < n; ++i) {
            data.add(new Data(0.5f + i, yAxisValue.get(i), xAxisValue.get(i)));
        }

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return data.get(Math.min(Math.max((int) value, 0), data.size() - 1)).xAxisValue;
            }
        });

        barChart.setScaleEnabled(false);//设置能否缩放
        setData(barChart, data, title, xAxisValue.size());
    }

    private static void setData(BarChart barChart, List<Data> dataList, String title, int size) {

        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        List<Integer> colors = new ArrayList<Integer>();

        int green = barChart.getContext().getResources().getColor(R.color.color_chart_669900);
        int red = barChart.getContext().getResources().getColor(R.color.color_chart_FF0000);

        for (int i = 0; i < dataList.size(); i++) {

            Data d = dataList.get(i);
            BarEntry entry = new BarEntry(d.xValue, d.yValue);
            values.add(entry);

            // specific colors
            if (d.yValue >= 0)
                colors.add(green);
            else
                colors.add(red);
        }

        BarDataSet set;

        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, title);
            set.setColors(colors);
            set.setValueTextColors(colors);
            BarData data = new BarData(set);
            data.setValueTextSize(10f);
            set.setDrawValues(false);//隐藏柱子顶部文字或数字说明

            //  data.setValueFormatter(new PositiveNegativeBarChartValueFormatter());
            data.setBarWidth(ChartWidthUtil.getBarWidth(size));
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    return AwConvertUtil.double2String(value, 2);
                }
            });

            barChart.setData(data);
            barChart.invalidate();
        }
    }

    /**
     * positive-negative data model representing data.
     */
    private static class Data {

        public String xAxisValue;
        public float yValue;
        public float xValue;

        public Data(float xValue, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xValue = xValue;
        }
    }

    private static class PositiveNegativeBarChartValueFormatter implements IValueFormatter {

        private DecimalFormat mFormattedStringCache;

        public PositiveNegativeBarChartValueFormatter() {
            mFormattedStringCache = new DecimalFormat("######.00");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormattedStringCache.format(value);
        }
    }
}
