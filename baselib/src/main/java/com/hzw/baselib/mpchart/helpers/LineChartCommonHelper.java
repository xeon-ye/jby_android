package com.hzw.baselib.mpchart.helpers;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hzw.baselib.R;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.mpchart.formatters.CommonStrFormatter;
import com.hzw.baselib.mpchart.formatters.StringValueFormatter;
import com.hzw.baselib.mpchart.formatters.ValueFormatter;
import com.hzw.baselib.mpchart.makerviews.CommonStrEnd0MarkerView;
import com.hzw.baselib.mpchart.makerviews.PercentMarkerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * https://blog.csdn.net/u014289337/article/details/78810185
 * Created by hzw on 2019/6/4.
 */

public class LineChartCommonHelper {

    private static final int OFFSET_LEFT = 15;
    private static final int OFFSET_TOP = -20;
    private static final int OFFSET_RIGTH = 15;
    private static final int OFFSET_BOTTOM = 10;

    public static void setData(LineChart lineChart) {
        lineChart.getDescription().setEnabled(false);
        //1.设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            entries.add(new Entry(i, new Random().nextInt(300)));

        LineDataSet dataSet = new LineDataSet(entries, "类别"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#ff5500"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#ff5500"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置

        //3.chart设置数据
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

        lineChart.animateY(2000);//动画效果，MPAndroidChart中还有很多动画效果可以挖掘
    }

    public static void setMoreLineChart(LineChart lineChart, List<String> xAxisValue, LinkedHashMap<String, List<Float>> map, List<Integer> colors, boolean percentage, String topSuffix) {
        //创建描述信息
        Description description = new Description();
        description.setText("测试图表");
        description.setTextColor(Color.RED);
        description.setTextSize(20);
        lineChart.setDescription(description);//设置图表描述信息
        //设置一页最大显示个数，超出部分就滑动
        float ratio = (float) xAxisValue.size()/(float) 7;
        //显示的时候是按照多大的比率缩放显示,1f表示不放大缩小
        lineChart.zoom(ratio,1f,0,0);


        lineChart.getDescription().setEnabled(false);//设置描述不显示
        lineChart.setNoDataText("没有数据");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线
        //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
        //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
        //lineChart.setLogEnabled(true);//打印日志
        //lineChart.notifyDataSetChanged();//刷新数据
        //lineChart.invalidate();//重绘

        //设置与图标交互
        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
        lineChart.setScaleXEnabled(false); //是否可以缩放 仅x轴
        lineChart.setScaleYEnabled(false); //是否可以缩放 仅y轴
        lineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        lineChart.setDoubleTapToZoomEnabled(false);//设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setHighlightPerDragEnabled(false);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        lineChart.setDragDecelerationEnabled(false);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        lineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。

        //设置自定义的markerView
        ValueFormatter xAxisValueFormatter = new StringValueFormatter(xAxisValue);
        if(percentage) {
            PercentMarkerView markerView = new PercentMarkerView(lineChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter);
            lineChart.setMarker(markerView);
        } else {
            CommonStrEnd0MarkerView markerView = new CommonStrEnd0MarkerView(lineChart.getContext(), R.layout.custom_marker_view, xAxisValueFormatter, topSuffix);
            lineChart.setMarker(markerView);
        }
        lineChart.setExtraOffsets(OFFSET_LEFT, OFFSET_TOP, OFFSET_RIGTH, OFFSET_BOTTOM);

        //设置X轴显示效果
        //获取此图表的x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(false);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        //xAxis.setTextSize(20f);//设置字体
        //xAxis.setTextColor(Color.BLACK);//设置字体颜色
        //设置竖线的显示样式为虚线
        //lineLength控制虚线段的长度
        //spaceLength控制线之间的空间
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMinimum(0f);//设置x轴的最小值
//        xAxis.setAxisMaximum(xAxisValue.size());//设置最大值
        //将X轴的值显示在中央
//        xAxis.setCenterAxisLabels(true);
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图  表或屏幕的边缘
//        xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
        //设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(xAxisValue.size(), true);
        xAxis.setTextColor(Color.parseColor("#999999"));//设置轴标签的颜色
        xAxis.setTextSize(10f);//设置轴标签的大小
        //xAxis.setGridLineWidth(10f);//设置竖线大小
        //xAxis.setGridColor(Color.RED);//设置竖线颜色
        //xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
        //xAxis.setAxisLineWidth(5f);//设置x轴线宽度
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));

