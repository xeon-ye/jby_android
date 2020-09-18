package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.CommonlyMultiplePresent;
import com.jkrm.education.mvp.views.CommonlyMultipleView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

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
        TableSectionAdapter adapter = new TableSectionAdapter(strings, sectionSSL);
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
        TableSectionAdapter adapter = new TableSectionAdapter(strings, classSSL);
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

        List<String> sList = new ArrayList<>();
        List<String> tList = new ArrayList<>();

        if (achievementBean == null) {
            Toast.makeText(CommonlyMultipleActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < achievementBean.getRows().size(); i++) {

            for (int j = 0; j < achievementBean.getRows().get(i).getReaList().size(); j++) {
                sList.add(achievementBean.getRows().get(i).getReaList().get(j).getCourseName());
//                View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
//                TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
//                TextView score_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
//                title.setText(achievementBean.getRows().get(i).getReaList().get(j).getCourseName());
//                score_text.setText("分数");
//                childRoot.addView(inflate);
            }
        }

        //去除重复科目
        HashSet<String> set = new LinkedHashSet<>(sList);
        for (String name : set) {
            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
            TextView score_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
            title.setText(name);
            score_text.setText("分数");
            inflate.setTag(name);
            childRoot.addView(inflate);
            sList.clear();
            sList.add(name);
            Log.e("xxxxxxxxxx", name);
        }

        achievementRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter adapter = new TableMultipleAdapter(achievementBean.getRows(), achievementSSL, sList);
//        TableMultipleAdapter adapter = new TableMultipleAdapter(strings, achievementSSL);
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
        TableSectionAdapter adapter = new TableSectionAdapter(strings, scoreSSL);
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
                toClass(ScoreAchievementActivity.class, false);
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
