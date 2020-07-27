package com.jkrm.education.old;

import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.mpchart.helpers.BarChartCommonMoreYHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYHelper;
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
import com.jkrm.education.ui.activity.ClassesChoiceActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 统计---练习统计(已取消该模块)
 * Created by hzw on 2019/5/29.
 */

public class StatisticsPracticeFragment extends AwMvpFragment {

    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_persons)
    TextView mTvPersons;
    @BindView(R.id.barchart)
    BarChart mBarchart;
    @BindView(R.id.barchart2)
    BarChart mBarchart2;
    @BindView(R.id.barchart3)
    BarChart mBarchart3;

    private List<TeacherClassBean> mTeacherClassBeanList = new ArrayList<>();
    private List<ClassStudentBean> mClassStudentBeanList = new ArrayList<>();

    @Override
    protected AwCommonPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_practice;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        setBarchart1Data();
        setBarchart2Data();
        setBarchart3Data();
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
        if(MyConstant.LocalConstant.TAG_STATISTICS_PRACTICE == type.getTag()) {
            //TODO 更新报表数据
            mTeacherClassBeanList = type.getTeacherList();
            AwLog.d("全局通知, 更新练习统计报表数据, 当前班级数量: " + mTeacherClassBeanList.size()) ;
        } else {
            AwLog.d("全局通知, 不更新练习统计报表数据, 非练习统计页面进入选择班级") ;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxStatisticsStudentType type) {
        if(type == null)
            return;
        if(MyConstant.LocalConstant.TAG_STATISTICS_PRACTICE == type.getTag()) {
            //TODO 更新报表数据
            mClassStudentBeanList = type.getStudentList();
            AwLog.d("全局通知, 更新练习统计报表数据, 当前学生数量: " + mClassStudentBeanList.size()) ;
        } else {
            AwLog.d("全局通知, 不更新练习统计报表数据, 非练习统计页面进入选择学生") ;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvClasses.setOnClickListener(v -> toClass(ClassesChoiceActiviity.class, false,
                Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_PRACTICE,
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
                    Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_PRACTICE,
                    Extras.COMMON_PARAMS2, sb.toString().substring(0, sb.toString().length() - 1),
                    Extras.KEY_BEAN_CLASSES_STUDENT_LIST, mClassStudentBeanList);
        });
    }


    private void setBarchart1Data() {
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
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), false, "");
    }

    private void setBarchart2Data() {
        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("函数及其表示");
        xAxisValues.add("函数的图像");
        xAxisValues.add("集合的概念与...");
        xAxisValues.add("函数与方程");

        List<Float> yAxisValues = new ArrayList<>();
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        BarChartCommonSingleYHelper.setBarChart(mBarchart2, xAxisValues, yAxisValues, "", null, 1, 0,"", 0, "");
    }

    private void setBarchart3Data() {
        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("单选题");
        xAxisValues.add("多选题");
        xAxisValues.add("判断题");
        xAxisValues.add("填空题");
        xAxisValues.add("解答题");

        List<Float> yAxisValues = new ArrayList<>();
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        yAxisValues.add(Float.valueOf(RandomValueUtil.getNum(50, 600)));
        BarChartCommonSingleYHelper.setBarChart(mBarchart3, xAxisValues, yAxisValues, "", null, 1, 0,"", 0, "");
    }
}
