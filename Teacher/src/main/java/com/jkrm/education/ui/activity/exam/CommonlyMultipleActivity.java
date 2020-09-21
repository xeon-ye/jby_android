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
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ScoreAdapter;
import com.jkrm.education.adapter.exam.TableClassAdapter;
import com.jkrm.education.adapter.exam.TableMultipleAdapter;
import com.jkrm.education.adapter.exam.TableMultipleAdapter01;
import com.jkrm.education.adapter.exam.TableScoreAdapter;
import com.jkrm.education.adapter.exam.TableSectionAdapter;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.CommonlyMultiplePresent;
import com.jkrm.education.mvp.views.CommonlyMultipleView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

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
    String EXAM_ID;
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

        //小题得分表：
        initScore();
        //班级成绩对比
        initClass();
        //成绩分段表
        initSection();
    }


    //成绩分段表
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initSection() {
        List<String> mList = new ArrayList<>();
        mList.add("[350, 360]");
        mList.add("[300, 350]");
        mList.add("[250, 300]");
        mList.add("[200, 250]");
        mList.add("[150, 200]");
        mList.add("[100, 150]");
        mList.add("[50, 100]");
        mList.add("[0, 50]");

        RelativeLayout section_relative = findViewById(R.id.item_section_top);
        LinearLayout childRoot = findViewById(R.id.item_section_linear);
        section_relative.setClickable(true);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            strings.add("张三" + i);
        }
        for (int i = 0; i < 8; i++) {
            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
            TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
            TextView right_text = inflate.findViewById(R.id.item_achievement_child_right_tv);
            left_text.setText("人数");
            right_text.setText("比例");
            title.setText(mList.get(i));
            childRoot.addView(inflate);
        }

        sectionRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter01 adapter = new TableMultipleAdapter01(strings, sectionSSL);
        sectionRV.setAdapter(adapter);

        sectionRV.setOnTouchListener(getListener(sectionSSL));
        section_relative.setOnTouchListener(getListener(sectionSSL));

        //item点击
        sectionSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(CommonlyMultipleActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();

            }
        });

    }

    //班级成绩对比
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initClass() {
        List<String> mList = new ArrayList<>();
        mList.add("优秀");
        mList.add("良好");
        mList.add("及格");
        mList.add("低分");

        RelativeLayout class_relative = findViewById(R.id.item_class_top);
        LinearLayout childRoot = findViewById(R.id.item_class_linear);
        class_relative.setClickable(true);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            strings.add("张三" + i);
        }

        for (int i = 0; i < 4; i++) {
            View inflate = View.inflate(this, R.layout.item_class_contrast_child_layout, null);
            TextView title = inflate.findViewById(R.id.item_class_child_title_tv);
            TextView left_text = inflate.findViewById(R.id.item_class_child_left_tv);
            TextView center_text = inflate.findViewById(R.id.item_class_child_center_tv);
            title.setText(mList.get(i));
            left_text.setText(i + "人数");
            center_text.setText(i + "率(%)");
            childRoot.addView(inflate);
        }

        classRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter01 adapter = new TableMultipleAdapter01(strings, classSSL);
        classRV.setAdapter(adapter);

        classRV.setOnTouchListener(getListener(classSSL));
        class_relative.setOnTouchListener(getListener(classSSL));

        //item点击
        classSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(CommonlyMultipleActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();

            }
        });

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
            title.setText(name);
            score_text.setText("分数");
            childRoot.addView(inflate);
            tList.add(name);
            Log.e("xxxxxxxxxx",name);
        }

//        List<MultipleAchievementBean.RowsBean.ReaListBean> titleList = new ArrayList<>();
        //传入adapter的数据形式
        Map<String, List<String>> listMap = new HashMap<>();

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

                    String s1 = TextUtils.isEmpty(reaList.get(i).getJointRank()) ? "-" :
                            reaList.get(i).getJointRank();
                    String s2 = TextUtils.isEmpty(reaList.get(i).getSchRank()) ? "-" :
                            reaList.get(i).getSchRank();
                    String s3 = TextUtils.isEmpty(reaList.get(i).getClassRank()) ? "-" :
                            reaList.get(i).getClassRank();
                    anList.add(s1 + "/" + s2 + "/" + s3);

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
                    if(tList.get(i) != null)
                        map.put(tList.get(i),i);
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
                        String s1 = TextUtils.isEmpty(reaList.get(i).getJointRank()) ? "-" :
                                reaList.get(i).getJointRank();
                        String s2 = TextUtils.isEmpty(reaList.get(i).getSchRank()) ? "-" :
                                reaList.get(i).getSchRank();
                        String s3 = TextUtils.isEmpty(reaList.get(i).getClassRank()) ? "-" :
                                reaList.get(i).getClassRank();
                        answerList.add(s1 + "/" + s2 + "/" + s3);
                    }
                    strings.addAll(answerList);
                }
            }
            listMap.put(rowsBean.getStudName(), strings);
        }
        achievementRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter adapter = new TableMultipleAdapter(listMap, achievementSSL);
        achievementRV.setAdapter(adapter);

        achievementRV.setOnTouchListener(getListener(achievementSSL));
        achievement_relative.setOnTouchListener(getListener(achievementSSL));

        //item点击
        achievementSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(CommonlyMultipleActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
                toClass(StudentAnalyseActivity.class,false, Extras.EXAM_ID,achievementBean.getRows().get(position).getExamId(),Extras.STUDENT_ID,achievementBean.getRows().get(position).getStudId());
            }
        });

    }

    //小题得分表：
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initScore() {
        RelativeLayout score_relative = findViewById(R.id.item_score_top);
        LinearLayout childRoot = findViewById(R.id.item_score_linear);
        score_relative.setClickable(true);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            strings.add("张三" + i);
        }

        for (int i = 0; i < 5; i++) {
            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
            TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
            TextView right_text = inflate.findViewById(R.id.item_achievement_child_right_tv);
            title.setText("一." + i + "(满分5分，正确答案C)");
            left_text.setText("得分");
            right_text.setText("作答");
            childRoot.addView(inflate);
        }

        scoreRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter01 adapter = new TableMultipleAdapter01(strings, scoreSSL);
        scoreRV.setAdapter(adapter);

        scoreRV.setOnTouchListener(getListener(scoreSSL));
        score_relative.setOnTouchListener(getListener(scoreSSL));

        //item点击
        scoreSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(CommonlyMultipleActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //综合成绩表
            case R.id.achievement_more_tv: {
                toClass(MultipleAchievementActivity.class, false, "class_list_0", mClassList);
                break;
            }
            //小题得分表
            case R.id.score_achievement_more_tv: {
                toClass(ScoreAchievementActivity.class, false,Extras.KEY_EXAM_COURSE_LIST,mExamCourseBeanList);
                break;
            }
            //班级成绩对比
            case R.id.class_score_more_tv: {
                toClass(ClassAchievementActivity.class, false);
                break;
            }
            //成绩分段表
            case R.id.section_achievement_more_tv: {
                toClass(SectionAchievementActivity.class, false);
                break;
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMultipleAchievementList(RequestUtil.MultipleAchievementBody("", "", ""));
        mPresenter.getExamCourse(RequestUtil.getExamCourseBody(EXAM_ID, UserUtil.getRoleld()));
    }

    @Override
    public void getMultipleAchievementSuccess(MultipleAchievementBean data) {
        achievementBean = data;
        //综合成绩表：根据接口的科目数据，动态添加科目
        initMultiple();
    }

    @Override
    public void getMultipleAchievementListFail(String msg) {

    }

    @Override
    public void getExamCourseSuccess(List<ExamCourseBean> data) {
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
