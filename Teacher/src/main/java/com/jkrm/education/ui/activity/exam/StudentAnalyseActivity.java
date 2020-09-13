package com.jkrm.education.ui.activity.exam;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.LevelAdapter;
import com.jkrm.education.adapter.exam.LevelGridViewAdapter;
import com.jkrm.education.mvp.presenters.StudentAnalysisPresent;
import com.jkrm.education.mvp.views.StudentAnalysisView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAnalyseActivity extends AwMvpActivity<StudentAnalysisPresent> implements StudentAnalysisView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar toolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.rcv_level)
    RecyclerView rcvLevel;
    @BindView(R.id.gv_level)
    GridView gvLevel;
    @BindView(R.id.gv_course)
    GridView gvCourse;
    @BindView(R.id.line_chart)
    LineChart lineChart;
    @BindView(R.id.barchart)
    BarChart barchart;
    private LevelAdapter levelAdapter;
    private List<MarkBean> markBeans = new ArrayList<>();
    private List<MarkBean> courseBeans = new ArrayList<>();
    private LevelGridViewAdapter levelGridViewAdapter = new LevelGridViewAdapter();
    private LevelGridViewAdapter courseGridViewAdapter = new LevelGridViewAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_student_analyse;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImg("单个学生分析", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        levelAdapter = new LevelAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, rcvLevel, levelAdapter, 5);
        markBeans.add(new MarkBean(true, "班级层次"));
        markBeans.add(new MarkBean(false, "校级层次"));
        levelGridViewAdapter.setMarkBeanList(markBeans);
        levelGridViewAdapter.setContext(StudentAnalyseActivity.this);
        courseBeans.add(new MarkBean(true, "总分"));
        courseBeans.add(new MarkBean(false, "生物"));
        courseBeans.add(new MarkBean(false, "化学"));
        courseBeans.add(new MarkBean(false, "语文"));
        courseBeans.add(new MarkBean(false, "数学"));
        courseBeans.add(new MarkBean(false, "政治"));
        courseBeans.add(new MarkBean(false, "历史"));
        courseBeans.add(new MarkBean(false, "英语"));
        courseBeans.add(new MarkBean(false, "地理"));
        gvLevel.setAdapter(levelGridViewAdapter);
        courseGridViewAdapter.setMarkBeanList(courseBeans);
        courseGridViewAdapter.setContext(StudentAnalyseActivity.this);
        gvCourse.setAdapter(courseGridViewAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        levelAdapter.addData(new MarkBean(true, "班级层次"));
        levelAdapter.addData(new MarkBean(false, "校级层次  "));
        setData();
        setBarChartData();
    }

    private void setBarChartData() {

        List<BarEntry>list;
        List<BarEntry>list2;
        List<BarEntry>list3;
        list=new ArrayList<>();
        list2=new ArrayList<>();
        list3=new ArrayList<>();
//为第一组添加数据
        list.add(new BarEntry(1,5));
        list.add(new BarEntry(2,8));
        list.add(new BarEntry(3,4));
        list.add(new BarEntry(4,3));
        list.add(new BarEntry(5,9));

        //为第二组添加数据
        list2.add(new BarEntry(1,6));
        list2.add(new BarEntry(2,8));
        list2.add(new BarEntry(3,5));
        list2.add(new BarEntry(4,3));
        list2.add(new BarEntry(5,4));
        list3.add(new BarEntry(1,5));
        list3.add(new BarEntry(2,4));
        list3.add(new BarEntry(3,3));
        list3.add(new BarEntry(4,8));
        list3.add(new BarEntry(5,6));

        BarDataSet barDataSet=new BarDataSet(list,"男");
        barDataSet.setColor(Color.RED);    //为第一组柱子设置颜色
        BarDataSet barDataSet2=new BarDataSet(list2,"女");
        barDataSet2.setColor(Color.BLUE);   //为第二组柱子设置颜色
        BarDataSet barDataSet3=new BarDataSet(list3,"女");
        barDataSet3.setColor(Color.BLUE);   //为第二组柱子设置颜色
        BarData barData=new BarData(barDataSet);   //加上第一组

        barData.addDataSet(barDataSet2);    //加上第二组   （多组也可以用同样的方法）
        barData.addDataSet(barDataSet3);    //加上第二组   （多组也可以用同样的方法）
        barchart.setData(barData);

        barData.setBarWidth(0.2f);//柱子的宽度
        //重点！   三个参数   分别代表   X轴起点     组与组之间的间隔      组内柱子的间隔
        barData.groupBars(1f,0.2f,0);

        barchart.getXAxis().setCenterAxisLabels(true);   //设置柱子（柱子组）居中对齐X轴上的点


        barchart.getXAxis().setAxisMaximum(6);   //X轴最大数值
        barchart.getXAxis().setAxisMinimum(1);   //X轴最小数值
        //X轴坐标的个数    第二个参数一般填false     true表示强制设置标签数 可能会导致X轴坐标显示不全等问题
        barchart.getXAxis().setLabelCount(5,false);
        barchart.getDescription().setEnabled(false);    //右下角一串英文字母不显示
        barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);   //X轴的位置设置为下  默认为上
        barchart.getAxisRight().setEnabled(false);
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("3");
        strings.add("4");
        strings.add("3");
        XAxis xAxis = barchart.getXAxis();
        xAxis.setGranularity(1);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return strings.get((int) (value % strings.size()));
            }
        });
        /*List<BarEntry> entries = new ArrayList<BarEntry>();
        List<BarEntry> entries2 = new ArrayList<BarEntry>();
        List<BarEntry> entries3 = new ArrayList<BarEntry>();

        for (int i = 0; i < 20; i++) {
            // turn your data into Entry objects
            entries.add(new BarEntry(i, 20 * i));
            entries2.add(new BarEntry(i,30 * i));
            entries3.add(new BarEntry(i,10 * i));
        }

        //创建数据集
        BarDataSet dataSet = new BarDataSet(entries, "BarDataSet1"); // add entries to dataset
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        BarDataSet dataSet2 = new BarDataSet(entries2, "BarDataSet2"); // add entries to dataset
        BarDataSet dataSet3 = new BarDataSet(entries2, "BarDataSet2"); // add entries to dataset
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置数据显示颜色：柱子颜色
        dataSet2.setColor(Color.RED);
        dataSet3.setColor(Color.RED);
        dataSet2.setBarBorderColor(Color.BLUE);
        dataSet3.setBarBorderColor(Color.BLUE);
        float groupSpace=1f;
        float barSpace=0.2f;
        float barWidth=0.45f;


        //柱状图数据集
        BarData data = new BarData(dataSet,dataSet2,dataSet3   );
        //设置柱子宽度
        data.setBarWidth(barWidth);
        List<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        //添加数据
        dataSets.add(dataSet);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);
        barchart.setData(data);//装载数据
        barchart.groupBars(0f,groupSpace,barSpace);
        barchart.invalidate();//刷新
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("3");
        strings.add("4");
        strings.add("3");
        XAxis xAxis = barchart.getXAxis();
        xAxis.setGranularity(1);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return strings.get((int) (value % strings.size()));
            }
        });*/
    }

    @Override
    protected void initListener() {
        super.initListener();
        levelAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_title:
                        List<MarkBean> data = adapter.getData();
                        for (int i = 0; i < data.size(); i++) {
                            if (i == position) {
                                data.get(i).setSelect(true);
                            } else {
                                data.get(i).setSelect(false);
                            }
                        }
                        levelAdapter.notifyDataSetChanged();
                        break;
                }


            }
        });
        gvLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < markBeans.size(); i++) {
                    if (i == position) {
                        markBeans.get(i).setSelect(true);
                    } else {
                        markBeans.get(i).setSelect(false);
                    }
                    levelGridViewAdapter.notifyDataSetChanged();
                }
            }
        });
        gvCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < courseBeans.size(); i++) {
                    if (i == position) {
                        courseBeans.get(i).setSelect(true);
                    } else {
                        courseBeans.get(i).setSelect(false);
                    }
                    courseGridViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected StudentAnalysisPresent createPresenter() {
        return new StudentAnalysisPresent(this);
    }

    @Override
    public void getColumnDataSuccess() {

    }

    @Override
    public void getColumnDataFail(String msg) {

    }

    @Override
    public void getLineDataSuccess() {

    }

    @Override
    public void getLineDataFail(String msg) {

    }

    private void setData() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        //1.设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        List<Entry> entrie2 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            entries.add(new Entry(i, new Random().nextInt(300)));
            entrie2.add(new Entry(i, new Random().nextInt(500)));
        }
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("3");
        strings.add("4");
        strings.add("3");

        LineDataSet dataSet = new LineDataSet(entries, "类别"); // add entries to dataset
        LineDataSet dataSet2 = new LineDataSet(entrie2, "2"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#ff5500"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#ff5500"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        dataSet2.setColor(Color.parseColor("#ff5500"));//线条颜色
        dataSet2.setCircleColor(Color.parseColor("#ff5500"));//圆点颜色
        dataSet2.setLineWidth(1f);//线条宽度


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return strings.get((int) (value % strings.size()));
            }
        });
//使用接口ILineDataSet
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        //添加数据
        dataSets.add(dataSet);
        dataSets.add(dataSet2);
        LineData data = new LineData(dataSets);
        //设置图表
        lineChart.setData(data);


        lineChart.invalidate(); // refresh

        lineChart.animateY(2000);//动画效果，MPAndroidChart中还有很多动画效果可以挖掘
        lineChart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);//设置注解的位置在左上方
        lineChart.getXAxis().setDrawGridLines(false);//不显示网格
        lineChart.getAxisLeft().setDrawGridLines(false);//不设置Y轴网格

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}