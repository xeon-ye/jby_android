package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableClassAdapter;
import com.jkrm.education.bean.exam.ClassAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ClassAchievementPresent;
import com.jkrm.education.mvp.views.ClassAchievementView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.widget.CommonDialog;
import com.jkrm.education.widget.SynScrollerLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hzw.baselib.util.AwDataUtil.isEmpty;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/8 10:51
 * Description: 班级成绩对比表界面
 */
public class ClassAchievementActivity extends AwMvpActivity<ClassAchievementPresent> implements ClassAchievementView.View {


    @BindView(R.id.class_common_toolbar)
    LinearLayout common_toolbar;

    @BindView(R.id.multiple_subject_tv)
    TextView subject_tv;
    @BindView(R.id.multiple_class_tv)
    TextView class_tv;

    @BindView(R.id.item_class_scroll)
    SynScrollerLayout classSSL;
    @BindView(R.id.class_recyclerview)
    RecyclerView classRV;

    private CommonDialog commonDialog;
    private String set_params;

    private ClassAchievementBean classAchievementBean;
    private String examId;
    private RelativeLayout class_relative;
    private boolean isFirst = true;
    private boolean isMiss = true;


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
        setStatusBlue();
        setToolbarWithBackImgAndRightView("班级成绩对比", "设置等级", new AwViewCustomToolbar.OnRightClickListener() {
            @Override
            public void onRightTextClick() {
                commonDialog.show();
            }
        });
        setToolbarTitleColor(R.color.white);

        examId = getIntent().getStringExtra(Extras.EXAM_ID);
        if (TextUtils.isEmpty(examId))
            examId = "6bfe14f69ba949bb944cdb2c3e4d63be"; //示例数据

        commonDialog = new CommonDialog(this, R.layout.dialog_class_achievement_set_layout, 4);
        commonDialog.setCanceledOnTouchOutside(true);
        commonDialog.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                commonDialog.dismiss();
            }
        });

        EditText editStart = commonDialog.findViewById(R.id.excellent_start_et);
        EditText editSEnd = commonDialog.findViewById(R.id.excellent_end_et);

        EditText editStart01 = commonDialog.findViewById(R.id.good_start_et);
        EditText editSEnd01 = commonDialog.findViewById(R.id.good_end_et);

        EditText editStart02 = commonDialog.findViewById(R.id.pass_start_et);
        EditText editSEnd02 = commonDialog.findViewById(R.id.pass_end_et);

        EditText editStart03 = commonDialog.findViewById(R.id.low_start_et);
        EditText editSEnd03 = commonDialog.findViewById(R.id.low_end_et);

        commonDialog.findViewById(R.id.dialog_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editStart.getText()) ||
                        TextUtils.isEmpty(editSEnd.getText()) ||
                        TextUtils.isEmpty(editStart01.getText()) ||
                        TextUtils.isEmpty(editSEnd01.getText()) ||
                        TextUtils.isEmpty(editStart02.getText()) ||
                        TextUtils.isEmpty(editSEnd02.getText()) ||
                        TextUtils.isEmpty(editStart03.getText()) ||
                        TextUtils.isEmpty(editSEnd03.getText())) {
                    Toast.makeText(ClassAchievementActivity.this, "分数设置请填写完整！", Toast.LENGTH_SHORT).show();
                } else {
                    //获取params
                    String ss = editStart.getText() + "," +
                            editSEnd.getText() + "_" + editStart01.getText() + "," +
                            editSEnd01.getText() + "_" + editStart02.getText() + "," +
                            editSEnd02.getText() + "_" + editStart03.getText() + "," +
                            editSEnd03.getText();
                    set_params = ss.trim();
                    commonDialog.dismiss();
                    initData();
                }
            }
        });

        //缺考title
        TextView textView = findViewById(R.id.item_class_no_tv);
        CheckBox checkBox = findViewById(R.id.class_score_one_cb); //是否包含缺考
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    textView.setVisibility(View.GONE);
                    isMiss = true;
                } else {
                    textView.setVisibility(View.VISIBLE);
                    isMiss = false;
                }
                getAdapterData();
            }
        });

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

    private void getClassName() {

    }

    private void getSubject() {
    }

    @Override
    protected void initData() {
        super.initData();
        //判断是否进行等级设置
        if (!TextUtils.isEmpty(set_params))
            isFirst = false;
        String param = TextUtils.isEmpty(set_params) ? "100,90_80,80_60,60_30,0" : set_params;
        mPresenter.getTableList(RequestUtil.ClassAchievementBody("", examId, "", param));
    }

    @Override
    public void getTableListSuccess(ClassAchievementBean data) {
        classAchievementBean = data;
        if (isFirst)
            initTable();
        else
            getAdapterData();
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void initTable() {
        List<String> mList = new ArrayList<>();
        mList.add("优秀");
        mList.add("良好");
        mList.add("及格");
        mList.add("低分");

        class_relative = findViewById(R.id.class_achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_class_linear);
        class_relative.setClickable(true);
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

        TextView title = (TextView) findViewById(R.id.item_achievement_child_title_tv);
        title.setText("平均分");
        TextView left_tv = (TextView) findViewById(R.id.item_achievement_child_left_tv);
        left_tv.setText("平均分");
        TextView right_tv = (TextView) findViewById(R.id.item_achievement_child_right_tv);
        right_tv.setText("排名");

        //传入adapter的数据形式
        getAdapterData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getAdapterData() {
        Map<String, List<String>> listMap = new LinkedHashMap<>();
        List<ClassAchievementBean.DataBean> dataList = classAchievementBean.getData();
        for (int i = 0; i < dataList.size(); i++) {
            ArrayList<String> strings = new ArrayList<>();
            //设置每行数据
            strings.add(!isEmpty(dataList.get(i).getRealNum()) ? dataList.get(i).getRealNum() : "-");
            if (!isMiss)
                strings.add(!isEmpty(dataList.get(i).getMissing()) ? dataList.get(i).getMissing() : "-");
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
        classRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableClassAdapter adapter = new TableClassAdapter(listMap, classSSL);
        classRV.setAdapter(adapter);

        classRV.setOnTouchListener(getListener(classSSL));
        class_relative.setOnTouchListener(getListener(classSSL));
    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(ClassAchievementActivity.this, msg, Toast.LENGTH_SHORT).show();
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
