package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
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
import com.jkrm.education.adapter.exam.TableScoreAdapter;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.ScoreQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ScoreAchievementPresent;
import com.jkrm.education.mvp.views.ScoreAchievementView;
import com.jkrm.education.receivers.event.MessageEvent;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CommonDialog;
import com.jkrm.education.widget.Solve7PopupWindow;
import com.jkrm.education.widget.SynScrollerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
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
 * Description: 小题得分表界面
 */
public class ScoreAchievementActivity extends AwMvpActivity<ScoreAchievementPresent> implements ScoreAchievementView.View {


    @BindView(R.id.multiple_subject_tv)
    TextView course_tv;
    @BindView(R.id.multiple_class_tv)
    TextView class_tv;

    @BindView(R.id.multiple_common_toolbar)
    LinearLayout common_toolbar;
    @BindView(R.id.multiple_top_search)
    RelativeLayout top_search;

    @BindView(R.id.multiple_top_tv)
    TextView search_cancel;
    @BindView(R.id.multiple_top_ed)
    EditText multiple_ed;


    @BindView(R.id.item_score_scroll)
    SynScrollerLayout scoreSSL;
    @BindView(R.id.score_recyclerview)
    RecyclerView scoreRV;


    private ScoreAchievementBean scoreBean;
    private List<ClassBean> mClassList;
    private List<ExamCourseBean> mExamCourseList;
    private Solve7PopupWindow mPopWindow;

    private RelativeLayout score_relative;
    private List<ScoreAchievementBean.DataBean> titleList;