        //设置Y轴显示效果, Y轴默认显示左右两个轴线
        //获取右边的轴线
        YAxis rightAxis=lineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //获取左边的轴线
        YAxis leftAxis = lineChart.getAxisLeft();
        //设置网格线为虚线效果
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisLineWidth(0); //设置线宽度
        leftAxis.setAxisLineColor(Color.TRANSPARENT);
        if(percentage) { //百分比时, 添加百分比符号
            leftAxis.setValueFormatter(new CommonStrFormatter(2, "%"));
        }
        leftAxis.setTextSize(10);
        leftAxis.setTextColor(Color.parseColor("#999999"));
        //设置坐标轴最大最小值
        leftAxis.setAxisMinimum(0);

        //设置图例
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setWordWrapEnabled(true);//是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        legend.setTextSize(10f);//设置文字大小
        legend.setForm(Legend.LegendForm.CIRCLE);//正方形，圆形或线
        legend.setFormSize(10f); // 设置Form的大小
        legend.setFormLineWidth(10f);//设置Form的宽度
        legend.setXEntrySpace(5f);//设置距离饼图的距离，防止与饼图重合
        legend.setYEntrySpace(5f);//设置距离饼图的距离，防止与饼图重合
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

//        setMoreLineChartData(lineChart);
        setMoreLineChartData(lineChart, map, colors);
    }

    private static void setMoreLineChartData(LineChart lineChart, LinkedHashMap<String, List<Float>> map, List<Integer> colors) {
        int currentPosition = 0;//用于柱状图颜色集合的index
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (Map.Entry<String, List<Float>> entry : map.entrySet()) {
            ArrayList<Entry> entries = new ArrayList<>();
            String title =  entry.getKey();
            List<Float> yAxisValue = entry.getValue();//4个
            AwLog.d("迭代title: " + title);
            for (int i=0; i < yAxisValue.size(); i++) {
                AwLog.d("迭代title y: " + yAxisValue.get(i));
                entries.add(new Entry(i, yAxisValue.get(i)));
            }
            LineDataSet dataset = new LineDataSet(entries, title);
            dataset.setColor(colors.get(currentPosition));
            dataset.setDrawValues(false);//隐藏柱子顶部文字或数字说明
            dataset.setColor(colors.get(currentPosition));
            dataset.setCircleColor(Color.BLACK);
            dataset.setLineWidth(1f);//设置线宽  1
            dataset.setCircleRadius(3f);//设置焦点圆心的大小
            dataset.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            dataset.setHighlightLineWidth(3f);//设置点击交点后显示高亮线宽
            dataset.setHighlightEnabled(true);//是否禁用点击高亮线
            dataset.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            dataset.setValueTextSize(9f);//设置显示值的文字大小   9   0不显示数字
            dataset.setDrawFilled(false);//设置禁用范围背景填充
            dataset.setDrawCircles(true);//图表上的数据点是否用小圆圈表示
            dataset.setMode(LineDataSet.Mode.LINEAR);  //CUBIC_BEZIER曲线  HORIZONTAL_BEZIER水平线  LINEAR线性  STEPPED走线
            dataSets.add(dataset);
            currentPosition++;
        }
        //创建LineData对象 属于LineChart折线图的数据集合
        LineData data = new LineData(dataSets);
        // 添加到图表中
        lineChart.setData(data);
        data.setValueTextSize(10f);
        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return AwConvertUtil.double2String(value, 2);
            }
        });


        //绘制图表
        lineChart.invalidate();
    }


    private static void setMoreLineChartData(LineChart lineChart) {
        /**
         * Entry 坐标点对象  构造函数 第一个参数为x点坐标 第二个为y点
         */
        ArrayList<Entry> values1 = new ArrayList<>();//CUBIC_BEZIER曲线
        ArrayList<Entry> values2 = new ArrayList<>();//HORIZONTAL_BEZIER水平线
        ArrayList<Entry> values3 = new ArrayList<>();//LINEAR线性
        ArrayList<Entry> values4 = new ArrayList<>();//STEPPED走线

        values1.add(new Entry(4, 5));
        values1.add(new Entry(6, 10));
        values1.add(new Entry(9, 20));
        values1.add(new Entry(12, 15));
        values1.add(new Entry(15, 3));

        values2.add(new Entry(3, 30));
        values2.add(new Entry(6, 50));
        values2.add(new Entry(9, 45));
        values2.add(new Entry(12, 35));
        values2.add(new Entry(15, 31));

        values3.add(new Entry(3, 60));
        values3.add(new Entry(6, 80));
        values3.add(new Entry(9, 65));
        values3.add(new Entry(12, 75));
        values3.add(new Entry(15, 68));

        values4.add(new Entry(3, 90));
        values4.add(new Entry(6, 130));
        values4.add(new Entry(9, 95));
        values4.add(new Entry(12, 105));
        values4.add(new Entry(15, 125));

        //LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;
        LineDataSet set4;

        //判断图表中原来是否有数据
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            //获取数据1
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set2.setValues(values2);
            set3 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set3.setValues(values3);
            set4 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
            set4.setValues(values4);
            //刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1, "测试数据1");
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);//设置线宽  1
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(true);//是否禁用点击高亮线
            set1.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(9f);//设置显示值的文字大小   9   0不显示数字
            set1.setDrawFilled(false);//设置禁用范围背景填充
            set1.setDrawCircles(false);//图表上的数据点是否用小圆圈表示

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);  //CUBIC_BEZIER曲线  HORIZONTAL_BEZIER水平线  LINEAR线性  STEPPED走线

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            });
//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);//设置范围背景填充
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }
            //设置数据2
            set2 = new LineDataSet(values2, "测试数据2");
            set2.setColor(Color.GRAY);
            set2.setCircleColor(Color.GRAY);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setValueTextSize(10f);
            set2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);  //CUBIC_BEZIER曲线  HORIZONTAL_BEZIER水平线  LINEAR线性  STEPPED走线
            //设置数据3
            set3 = new LineDataSet(values3, "测试数据3");
            set3.setColor(Color.GREEN);
            set3.setCircleColor(Color.GREEN);
            set3.setLineWidth(1f);
            set3.setCircleRadius(3f);
            set3.setValueTextSize(10f);
            set3.setMode(LineDataSet.Mode.LINEAR);  //CUBIC_BEZIER曲线  HORIZONTAL_BEZIER水平线  LINEAR线性  STEPPED走线
            //设置数据4
            set4 = new LineDataSet(values4, "测试数据4");
            set4.setColor(Color.YELLOW);
            set4.setCircleColor(Color.YELLOW);
            set4.setLineWidth(1f);
            set4.setCircleRadius(3f);
            set4.setValueTextSize(10f);
            set4.setMode(LineDataSet.Mode.STEPPED);  //CUBIC_BEZIER曲线  HORIZONTAL_BEZIER水平线  LINEAR线性  STEPPED走线
            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            set1.setDrawValues(false);//隐藏顶部文字或数字说明
            set2.setDrawValues(false);
            set3.setDrawValues(false);
            set4.setDrawValues(false);
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);
            dataSets.add(set3);
            dataSets.add(set4);
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            lineChart.setData(data);
            //绘制图表
            lineChart.invalidate();
        }
    }

}
