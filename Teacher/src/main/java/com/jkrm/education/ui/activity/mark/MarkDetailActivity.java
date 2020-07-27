package com.jkrm.education.ui.activity.mark;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkDetailAdapter;
import com.jkrm.education.adapter.mark.MarkDetailByStudentAdapter;
import com.jkrm.education.adapter.mark.MarkDetailWithStudentStatusAdapter;
import com.jkrm.education.adapter.mark.MarkDetailWithStudentSwitchAdapter;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.rx.RxRefreshMarkDetailType;
import com.jkrm.education.bean.rx.RxStatisticsClassesType;
import com.jkrm.education.bean.rx.RxUnConnectType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.MarkDetailPresent;
import com.jkrm.education.mvp.views.MarkDetailView;
import com.jkrm.education.ui.activity.achievement.SeeAchievementActivity;
import com.jkrm.education.ui.activity.achievement.SeeChoiceAchievementActivity;
import com.jkrm.education.ui.activity.achievement.SeeChoiceAchievementOnlyOneActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/14.
 */

public class MarkDetailActivity extends AwMvpActivity<MarkDetailPresent> implements MarkDetailView.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawerLayout_studentSwitch)
    DrawerLayout mDrawerLayoutStudentSwitch;
    @BindView(R.id.btn_seeOriginal)
    Button mBtnSeeOriginal;
    @BindView(R.id.btn_seeScore)
    Button mBtnSeeScore;
    @BindView(R.id.btn_reMark)
    Button mBtnReMark;
    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_homeworkSubmitResult)
    TextView mTvHomeworkSubmitResult;
    @BindView(R.id.tv_currentStudent)
    TextView mTvCurrentStudent;
    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.rcv_dataStudentStatus)
    RecyclerView mRcvDataStudentStatus;
    @BindView(R.id.rcv_dataStudentSwitch)
    RecyclerView mRcvDataStudentSwitch;

    private MarkDetailAdapter mDetailAdapter;
    private MarkDetailByStudentAdapter mDetailByStudentAdapter;
    private MarkDetailWithStudentStatusAdapter mStudentStatusAdapter;
    private MarkDetailWithStudentSwitchAdapter mStudentSwitchAdapter;

    private RowsHomeworkBean mRowsHomeworkBean;
    private HomeworkDetailResultBean mHomeworkDetailResultBean;
    private String homeworkId = "";
    private String classId = "";
    private String currentStudentId = "";
    private AnswerSheetProgressResultBean mCurrentStudentBean;

    private List<GradQusetionBean> mQuestionList = new ArrayList<>();//题
    private List<GradQusetionBean> mQuestionListWithStudent = new ArrayList<>();//题目以及学生
    private List<AnswerSheetProgressResultBean> mStudentList=new ArrayList<>();

    /**
     * 是否按题批阅
     */
    private boolean markWithQuestion = true;

    /**
     * 是否查看原题
     */
    private boolean isSeeOriginPager = false;
    /**
     * 是否选中回评(回评批阅时展示所有题目学生, 否则仅展示未批阅的)
     */
    private boolean isSelectReMark = false;
    private int mSubmitNum;
    @Override
    protected MarkDetailPresent createPresenter() {
        return new MarkDetailPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImgAndRightView("批阅详情", "选择学生", () -> {
            //当前为按题批阅, 需抽屉选择学生, 当前为按人批阅, 点击直接切换到按题批阅
            if(markWithQuestion) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            } else {
               //TODO 切换到按题批阅UI
                mToolbar.setRightTextWithImg("选择学生");
                markWithQuestion = true;
                showView(mBtnReMark, true);
                showView(mTvHomeworkSubmitResult, markWithQuestion ? true : false);
                showView(mTvCurrentStudent, markWithQuestion ? false : true);
                setQuestionList();
            }
        });
        mToolbar.setRTextColor(R.color.blue);
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayoutStudentSwitch.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mDetailAdapter = new MarkDetailAdapter();
        mDetailByStudentAdapter = new MarkDetailByStudentAdapter();

        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        if(mRowsHomeworkBean != null) {
            homeworkId = mRowsHomeworkBean.getId();
            classId = mRowsHomeworkBean.getClasses().getId();
            refreshData();
            setText(mTvClasses, mRowsHomeworkBean.getClasses().getName());
            mSubmitNum=Integer.parseInt(mRowsHomeworkBean.getStatistics().getSubmitted());
            setText(mTvHomeworkSubmitResult, String.format(getString(R.string.format_homework_detail_submit_status), mRowsHomeworkBean.getClasses().getPopulation(), mRowsHomeworkBean.getStatistics().getSubmitted()));
        } else {
            showDialog("获取作业失败，数据无法展示完全");
        }

        mStudentStatusAdapter = new MarkDetailWithStudentStatusAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvDataStudentStatus, mStudentStatusAdapter, false);

        mStudentSwitchAdapter = new MarkDetailWithStudentSwitchAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvDataStudentSwitch, mStudentSwitchAdapter, false);
        if(isSelectReMark) {
            mBtnReMark.setSelected(true);
            mBtnReMark.setBackground(getResources().getDrawable(R.drawable.aw_bg_blue_radius_13));
            mBtnReMark.setTextColor(getResources().getColor(R.color.white));
        } else {
            mBtnReMark.setSelected(false);
            mBtnReMark.setBackground(getResources().getDrawable(R.drawable.aw_bg_gray_eaeef7));
            mBtnReMark.setTextColor(getResources().getColor(R.color.gray));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshMarkDetailType type) {
        if(type == null)
            return;
        refreshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxUnConnectType type) {
        if(type == null)
            return;
        mPresenter.answerSheetProgress(homeworkId, classId);
        refreshData();
        if(mSubmitNum>0){
            mSubmitNum=mSubmitNum-1;
            if(mSubmitNum==0){
                finish();
            }
            setText(mTvHomeworkSubmitResult, String.format(getString(R.string.format_homework_detail_submit_status), mRowsHomeworkBean.getClasses().getPopulation(), mSubmitNum+""));
        }
    }


    private void refreshData() {
        if(markWithQuestion) {
            mPresenter.getHomeworkDetail(homeworkId, classId);
        } else {
            mPresenter.getQustionAnswerWithSingleStudent(homeworkId, currentStudentId);
        }
    }

    @Override
    public void onRefresh() {
        refreshData();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mBtnReMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectReMark) {
                    isSelectReMark = false;
                    mBtnReMark.setSelected(false);
                    mBtnReMark.setBackground(getResources().getDrawable(R.drawable.aw_bg_gray_eaeef7));
                    mBtnReMark.setTextColor(getResources().getColor(R.color.gray));
                } else {
                    isSelectReMark = true;
                    mBtnReMark.setSelected(true);
                    mBtnReMark.setBackground(getResources().getDrawable(R.drawable.aw_bg_blue_radius_13));
                    mBtnReMark.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
        mBtnSeeOriginal.setOnClickListener(v -> {
            isSeeOriginPager = true;
            //当前为按题批阅, 需抽屉选择学生, 当前为按人批阅, 点击直接进入该学生的原卷展示
            if(markWithQuestion) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            } else {
                isSeeOriginPager = false;
                toClass(MarkOriginPaperActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS, mCurrentStudentBean);
            }
        });
        mBtnSeeScore.setOnClickListener(v -> toClass(SeeAchievementActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean));
        mDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GradQusetionBean bean = (GradQusetionBean) adapter.getItem(position);
            if(bean.isChoiceQuestion()) {
                toClass(SeeChoiceAchievementActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean);
            } else {
//                toClass(MarkActivityOld.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_GRADQUSETIONBEAN, bean);
                toClass(MarkActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_GRADQUSETIONBEAN, bean,
                        Extras.KEY_IS_RE_MARK, MyDateUtil.getMarkProgress(bean.getProgress())[0].equals(MyDateUtil.getMarkProgress(bean.getProgress())[1]) ? true : isSelectReMark,
                        Extras.KEY_BEAN_GRADQUESTIONLIST,(Serializable)mQuestionList,Extras.KEY_BEAN_GRADQUESTION_WITH_STUDENT_LIST,(Serializable)mStudentList
                );
            }
        });
        mDetailByStudentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GradQusetionBean bean = (GradQusetionBean) adapter.getItem(position);
            if(bean.isChoiceQuestion()) {
                toClass(SeeChoiceAchievementOnlyOneActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.STUDENT_ID, currentStudentId);
            } else {
                toClass(MarkActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_GRADQUSETIONBEAN, bean,
                        Extras.BOOLEAN_MARK_IS_BY_QUESTION, false, Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS, mCurrentStudentBean, Extras.KEY_IS_RE_MARK, isSelectReMark,
                        Extras.KEY_BEAN_GRADQUESTIONLIST,(Serializable)mQuestionList,Extras.KEY_BEAN_GRADQUESTION_WITH_STUDENT_LIST,(Serializable)mStudentList
                );
            }
