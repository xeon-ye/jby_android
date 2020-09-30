package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableClassAdapter;
import com.jkrm.education.adapter.exam.TableMultipleAdapter;
import com.jkrm.education.adapter.exam.TableScoreAdapter;
import com.jkrm.education.adapter.exam.TableSectionAdapter;
import com.jkrm.education.bean.exam.ClassAchievementBean;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.ScoreQuestionBean;
import com.jkrm.education.bean.exam.SectionAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.CommonlyMultiplePresent;
import com.jkrm.education.mvp.views.CommonlyMultipleView;
import com.jkrm.education.receivers.event.MessageEvent;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.SynScrollerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

import static com.hzw.baselib.util.AwDataUtil.isEmpty;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/1 17:49
 * Description: 常用综合报表
 */
public class CommonlyMultipleActivity extends AwMvpActivity<CommonlyMultiplePresent> implements CommonlyMultipleView.View, View.OnClickListener {

    //综合成绩表
    @BindView(R.id.item_achievement_contain)
    SynScrollerLayout achievementSSL;
    @BindView(R.id.item_achievement_recyclerview)
    RecyclerView achievementRV;

    //小题得分表
    @BindView(R.id.item_score_scroll)
    SynScrollerLayout scoreSSL;
    @BindView(R.id.item_score_recyclerview)
    RecyclerView scoreRV;

    //班级成绩对比
    @BindView(R.id.item_class_scroll)
    SynScrollerLayout classSSL;
    @BindView(R.id.item_class_recyclerview)
    RecyclerView classRV;

    //成绩分段表
    @BindView(R.id.item_section_scroll)
    SynScrollerLayout sectionSSL;
    @BindView(R.id.item_section_recyclerview)
    RecyclerView sectionRV;

    private List<ClassBean> mClassList = new ArrayList<>();


    private MultipleAchievementBean achievementBean;
    private ClassAchievementBean classAchievementBean;
    private ScoreAchievementBean scoreBean;
    private SectionAchievementBean sectionBean;


    private String EXAM_ID, CourseId;
    private String examCategory;
    private List<ExamCourseBean> mExamCourseBeanList;

