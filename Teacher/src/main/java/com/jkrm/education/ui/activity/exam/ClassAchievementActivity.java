package com.jkrm.education.ui.activity.exam;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.bean.exam.ClassAchievementBean;
import com.jkrm.education.mvp.presenters.ClassAchievementPresent;
import com.jkrm.education.mvp.views.ClassAchievementView;
import com.jkrm.education.widget.SynScrollerLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/8 10:51
 * Description: 班级成绩对比表界面
 */
public class ClassAchievementActivity extends AwMvpActivity<ClassAchievementPresent> implements ClassAchievementView.View {


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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_class_achievement_layout;
    }

    @Override
    protected ClassAchievementPresent createPresenter() {
        return new ClassAchievementPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarWithBackImgAndRightImg("综合成绩表", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                common_toolbar.setVisibility(View.GONE);
                top_search.setVisibility(View.VISIBLE);
            }
        });
        setToolbarTitleColor(R.color.white);
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

    //获取班级
    private void getClassName() {

    }

    //获取科目
    private void getSubject() {

    }

    @Override
    public void getTableListSuccess(ClassAchievementBean data) {

    }

    @Override
    public void getTableListFail(String msg) {

    }



}