    private String EXAM_ID;
    private String classId, courseId;
    private String examCategory;
    private boolean isFirst = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_score_achievement_layout;
    }

    @Override
    protected ScoreAchievementPresent createPresenter() {
        return new ScoreAchievementPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImgAndRightImg("小题得分表", new AwViewCustomToolbar.OnRightClickListener() {
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

        //搜索相关（参数和接口不对，缺少keywords，不知道是否要加）
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

//        answerDialog = new CommonDialog(this,R.layout.dialog_stu_answer_layout,4);
    }

    private void getData(String search) {
        String claId = TextUtils.isEmpty(classId) ? "" : classId;
        String couId = TextUtils.isEmpty(courseId) ? "" : courseId;

        mPresenter.getTableList(RequestUtil.ScoreAchievementBody(
                UserUtil.getRoleld(), claId, EXAM_ID, couId, search));
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

    @Override
    protected void initData() {
        super.initData();

        getData("");
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void initScore() {
        score_relative = findViewById(R.id.score_achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_score_linear);
        score_relative.setClickable(true);

        titleList = scoreBean.getData();

        for (int i = 0; i < titleList.size(); i++) {
            View inflate;
            if (titleList.get(i).getIsOption().equals("2")) {
                inflate = View.inflate(this, R.layout.item_achievement_child_scroll_layout, null);//客观题
                TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
                TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
                TextView right_text = inflate.findViewById(R.id.item_achievement_child_right_tv);
                title.setText("一." + titleList.get(i).getQuestionNum() + "(满分" +
                        titleList.get(i).getMaxScore() + "分，正确答案" + titleList.get(i).getNoSpanAnswer() + ")");
                left_text.setText("得分");
                right_text.setText("作答");

            } else {
                inflate = View.inflate(this, R.layout.item_score_child_scroll_layout, null);  //主观题
                TextView title = inflate.findViewById(R.id.item_achievement_child_title_tv);
                TextView left_text = inflate.findViewById(R.id.item_achievement_child_left_tv);
                title.setText("二." + titleList.get(i).getQuestionNum() + "(满分" +
                        titleList.get(i).getMaxScore() + "分，正确答案" + titleList.get(i).getNoSpanAnswer() + ")");
                left_text.setText("得分");

                //questionId作为表头标记的对应关系
                left_text.setTag("2");
                left_text.setId(Integer.parseInt(titleList.get(i).getQuestionId()));
            }
            childRoot.addView(inflate);
        }

        TextView textView = findViewById(R.id.item_score_ranking_tv);
        if (examCategory.equals("1"))
            textView.setText("学校/班级(排名)");
        else
            textView.setText("联考/学校/班级排名");

        isFirst = false;
        getAdapterData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getAdapterData() {
        //传入adapter的数据形式
        Map<String, List<ScoreQuestionBean>> listMap = new LinkedHashMap<>();
        for (int k = 0; k < scoreBean.getRows().size(); k++) {
            ScoreAchievementBean.RowsBean bean = scoreBean.getRows().get(k);
            List<ScoreAchievementBean.RowsBean.QuestListBean> questList = bean.getQuestList();
            //
            List<ScoreQuestionBean> scbList = new ArrayList<>();

            for (int i = 0; i < 7; i++) { //固定表头对应数据
                ScoreQuestionBean scqBean = new ScoreQuestionBean();
                scqBean.setStudCode(bean.getStudCode()); //学号
                scqBean.setStudExamCode(bean.getStudExamCode());//新教育好
                scqBean.setClassName(bean.getClassName());
                scqBean.setMyScore(bean.getMyScore());
                scqBean.setObjectScore(bean.getObjectScore());//客观题
                scqBean.setSubjectScore(bean.getSubjectScore()); //主观题
                scqBean.setExamCategory(examCategory);

                scqBean.setClassRank(bean.getClassRank());
                scqBean.setGradeRank(bean.getGradeRank());
                scqBean.setJointRank(bean.getJointRank());

                scbList.add(scqBean);
            }

            //两种情况（内容与标题相等，内容小于标题）
            if (titleList.size() == questList.size()) {//内容等于标题
                for (int i = 0; i < questList.size(); i++) {
//                    ArrayList<String> answerList = new ArrayList<>();
                    ScoreQuestionBean scqBean = new ScoreQuestionBean();
                    scqBean.setStudCode(bean.getStudCode()); //学号
                    scqBean.setStudExamCode(bean.getStudExamCode());//新教育好
                    scqBean.setClassName(bean.getClassName());
                    scqBean.setMyScore(bean.getMyScore());
                    scqBean.setCourseId(bean.getCourseId());
                    scqBean.setClassId(bean.getClassId());
                    scqBean.setObjectScore(bean.getObjectScore());//客观题
                    scqBean.setSubjectScore(bean.getSubjectScore()); //主观题
                    scqBean.setExamCategory(examCategory);

                    scqBean.setClassRank(bean.getClassRank());
                    scqBean.setGradeRank(bean.getGradeRank());
                    scqBean.setJointRank(bean.getJointRank());

                    scqBean.setIsOption(questList.get(i).getIsOption());
                    scqBean.setNoSpanAnswer(questList.get(i).getNoSpanAnswer());

                    scqBean.setScore(TextUtils.isEmpty(questList.get(i).getScore()) ? "-" : questList.get(i).getScore());
                    scqBean.setStudAnswer(TextUtils.isEmpty(questList.get(i).getStudAnswer()) ? "-" : questList.get(i).getStudAnswer());
                    scbList.add(scqBean);
                }
            } else {//内容小于标题
                Map<String, Integer> map = new HashMap<>();
                //查找缺失的题目
                for (int i = 0; i < titleList.size(); i++) {
                    map.put(titleList.get(i).getQuestionNum(), i);
                }
                for (int i = 0; i < questList.size(); i++) {
                    ScoreQuestionBean scqBean = new ScoreQuestionBean();
                    scqBean.setStudCode(bean.getStudCode()); //学号
                    scqBean.setStudExamCode(bean.getStudExamCode());//新教育好
                    scqBean.setClassName(bean.getClassName());
                    scqBean.setMyScore(bean.getMyScore());
                    scqBean.setObjectScore(bean.getObjectScore());//客观题
                    scqBean.setSubjectScore(bean.getSubjectScore()); //主观题
                    scqBean.setCourseId(bean.getCourseId());
                    scqBean.setClassId(bean.getClassId());
                    scqBean.setExamCategory(examCategory);

                    scqBean.setClassRank(bean.getClassRank());
                    scqBean.setGradeRank(bean.getGradeRank());
                    scqBean.setJointRank(bean.getJointRank());
                    scqBean.setIsOption(questList.get(i).getIsOption());

                    scqBean.setStudAnswer(questList.get(i).getStudAnswer());
                    scqBean.setAnswer(questList.get(i).getAnswer());
                    scqBean.setMaxScore(questList.get(i).getMaxScore());
                    scqBean.setNoSpanAnswer(questList.get(i).getNoSpanAnswer());
                    scqBean.setQuestionId(questList.get(i).getQuestionId());
                    scqBean.setQuestionNum(questList.get(i).getQuestionNum());
                    scqBean.setScore(questList.get(i).getScore());
                    scbList.add(scqBean);

                    Integer pos = map.get(questList.get(i).getQuestionNum());
                    if (pos == null) {
                        continue;
                    }
                    titleList.set(pos, null);
                }
                for (int i = titleList.size() - 1; i >= 0; i--) {
                    if (titleList.get(i) == null) {
                        titleList.remove(i);
                    }
                }
                //补全缺失条目
                for (int i = 0; i < titleList.size(); i++) {
                    ScoreQuestionBean s_bean = new ScoreQuestionBean();
                    s_bean.setIsOption(titleList.get(i).getIsOption());
                    s_bean.setQuestionNum(titleList.get(i).getQuestionNum());
                    s_bean.setQuestionId(titleList.get(i).getQuestionId());
                    s_bean.setScore("-");
                    s_bean.setStudAnswer("-");

                    scbList.add(Integer.parseInt(titleList.get(i).getQuestionNum()) - 1, s_bean);
                }
            }
            listMap.put(bean.getStudName(), scbList);
        }

        scoreRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //可以通用adapter
        TableScoreAdapter adapter = new TableScoreAdapter(listMap, scoreSSL, 22);
        scoreRV.setAdapter(adapter);

        scoreRV.setOnTouchListener(getListener(scoreSSL));
        score_relative.setOnTouchListener(getListener(scoreSSL));

    }

    //获取班级
    private void getClassName() {
        View contentView = LayoutInflater.from(ScoreAchievementActivity.this).inflate(R.layout.item_table_drop_popup_layout, null);
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

    //获取科目
    private void getSubject() {
        View contentView = LayoutInflater.from(ScoreAchievementActivity.this).inflate(R.layout.item_table_drop_popup_layout, null);
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

    @Override
    public void getTableListSuccess(ScoreAchievementBean data) {
        scoreBean = data;
        if (isFirst)
            initScore();
        else
            getAdapterData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageEvent message) {
        Log.e(TAG, "onReceiveMsg: " + message.toString());
        if (message.getType() == 1) {
            //小题得分表跳转
            int position = Integer.parseInt(message.getMessage());
            if (message.getTag() == 22) {
                toClass(ViewStudentAnswerSheetActivity.class, false,
                        Extras.EXAM_ID, scoreBean.getRows().get(position).getExamId(),
                        Extras.STUDENT_ID, scoreBean.getRows().get(position).getStudId(),
                        Extras.KEY_COURSE_ID, scoreBean.getRows().get(position).getCourseId(),
                        Extras.KEY_EXAM_COURSE_LIST, mExamCourseList);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getTableListFail(String msg) {
        Toast.makeText(ScoreAchievementActivity.this, msg, Toast.LENGTH_SHORT).show();
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