    @Override
    protected CommonlyMultiplePresent createPresenter() {
        return new CommonlyMultiplePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commonly_multiple_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImg("常用综合报表", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        setToolbarTitleColor(R.color.white);

        mClassList = (List<ClassBean>) getIntent().getSerializableExtra("class_list");

        findViewById(R.id.achievement_more_tv).setOnClickListener(this);
        findViewById(R.id.score_achievement_more_tv).setOnClickListener(this);
        findViewById(R.id.class_score_more_tv).setOnClickListener(this);
        findViewById(R.id.section_achievement_more_tv).setOnClickListener(this);
        EXAM_ID = getIntent().getStringExtra(Extras.EXAM_ID);
        CourseId = getIntent().getStringExtra(Extras.COURSE_ID);
        examCategory = getIntent().getStringExtra(Extras.KEY_EXAM_CATEGORY);

    }

    private  List<String> nameList = new ArrayList<>();
    //成绩分段表
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initSection() {

        RelativeLayout section_relative = findViewById(R.id.item_section_top);
        LinearLayout childRoot = findViewById(R.id.item_section_linear);
        section_relative.setClickable(true);

        String[] strTitle = sectionBean.getData().split("_");

        List<String> titleList = new ArrayList<>();
        //截取字符串，拼接标题
        for (String s : strTitle) {
            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
            TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
            TextView right_text = inflate.findViewById(R.id.item_achievement_child_right_tv);
            left_text.setText("人数");
            right_text.setText("比例");
            title.setText("[" + s + "]");
            titleList.add(s);
            childRoot.addView(inflate);
        }

        //传入adapter的数据形式
        Map<String, List<String>> listMap = new LinkedHashMap<>();

        for (int k = 0; k < sectionBean.getRows().size(); k++) {
            SectionAchievementBean.RowsBean rowsBean = sectionBean.getRows().get(k);
            ArrayList<String> strings = new ArrayList<>();
            strings.add(rowsBean.getJoinNum());
            List<SectionAchievementBean.RowsBean.ListBean> sList = sectionBean.getRows().get(k).getList();

            for (int i = 0; i < sList.size(); i++) {
                ArrayList<String> answerList = new ArrayList<>();
                answerList.add(sList.get(i).getStudNum());
                answerList.add(sList.get(i).getStudRate() + "%");
                strings.addAll(answerList);
            }

            listMap.put(rowsBean.getClassName(), strings);
        }

        //仅展示三条数据
        Map<String, List<String>> dataMap = new LinkedHashMap<>();
        if (listMap.keySet().size() > 3) {
            List<String> aList = new ArrayList<>(listMap.keySet());
            for (int i = 0; i < aList.size(); i++) {
                if (i < 3) {
                    dataMap.put(aList.get(i), Objects.requireNonNull(listMap.get(aList.get(i))));
                } else
                    break;
            }
        } else
            dataMap.putAll(listMap);

        nameList = titleList;
        sectionRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableSectionAdapter adapter = new TableSectionAdapter(dataMap, sectionSSL, titleList, sectionBean);
        sectionRV.setAdapter(adapter);

        sectionRV.setOnTouchListener(getListener(sectionSSL));
        section_relative.setOnTouchListener(getListener(sectionSSL));

        //item点击
//        sectionSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                toClass(StuInfoTableActivity.class, false,
//                        Extras.EXAM_ID, EXAM_ID,
//                        Extras.KEY_COURSE_ID,sectionBean.getRows().get(position).getCourseId(),
//                        Extras.KEY_CLASS_ID,sectionBean.getRows().get(position).getClassId());//分数段字段 Extras.KEY_EXAM_STU_LIST
//            }
//        });

    }

    //班级成绩对比
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initClass() {

        RelativeLayout class_relative = findViewById(R.id.item_class_top);
        LinearLayout childRoot = findViewById(R.id.item_class_linear);
        class_relative.setClickable(true);

        List<String> mList = new ArrayList<>();
        mList.add("优秀");
        mList.add("良好");
        mList.add("及格");
        mList.add("低分");

        //表头
        for (int i = 0; i < 4; i++) {
            View inflate = View.inflate(this, R.layout.item_class_contrast_child_layout, null);
            TextView title = inflate.findViewById(R.id.item_class_child_title_tv);
            TextView left_text = inflate.findViewById(R.id.item_class_child_left_tv);
            TextView center_text = inflate.findViewById(R.id.item_class_child_center_tv);
            title.setText(mList.get(i));
            left_text.setText(mList.get(i) + "人数");
            center_text.setText(mList.get(i) + "率(%)");
            childRoot.addView(inflate);
        }

        //传入adapter的数据形式
        Map<String, List<String>> listMap = new LinkedHashMap<>();
        List<ClassAchievementBean.DataBean> dataList = classAchievementBean.getData();
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> strings = new ArrayList<>();
            //设置每行数据
            strings.add(!isEmpty(dataList.get(i).getRealNum()) ? dataList.get(i).getRealNum() : "-");
            strings.add(!isEmpty(dataList.get(i).getMissing()) ? dataList.get(i).getMissing() : "-"); //默认显示缺考
            strings.add(!isEmpty(dataList.get(i).getMaxScore()) ? dataList.get(i).getMaxScore() : "-");
            strings.add(!isEmpty(dataList.get(i).getMinScore()) ? dataList.get(i).getMinScore() : "-");
            //平均
            strings.add(!isEmpty(dataList.get(i).getAvgScore()) ? dataList.get(i).getAvgScore() : "-");
            strings.add(!isEmpty(dataList.get(i).getAvgRank()) ? dataList.get(i).getAvgRank() : "-");
            //优秀
            strings.add(!isEmpty(dataList.get(i).getPerfectNum()) ? dataList.get(i).getPerfectNum() : "-");
            strings.add(!isEmpty(dataList.get(i).getPerfectRate()) ? dataList.get(i).getPerfectRate() : "-");
            strings.add(!isEmpty(dataList.get(i).getPerfectRank()) ? dataList.get(i).getPerfectRank() : "-");
            //良好
            strings.add(!isEmpty(dataList.get(i).getGoodNum()) ? dataList.get(i).getGoodNum() : "-");
            strings.add(!isEmpty(dataList.get(i).getGoodRate()) ? dataList.get(i).getGoodRate() : "-");
            strings.add(!isEmpty(dataList.get(i).getGoodRank()) ? dataList.get(i).getGoodRank() : "-");
            //及格
            strings.add(!isEmpty(dataList.get(i).getPassNum()) ? dataList.get(i).getPassNum() : "-");
            strings.add(!isEmpty(dataList.get(i).getPassRate()) ? dataList.get(i).getPassRate() : "-");
            strings.add(!isEmpty(dataList.get(i).getPassRank()) ? dataList.get(i).getPassRank() : "-");
            //低分
            strings.add(!isEmpty(dataList.get(i).getLowNum()) ? dataList.get(i).getLowNum() : "-");
            strings.add(!isEmpty(dataList.get(i).getLowRate()) ? dataList.get(i).getLowRate() : "-");
            strings.add(!isEmpty(dataList.get(i).getLowRank()) ? dataList.get(i).getLowRank() : "-");

            listMap.put(dataList.get(i).getClassName(), strings);
        }

        //仅展示四条数据
        Map<String, List<String>> dataMap = new LinkedHashMap<>();
        if (listMap.keySet().size() > 3) {
            List<String> aList = new ArrayList<>(listMap.keySet());
            for (int i = 0; i < aList.size(); i++) {
                if (i < 3) {
                    dataMap.put(aList.get(i), Objects.requireNonNull(listMap.get(aList.get(i))));
                } else
                    break;
            }
        } else
            dataMap.putAll(listMap);

        classRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableClassAdapter adapter = new TableClassAdapter(dataMap, classSSL, 11, false);
        classRV.setAdapter(adapter);

        classRV.setOnTouchListener(getListener(classSSL));
        class_relative.setOnTouchListener(getListener(classSSL));

        //item点击
//        classSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Toast.makeText(ClassAchievementActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    //综合成绩表：根据接口的科目数据，动态添加科目
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initMultiple() {

        RelativeLayout achievement_relative = findViewById(R.id.item_achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_achievement_linear);
        achievement_relative.setClickable(true);

        // 表头
        List<String> sList = new ArrayList<>();
        //无重复表头数据
        List<String> tList = new ArrayList<>();
        for (int i = 0; i < achievementBean.getRows().size(); i++) {

            for (int j = 0; j < achievementBean.getRows().get(i).getReaList().size(); j++) {
                sList.add(achievementBean.getRows().get(i).getReaList().get(j).getCourseName());
            }
        }

        //去除重复科目,设置表头
        HashSet<String> hashSet = new LinkedHashSet<>(sList);
        for (String name : hashSet) {
            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
            TextView score_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
            TextView rightText = inflate.findViewById(R.id.item_achievement_child_right_tv);
            title.setText(name);
            score_text.setText("分数");
            if (examCategory.equals("1"))
                rightText.setText("学校/班级(排名)");
            childRoot.addView(inflate);
            tList.add(name);
            Log.e("xxxxxxxxxx", name);
        }

//        List<MultipleAchievementBean.RowsBean.ReaListBean> titleList = new ArrayList<>();
        //传入adapter的数据形式
        Map<String, List<String>> listMap = new LinkedHashMap<>();

        for (int k = 0; k < achievementBean.getRows().size(); k++) {
//            titleList.addAll(achievementBean.getRows().get(k).getReaList());

            MultipleAchievementBean.RowsBean rowsBean = achievementBean.getRows().get(k);
            List<MultipleAchievementBean.RowsBean.ReaListBean> reaList = rowsBean.getReaList();

            ArrayList<String> strings = new ArrayList<>();

            strings.add(rowsBean.getStudCode()); //学号
            strings.add(rowsBean.getStudExamCode());//新教育好
            strings.add(rowsBean.getClassName());//班级

            //添加全部表头
            tList.clear();
            tList.addAll(hashSet);
            //两种情况（内容与标题相等，内容小于标题）
            if (tList.size() == reaList.size()) { //内容与标题相等
                for (int i = 0; i < reaList.size(); i++) {
                    ArrayList<String> anList = new ArrayList<>();

                    anList.add(TextUtils.isEmpty(reaList.get(i).getScore()) ? "-" :
                            reaList.get(i).getScore());

                    String s2 = TextUtils.isEmpty(reaList.get(i).getSchRank()) ? "-" :
                            reaList.get(i).getSchRank();
                    String s3 = TextUtils.isEmpty(reaList.get(i).getClassRank()) ? "-" :
                            reaList.get(i).getClassRank();
                    if (examCategory.equals("1")) { //等于1，没有联考，否则有
                        anList.add(s2 + "/" + s3);
                    } else {
                        String s1 = TextUtils.isEmpty(reaList.get(i).getJointRank()) ? "-" :
                                reaList.get(i).getJointRank();
                        anList.add(s1 + "/" + s2 + "/" + s3);
                    }

                    strings.addAll(anList);
                }
            } else {//内容小于标题
                Map<String, Integer> map = new HashMap<>();
                //查找缺失的科目
                for (int i = 0; i < tList.size(); i++) {
                    map.put(tList.get(i), i);
                }
                for (int i = 0; i < reaList.size(); i++) {
                    Integer pos = map.get(reaList.get(i).getCourseName());
                    if (pos == null) {
                        continue;
                    }
                    tList.set(pos, null);
                }

                map.clear();
                for (int i = tList.size() - 1; i >= 0; i--) {
                    if (tList.get(i) != null)
                        map.put(tList.get(i), i);
                    else
                        tList.remove(i);
                }
                //补全缺失科目(有bug)
                for (int i = 0; i < tList.size(); i++) {
                    MultipleAchievementBean.RowsBean.ReaListBean r_bean = new MultipleAchievementBean.RowsBean.ReaListBean();
//                    r_bean.setIsOption(titleList.get(i).get());
//                    r_bean.setQuestionNum(titleList.get(i).getQuestionNum());
//                    r_bean.setQuestionId(titleList.get(i).getQuestionId());
                    r_bean.setScore("-");

                    Integer num = map.get(tList.get(i));
                    reaList.add(num, r_bean);
                }

                //构造adapter数据
                for (int i = 0; i < reaList.size(); i++) {
                    ArrayList<String> answerList = new ArrayList<>();
                    if (reaList.get(i).getScore().equals("-")) { //是否补全的数据
                        answerList.add("-"); //分数
                        answerList.add("-");//排名
                    } else {
                        //接口无分数数据，默认显示-
                        answerList.add(TextUtils.isEmpty(reaList.get(i).getScore()) ? "-" : reaList.get(i).getScore()); //分数
                        //接口无排名数据，默认显示"-"
                        String s2 = TextUtils.isEmpty(reaList.get(i).getSchRank()) ? "-" :
                                reaList.get(i).getSchRank();
                        String s3 = TextUtils.isEmpty(reaList.get(i).getClassRank()) ? "-" :
                                reaList.get(i).getClassRank();
                        if (examCategory.equals("1")) { //等于1，没有联考，否则有
                            answerList.add(s2 + "/" + s3);
                        } else {
                            String s1 = TextUtils.isEmpty(reaList.get(i).getJointRank()) ? "-" :
                                    reaList.get(i).getJointRank();
                            answerList.add(s1 + "/" + s2 + "/" + s3);
                        }
                    }
                    strings.addAll(answerList);
                }
            }
            listMap.put(rowsBean.getStudName(), strings);
        }

        //仅展示三条数据
        Map<String, List<String>> dataMap = new LinkedHashMap<>();
        if (listMap.keySet().size() > 3) {
            List<String> aList = new ArrayList<>(listMap.keySet());
            for (int i = 0; i < aList.size(); i++) {
                if (i < 3) {
                    dataMap.put(aList.get(i), Objects.requireNonNull(listMap.get(aList.get(i))));
                } else
                    break;
            }
        } else
            dataMap.putAll(listMap);

        achievementRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter adapter = new TableMultipleAdapter(dataMap, achievementSSL, achievementBean, 11);
        achievementRV.setAdapter(adapter);

        achievementRV.setOnTouchListener(getListener(achievementSSL));
        achievement_relative.setOnTouchListener(getListener(achievementSSL));

        //item点击
//        achievementSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                //Toast.makeText(CommonlyMultipleActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
//                toClass(StudentAnalyseActivity.class, false,
//                        Extras.EXAM_ID, achievementBean.getRows().get(position).getExamId(),
//                        Extras.STUDENT_ID, achievementBean.getRows().get(position).getStudId());
//            }
//        });

    }

    //小题得分表：
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initScore() {
        RelativeLayout score_relative = findViewById(R.id.item_score_top);
        LinearLayout childRoot = findViewById(R.id.item_score_linear);
        score_relative.setClickable(true);

        List<ScoreAchievementBean.RowsBean.QuestListBean> realList = new ArrayList<>();
        List<ScoreAchievementBean.DataBean> titleList = scoreBean.getData();

        TextView textView = findViewById(R.id.item_score_ranking_tv);
        if (examCategory.equals("1"))
            textView.setText("学校/班级(排名)");
        else
            textView.setText("联考/学校/班级排名");

        for (int i = 0; i < titleList.size(); i++) {
            View inflate;
            if (titleList.get(i).getIsOption().equals("2")) {
                inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);//客观题
                TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
                TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
                TextView right_text = inflate.findViewById(R.id.item_achievement_child_right_tv);
                title.setText("一." + titleList.get(i).getQuestionNum() + "(满分" +
                        titleList.get(i).getMaxScore() + "分，正确答案" + titleList.get(i).getNoSpanAnswer() + ")");
                left_text.setText("得分");
                right_text.setText("作答");

            } else {
                inflate = View.inflate(this, R.layout.item_score_child_scroll_layout, null);  //主观题
                TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
                TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
                title.setText("二." + titleList.get(i).getQuestionNum() + "(满分" +
                        titleList.get(i).getMaxScore() + "分，正确答案" + titleList.get(i).getNoSpanAnswer() + ")");
                left_text.setText("得分");

                //questionId作为表头标记的对应关系
                left_text.setTag("2");
                left_text.setId(Integer.parseInt(titleList.get(i).getQuestionId()));
            }
            childRoot.addView(inflate);
        }

        //传入adapter的数据形式
        Map<String, List<ScoreQuestionBean>> listMap = new LinkedHashMap<>();
        for (int k = 0; k < scoreBean.getRows().size(); k++) {
            ScoreAchievementBean.RowsBean bean = scoreBean.getRows().get(k);
            List<ScoreAchievementBean.RowsBean.QuestListBean> questList = bean.getQuestList();
            //
            List<ScoreQuestionBean> scbList = new ArrayList<>();

            for (int i = 0; i < 7; i++) { //固定表头对应数据
                ScoreQuestionBean scqBean = new ScoreQuestionBean();
                scqBean.setStudCode(bean.getStudCode()); //学号
                scqBean.setStudExamCode(bean.getStudExamCode());//新教育好
                scqBean.setClassName(bean.getClassName());
                scqBean.setMyScore(bean.getMyScore());
                scqBean.setObjectScore(bean.getObjectScore());//客观题
                scqBean.setSubjectScore(bean.getSubjectScore()); //主观题
                scqBean.setExamCategory(examCategory);

                scqBean.setClassRank(bean.getClassRank());
                scqBean.setGradeRank(bean.getGradeRank());
                scqBean.setJointRank(bean.getJointRank());

                scbList.add(scqBean);
            }

            //两种情况（内容与标题相等，内容小于标题）
            if (titleList.size() == questList.size()) {//内容等于标题
                for (int i = 0; i < questList.size(); i++) {
//                    ArrayList<String> answerList = new ArrayList<>();
                    ScoreQuestionBean scqBean = new ScoreQuestionBean();
                    scqBean.setStudCode(bean.getStudCode()); //学号
                    scqBean.setStudExamCode(bean.getStudExamCode());//新教育好
                    scqBean.setClassName(bean.getClassName());
                    scqBean.setMyScore(bean.getMyScore());
                    scqBean.setCourseId(bean.getCourseId());
                    scqBean.setClassId(bean.getClassId());
                    scqBean.setObjectScore(bean.getObjectScore());//客观题
                    scqBean.setSubjectScore(bean.getSubjectScore()); //主观题
                    scqBean.setExamCategory(examCategory);

                    scqBean.setClassRank(bean.getClassRank());
                    scqBean.setGradeRank(bean.getGradeRank());
                    scqBean.setJointRank(bean.getJointRank());

                    scqBean.setIsOption(questList.get(i).getIsOption());
                    scqBean.setNoSpanAnswer(questList.get(i).getNoSpanAnswer());

                    scqBean.setScore(TextUtils.isEmpty(questList.get(i).getScore()) ? "-" : questList.get(i).getScore());
                    scqBean.setStudAnswer(TextUtils.isEmpty(questList.get(i).getStudAnswer()) ? "-" : questList.get(i).getStudAnswer());
                    scbList.add(scqBean);
                }
            } else {//内容小于标题
                Map<String, Integer> map = new HashMap<>();
                //查找缺失的题目
                for (int i = 0; i < titleList.size(); i++) {
                    map.put(titleList.get(i).getQuestionNum(), i);
                }
                for (int i = 0; i < questList.size(); i++) {
                    ScoreQuestionBean scqBean = new ScoreQuestionBean();
                    scqBean.setStudCode(bean.getStudCode()); //学号
                    scqBean.setStudExamCode(bean.getStudExamCode());//新教育好
                    scqBean.setClassName(bean.getClassName());
                    scqBean.setMyScore(bean.getMyScore());
                    scqBean.setObjectScore(bean.getObjectScore());//客观题
                    scqBean.setSubjectScore(bean.getSubjectScore()); //主观题
                    scqBean.setCourseId(bean.getCourseId());
                    scqBean.setClassId(bean.getClassId());
                    scqBean.setExamCategory(examCategory);

                    scqBean.setClassRank(bean.getClassRank());
                    scqBean.setGradeRank(bean.getGradeRank());
                    scqBean.setJointRank(bean.getJointRank());
                    scqBean.setIsOption(questList.get(i).getIsOption());

                    scqBean.setStudAnswer(questList.get(i).getStudAnswer());
                    scqBean.setAnswer(questList.get(i).getAnswer());
                    scqBean.setMaxScore(questList.get(i).getMaxScore());
                    scqBean.setNoSpanAnswer(questList.get(i).getNoSpanAnswer());
                    scqBean.setQuestionId(questList.get(i).getQuestionId());
                    scqBean.setQuestionNum(questList.get(i).getQuestionNum());
                    scqBean.setScore(questList.get(i).getScore());
                    scbList.add(scqBean);

                    Integer pos = map.get(questList.get(i).getQuestionNum());
                    if (pos == null) {
                        continue;
                    }
                    titleList.set(pos, null);
                }
                for (int i = titleList.size() - 1; i >= 0; i--) {
                    if (titleList.get(i) == null) {
                        titleList.remove(i);
                    }
                }
                //补全缺失条目
                for (int i = 0; i < titleList.size(); i++) {
                    ScoreQuestionBean s_bean = new ScoreQuestionBean();
                    s_bean.setIsOption(titleList.get(i).getIsOption());
                    s_bean.setQuestionNum(titleList.get(i).getQuestionNum());
                    s_bean.setQuestionId(titleList.get(i).getQuestionId());
                    s_bean.setScore("-");
                    s_bean.setStudAnswer("-");

                    scbList.add(Integer.parseInt(titleList.get(i).getQuestionNum()) - 1, s_bean);
                }
            }
            listMap.put(bean.getStudName(), scbList);
        }

        scoreRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //仅展示三条数据
        Map<String, List<ScoreQuestionBean>> dataMap = new LinkedHashMap<>();
        if (listMap.keySet().size() > 3) {
            List<String> aList = new ArrayList<>(listMap.keySet());
            for (int i = 0; i < aList.size(); i++) {
                if (i < 3) {
                    dataMap.put(aList.get(i), Objects.requireNonNull(listMap.get(aList.get(i))));
                } else
                    break;
            }
        } else
            dataMap.putAll(listMap);

        //可以通用adapter
        TableScoreAdapter adapter = new TableScoreAdapter(dataMap, scoreSSL, 11);
        scoreRV.setAdapter(adapter);
        scoreRV.setOnTouchListener(getListener(scoreSSL));
        score_relative.setOnTouchListener(getListener(scoreSSL));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //综合成绩表
            case R.id.achievement_more_tv: {
                toClass(MultipleAchievementActivity.class,
                        false, Extras.KEY_CLASS_LIST, mClassList,
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseBeanList,
                        Extras.EXAM_ID, EXAM_ID,
                        Extras.KEY_EXAM_CATEGORY, examCategory);
                break;
            }
            //小题得分表
            case R.id.score_achievement_more_tv: {
                toClass(ScoreAchievementActivity.class, false,
                        Extras.KEY_CLASS_LIST, mClassList,
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseBeanList,
                        Extras.EXAM_ID, EXAM_ID,
                        Extras.KEY_EXAM_CATEGORY, examCategory);
                break;
            }
            //班级成绩对比
            case R.id.class_score_more_tv: {
                toClass(ClassAchievementActivity.class, false,
                        Extras.KEY_CLASS_LIST, mClassList,
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseBeanList,
                        Extras.EXAM_ID, EXAM_ID);
                break;
            }
            //成绩分段表
            case R.id.section_achievement_more_tv: {
                toClass(SectionAchievementActivity.class, false,
                        Extras.KEY_CLASS_LIST, mClassList,
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseBeanList,
                        Extras.EXAM_ID, EXAM_ID,
                        Extras.COURSE_ID, CourseId,
                        Extras.KEY_EXAM_CATEGORY, examCategory);
                break;
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        //综合成绩
        mPresenter.getMultipleAchievementList(RequestUtil.MultipleAchievementBody(
                UserUtil.getRoleld(), "", "", EXAM_ID, ""));
        mPresenter.getExamCourse(RequestUtil.getExamCourseBody(EXAM_ID, UserUtil.getRoleld()));
        //小题得分
        mPresenter.getQuestionScoreList(RequestUtil.ScoreAchievementBody(UserUtil.getRoleld(), "", EXAM_ID, "", ""));
        //班级成绩对比
        String param = "100,90_90,80_80,60_60,0";
        mPresenter.getClassAchievementList(RequestUtil.ClassAchievementBody(UserUtil.getRoleld(),
                "", EXAM_ID, "", param));
        //成绩分段，params 先默认为50
        String mParams = "50";
        mPresenter.getAchievementSectionList(RequestUtil.SectionAchievementBody(
                UserUtil.getRoleld(), "", EXAM_ID, "", "1", "1000", mParams));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageEvent message) {
        Log.e(TAG, "onReceiveMsg: " + message.toString());
        int position = Integer.parseInt(message.getMessage());
        if (message.getType() == 0) {
            //综合成绩表跳转
            if (message.getTag() == 11) {
                toClass(ViewStudentAnswerSheetActivity.class, false,
                        Extras.EXAM_ID, achievementBean.getRows().get(position).getExamId(),
                        Extras.STUDENT_ID, achievementBean.getRows().get(position).getStudId(),
//                                Extras.KEY_COURSE_ID, achievementBean.getRows().get(position).getCourseId(), //先不加
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseBeanList);
            }
        } else if (message.getType() == 1) {
            //小题得分表跳转
            if (message.getTag() == 11) {
                toClass(ViewStudentAnswerSheetActivity.class, false,
                        Extras.EXAM_ID, scoreBean.getRows().get(position).getExamId(),
                        Extras.STUDENT_ID, scoreBean.getRows().get(position).getStudId(),
                        Extras.KEY_COURSE_ID, scoreBean.getRows().get(position).getCourseId(),
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseBeanList);
            }
        } else if (message.getType() == 2) {
            //班级成绩对比跳转
            if (message.getTag() == 11) {
                String param = setNum(position);
                toClass(StuInfoTableActivity.class, false,
                        Extras.EXAM_ID, classAchievementBean.getData().get(position).getExamId(),
                        Extras.KEY_COURSE_ID, classAchievementBean.getData().get(position).getCourseId(),
                        Extras.KEY_CLASS_ID, classAchievementBean.getData().get(position).getClassId(),
                        Extras.KEY_EXAM_STU_PARAM,param );
            }
        }

    }

    private String setNum(int num) {
        String param = "";
        String ss = "90,100_80,100_60,100_0,30";
        String[] strings = ss.split("_");
        switch (num) {
            case 6:
                param = strings[0];
                break;
            case 9:
                param = strings[1];
                break;
            case 12:
                param = strings[2];
                break;
            case 15:
                param = strings[3];
                break;
        }
        return param;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getMultipleAchievementSuccess(MultipleAchievementBean data) {
        achievementBean = data;
        //综合成绩表：根据接口的科目数据，动态添加科目
        initMultiple();
    }

    @Override
    public void getMultipleAchievementListFail(String msg) {
        Toast.makeText(CommonlyMultipleActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getQuestionScoreListSuccess(ScoreAchievementBean data) {
        scoreBean = data;
        initScore();
    }

    @Override
    public void getQuestionScoreListFail(String msg) {
        Toast.makeText(CommonlyMultipleActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getClassAchievementListSuccess(ClassAchievementBean data) {
        classAchievementBean = data;
        initClass();
    }

    @Override
    public void getClassAchievementListFail(String msg) {
        Toast.makeText(CommonlyMultipleActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getAchievementSectionListSuccess(SectionAchievementBean data) {
        sectionBean = data;
        initSection();
    }

    @Override
    public void getAchievementSectionListFail(String msg) {
        Toast.makeText(CommonlyMultipleActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getExamCourseSuccess(List<ExamCourseBean> data) {
        ExamCourseBean bean = new ExamCourseBean();
        bean.setCourseId("");
        bean.setCourseName("全部");
        bean.setChecked(true);
        data.add(0, bean);
        mExamCourseBeanList = data;
    }

    @Override
    public void getExamCourseFail(String msg) {
        showMsg(msg);
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    private View.OnTouchListener getListener(SynScrollerLayout scrollerLayout) {

        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollerLayout.onTouchEvent(motionEvent);
                return false;
            }
        };
    }

}
