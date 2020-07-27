package com.jkrm.education.old;

import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.mpchart.helpers.BarChartCommonMoreYHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYCustomSpaceHelper;
import com.hzw.baselib.mpchart.helpers.BarChartCommonSingleYHelper;
import com.hzw.baselib.mpchart.helpers.LineChartCommonHelper;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;
import com.jkrm.education.R;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionKnowledgeResultBean;
import com.jkrm.education.bean.result.StatisticsErrorQuestionRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitRatioResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBean;
import com.jkrm.education.bean.result.StatisticsHomeworkSubmitTableResultBeanNew;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.rx.RxStatisticsClassesType;
import com.jkrm.education.bean.rx.RxStatisticsStudentType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.StatisticsHomeworkPresent;
import com.jkrm.education.mvp.views.StatisticsHomeworkView;
import com.jkrm.education.ui.activity.ClassesChoiceActiviity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 原统计--- 作业统计类
 * Created by hzw on 2019/5/29.
 */

public class StatisticsHomeworkFragmentOld extends AwMvpFragment<StatisticsHomeworkPresent> implements StatisticsHomeworkView.View {

    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_persons)
    TextView mTvPersons;
    @BindView(R.id.barchart)
    BarChart mBarchart;
    @BindView(R.id.lineChart)
    LineChart mLineChart;
    @BindView(R.id.barchart3)
    BarChart mBarchart3;

    private List<TeacherClassBean> mTeacherClassBeanList = new ArrayList<>();
    private List<ClassStudentBean> mClassStudentBeanList = new ArrayList<>();

    private boolean isChoiceWithClass = true;

    @Override
    protected StatisticsHomeworkPresent createPresenter() {
        return new StatisticsHomeworkPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_homework;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        setBarchart1Data();
        setLineChart();
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
        if(MyConstant.LocalConstant.TAG_STATISTICS_HOMEWORK == type.getTag()) {
            //TODO 更新报表数据
            mTeacherClassBeanList = type.getTeacherList();
            AwLog.d("全局通知, 更新作业统计报表数据, 当前班级数量: " + mTeacherClassBeanList.size()) ;

            if (!AwDataUtil.isEmpty(mTeacherClassBeanList)) {
                isChoiceWithClass = true;
                StringBuffer sb = new StringBuffer();
                for(TeacherClassBean tempBean : mTeacherClassBeanList) {
                    sb.append(tempBean.getClassId()).append(",");
                }
                String classesIds = sb.substring(0, sb.toString().length() - 1);
                AwLog.d("统计, 筛选班级: " + classesIds);
//                mPresenter.getStatisticsHomeworkSubmitRatio(MyApp.getInstance().getAppUser().getTeacherId(),
//                            RequestUtil.getStatisticsHomeworkRatio("", "", "1", "", classesIds));
//                mPresenter.getStatisticsHomeworkMisstakeRatio(MyApp.getInstance().getAppUser().getTeacherId(),
//                            RequestUtil.getStatisticsHomeworkRatio("", "", "1", "", classesIds));
//                mPresenter.getStatisticsHomeworkErrorQuestionKnowledge(MyApp.getInstance().getAppUser().getTeacherId(),
//                            RequestUtil.getStatisticsHomeworkErrorQuestionKnowledge("", "", "", classesIds));
            }
        } else {
            AwLog.d("全局通知, 不更新作业统计报表数据, 非作业统计页面进入选择班级") ;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxStatisticsStudentType type) {
        if(type == null)
            return;
        if(MyConstant.LocalConstant.TAG_STATISTICS_HOMEWORK == type.getTag()) {
            //TODO 更新报表数据
            mClassStudentBeanList = type.getStudentList();
            AwLog.d("全局通知, 更新作业统计报表数据, 当前学生数量: " + mClassStudentBeanList.size()) ;
            if (!AwDataUtil.isEmpty(mClassStudentBeanList)) {
                isChoiceWithClass = false;
                StringBuffer sb = new StringBuffer();
                for(ClassStudentBean tempBean : mClassStudentBeanList) {
                    sb.append(tempBean.getId()).append(",");
                }
                String studentIds = sb.substring(0, sb.toString().length() - 1);
                AwLog.d("统计, 筛选学生: " + studentIds);
//                mPresenter.getStatisticsHomeworkSubmitRatio(MyApp.getInstance().getAppUser().getTeacherId(),
//                        RequestUtil.getStatisticsHomeworkRatio("", "", "2", studentIds, ""));
//                mPresenter.getStatisticsHomeworkMisstakeRatio(MyApp.getInstance().getAppUser().getTeacherId(),
//                        RequestUtil.getStatisticsHomeworkRatio("", "", "2", studentIds, ""));
//                mPresenter.getStatisticsHomeworkErrorQuestionKnowledge(MyApp.getInstance().getAppUser().getTeacherId(),
//                        RequestUtil.getStatisticsHomeworkErrorQuestionKnowledge("", "", studentIds, ""));
            }
        } else {
            AwLog.d("全局通知, 不更新作业统计报表数据, 非作业统计页面进入选择学生") ;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvClasses.setOnClickListener(v -> toClass(ClassesChoiceActiviity.class, false,
                Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_HOMEWORK,
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
                    Extras.COMMON_PARAMS, MyConstant.LocalConstant.TAG_STATISTICS_HOMEWORK,
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
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart, xAxisValues, map, MyDateUtil.getChartColorsList(mActivity), true, "");
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

    private void setBarchart3Data() {
        List<String> xAxisValues = new ArrayList<>();
        xAxisValues.add("函数及其表示");
        xAxisValues.add("函数的图像");
        xAxisValues.add("集合的概念与...");
        xAxisValues.add("函数与方程");

        List<Float> yAxisValues = new ArrayList<>();
        yAxisValues.add(15f);
        yAxisValues.add(35f);
        yAxisValues.add(10f);
        yAxisValues.add(40f);
        BarChartCommonSingleYHelper.setBarChart(mBarchart3, xAxisValues, yAxisValues, "", null, 1, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 2, AwBaseConstant.COMMON_SUFFIX_RATIO);
        mBarchart3.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    public void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {

    }

    @Override
    public void getAnswerSheetsFail(String msg) {

    }

    @Override
    public void getStatisticsHomeworkSubmitTableSuccess(List<StatisticsHomeworkSubmitTableResultBean> list) {

    }

    @Override
    public void getStatisticsHomeworkSubmitTableFail(String msg) {

    }

    @Override
    public void getStatisticesHomeworkSubmitRatioSuccess(List<StatisticsHomeworkSubmitRatioResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            return;
        }
        if(isChoiceWithClass) {
            handleSubmitRatioClassData(list);
        } else {
            handleSubmitRatioStudentData(list);
        }
    }

    @Override
    public void getStatisticsHomeworkMisstakeRatioSuccess(List<StatisticsErrorQuestionRatioResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            return;
        }
        if(isChoiceWithClass) {
            handleErrorQuestionRatioClassData(list);
        } else {

        }
    }

    @Override
    public void getStatisticsHomeworkErrorQuestionKnowledgeSuccess(List<StatisticsErrorQuestionKnowledgeResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            return;
        }
        AwLog.d("getStatusErrorQuestionInSomeDaySuccess list size: " + list.size());
        Collections.sort(list, (o1, o2) -> (int) (Double.parseDouble(o2.getErrorRate()) * 100 - Double.parseDouble(o1.getErrorRate())  * 100));
        List<String> xAxisValues = new ArrayList<>();
        List<Float> yAxisValues = new ArrayList<>();
        for(StatisticsErrorQuestionKnowledgeResultBean temp : list) {
            String name = temp.getCatalogName();
            if(name.length() > 10) {
                name = name.substring(0, 10) + "...";
            }
            AwLog.d("name: " + name);
            xAxisValues.add(name);
            yAxisValues.add(Float.valueOf(temp.getErrorRate()) * 100);
        }
        BarChartCommonSingleYCustomSpaceHelper.setBarChart(mBarchart3, xAxisValues, yAxisValues,
                "", null, 1, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 2, AwBaseConstant.COMMON_SUFFIX_RATIO, 4,
                false, false, false);
        mBarchart3.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                showToastDialog(list.get((int) e.getX()).getCatalogName() + ":" + AwConvertUtil.double2String(e.getY(), 2) + AwBaseConstant.COMMON_SUFFIX_RATIO);
                AwLog.d("Entry: " + e.toString() + " \nHighlight: " + h.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    @Override
    public void getStatisticsHomeworkErrorQuestionKnowledgeFail(String msg) {

    }

    @Override
    public void getStatisticsHomeworkSubmitTableNewSuccess(StatisticsHomeworkSubmitTableResultBeanNew statisticsHomeworkSubmitTableResultBeanNew) {

    }

    @Override
    public void getStatisticsHomeworkSubmitTableNewFail(String msg) {

    }

    @Override
    public void getTeacherClassListSuccess(List<TeacherClassBean> list) {

    }

    @Override
    public void getTeacherClassListFail(String msg) {

    }

    private void handleSubmitRatioClassData(List<StatisticsHomeworkSubmitRatioResultBean> list) {
        //获取组个数(即这里的日期)
        List<String> dateList = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(i);
            if(dateList.contains(tempBean.getCtime())) {
                continue;
            }
            dateList.add(tempBean.getCtime());
            try {
                xAxisValues.add(AwDateUtils.formatDate2.format(AwDateUtils.formatISODate.parse(tempBean.getCtime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //获取班级个数(即这里的班级列)
        List<String> dataClassList = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(i);
            if(!dataClassList.contains(tempBean.getClassName())) {
                dataClassList.add(tempBean.getClassName());
            }
        }
        Collections.sort(dataClassList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (Integer.parseInt(o1.substring(o1.indexOf("(") + 1, o1.lastIndexOf(")"))) >= Integer.parseInt(o2.substring(o2.indexOf("(") + 1, o2.lastIndexOf(")")))) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        LinkedHashMap<String, List<Float>> chartMap = new LinkedHashMap<>();
        for(int i=0; i<dataClassList.size(); i++) { //初一1班 初一4班
            List<Float> yAxisValues = new ArrayList<>();
            for(int j=0; j<list.size(); j++) {
                StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(j);
                if(tempBean.getClassName().equals(dataClassList.get(i))) {
                    yAxisValues.add(Float.valueOf(tempBean.getSubmittedRatio()) * 100);
                }
            }
            chartMap.put(dataClassList.get(i), yAxisValues);
        }
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart, xAxisValues, chartMap, MyDateUtil.getChartColorsList(mActivity), true, "");
    }

    private void handleSubmitRatioStudentData(List<StatisticsHomeworkSubmitRatioResultBean> list) {
        //获取组个数(即这里的日期)
        List<String> dateList = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(i);
            if(dateList.contains(tempBean.getCtime())) {
                continue;
            }
            dateList.add(tempBean.getCtime());
            try {
                xAxisValues.add(AwDateUtils.formatDate2.format(AwDateUtils.formatISODate.parse(tempBean.getCtime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //获取班级个数(即这里的班级列)
        List<String> dataStudentList = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(i);
            if(!dataStudentList.contains(tempBean.getStudentName())) {
                dataStudentList.add(tempBean.getStudentName());
            }
        }

        LinkedHashMap<String, List<Float>> chartMap = new LinkedHashMap<>();
        for(int i=0; i<dataStudentList.size(); i++) { //学生1 学生2
            List<Float> yAxisValues = new ArrayList<>();
            for(int j=0; j<list.size(); j++) {
                StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(j);
                if(tempBean.getStudentName().equals(dataStudentList.get(i))) {
                    yAxisValues.add(Float.valueOf(tempBean.getSubmittedRatio()) * 100);
                }
            }
            chartMap.put(dataStudentList.get(i), yAxisValues);
        }
        BarChartCommonMoreYHelper.setMoreBarChart(mBarchart, xAxisValues, chartMap, MyDateUtil.getChartColorsList(mActivity), true, "");
    }

    private void handleErrorQuestionRatioClassData(List<StatisticsErrorQuestionRatioResultBean> list) {
        //获取组个数(即这里的日期)
        List<String> dateList = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsErrorQuestionRatioResultBean tempBean = list.get(i);
            if(dateList.contains(tempBean.getCtime())) {
                continue;
            }
            dateList.add(tempBean.getCtime());
            try {
                xAxisValues.add(AwDateUtils.formatDate2.format(AwDateUtils.formatISODate.parse(tempBean.getCtime())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //获取班级个数(即这里的班级列)
        List<String> dataClassList = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsErrorQuestionRatioResultBean tempBean = list.get(i);
            if(!dataClassList.contains(tempBean.getClassName())) {
                dataClassList.add(tempBean.getClassName());
            }
        }
        Collections.sort(dataClassList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (Integer.parseInt(o1.substring(o1.indexOf("(") + 1, o1.lastIndexOf(")"))) >= Integer.parseInt(o2.substring(o2.indexOf("(") + 1, o2.lastIndexOf(")")))) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        LinkedHashMap<String, List<Float>> chartMap = new LinkedHashMap<>();
        for(int i=0; i<dataClassList.size(); i++) { //初一1班 初一4班
            List<Float> yAxisValues = new ArrayList<>();
            for(int j=0; j<list.size(); j++) {
                StatisticsErrorQuestionRatioResultBean tempBean = list.get(j);
                if(tempBean.getClassName().equals(dataClassList.get(i))) {
                    yAxisValues.add(Float.valueOf(tempBean.getMistakeRatio()) * 100);
                }
            }
            chartMap.put(dataClassList.get(i), yAxisValues);
        }
        LineChartCommonHelper.setMoreLineChart(mLineChart, xAxisValues, chartMap, MyDateUtil.getChartColorsList(mActivity), true, "");
    }

    private void handleData1(List<StatisticsHomeworkSubmitRatioResultBean> list) {
        List<String> dateList = new ArrayList<>();
        //一共多少分组
        List<String> xAxisValues = new ArrayList<>();
        for(int i=0; i<list.size(); i++) {
            StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(i);
            if(!xAxisValues.contains(tempBean.getCtime())) {
                dateList.add(tempBean.getCtime());
                try {
                    xAxisValues.add(AwDateUtils.formatDate2.format(AwDateUtils.formatISODate.parse(tempBean.getCtime())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        Map<String, List<StatisticsHomeworkSubmitRatioResultBean>> map = new HashMap<>();
        //获取各日期列的数量
        for(int j=0; j<dateList.size(); j++) { //20190617 20190618
            List<StatisticsHomeworkSubmitRatioResultBean> tempList = new ArrayList<>();
            for(int i=0; i<list.size(); i++) {
                StatisticsHomeworkSubmitRatioResultBean tempBean = list.get(i);
                if(tempBean.getCtime().equals(dateList.get(j))) {
                    tempList.add(tempBean);
                }
            }
            map.put(dateList.get(j), tempList);
        }

        int totalNum = 0;
        for(String key : map.keySet()){
            AwLog.d("提交率报表 key:" + key);
            AwLog.d("提交率报表 value:" + map.get(key));
            if(totalNum < map.get(key).size()) {
                totalNum = map.get(key).size();
            }
            //接着进行取list值
            List<StatisticsHomeworkSubmitRatioResultBean> lisMap = map.get(key);
            for (int i = 0 ; i< lisMap.size() ; i++){
                AwLog.d("提交率报表 取出List中VALUE["+key+ "]的第" + "[" +(i+1)+"]个值："+ lisMap.get(i).toString());
            }
        }
        AwLog.d("提交率报表 totalNum:" + totalNum);


        LinkedHashMap<String, List<Float>> chartMap = new LinkedHashMap<>();
        for(int i=0; i<totalNum; i++) {
            List<Float> yAxisValues = new ArrayList<>();
            for(String key : map.keySet()){
                List<StatisticsHomeworkSubmitRatioResultBean> lisMap = map.get(key);
                if(lisMap.size() < totalNum) {
                    for(int k=0; k<totalNum-lisMap.size(); k++) {
                        StatisticsHomeworkSubmitRatioResultBean tempBean = new StatisticsHomeworkSubmitRatioResultBean();
                        lisMap.add(0, tempBean);
                    }
                }
                for(int j=0; j<lisMap.size(); j++) {
                    if(AwDataUtil.isEmpty(lisMap.get(j).getSubmittedRatio())) {
                        yAxisValues.add(Float.valueOf(0));
                    } else {
                        yAxisValues.add(Float.valueOf(lisMap.get(j).getSubmittedRatio()) * 100);
                    }
                }
            }
        }
    }
}
