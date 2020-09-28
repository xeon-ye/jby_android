package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.jkrm.education.adapter.exam.TableCourseGridAdapter;
import com.jkrm.education.adapter.exam.TableMultipleAdapter;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.MultipleAchievementPresent;
import com.jkrm.education.mvp.views.MultipleAchievementView;
import com.jkrm.education.receivers.event.MessageEvent;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.Solve7PopupWindow;
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
    TextView course_tv;
    @BindView(R.id.multiple_class_tv)
    TextView class_tv;

    //综合成绩表
    @BindView(R.id.item_achievement_contain)
    SynScrollerLayout achievementSSL;
    @BindView(R.id.achievement_recyclerview)
    RecyclerView achievementRV;


    private MultipleAchievementBean achievementBean;

    private List<ClassBean> mClassList;
    private List<ExamCourseBean> mExamCourseList;
    private Solve7PopupWindow mPopWindow;
    private List<String> tList;

    private String EXAM_ID;

    private String classId, courseId;
    private boolean isFirst = true;
    private String examCategory;
    private RelativeLayout achievement_relative;


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

        EXAM_ID = getIntent().getStringExtra(Extras.EXAM_ID);
        examCategory = getIntent().getStringExtra(Extras.KEY_EXAM_CATEGORY);
        mClassList = (List<ClassBean>) getIntent().getSerializableExtra(Extras.KEY_CLASS_LIST);
        mExamCourseList = (List<ExamCourseBean>) getIntent().getSerializableExtra(Extras.KEY_EXAM_COURSE_LIST);

        EditText editText = findViewById(R.id.multiple_top_ed);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    String ss = editText.getText().toString().trim();
                    getData(ss);
                    editText.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);

    }

    @Override
    protected void initData() {
        super.initData();

        //科目列表
        mPresenter.getSubjectList(UserUtil.getAppUser().getTeacherId());
        getData("");
    }

    //可接受（姓名考号教育号搜索）
    private void getData(String serText) {

        String claId = TextUtils.isEmpty(classId) ? "" : classId;
        String couId = TextUtils.isEmpty(courseId) ? "" : courseId;
        mPresenter.getTableList(RequestUtil.MultipleAchievementBody(
                UserUtil.getRoleld(), claId, couId, EXAM_ID, serText));
    }

    //综合成绩表：根据接口的科目数据，动态添加科目
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initMultiple() {

        achievement_relative = findViewById(R.id.achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_achievement_linear);
        achievement_relative.setClickable(true);

        // 表头
        List<String> sList = new ArrayList<>();
        //无重复表头数据
        tList = new ArrayList<>();
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

        getAdapterData();
        isFirst = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getAdapterData() {
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

//            String ss = achievementBean.getRows().get(0).getReaList().get(0).

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

        achievementRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        TableMultipleAdapter adapter = new TableMultipleAdapter(listMap, achievementSSL,achievementBean,22);
        achievementRV.setAdapter(adapter);

        achievementRV.setOnTouchListener(getListener(achievementSSL));
        achievement_relative.setOnTouchListener(getListener(achievementSSL));

//        //item点击
//        achievementSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                // Toast.makeText(MultipleAchievementActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
//                toClass(StudentAnalyseActivity.class, false,
//                        Extras.EXAM_ID, achievementBean.getRows().get(position).getExamId(),
//                        Extras.STUDENT_ID, achievementBean.getRows().get(position).getStudId());
//            }
//        });
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
        View contentView = LayoutInflater.from(MultipleAchievementActivity.this).inflate(R.layout.item_table_drop_popup_layout, null);
        mPopWindow = new Solve7PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopWindow.setContentView(contentView);


        RelativeLayout relativeLayout = contentView.findViewById(R.id.item_table_drop_relative);

        GridView gridView = contentView.findViewById(R.id.dialog_class_name_gv);
        if (mClassList.size() > 0) {
            TableClassGridAdapter gridAdapter = new TableClassGridAdapter(this, mClassList);
            gridView.setAdapter(gridAdapter);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classId = mClassList.get(position).getClassId();
                class_tv.setText(mClassList.get(position).getClassName());
                mPopWindow.dismiss();
                getData("");
            }
        });

        //解决5.0以下版本点击外部不消失问题
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setClippingEnabled(false);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.showAsDropDown(class_tv);
        mPopWindow.showAsDropDown(class_tv, 0, -class_tv.getHeight());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                }
            }
        });

    }

    private void getSubject() {
        View contentView = LayoutInflater.from(MultipleAchievementActivity.this).inflate(R.layout.item_table_drop_popup_layout, null);
        mPopWindow = new Solve7PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mPopWindow.setContentView(contentView);


        RelativeLayout relativeLayout = contentView.findViewById(R.id.item_table_drop_relative);

        GridView gridView = contentView.findViewById(R.id.dialog_class_name_gv);
        if (mExamCourseList.size() > 0) {
            TableCourseGridAdapter gridAdapter = new TableCourseGridAdapter(this, mExamCourseList);
            gridView.setAdapter(gridAdapter);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                courseId = mExamCourseList.get(position).getCourseId();
                course_tv.setText(mExamCourseList.get(position).getCourseName());
                mPopWindow.dismiss();
                getData("");
            }
        });

        //解决5.0以下版本点击外部不消失问题
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setClippingEnabled(false);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.showAsDropDown(class_tv);
        mPopWindow.showAsDropDown(class_tv, 0, -class_tv.getHeight());

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWindow.isShowing()) {
                    mPopWindow.dismiss();
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageEvent message) {
        Log.e(TAG, "onReceiveMsg: " + message.toString());
        if (message.getType() == 0) {
            //综合成绩表跳转
            int position = Integer.parseInt(message.getMessage());
            if (message.getTag() == 22) {
                toClass(ViewStudentAnswerSheetActivity.class, false,
                        Extras.EXAM_ID, achievementBean.getRows().get(position).getExamId(),
                        Extras.STUDENT_ID, achievementBean.getRows().get(position).getStudId(),
//                                Extras.KEY_COURSE_ID, achievementBean.getRows().get(position).getCourseId(), //先不加
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseList);
            }
        }

    }

    @Override
    public void getTableListSuccess(MultipleAchievementBean data) {
        achievementBean = data;
        if (isFirst)
            initMultiple();
        else
            getAdapterData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(MultipleAchievementActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSubjectSuccess(List<ErrorCourseBean> data) {

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
