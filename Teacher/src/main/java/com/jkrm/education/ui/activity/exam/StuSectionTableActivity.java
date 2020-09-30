package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableStuInfoAdapter;
import com.jkrm.education.adapter.exam.TableStuSectionAdapter;
import com.jkrm.education.bean.exam.StuInfoTableBean;
import com.jkrm.education.bean.exam.StuSectionTableBean;
import com.jkrm.education.bean.exam.StuTableTitleBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.StuSectionTablePresent;
import com.jkrm.education.mvp.views.StuSectionTableView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/29 16:24
 * Description: 学生名单详情列表(成绩分段表人数跳转)
 */
public class StuSectionTableActivity extends AwMvpActivity<StuSectionTablePresent> implements StuSectionTableView.View {


    @BindView(R.id.item_achievement_contain)
    SynScrollerLayout stuSSL;
    @BindView(R.id.stu_recyclerview)
    RecyclerView stuRV;

    @BindView(R.id.stu_num_tv)
    TextView numTv;
    @BindView(R.id.stu_avg_score_tv)
    TextView avgTv;
    @BindView(R.id.stu_max_score_tv)
    TextView maxTv;
    @BindView(R.id.stu_min_score_tv)
    TextView minTv;

    private String EXAM_ID;
    private String params;
    private String classId, courseId;
    private StuSectionTableBean tableBean;


    @Override
    protected StuSectionTablePresent createPresenter() {
        return new StuSectionTablePresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stu_section_table_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImg("学生名单详情", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        setToolbarTitleColor(R.color.white);
        EXAM_ID = getIntent().getStringExtra(Extras.EXAM_ID);
        params = getIntent().getStringExtra(Extras.KEY_EXAM_STU_PARAM);
        classId = getIntent().getStringExtra(Extras.KEY_CLASS_ID);
        courseId = getIntent().getStringExtra(Extras.KEY_COURSE_ID);
    }

    @Override
    protected void initData() {
        super.initData();
        String claId = TextUtils.isEmpty(classId) ? "" : classId;
        String couId = TextUtils.isEmpty(courseId) ? "" : courseId;
        String param = TextUtils.isEmpty(params) ? "" : params;

        mPresenter.getTableList(RequestUtil.StuInfoTableBody(
                claId, EXAM_ID, couId, "1", "10000", param));

        mPresenter.getTableTitle(RequestUtil.StuInfoTableBody(
                claId, EXAM_ID, couId, "1", "10000", param));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTable() {
        RelativeLayout stu_relative = findViewById(R.id.achievement_top);
//        LinearLayout childRoot = findViewById(R.id.item_section_linear);
        stu_relative.setClickable(true);

        TextView title = findViewById(R.id.item_achievement_child_title_tv);
        title.setText("总分");
        TextView leftTv = findViewById(R.id.item_achievement_child_left_tv);
        leftTv.setText("分数");
        TextView rightTv = findViewById(R.id.item_achievement_child_right_tv);
//        title.setText("");
        String examCategory = AwSpUtil.getString("examCategory", Extras.KEY_EXAM_CATEGORY, "");
        if (examCategory.equals("1"))//无联考
            title.setText("学校/班级(排名)");

        Map<String, List<String>> listMap = new LinkedHashMap<>();
        for (int k = 0; k < tableBean.getRows().size(); k++) {
            ArrayList<String> strings = new ArrayList<>();
            StuSectionTableBean.RowsBean rowsBean = tableBean.getRows().get(k);
            strings.add(rowsBean.getStudCode());
            strings.add(rowsBean.getStudExamCode());
            strings.add(rowsBean.getClassName());
            strings.add(rowsBean.getMyScore());

            String ss;
            if (examCategory.equals("1"))//无联考
                ss = rowsBean.getGradeRank() + "/" + rowsBean.getClassRank();
            else
                ss = rowsBean.getJointRank() + "/" + rowsBean.getGradeRank() + "/" + rowsBean.getClassRank();
            strings.add(ss);

            listMap.put(rowsBean.getStudName(), strings);
        }

        stuRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableStuSectionAdapter adapter = new TableStuSectionAdapter(listMap, stuSSL);
        stuRV.setAdapter(adapter);

        stuRV.setOnTouchListener(getListener(stuSSL));
        stu_relative.setOnTouchListener(getListener(stuSSL));
    }

    @Override
    public void getTableListSuccess(StuSectionTableBean data) {
        tableBean = data;
        initTable();
    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(StuSectionTableActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTableTitleSuccess(StuTableTitleBean data) {
        numTv.setText("人数：" + data.getData().getStudNum());
        avgTv.setText("平均分：" + data.getData().getClassAvgScore());
        maxTv.setText("最高分：" + data.getData().getClassMaxScore());
        minTv.setText("最低分：" + data.getData().getClassMinScore());
    }

    @Override
    public void getTableTitleFail(String msg) {
        Toast.makeText(StuSectionTableActivity.this, msg, Toast.LENGTH_SHORT).show();
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
