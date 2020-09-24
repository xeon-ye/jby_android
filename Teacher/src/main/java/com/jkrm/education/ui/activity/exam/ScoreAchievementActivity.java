package com.jkrm.education.ui.activity.exam;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.jkrm.education.adapter.exam.TableScoreAdapter;
import com.jkrm.education.bean.exam.ExamCourseBean;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ScoreAchievementPresent;
import com.jkrm.education.mvp.views.ScoreAchievementView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.SynScrollerLayout;

import java.io.Serializable;
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
 * Description: 小题得分表界面
 */
public class ScoreAchievementActivity extends AwMvpActivity<ScoreAchievementPresent> implements ScoreAchievementView.View {


    @BindView(R.id.multiple_subject_tv)
    TextView subject_tv;
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
    private List<ExamCourseBean> mCourseBeanList;
    private String EXAM_ID;


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
        mCourseBeanList = (List<ExamCourseBean>) getIntent().getSerializableExtra(Extras.KEY_EXAM_COURSE_LIST);
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
    protected void initData() {
        super.initData();

        mPresenter.getTableList(RequestUtil.ScoreAchievementBody(
                UserUtil.getRoleld(),"", EXAM_ID, ""));
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    private void initScore() {
        RelativeLayout score_relative = findViewById(R.id.score_achievement_top);
        LinearLayout childRoot = findViewById(R.id.item_score_linear);
        score_relative.setClickable(true);

        List<ScoreAchievementBean.DataBean> titleList = scoreBean.getData();

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

        //传入adapter的数据形式
        Map<String, List<String>> listMap = new LinkedHashMap<>();

        for (int k = 0; k < scoreBean.getRows().size(); k++) {
            ScoreAchievementBean.RowsBean bean = scoreBean.getRows().get(k);
            List<ScoreAchievementBean.RowsBean.QuestListBean> questList = bean.getQuestList();

            ArrayList<String> strings = new ArrayList<>();
            strings.add(bean.getStudCode()); //学号
            strings.add(bean.getStudExamCode());//新教育好
            strings.add(bean.getClassName());
            strings.add(bean.getMyScore());
            strings.add(bean.getObjectScore());//客观题
            strings.add(bean.getSubjectScore()); //主观题
            strings.add(bean.getJointRank() + "/" + bean.getGradeRank() + "/" + bean.getClassRank());

            //两种情况（内容与标题相等，内容小于标题）
            if (titleList.size() == questList.size()) {
                for (int i = 0; i < questList.size(); i++) {
                    ArrayList<String> answerList = new ArrayList<>();
                    if (questList.get(i).getIsOption().equals("2")) { //客观题
                        answerList.add(TextUtils.isEmpty(questList.get(i).getScore()) ? "-" : questList.get(i).getScore()); //得分
                        answerList.add(questList.get(i).getStudAnswer());//作答
                    } else
                        answerList.add(TextUtils.isEmpty(questList.get(i).getScore()) ? "-" : questList.get(i).getScore()); //得分
                    strings.addAll(answerList);
                }
            } else {//内容小于标题
                Map<String, Integer> map = new HashMap<>();
                //查找缺失的题目
                for (int i = 0; i < titleList.size(); i++) {
                    map.put(titleList.get(i).getQuestionNum(), i);
                }
                for (int i = 0; i < questList.size(); i++) {
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
                    ScoreAchievementBean.RowsBean.QuestListBean r_bean = new ScoreAchievementBean.RowsBean.QuestListBean();
                    r_bean.setIsOption(titleList.get(i).getIsOption());
                    r_bean.setQuestionNum(titleList.get(i).getQuestionNum());
                    r_bean.setQuestionId(titleList.get(i).getQuestionId());
                    r_bean.setScore("-");
                    r_bean.setStudAnswer("-");
                    //questionNum从1开始，这里下标减一插入
                    questList.add(Integer.parseInt(titleList.get(i).getQuestionNum()) - 1, r_bean);
                }
                //构造adapter数据
                for (int i = 0; i < questList.size(); i++) {
                    ArrayList<String> answerList = new ArrayList<>();
                    if (questList.get(i).getIsOption().equals("2")) { //客观题
                        answerList.add(TextUtils.isEmpty(questList.get(i).getScore()) ? "-" : questList.get(i).getScore()); //得分
                        answerList.add(questList.get(i).getStudAnswer());//作答
                    } else
                        answerList.add(TextUtils.isEmpty(questList.get(i).getScore()) ? "-" : questList.get(i).getScore()); //得分
                    strings.addAll(answerList);
                }
            }

            listMap.put(bean.getStudName(), strings);
        }

        scoreRV.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //可以通用adapter
        TableScoreAdapter adapter = new TableScoreAdapter(listMap, scoreSSL);
        scoreRV.setAdapter(adapter);

        scoreRV.setOnTouchListener(getListener(scoreSSL));
        score_relative.setOnTouchListener(getListener(scoreSSL));

        //item点击
        scoreSSL.setOnItemClickListener(new SynScrollerLayout.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(ScoreAchievementActivity.this, "*****" + position, Toast.LENGTH_SHORT).show();
                toClass(ViewStudentAnswerSheetActivity.class,false,
                        Extras.EXAM_ID,scoreBean.getRows().get(position).getExamId(),
                        Extras.STUDENT_ID,scoreBean.getRows().get(position).getStudId(),
                        Extras.KEY_COURSE_ID,scoreBean.getRows().get(position).getCourseId(),
                        Extras.KEY_EXAM_COURSE_LIST,mCourseBeanList);
            }
        });
    }

    @Override
    public void getTableListSuccess(ScoreAchievementBean data) {
        scoreBean = data;
        initScore();
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
