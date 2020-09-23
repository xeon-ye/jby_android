package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableMultipleAdapter01;
import com.jkrm.education.adapter.exam.TableSectionAdapter;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.SectionAchievementBean;
import com.jkrm.education.bean.exam.SectionScoreBean;
import com.jkrm.education.mvp.presenters.SectionAchievementPresent;
import com.jkrm.education.mvp.views.SectionAchievementView;
import com.jkrm.education.ui.activity.me.MeAgreementActivity;
import com.jkrm.education.ui.activity.me.PrivacyActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.CommonDialog;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/8 10:51
 * Description: 成绩分段表界面
 */
public class SectionAchievementActivity extends AwMvpActivity<SectionAchievementPresent> implements SectionAchievementView.View {


    @BindView(R.id.multiple_subject_tv)
    TextView subject_tv;
    @BindView(R.id.multiple_class_tv)
    TextView class_tv;

    //成绩分段表
    @BindView(R.id.item_section_scroll)
    SynScrollerLayout sectionSSL;
    @BindView(R.id.section_recyclerview)
    RecyclerView sectionRV;


    private SectionAchievementBean sectionBean;
    private SectionScoreBean sectionScoreBean;

    private CommonDialog commonDialog;
    private String mParams = "";
    private String maxScore;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_section_achievement_layout;
    }

    @Override
    protected SectionAchievementPresent createPresenter() {
        return new SectionAchievementPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImgAndRightView("成绩分段表", "设置分数段", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                commonDialog.show();
            }
        });
        setToolbarTitleColor(R.color.white);

        commonDialog = new CommonDialog(this, R.layout.dialog_section_layout, 4);
        commonDialog.setCanceledOnTouchOutside(true);

        TextView scoreText = commonDialog.findViewById(R.id.dialog_score_tv);
        TextView leftTv = commonDialog.findViewById(R.id.dialog_cancel_tv);
        TextView rightTv = commonDialog.findViewById(R.id.dialog_sure_tv);
        EditText editText = commonDialog.findViewById(R.id.dialog_score_et);

        leftTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonDialog.dismiss();
            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString()))
                    Toast.makeText(SectionAchievementActivity.this, "请先设置分数段！", Toast.LENGTH_SHORT).show();
                else {
                    commonDialog.dismiss();
                    mParams = editText.getText().toString().trim();
                    getTableData();
                }
            }
        });

        SpannableString spannableString = new SpannableString("满分为" + mParams + "分,您可以设置分数段：");
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#0A93FC"));
        spannableString.setSpan(foregroundColorSpan, 3, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        scoreText.setMovementMethod(LinkMovementMethod.getInstance());
        scoreText.setText(spannableString);

    }

    @Override
    protected void initData() {
        super.initData();
        getTableData();
//        mPresenter.getScore(RequestUtil.getSectionScore( "",""));
    }

    private void getTableData() {
        String examId = "6bfe14f69ba949bb944cdb2c3e4d63be";
        //params 默认为50
        if (TextUtils.isEmpty(mParams))
            mParams = "50";
        mPresenter.getTableList(RequestUtil.SectionAchievementBody("", examId, "", "1", "10000", mParams));
    }

    @OnClick({R.id.multiple_subject_tv, R.id.multiple_class_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.multiple_subject_tv: //选择学科
                getSubject();
                break;
            case R.id.multiple_class_tv: //选择班级
                getClassName();
                break;
        }
    }

    //成绩分段表
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initSection() {
        //
        RelativeLayout section_relative = findViewById(R.id.section_achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_section_linear);
        section_relative.setClickable(true);

        String[] strTitle = sectionBean.getData().split("_");
        String ss = "";

        //截取字符串，拼接标题
        for (String s : strTitle) {
            View inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);
            TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
            TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
            TextView right_text = inflate.findViewById(R.id.item_achievement_child_right_tv);
            left_text.setText("人数");
            right_text.setText("比例");
            title.setText("[" + s + "]");
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

        sectionRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableSectionAdapter adapter = new TableSectionAdapter(listMap, sectionSSL);
        sectionRV.setAdapter(adapter);

        sectionRV.setOnTouchListener(getListener(sectionSSL));
        section_relative.setOnTouchListener(getListener(sectionSSL));

        //item点击
        sectionSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(SectionAchievementActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
                toClass(StuInfoTableActivity.class, false);
            }
        });

    }

    //获取班级
    private void getClassName() {

    }

    //获取科目
    private void getSubject() {

    }

    @Override
    public void getTableListSuccess(SectionAchievementBean data) {
        sectionBean = data;
        initSection();
    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(SectionAchievementActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSectionScoreSuccess(SectionScoreBean data) {
        sectionScoreBean = data;
    }

    @Override
    public void getSectionScoreFail(String msg) {
        Toast.makeText(SectionAchievementActivity.this, msg, Toast.LENGTH_SHORT).show();
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
