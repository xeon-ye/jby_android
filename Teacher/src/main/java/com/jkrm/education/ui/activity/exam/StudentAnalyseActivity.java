package com.jkrm.education.ui.activity.exam;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.LevelAdapter;
import com.jkrm.education.adapter.exam.LevelGridViewAdapter;
import com.jkrm.education.bean.exam.ColumnDataBean;
import com.jkrm.education.bean.exam.LineDataBean;
import com.jkrm.education.bean.exam.OverViewBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.StudentAnalysisPresent;
import com.jkrm.education.mvp.views.StudentAnalysisView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

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
    @BindView(R.id.tv_myScore)
    TextView mTvMyScore;
    @BindView(R.id.tv_gradeBeatNum)
    TextView mTvGradeBeatNum;
    @BindView(R.id.tv_gradeRank)
    TextView mTvGradeRank;
    @BindView(R.id.tv_classAvgScore)
    TextView mTvClassAvgScore;
    @BindView(R.id.tv_gradeMaxScore)
    TextView mTvGradeMaxScore;
    private LevelAdapter levelAdapter;
    private List<MarkBean> markBeans = new ArrayList<>();
    private List<MarkBean> courseBeans = new ArrayList<>();
    private LevelGridViewAdapter levelGridViewAdapter = new LevelGridViewAdapter();
    private LevelGridViewAdapter courseGridViewAdapter = new LevelGridViewAdapter();
    String EXAM_ID;
    String STUDENT_ID;
    private final int CLASS_TYPE = 0, SCHOOL_TYPE = 1;
    private List<ColumnDataBean> mColumnDataBeans;

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
        /*courseBeans.add(new MarkBean(false, "生物"));
        courseBeans.add(new MarkBean(false, "化学"));
        courseBeans.add(new MarkBean(false, "语文"));
        courseBeans.add(new MarkBean(false, "数学"));
        courseBeans.add(new MarkBean(false, "政治"));
        courseBeans.add(new MarkBean(false, "历史"));
        courseBeans.add(new MarkBean(false, "英语"));
        courseBeans.add(new MarkBean(false, "地理"));*/
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
        EXAM_ID = getIntent().getExtras().getString(Extras.EXAM_ID);
        STUDENT_ID = getIntent().getExtras().getString(Extras.STUDENT_ID);
        //添加Roleld
        mPresenter.getColumnData(RequestUtil.getReportFormColumnar(UserUtil.getRoleld(), EXAM_ID, STUDENT_ID));
        mPresenter.getLineData(RequestUtil.getReportForm(EXAM_ID, STUDENT_ID));
        mPresenter.getOverView(RequestUtil.getReportForm(EXAM_ID, STUDENT_ID));
    }

    private void setBarChartData(List<ColumnDataBean> columnDataBeans, int type) {

        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < columnDataBeans.size(); i++) {
            if (type == CLASS_TYPE) {
                values1.add(new BarEntry(i, Float.parseFloat(columnDataBeans.get(i).getMyScore())));
                values2.add(new BarEntry(i, Float.parseFloat(columnDataBeans.get(i).getClassMaxScore())));
                values3.add(new BarEntry(i, Float.parseFloat(columnDataBeans.get(i).getClassAvgScore())));
            } else if (type == SCHOOL_TYPE) {
                values1.add(new BarEntry(i, Float.parseFloat(columnDataBeans.get(i).getMyScore())));
                values2.add(new BarEntry(i, Float.parseFloat(columnDataBeans.get(i).getGradeMaxScore())));
                values3.add(new BarEntry(i, Float.parseFloat(columnDataBeans.get(i).getClassAvgScore())));
            }

            strings.add(columnDataBeans.get(i).getCourseName());
        }

        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(values1, "我的成绩");
        set1.setColor(Color.rgb(10, 147, 252));
        set2 = new BarDataSet(values2, "最高分");
        set2.setColor(Color.rgb(255, 44, 21));
        set3 = new BarDataSet(values3, "平均分");
        set3.setColor(Color.rgb(109, 212, 0));

        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new LargeValueFormatter());
        barchart.getXAxis().setAxisMinimum(0);
        XAxis xAxis = barchart.getXAxis();
        xAxis.setGranularity(1f);
        barchart.getLegend().setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);//设置注解的位置在左上方
        barchart.getXAxis().setDrawGridLines(false);//不显示网格
        barchart.getAxisLeft().setDrawGridLines(false);//不设置Y轴网格
       /* strings.add("总分");
        strings.add("生物");
        strings.add("化学");
        strings.add("语文");
        strings.add("数学");
        strings.add("政治");
        strings.add("历史");
        strings.add("英语");
        strings.add("地理");*/
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return strings.get((int) (value % strings.size()));
            }
        });
        barchart.setData(data);
        barchart.getBarData().setBarWidth(0.05f);
        barchart.groupBars(0, 0.8f, 0);
        barchart.invalidate();
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
                    if (position == 0) {
                        setBarChartData(mColumnDataBeans, CLASS_TYPE);
                    } else {
                        setBarChartData(mColumnDataBeans, SCHOOL_TYPE);
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
    public void getOverViewSuccess(OverViewBean overViewBean) {
        mTvMyScore.setText(overViewBean.getMyScore());
        mTvClassAvgScore.setText(overViewBean.getClassAvgScore());
        mTvGradeMaxScore.setText(overViewBean.getGradeMaxScore());
        mTvGradeBeatNum.setText(overViewBean.getClassBeatNum());
        mTvGradeRank.setText("/" + overViewBean.getGradeRank());
    }

    @Override
    public void getOverViewFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getColumnDataSuccess(List<ColumnDataBean> data) {
        mColumnDataBeans = data;
        setBarChartData(mColumnDataBeans, CLASS_TYPE);

    }


    @Override
    public void getColumnDataFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getLineDataSuccess(List<LineDataBean> data) {
        setData(data);
    }

    @Override
    public void getLineDataFail(String msg) {
        showMsg(msg);
    }

    private void setData(List<LineDataBean> lineDataBeans) {
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);
        //1.设置x轴和y轴的点
        List<Entry> entries = new ArrayList<>();
        List<Entry> entrie2 = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        for (int i = 0; i < lineDataBeans.size(); i++) {
            entries.add(new Entry(i, Float.parseFloat(lineDataBeans.get(i).getClassAvgScore())));
            entrie2.add(new Entry(i, Float.parseFloat(lineDataBeans.get(i).getMyScore())));
            strings.add(lineDataBeans.get(i).getExamName());
            //entrie2.add(new Entry(i, new Random().nextInt(500)));
        }


        LineDataSet dataSet = new LineDataSet(entries, "个人得分"); // add entries to dataset
        LineDataSet dataSet2 = new LineDataSet(entrie2, "班级平均分"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#0C94FC"));//线条颜色
        dataSet.setCircleColor(Color.parseColor("#0C94FC"));//圆点颜色
        dataSet.setLineWidth(1f);//线条宽度
        dataSet2.setColor(Color.parseColor("#95E046"));//线条颜色
        dataSet2.setCircleColor(Color.parseColor("#95E046"));//圆点颜色
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
        lineChart.getAxisRight().setEnabled(false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}