package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableMultipleAdapter;
import com.jkrm.education.adapter.exam.TableStuInfoAdapter;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.StuInfoTableBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.MultipleAchievementPresent;
import com.jkrm.education.mvp.presenters.ScoreAchievementPresent;
import com.jkrm.education.mvp.presenters.StuInfoTablePresent;
import com.jkrm.education.mvp.views.MultipleAchievementView;
import com.jkrm.education.mvp.views.StuInfoTableView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/22 9:45
 * Description: 学生名单详情列表
 */
public class StuInfoTableActivity extends AwMvpActivity<StuInfoTablePresent> implements StuInfoTableView.View{



    @BindView(R.id.item_stu_contain)
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

    private StuInfoTableBean tableBean;
    private String EXAM_ID;
    private String params;
    private String classId, courseId;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_stu_info_table_layout;
    }

    @Override
    protected StuInfoTablePresent createPresenter() {
        return new StuInfoTablePresent(this);
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
                claId, EXAM_ID, couId,"1","10000",param));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getTableListSuccess(StuInfoTableBean data) {
        tableBean = data;
        numTv.setText("人数："+data.getTotal());
//        avgTv.setText("平均分："+data.getTotal());
//        maxTv.setText("最高分："+data.getTotal());
//        minTv.setText("最低分："+data.getTotal());
        initTable();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTable() {
        RelativeLayout stu_relative = findViewById(R.id.stu_table_top);
//        LinearLayout childRoot = findViewById(R.id.item_section_linear);
        stu_relative.setClickable(true);

        Map<String, List<String>> listMap = new LinkedHashMap<>();
        for (int k = 0; k < tableBean.getRows().size(); k++) {
            ArrayList<String> strings = new ArrayList<>();
            StuInfoTableBean.RowsBean rowsBean = tableBean.getRows().get(k);
//            strings.add(rowsBean.getStudName());
            strings.add(rowsBean.getStudCode());
            strings.add(rowsBean.getClassName());
            strings.add(rowsBean.getClassMaxScore());
            listMap.put(rowsBean.getStudName(),strings);
        }

        stuRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableStuInfoAdapter adapter = new TableStuInfoAdapter(listMap, stuSSL);
        stuRV.setAdapter(adapter);

        stuRV.setOnTouchListener(getListener(stuSSL));
        stu_relative.setOnTouchListener(getListener(stuSSL));

    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(StuInfoTableActivity.this, msg, Toast.LENGTH_SHORT).show();
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
