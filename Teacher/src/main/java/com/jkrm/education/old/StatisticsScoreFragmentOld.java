package com.jkrm.education.old;

import android.os.Handler;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.mpchart.helpers.BarChartCommonMoreYHelper;
import com.hzw.baselib.mpchart.helpers.CombineChartCommonMoreYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.LineChartCommonHelper;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.rx.RxStatisticsClassesType;
import com.jkrm.education.bean.rx.RxStatisticsStudentType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.old.ClassesStudentChoiceActivity;
import com.jkrm.education.ui.activity.ClassesChoiceActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/29.
 */

public class StatisticsScoreFragmentOld extends AwMvpFragment {

    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_persons)
    TextView mTvPersons;
    @BindView(R.id.lineChart)
    LineChart mLineChart;
    @BindView(R.id.barchart2)
    BarChart mBarchart2;
    @BindView(R.id.lineChart2)
    LineChart mLineChart2;
    @BindView(R.id.combinedChartRank)
    CombinedChart mCombinedChartRank;

    private List<TeacherClassBean> mTeacherClassBeanList = new ArrayList<>();
    private List<ClassStudentBean> mClassStudentBeanList = new ArrayList<>();

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_score_old;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        setLineChart();
        setBarchart2Data();
        setLineChart2();
        setCombinedChartRankData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxStatisticsClassesType type) {
        if(type == null)
            return;
        if(MyConstant.LocalConstant.TAG_STATISTICS_SCORE == type.getTag()) {
            //TODO 更新报表数据
            mTeacherClassBeanList = type.getTeacherList();
            AwLog.d("全局通知, 更新成绩统计报表数据, 当前班级数量: " + mTeacherClassBeanList.size()) ;

            //TODO 测试更新
            setCombinedChartRankData();
        } else {
            AwLog.d("全局通知, 不更新成绩统计报表数据, 非成绩统计页面进入选择班级") ;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxStatisticsStudentType type) {
        if(type == null)
            return;
        if(MyConstant.LocalConstant.TAG_STATISTICS_SCORE == type.getTag()) {
            //TODO 更新报表数据
            mClassStudentBeanList = type.getStudentList();
            AwLog.d("全局通知, 更新成绩统计报表数据, 当前学生数量: " + mClassStudentBeanList.size()) ;
        } else {
            AwLog.d("全局通知, 不更新成绩统计报表数据, 非成绩统计页面进入选择学生") ;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvClasses.setOnClickListener(v -> toClass(ClassesChoiceActiviity.class, false,
                Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_SCORE,
                Extras.KEY_BEAN_TEACHER_CLASSES_LIST, mTeacherClassBeanList));
        mTvPersons.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(mTeacherClassBeanList)) {
                showDialog("请先选择班级");
                return;
            }
            StringBuffer sb = new StringBuffer();
            for(TeacherClassBean temp : mTeacherClassBeanList) {
                sb.append(temp.getClassId()).append(",");
            }
            toClass(ClassesStudentChoiceActivity.class, false,
                    Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_SCORE,
                    Extras.COMMON_PARAMS2, sb.toString().substring(0, sb.toString().length() - 1),
                    Extras.KEY_BEAN_CLASSES_STUDENT_LIST, mClassStudentBeanList);
        });
    }

    private void setBarchart2Data() {
        List<String> xAxisValues = new ArrayList<>();
        int totalColumn = 6;

        for(int i=1; i<totalColumn + 1; i++) {
            xAxisValues.add("6月" + i + "日");
        }



        List<Float> yAxisValues1 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues2 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues3 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues3.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues4 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues4.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues5 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues5.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues6 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues6.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        map.put("初一1班", yAxisValues1);
        map.put("初一2班", yAxisValues2);
        map.put("初一3班", yAxisValues3);
        map.put("初一4班", yAxisValues4);
        map.put("初一5班", yAxisValues5);
        map.put("初一6班", yAxisValues6);
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart2, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), true, "");
    }

    private void setLineChart() {
        List<String> xAxisValues = new ArrayList<>();

        for(int i=1; i<6; i++) {
            xAxisValues.add("6月" + i + "日");
        }

        int totalColumn = 5;

        List<Float> yAxisValues1 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues2 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues3 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues3.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues4 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues4.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues5 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues5.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        map.put("初一1班", yAxisValues1);
        map.put("初一2班", yAxisValues2);
        map.put("初一3班", yAxisValues3);
        map.put("初一4班", yAxisValues4);
        LineChartCommonHelper.setMoreLineChart(mLineChart, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), true, "");
    }

    private void setLineChart2() {
        List<String> xAxisValues = new ArrayList<>();

        for(int i=1; i<6; i++) {
            xAxisValues.add("6月" + i + "日");
        }

        int totalColumn = 5;

        List<Float> yAxisValues1 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues2 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues3 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues3.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues4 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues4.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        List<Float> yAxisValues5 = new ArrayList<>();
        for(int i=0; i< totalColumn; i++) {
            yAxisValues5.add(Float.valueOf(RandomValueUtil.getNum(1, 100)));
        }
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        map.put("初一1班", yAxisValues1);
        map.put("初一2班", yAxisValues2);
        map.put("初一3班", yAxisValues3);
        map.put("初一4班", yAxisValues4);
        LineChartCommonHelper.setMoreLineChart(mLineChart2, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), true, "");
    }

    private void setCombinedChartRankData() {
        //TODO, 有问题, 刷新数据每页大小异常. 需调试
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues1 = new ArrayList<>();
        List<Float> yAxisValues2 = new ArrayList<>();
        List<Float> yAxisValuesLine1 = new ArrayList<>();
        List<Float> yAxisValuesLine2 = new ArrayList<>();
        LinkedHashMap<String, List<Float>> map = new LinkedHashMap<>();
        LinkedHashMap<String, List<Float>> mapLine = new LinkedHashMap<>();
        map.put("本段人数", yAxisValues1);
        map.put("累计人数", yAxisValues2);
        mapLine.put("本段占比", yAxisValuesLine1);
        mapLine.put("累计占比", yAxisValuesLine2);
        CombineChartCommonMoreYCustomSpaceHelper.setMoreBarChart(mCombinedChartRank, xAxisValues, map, mapLine,
                MyDateUtil.getChartColorsList(mActivity), MyDateUtil.getChartLineColorsList(mActivity), false, true, "", 8, false, 0f, 0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xAxisValues.add("初一(1)班");
                xAxisValues.add("初一(2)班");
                xAxisValues.add("初一(3)班");
                xAxisValues.add("初一(4)班");
                xAxisValues.add("初一(5)班");
                xAxisValues.add("初一(6)班");

                yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues1.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));

                yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));
                yAxisValues2.add(Float.valueOf(RandomValueUtil.getNum(1, 10)));

                map.put("本段人数", yAxisValues1);
                map.put("累计人数", yAxisValues2);


                for(int i=1; i< 7; i++) {
                    yAxisValuesLine1.add(Float.valueOf(RandomValueUtil.getNum(1, 80)));
                }
                for(int i=1; i< 7; i++) {
                    yAxisValuesLine2.add(Float.valueOf(RandomValueUtil.getNum(1, 80)));
                }
                mapLine.put("本段占比", yAxisValuesLine1);
                mapLine.put("累计占比", yAxisValuesLine2);
                CombineChartCommonMoreYCustomSpaceHelper.setMoreBarChart(mCombinedChartRank, xAxisValues, map, mapLine,
                        MyDateUtil.getChartColorsList(mActivity), MyDateUtil.getChartLineColorsList(mActivity), false, true, "", 8, false, 0f, 0f);
            }
        }, 500);
    }
}
