package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableClassGridAdapter;
import com.jkrm.education.adapter.exam.TableMultipleAdapter;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.TableClassBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.mvp.presenters.MultipleAchievementPresent;
import com.jkrm.education.mvp.views.MultipleAchievementView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CommonDialog;
import com.jkrm.education.widget.SelectDialog;
import com.jkrm.education.widget.SynScrollerLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/8 10:51
 * Description: 综合成绩表界面
 */
public class MultipleAchievementActivity extends AwMvpActivity<MultipleAchievementPresent> implements MultipleAchievementView.View {


    @BindView(R.id.multiple_common_toolbar)
    LinearLayout common_toolbar;
    @BindView(R.id.multiple_top_search)
    RelativeLayout top_search;

    @BindView(R.id.multiple_top_tv)
    TextView search_cancel;
    @BindView(R.id.multiple_top_ed)
    EditText multiple_ed;

    @BindView(R.id.multiple_subject_tv)
    TextView subject_tv;
    @BindView(R.id.multiple_class_tv)
    TextView class_tv;

    //综合成绩表
    @BindView(R.id.item_achievement_contain)
    SynScrollerLayout achievementSSL;
    @BindView(R.id.achievement_recyclerview)
    RecyclerView achievementRV;


    private MultipleAchievementBean achievementBean;

    private List<ClassBean> mClassList = new ArrayList<>();

    private SelectDialog popView;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_multiple_achievement_layout;
    }

    @Override
    protected MultipleAchievementPresent createPresenter() {
        return new MultipleAchievementPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImgAndRightImg("综合成绩表", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                common_toolbar.setVisibility(View.GONE);
                top_search.setVisibility(View.VISIBLE);
            }
        });
        setToolbarTitleColor(R.color.white);

        mClassList = (List<ClassBean>) getIntent().getSerializableExtra("class_list_0");

        popView = new SelectDialog(this,R.layout.dialog_multiple_achievement_layout);
        popView.setOutsideTouchable(true);
        popView.setClippingEnabled(false);//不超出屏幕

        GridView gridView =popView.getContentView().findViewById(R.id.dialog_class_name_gv);
        if(mClassList.size()>0){
            TableClassGridAdapter gridAdapter = new TableClassGridAdapter(this,mClassList);
            gridView.setAdapter(gridAdapter);
        }

//
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.getTableList(RequestUtil.MultipleAchievementBody("", "", ""));

        //科目列表
        mPresenter.getSubjectList(UserUtil.getAppUser().getTeacherId());

    }

    //综合成绩表：根据接口的科目数据，动态添加科目
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initMultiple() {

        RelativeLayout achievement_relative = findViewById(R.id.achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_achievement_linear);
        achievement_relative.setClickable(true);

        List<String> sList = new ArrayList<>();

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
            Log.e("xxxxxxxxxx",name);
        }

//        for (int i = 0; i < 5; i++) {
//            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
//            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
//            TextView score_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
//            title.setText("科目" + i);
//            score_text.setText("类别" + i);
//            childRoot.addView(inflate);
//        }

        achievementRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter adapter = new TableMultipleAdapter(achievementBean.getRows(), achievementSSL,sList);
//        TableMultipleAdapter adapter = new TableMultipleAdapter(strings, achievementSSL);
        achievementRV.setAdapter(adapter);

        achievementRV.setOnTouchListener(getListener(achievementSSL));
        achievement_relative.setOnTouchListener(getListener(achievementSSL));

        //item点击
        achievementSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MultipleAchievementActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.multiple_top_tv, R.id.multiple_subject_tv, R.id.multiple_class_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.multiple_top_tv: //取消
                common_toolbar.setVisibility(View.VISIBLE);
                top_search.setVisibility(View.GONE);
                break;
            case R.id.multiple_subject_tv: //选择学科
                getSubject();
                break;
            case R.id.multiple_class_tv: //选择班级
                getClassName();
                break;
        }
    }

    private void getClassName() {
        popView.showAsDropDown(findViewById(R.id.multiple_class_tv));
    }

    private void getSubject() {

    }

    @Override
    public void getTableListSuccess(MultipleAchievementBean data) {
        achievementBean = data;
        initMultiple();
    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(MultipleAchievementActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSubjectSuccess(List<ErrorCourseBean> data) {
        //科目这有点奇怪
    }

    @Override
    public void getSubjectListFail(String msg) {

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