//            toClass(MarkActivityOld.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_GRADQUSETIONBEAN, bean, Extras.BOOLEAN_MARK_IS_BY_QUESTION, false, Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS, mCurrentStudentBean);

        });
        mStudentStatusAdapter.setOnItemClickListener((adapter, view, position) -> {
            AnswerSheetProgressResultBean bean = (AnswerSheetProgressResultBean) adapter.getItem(position);
            mCurrentStudentBean = bean;
            //如果是查看原卷, 则进入原卷页面, 否则更换到按人批阅UI
            if(isSeeOriginPager && markWithQuestion) {
                toClass(MarkOriginPaperActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS, mCurrentStudentBean);
                mDrawerLayout.closeDrawers();
                isSeeOriginPager = false;
                return;
            }
            mToolbar.setRightTextWithImg("选择题目");
            markWithQuestion = false;
//            showView(mBtnReMark, false);
            showView(mTvHomeworkSubmitResult, markWithQuestion ? true : false);
            showView(mTvCurrentStudent, markWithQuestion ? false : true);
            mDrawerLayout.closeDrawers();
            setQuestionList();
            setText(mTvCurrentStudent, bean.getStudentName());
            currentStudentId = bean.getStudentId();
            refreshData();
        });
        mStudentSwitchAdapter.setOnItemClickListener((adapter, view, position) -> {
            mDrawerLayoutStudentSwitch.closeDrawers();
            AnswerSheetProgressResultBean bean = (AnswerSheetProgressResultBean) adapter.getItem(position);
            mCurrentStudentBean = bean;
            markWithQuestion = false;
            setText(mTvCurrentStudent, bean.getStudentName());
            currentStudentId = bean.getStudentId();
            refreshData();
        });
        mTvCurrentStudent.setOnClickListener(v -> {
            if (mDrawerLayoutStudentSwitch.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayoutStudentSwitch.closeDrawers();
            } else {
                mDrawerLayoutStudentSwitch.openDrawer(Gravity.RIGHT);
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isSeeOriginPager = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * 填充题目列表数据
     */
    private void setQuestionList() {
        mRcvData.setAdapter(null);
        if(markWithQuestion) {
            AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mDetailAdapter, false);
            if(AwDataUtil.isEmpty(mQuestionList)) {
                mDetailAdapter.clearData();
                mRcvData.removeAllViews();
                mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            } else {
                //无api, 本地处理其他接口返回数据整理, 展示为设计要求样式
                GradQusetionBean customBean = new GradQusetionBean();
                StringBuffer questionNums = new StringBuffer();
                for(GradQusetionBean temp : mQuestionList) {
                    if(temp.isChoiceQuestion()) {
                        questionNums.append(temp.getQuestionNum()).append("、");
                    }
                }
                if(!AwDataUtil.isEmpty(questionNums.toString())) {
                    String questionNumsStr = questionNums.toString().substring(0, questionNums.toString().length() - 1);
                    String[] questionArrays = questionNumsStr.split("、");
                    if(questionArrays.length > 8) {
                        questionNums = new StringBuffer();
                        questionNums.append(questionArrays[0]).append("、")
                                .append(questionArrays[1]).append("、")
                                .append(questionArrays[2]).append(" ").append("......").append(" ")
                                .append(questionArrays[questionArrays.length - 3]).append("、")
                                .append(questionArrays[questionArrays.length - 2]).append("、")
                                .append(questionArrays[questionArrays.length - 1]);
                        questionNumsStr = questionNums.toString();
                    }
                    customBean.setQuestionNum(questionNumsStr);
                    customBean.setTypeName("选择题");
                    customBean.setIsOption("2");

                    for (Iterator it = mQuestionList.iterator(); it.hasNext(); ) {
                        GradQusetionBean temp = (GradQusetionBean) it.next();
                        if(temp.isChoiceQuestion()) {
                            it.remove();
                        }
                    }
                    mQuestionList.add(0, customBean);
                }


                mDetailAdapter.addAllData(mQuestionList);
                mDetailAdapter.loadMoreComplete();
                mDetailAdapter.setEnableLoadMore(false);
                mDetailAdapter.disableLoadMoreIfNotFullPage(mRcvData);
            }
        } else {
            AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mDetailByStudentAdapter, false);
            if(AwDataUtil.isEmpty(mQuestionListWithStudent)) {
                mDetailByStudentAdapter.clearData();
                mRcvData.removeAllViews();
                mDetailByStudentAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            } else {
                //无api, 本地处理其他接口返回数据整理, 展示为设计要求样式
                GradQusetionBean customBean = new GradQusetionBean();
                StringBuffer questionNums = new StringBuffer();
                for(GradQusetionBean temp : mQuestionListWithStudent) {
                    if(temp.isChoiceQuestion()) {
                        questionNums.append(temp.getQuestionNum()).append("、");
                    }
                }
                if(!AwDataUtil.isEmpty(questionNums.toString())) {
                    String questionNumsStr = questionNums.toString().substring(0, questionNums.toString().length() - 1);
                    String[] questionArrays = questionNumsStr.split("、");
                    if(questionArrays.length > 8) {
                        questionNums = new StringBuffer();
                        questionNums.append(questionArrays[0]).append("、")
                                .append(questionArrays[1]).append("、")
                                .append(questionArrays[2]).append(" ").append("......").append(" ")
                                .append(questionArrays[questionArrays.length - 3]).append("、")
                                .append(questionArrays[questionArrays.length - 2]).append("、")
                                .append(questionArrays[questionArrays.length - 1]);
                        questionNumsStr = questionNums.toString();
                    }
                    customBean.setQuestionNum(questionNumsStr);
                    customBean.setTypeName("选择题");
                    customBean.setIsOption("2");

                    for (Iterator it = mQuestionListWithStudent.iterator(); it.hasNext(); ) {
                        GradQusetionBean temp = (GradQusetionBean) it.next();
                        if(temp.isChoiceQuestion()) {
                            it.remove();
                        }
                    }

                    mQuestionListWithStudent.add(0, customBean);
                }
                mDetailByStudentAdapter.addAllData(mQuestionListWithStudent);
                mDetailByStudentAdapter.loadMoreComplete();
                mDetailByStudentAdapter.setEnableLoadMore(false);
                mDetailByStudentAdapter.disableLoadMoreIfNotFullPage(mRcvData);
            }
        }
    }

    @Override
    public void getHomeworkDetailSuccess(HomeworkDetailResultBean bean) {
        mSflLayout.setRefreshing(false);
        mHomeworkDetailResultBean = bean;
        mQuestionList = mHomeworkDetailResultBean.getGradQusetion();
        if(!AwDataUtil.isEmpty(mQuestionList)) {
            //TODO 20190614服务器返回数据异常, 第一条是空数据, 去掉该条无用数据
            if(AwDataUtil.isEmpty(mQuestionList.get(0).getQuestionId())) {
                mQuestionList.remove(0);
            }
        }
        mPresenter.answerSheetProgress(homeworkId, classId);
        setQuestionList();
    }

    @Override
    public void getHomeworkDetailFail(String msg) {
        mSflLayout.setRefreshing(false);
        mPresenter.answerSheetProgress(homeworkId, classId);
        if(markWithQuestion) {
            mDetailAdapter.clearData();
            mRcvData.removeAllViews();
            mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {

        }
    }

    @Override
    public void answerSheetProgressSuccess(List<AnswerSheetProgressResultBean> list) {
        if(AwDataUtil.isEmpty(list)) {
            mStudentSwitchAdapter.clearData();
            mRcvDataStudentSwitch.removeAllViews();
            mStudentSwitchAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

            mStudentStatusAdapter.clearData();
            mRcvDataStudentStatus.removeAllViews();
            mStudentStatusAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mStudentList=list;
            mStudentSwitchAdapter.addAllData(list);
            mStudentSwitchAdapter.loadMoreComplete();
            mStudentSwitchAdapter.setEnableLoadMore(false);
            mStudentSwitchAdapter.disableLoadMoreIfNotFullPage(mRcvDataStudentSwitch);

            mStudentStatusAdapter.addAllData(list);
            mStudentStatusAdapter.loadMoreComplete();
            mStudentStatusAdapter.setEnableLoadMore(false);
            mStudentStatusAdapter.disableLoadMoreIfNotFullPage(mRcvDataStudentStatus);
        }
    }

    @Override
    public void getQustionAnswerWithSingleStudentSuccess(List<GradQusetionBean> list) {
        mSflLayout.setRefreshing(false);
        mQuestionListWithStudent = list;
        setQuestionList();
    }

    @Override
    public void getQustionAnswerWithSingleStudentFail(String msg) {
        mSflLayout.setRefreshing(false);
        mQuestionListWithStudent = new ArrayList<>();
        setQuestionList();
    }
}
