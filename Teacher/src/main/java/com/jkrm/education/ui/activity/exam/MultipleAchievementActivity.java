package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.TableClassGridAdapter;
import com.jkrm.education.adapter.exam.TableMultipleAdapter;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.MultipleAchievementPresent;
import com.jkrm.education.mvp.views.MultipleAchievementView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.Solve7PopupWindow;
import com.jkrm.education.widget.SynScrollerLayout;

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
    TextView subject_tv;
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

    private String EXAM_ID;


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
        mClassList = (List<ClassBean>) getIntent().getSerializableExtra(Extras.KEY_CLASS_LIST);
        mExamCourseList = (List<ExamCourseBean>) getIntent().getSerializableExtra(Extras.KEY_EXAM_COURSE_LIST);


//        View contentView = LayoutInflater.from(MultipleAchievementActivity.this).inflate(R.layout.item_table_drop_popup_layout, null);
//        mPopWindow = new Solve7PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        mPopWindow.setContentView(contentView);
//
//        GridView gridView =contentView.findViewById(R.id.dialog_class_name_gv);
//        if(mClassList.size()>0){
//            TableClassGridAdapter gridAdapter = new TableClassGridAdapter(this,mClassList);
//            gridView.setAdapter(gridAdapter);
//        }
//
//        //解决5.0以下版本点击外部不消失问题
//        mPopWindow.setOutsideTouchable(true);
//        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示方式

//        popView = new PopupWindow(MultipleAchievementActivity.this,R.layout.item_table_drop_popup_layout);
//        popView.setOutsideTouchable(true);
//        popView.setClippingEnabled(false);//不超出屏幕
//
//        GridView gridView =popView.getContentView().findViewById(R.id.dialog_class_name_gv);
//        if(mClassList.size()>0){
//            TableClassGridAdapter gridAdapter = new TableClassGridAdapter(this,mClassList);
//            gridView.setAdapter(gridAdapter);
//        }

//
    }

    @Override
    protected void initData() {
        super.initData();

        //筛选测试
        mPresenter.getTableList(RequestUtil.MultipleAchievementBody(
                UserUtil.getRoleld(),"", EXAM_ID, ""));

        //科目列表
        mPresenter.getSubjectList(UserUtil.getAppUser().getTeacherId());

    }

    //综合成绩表：根据接口的科目数据，动态添加科目
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initMultiple() {

        RelativeLayout achievement_relative = findViewById(R.id.achievement_top);
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
               // Toast.makeText(MultipleAchievementActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
                toClass(StudentAnalyseActivity.class,false,
                        Extras.EXAM_ID,achievementBean.getRows().get(position).getExamId(),
                        Extras.STUDENT_ID,achievementBean.getRows().get(position).getStudId());
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
//        mPopWindow.showAsDropDown(class_tv);
    }

    private void getSubject() {
//        mPopWindow.showAsDropDown(subjectTv);
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
