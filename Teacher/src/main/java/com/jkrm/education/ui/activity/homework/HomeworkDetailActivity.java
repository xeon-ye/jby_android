package com.jkrm.education.ui.activity.homework;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.bean.ClassesResponseBean;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.HomeworkDetailViewPagerAdapter;
import com.jkrm.education.adapter.mark.MarkHomeWordDetailGroupAdapter;
import com.jkrm.education.adapter.mark.MarkHomeworkDetailAdapter;
import com.jkrm.education.adapter.mark.MarkHomeworkDetailStudentAnswerAdapter;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean;
import com.jkrm.education.bean.result.HomeworkStudentAnswerWithSingleQuestionResultBean;
import com.jkrm.education.bean.result.QuestionSpecialResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailDurationType;
import com.jkrm.education.bean.rx.RxHomeworkDetailRatioType;
import com.jkrm.education.bean.rx.RxRefreshHomeworkDetailType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.HomeworkDetailPresent;
import com.jkrm.education.mvp.views.HomeworkDetailView;
import com.jkrm.education.ui.activity.FamousTeacherLectureActivity;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.QuestionExpandActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.ui.activity.VideoPointActivity;
import com.jkrm.education.ui.activity.achievement.SeeAchievementActivity;
import com.jkrm.education.ui.activity.mark.MarkDetailActivity;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.StudentListDialogFrament;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hzw on 2019/5/14.
 */

public class HomeworkDetailActivity extends AwMvpActivity<HomeworkDetailPresent> implements HomeworkDetailView.View {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.view_alpha)
    View mViewAlpha;
    @BindView(R.id.tv_studentResult)
    TextView mTvStudentResult;
    @BindView(R.id.rl_layoutBottom)
    RelativeLayout mRlLayoutBottom;
    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_homeworkSubmitResult)
    TextView mTvHomeworkSubmitResult;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_videoPoint)
    Button mBtnVideoPoint;
    @BindView(R.id.btn_seeScore)
    Button mBtnSeeScore;
    @BindView(R.id.btn_review)
    Button mBtnReview;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.rcv_dataStudentAnswer)
    RecyclerView mRcvDataStudentAnswer;

    private static final int TAG_SORT_QUESTION_NUM = 0;
    private static final int TAG_SORT_QUESTION_RATIO_INCREASE = 1;
    private static final int TAG_SORT_QUESTION_RATIO_REDUCE = 2;
    private static final int TAG_SORT_QUESTION_EXPLAIN = 3;
    @BindView(R.id.tv_subTitle)
    TextView mTvSubTitle;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll_bottomBtn)
    LinearLayout mLlBottomBtn;
    @BindView(R.id.tv_title_head)
    TextView mTvTitleHead;
    private IndicatorViewPager indicatorViewPager;
    private HomeworkDetailViewPagerAdapter mViewPagerAdapter;

    private MarkHomeworkDetailAdapter mDetailAdapter;
    /**
     * 作业列表每item数据
     */
    private RowsHomeworkBean mRowsHomeworkBean;
    /**
     * 该作业详情数据
     */
    private HomeworkDetailResultBean mHomeworkDetailResultBean;
    private MarkHomeworkDetailStudentAnswerAdapter mStudentAnswerAdapter;
    private String homeworkId = "";
    private String classid = "";
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    /**
     * 对点微课列表
     */
    private List<VideoPointResultBean> mVideoPointResultBeanList = new ArrayList<>();
    private List<RowsHomeworkBean> mRowsHomeworkBeans;
    private int mExtraPro;
    private View mHeaderView;
    private GradQusetionBean mGradQusetionBean;
    private List<ClassesResponseBean> mClassesResponseBeanList = new ArrayList<>();
    private MarkHomeWordDetailGroupAdapter mMarkHomeWordDetailGroupAdapter;
    private Map<String, List<GradQusetionBean>> mCollect;

    @Override
    protected HomeworkDetailPresent createPresenter() {
        return new HomeworkDetailPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homework_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbar("作业详情", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
       /* setToolbarWithBackImgAndRightView("作业详情", "题号", () -> {
            showView(mViewAlpha, true);
            AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlpha(mActivity, TestDataUtil.createHomeworkDetailType(), mToolbar,
                    () -> showView(mViewAlpha, false)
                    , bean -> {
                        showView(mViewAlpha, false);
                        if (!AwDataUtil.isEmpty(mToolbar.getRightText()) && mToolbar.getRightText().equals(bean)
                                || null == mHomeworkDetailResultBean
                                || AwDataUtil.isEmpty(mHomeworkDetailResultBean.getGradQusetion())) {
                            return;
                        }
                        if ("按题号排序".equals(bean)) {
                            mToolbar.setRightTextWithImg("题号");
                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_NUM);
                        } else if ("得分率排序".equals(bean)) {
                            mToolbar.setRightTextWithImg("得分率");
                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_INCREASE);
                        }
//                        else if("得分率降序".equals(bean)) {
//                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE);
//                        }
                    });

        });*/
        mToolbar.setRTextColor(R.color.blue);
        mToolbar.setRightImgWithTxt(R.mipmap.icon_sanjiao);
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        TextView textView = mToolbar.findViewById(R.id.toolbar_title);
        textView.setTypeface(CustomFontStyleUtil.getNewRomenType());
        mTvTitleHead.setTypeface(CustomFontStyleUtil.getNewRomenType());
    }

    @Override
    protected void initData() {
        super.initData();
//        AwEffectiveRequestViewUtil.setButtonEnableBlue(mActivity, mBtnVideoPoint, false);
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        mRowsHomeworkBeans = (List<RowsHomeworkBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK_LIST);//列表
        mExtraPro = getIntent().getIntExtra(Extras.KEY_BEAN_ROWS_HOMEWORK_PRO, 0);//当前位置
        if (mRowsHomeworkBean == null) {
            showDialogToFinish("获取作业数据失败");
            return;
        }
        homeworkId = mRowsHomeworkBean.getId();
        classid = mRowsHomeworkBean.getClasses().getId();
        refreshData();

        mDetailAdapter = new MarkHomeworkDetailAdapter();
        mMarkHomeWordDetailGroupAdapter = new MarkHomeWordDetailGroupAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mDetailAdapter, false);

        mHeaderView = getLayoutInflater().inflate(R.layout.inflate_homework_detail, null);
        FixedIndicatorView mScrollIndicator = mHeaderView.findViewById(R.id.scroll_indicator);
        SViewPager mScrollViewPager = mHeaderView.findViewById(R.id.scroll_viewPager);
        initScoreViewPager(mScrollIndicator, mScrollViewPager);
        mDetailAdapter.addHeaderView(mHeaderView);//添加了headerview, 注意position位置处理

        //初始化侧滑学生作答(答题详情)组件
        mStudentAnswerAdapter = new MarkHomeworkDetailStudentAnswerAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvDataStudentAnswer, mStudentAnswerAdapter, false);

        mPresenter.getExplainClasses(UserUtil.getTeacherId(), homeworkId);
        mDetailAdapter.setOnSortChickLister(new MarkHomeworkDetailAdapter.onSortChickLister() {


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSortChick(View view, TextView textView,String title) {
                boolean b = checkIsHaveExplan(mHomeworkDetailResultBean);
                if (b) {
                    AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlpha(mActivity, TestDataUtil.createHomeworkDetailType(), view,
                            () -> showView(mViewAlpha, false)
                            , bean -> {
                                showView(mViewAlpha, false);
                                if (!AwDataUtil.isEmpty(mToolbar.getRightText()) && mToolbar.getRightText().equals(bean)
                                        || null == mHomeworkDetailResultBean
                                        || AwDataUtil.isEmpty(mHomeworkDetailResultBean.getGradQusetion())) {
                                    return;
                                }

                                if ("按题号正序".equals(bean)) {
                                    textView.setText("按题号正序");
                                    setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_NUM,title);
                                } else if ("按得分率倒序".equals(bean)) {
                                    textView.setText("按得分率倒序");
                                    setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE,title);
                                } else if ("按需讲解人数倒序".equals(bean)) {
                                    textView.setText("按需讲解人数倒序");
                                    setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_EXPLAIN,title);
                                }
                                EventBus.getDefault().post(title);
//                        else if("得分率降序".equals(bean)) {
//                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE);
//                        }
                            });
                } else {
                    AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlpha(mActivity, TestDataUtil.createHomeworkDetailWithoutExplan(), view,
                            () -> showView(mViewAlpha, false)
                            , bean -> {
                                showView(mViewAlpha, false);
                                if (!AwDataUtil.isEmpty(mToolbar.getRightText()) && mToolbar.getRightText().equals(bean)
                                        || null == mHomeworkDetailResultBean
                                        || AwDataUtil.isEmpty(mHomeworkDetailResultBean.getGradQusetion())) {
                                    return;
                                }

                                if ("按题号正序".equals(bean)) {
                                    textView.setText("按题号正序");
                                    setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_NUM,title);
                                } else if ("按得分率倒序".equals(bean)) {
                                    textView.setText("按得分率倒序");
                                    setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE,title);
                                }
                            });
                }

            }
        });
    }

    /**
     * 检测所有问题是否有需要讲解
     *
     * @param homeworkDetailResultBean
     * @return
     */
    private boolean checkIsHaveExplan(HomeworkDetailResultBean homeworkDetailResultBean) {
        boolean isHaveExplat = false;
        for (GradQusetionBean gradQusetionBean : homeworkDetailResultBean.getGradQusetion()) {
            if (!"0".equals(gradQusetionBean.getExPlat())) {
                isHaveExplat = true;
            }
        }
        return isHaveExplat;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshHomeworkDetailType type) {
        if (type == null) {
            return;
        }
        refreshData();
    }



    private void refreshData() {
        //mRowsHomeworkBean = mRowsHomeworkBeans.get(mExtraPro);
        setText(mTvClasses, mRowsHomeworkBean.getClasses().getName());
        setText(mTvHomeworkSubmitResult, String.format(getString(R.string.format_homework_detail_submit_status), mRowsHomeworkBean.getClasses().getPopulation(), mRowsHomeworkBean.getStatistics().getSubmitted()));
       /* homeworkId = mRowsHomeworkBean.getId();
        classid = mRowsHomeworkBean.getClasses().getId();*/
        mPresenter.getHomeworkDetail(homeworkId, classid);
//        mPresenter.getVideoPointList(homeworkId);
        mPresenter.getVideoPointListNew(homeworkId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnVideoPoint.setOnClickListener(v -> {
            if (!AwDataUtil.isEmpty(mVideoPointResultBeanList)) {
                toClass(VideoPointActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, mVideoPointResultBeanList);
            } else {
                showDialog("暂无微课视频");
            }
        });
        mBtnReview.setOnClickListener(v -> toClass(MarkDetailActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean, Extras.KEY_BEAN_DETAIL_HOMEWORK, mHomeworkDetailResultBean));
        mBtnSeeScore.setOnClickListener(v -> {
//                mPresenter.getHomeworkStudentScoreList(homeworkId, classid);
            toClass(SeeAchievementActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, mRowsHomeworkBean);
        });
        mDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GradQusetionBean bean = (GradQusetionBean) adapter.getItem(position);
            AwLog.d("点击的题目是: " + bean.getQuestionNum() + " ,类型: " + bean.getTypeName());
            switch (view.getId()) {
                case R.id.rl_questionTitle:
                    toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_BOOLEAN, bean.isChoiceQuestion() ? false : true, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_studentAnswer:
                    mGradQusetionBean = mDetailAdapter.getData().get(position);
                    String rightResult = bean.isChoiceQuestion() ? bean.getProdAnswer() : MyDateUtil.replace(bean.getMaxScore());
                    mPresenter.getHomeworkStudentScoreWithQuestionSingle(rightResult, homeworkId, classid, bean.getQuestionId());
                    if (bean.isChoiceQuestion()) {
                        setText(mTvStudentResult, "作答");
                    } else {
                        setText(mTvStudentResult, "得分");
                    }
                    //插入测试数据
                    if (bean.isChoiceQuestion()) {
                        setText(mTvTitle, "第" + bean.getQuestionNum() + "题 【" + Html.fromHtml(rightResult).toString() + "】");
                    } else {
                        setText(mTvTitle, "第" + bean.getQuestionNum() + "题 【" + Html.fromHtml(rightResult).toString() + "分】");
                    }
                    if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        mDrawerLayout.closeDrawers();
                    } else {
                        mDrawerLayout.openDrawer(Gravity.RIGHT);
                    }

                    break;
                case R.id.btn_seeSpecial:
                    mPresenter.getQuestionSpecial(homeworkId, bean.getQuestionId());
                    break;
                case R.id.btn_questionExpand:
                    toClass(QuestionExpandActivity.class, false, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_famousTeacherLecture:
                    mPresenter.getVideos(bean.getQuestionId());
                    break;
                case R.id.tv_exPlat:
                    mPresenter.getExplainStudent(bean.getHomeworkId(), bean.getQuestionId());
                    break;
            }
        });
        mStudentAnswerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeworkStudentAnswerWithSingleQuestionResultBean resultBean = (HomeworkStudentAnswerWithSingleQuestionResultBean) adapter.getItem(position);
                if (!resultBean.isChoiceQuestion() && !"2".equals(mGradQusetionBean.getIsOption())) {
                    toClass(ImgActivity.class, false, Extras.IMG_URL, resultBean.getRowScan());
                }
            }
        });
        mTvClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlpha(mActivity, mClassesResponseBeanList, mTvClasses,
                        () -> showView(mViewAlpha, false)
                        , bean -> {
                            showView(mViewAlpha, false);
                            if (!AwDataUtil.isEmpty(mToolbar.getRightText()) && mToolbar.getRightText().equals(bean)
                                    || null == mHomeworkDetailResultBean
                                    || AwDataUtil.isEmpty(mHomeworkDetailResultBean.getGradQusetion())) {
                                return;
                            }
                            ClassesResponseBean classesResponseBean = (ClassesResponseBean) bean;
                            homeworkId = classesResponseBean.getHomeId();
                            classid = classesResponseBean.getClassId();
                            mPresenter.getHomeworkDetail(homeworkId, classid);
                            mTvClasses.setText(classesResponseBean.getClassName());
                            changemRowsHomeworkBean(homeworkId, classid);
//        mPresenter.getVideoPointList(homeworkId);
                            mPresenter.getVideoPointListNew(homeworkId);
                           /* if ("按题号排序".equals(bean)) {
                                mToolbar.setRightTextWithImg("题号");
                                setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_NUM);
                            } else if ("得分率排序".equals(bean)) {
                                mToolbar.setRightTextWithImg("得分率");
                                setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_INCREASE);
                            }*/
//                        else if("得分率降序".equals(bean)) {
//                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE);
//                        }
                        });

            }
        });
        mTvSort.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlpha(mActivity, TestDataUtil.createHomeworkDetailType(), mTvClasses,
                        () -> showView(mViewAlpha, false)
                        , bean -> {
                            showView(mViewAlpha, false);
                            if (!AwDataUtil.isEmpty(mToolbar.getRightText()) && mToolbar.getRightText().equals(bean)
                                    || null == mHomeworkDetailResultBean
                                    || AwDataUtil.isEmpty(mHomeworkDetailResultBean.getGradQusetion())) {
                                return;
                            }

                            if ("按题号排序".equals(bean)) {
                                mTvSort.setText("题号");
                                setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_NUM,"");
                            } else if ("得分率排序".equals(bean)) {
                                mTvSort.setText("得分率");
                                setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_INCREASE,"");
                            }
//                        else if("得分率降序".equals(bean)) {
//                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE);
//                        }
                        });
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void changemRowsHomeworkBean(String homeworkId, String classid) {
        for (int i = 0; i < mRowsHomeworkBeans.size(); i++) {
            if (homeworkId.equals(mRowsHomeworkBeans.get(i).getId())) {
                mRowsHomeworkBean = mRowsHomeworkBeans.get(i);
                refreshData();
                mExtraPro = i;
            }
        }
    }

    private void initScoreViewPager(FixedIndicatorView mScrollIndicator, SViewPager mScrollViewPager) {
        mScrollIndicator.setScrollBar(new ColorBar(mActivity, getResources().getColor(R.color.color_0093FF), AwDisplayUtil.dipToPix(mActivity, 2)));
        int selectColor = getResources().getColor(R.color.color_0093FF);
        int unSelectColor = getResources().getColor(R.color.black);
        mScrollIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor));
        mScrollViewPager.setOffscreenPageLimit(2);
        mScrollViewPager.setCanScroll(false);/// 禁止viewpager的滑动事件
        indicatorViewPager = new IndicatorViewPager(mScrollIndicator, mScrollViewPager);
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        mViewPagerAdapter = new HomeworkDetailViewPagerAdapter(getSupportFragmentManager(), mActivity);
        indicatorViewPager.setAdapter(mViewPagerAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData(HomeworkDetailResultBean bean, int sortTag,String sortTitle) {
        List<GradQusetionBean> list = bean.getGradQusetion();
        mCollect = new HashMap<>();
        for (GradQusetionBean gradQusetionBean : list) {
            String title = gradQusetionBean.getTitle();
            if(AwDataUtil.isEmpty(title)){
                title="答题详情";
            }if(AwDataUtil.isEmpty(sortTitle)){
                sortTitle="答题详情";
            }
            if (mCollect.containsKey(title)) {
                mCollect.get(title).add(gradQusetionBean);
            } else {
                ArrayList<GradQusetionBean> gradQusetionBeans = new ArrayList<>();
                gradQusetionBeans.add(gradQusetionBean);

                mCollect.put(title, gradQusetionBeans);
            }
        }
        list.clear();
        Set<String> strings = mCollect.keySet();
        for (String string : strings) {
            List<GradQusetionBean> gradQusetionBeans = mCollect.get(string);
            list.addAll(gradQusetionBeans);
        }
        if (AwDataUtil.isEmpty(list)) {
            mDetailAdapter.clearData();
            mRcvData.removeAllViews();
            mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            //TODO 20190614服务器返回数据异常, 第一条是空数据, 去掉该条无用数据
            if (AwDataUtil.isEmpty(list.get(0).getQuestionId())) {
                list.remove(0);
            }

            List<GradQusetionBean> gradQusetionBeans = mCollect.get(sortTitle);
            switch (sortTag) {
                case TAG_SORT_QUESTION_NUM:

               /*     Collections.sort(list, new Comparator<GradQusetionBean>() {
                        @Override
                        public int compare(GradQusetionBean bean, GradQusetionBean t1) {
                            int i = list.indexOf(bean.getTitle());
                            int j = list.indexOf(t1.getTitle());
                            return i - j;
                        }
                    });*/
                   /* Map<String, List<GradQusetionBean>> collect = list.stream().collect(Collectors.groupingBy(GradQusetionBean::getTitle));
                    list.clear();
                    Set<String> strings = collect.keySet();
                    for (String string : strings) {
                        List<GradQusetionBean> gradQusetionBeans = collect.get(string);
                        Collections.sort(gradQusetionBeans, new Comparator<GradQusetionBean>() {
                            @Override
                            public int compare(GradQusetionBean bean, GradQusetionBean t1) {
                                if(Float.parseFloat(bean.getQuestionNum())>=Float.parseFloat(t1.getQuestionNum())){
                                    return 1;
                                }
                                return -1;
                            }
                        });
                        list.addAll(gradQusetionBeans);
                    }*/
                   if(null!=gradQusetionBeans){
                       Collections.sort(gradQusetionBeans, (o1, o2) -> {
                           String questionNum1 = o1.getQuestionNum();
                           String questionNum2 = o2.getQuestionNum();
                           if (AwDataUtil.isEmpty(questionNum1)) {
                               questionNum1 = AwBaseConstant.COMMON_INVALID_VALUE;
                           }
                           if (AwDataUtil.isEmpty(questionNum2)) {
                               questionNum2 = AwBaseConstant.COMMON_INVALID_VALUE;
                           }
                           if (Float.parseFloat(questionNum1) >= Float.parseFloat(questionNum2)) {
                               return 1;
                           } else {
                               return -1;
                           }

                       });
                   }

                    break;
                case TAG_SORT_QUESTION_RATIO_INCREASE:
                /*    Map<String, List<GradQusetionBean>> collect1 = list.stream().collect(Collectors.groupingBy(GradQusetionBean::getTitle));
                    list.clear();
                    Set<String> strings1 = collect1.keySet();
                    for (String string : strings1) {
                        List<GradQusetionBean> gradQusetionBeans = collect1.get(string);
                        Collections.sort(gradQusetionBeans, new Comparator<GradQusetionBean>() {
                            @Override
                            public int compare(GradQusetionBean bean, GradQusetionBean t1) {
                                if (Float.parseFloat(bean.getRatio()) >= Float.parseFloat(t1.getRatio())) {
                                    return 1;
                                }
                                return -1;
                            }
                        });
                        list.addAll(gradQusetionBeans);
                    }*/
                    Collections.sort(gradQusetionBeans, (o1, o2) -> {
                        String ratio1 = o1.getRatio();
                        String ratio2 = o2.getRatio();
                                    if (AwDataUtil.isEmpty(ratio1)) {
                                        ratio1 = AwBaseConstant.COMMON_INVALID_VALUE;
                                    }
                                    if (AwDataUtil.isEmpty(ratio2)) {
                                        ratio2 = AwBaseConstant.COMMON_INVALID_VALUE;
                                    }
                                    if (Float.parseFloat(ratio1) >= Float.parseFloat(ratio2)) {
                                        return 1;
                                    } else {
                                        return -1;
                                    }


                    });

                    break;
                case TAG_SORT_QUESTION_RATIO_REDUCE:
                    Collections.sort(gradQusetionBeans, (o1, o2) -> {
                        /*String ratio1 = o1.getRatio();
                        String ratio2 = o2.getRatio();
                                    if (AwDataUtil.isEmpty(ratio1)) {
                                        ratio1 = AwBaseConstant.COMMON_INVALID_VALUE;
                                    }
                                    if (AwDataUtil.isEmpty(ratio2)) {
                                        ratio2 = AwBaseConstant.COMMON_INVALID_VALUE;
                                    }
                                    if (Float.parseFloat(ratio2) >= Float.parseFloat(ratio1)) {
                                        return 1;
                                    } else {
                                        return -1;
                                    }*/
                        if(o1 == null && o2 == null) {
                            return 0;
                        }
                        if(o1 == null) {
                            return -1;
                        }
                        if(o2 == null) {
                            return 1;
                        }
                        if(!AwDataUtil.isEmpty(o1.getRatio())&&!AwDataUtil.isEmpty(o2.getRatio())){
                            if(Float.parseFloat(o2.getRatio()) > Float.parseFloat(o1.getRatio())) {
                                return 1;
                            }
                            if(Float.parseFloat(o2.getRatio()) < Float.parseFloat(o1.getRatio())) {
                                return -1;
                            }
                        }

                        return 0;

                    });
                    /*Map<String, List<GradQusetionBean>> collect3 = list.stream().collect(Collectors.groupingBy(GradQusetionBean::getTitle));
                    list.clear();
                    Set<String> strings3 = collect3.keySet();
                    for (String string : strings3) {
                        List<GradQusetionBean> gradQusetionBeans = collect3.get(string);
                        Collections.sort(gradQusetionBeans, new Comparator<GradQusetionBean>() {
                            @Override
                            public int compare(GradQusetionBean bean, GradQusetionBean t1) {
                                if (Float.parseFloat(bean.getRatio()) >= Float.parseFloat(t1.getRatio())) {
                                    return 1;
                                }
                                return -1;
                            }
                        });
                        list.addAll(gradQusetionBeans);
                    }*/
                    break;
                case TAG_SORT_QUESTION_EXPLAIN:
                    /*Map<String, List<GradQusetionBean>> collect2 = list.stream().collect(Collectors.groupingBy(GradQusetionBean::getTitle));
                    list.clear();
                    Set<String> strings2 = collect2.keySet();
                    for (String string : strings2) {
                        List<GradQusetionBean> gradQusetionBeans = collect2.get(string);
                        Collections.sort(gradQusetionBeans, new Comparator<GradQusetionBean>() {
                            @Override
                            public int compare(GradQusetionBean gradQusetionBean, GradQusetionBean t1) {
                                return new Double(t1.getExPlat()).compareTo(new Double(gradQusetionBean.getExPlat()));
                            }
                        });
                        list.addAll(gradQusetionBeans);
                    }*/
                    Collections.sort(gradQusetionBeans, (o1, o2) -> {
                        String ratio1 = o1.getExPlat();
                        String ratio2 = o2.getExPlat();
                            if (AwDataUtil.isEmpty(ratio1)) {
                                ratio1 = AwBaseConstant.COMMON_INVALID_VALUE;
                            }
                            if (AwDataUtil.isEmpty(ratio2)) {
                                ratio2 = AwBaseConstant.COMMON_INVALID_VALUE;
                            }
                            if (Float.parseFloat(ratio2) >= Float.parseFloat(ratio1)) {
                                return 1;
                            } else {
                                return -1;
                            }


                    });
                   /* Collections.sort(list, new Comparator<GradQusetionBean>() {
                        @Override
                        public int compare(GradQusetionBean gradQusetionBean, GradQusetionBean t1) {
                            return new Double(t1.getExPlat()).compareTo(new Double(gradQusetionBean.getExPlat()));
                        }
                    });*/
                    break;
            }
            list.clear();
            for (String string : strings) {
                List<GradQusetionBean> beans = mCollect.get(string);
                list.addAll(beans);
            }
            mDetailAdapter.addAllData(list);
            mDetailAdapter.loadMoreComplete();
            mDetailAdapter.setEnableLoadMore(false);
            mDetailAdapter.disableLoadMoreIfNotFullPage(mRcvData);
        }

        List<HomeworkDetailResultBean.QuestionScoreBean> questionScoreBeanList = bean.getQuestionScore();
        /*if (!AwDataUtil.isEmpty(questionScoreBeanList)) {
            switch (sortTag) {
                case TAG_SORT_QUESTION_NUM:
                    Collections.sort(questionScoreBeanList, (o1, o2) -> {
                        String questionNum1 = o1.getClassScoredRatio().getQuestionNum();
                        String questionNum2 = o2.getClassScoredRatio().getQuestionNum();
                        if (AwDataUtil.isEmpty(questionNum1)) {
                            questionNum1 = AwBaseConstant.COMMON_INVALID_VALUE;
                        }
                        if (AwDataUtil.isEmpty(questionNum2)) {
                            questionNum2 = AwBaseConstant.COMMON_INVALID_VALUE;
                        }
                        if (Float.parseFloat(questionNum1) >= Float.parseFloat(questionNum2)) {
                            return 1;
                        } else {
                            return -1;
                        }
                    });
                    break;
                case TAG_SORT_QUESTION_RATIO_INCREASE:
                    Collections.sort(questionScoreBeanList, (o1, o2) -> {
                        String ratio1 = o1.getClassScoredRatio().getRatio();
                        String ratio2 = o2.getClassScoredRatio().getRatio();
                        if (AwDataUtil.isEmpty(ratio1)) {
                            ratio1 = AwBaseConstant.COMMON_INVALID_VALUE;
                        }
                        if (AwDataUtil.isEmpty(ratio2)) {
                            ratio2 = AwBaseConstant.COMMON_INVALID_VALUE;
                        }
                        if (Float.parseFloat(ratio1) >= Float.parseFloat(ratio2)) {
                            return 1;
                        } else {
                            return -1;
                        }
                    });
                    break;
                case TAG_SORT_QUESTION_RATIO_REDUCE:
                    Collections.sort(questionScoreBeanList, (o1, o2) -> {
                        String ratio1 = o1.getClassScoredRatio().getRatio();
                        String ratio2 = o2.getClassScoredRatio().getRatio();
                        if (AwDataUtil.isEmpty(ratio1)) {
                            ratio1 = AwBaseConstant.COMMON_INVALID_VALUE;
                        }
                        if (AwDataUtil.isEmpty(ratio2)) {
                            ratio2 = AwBaseConstant.COMMON_INVALID_VALUE;
                        }
                        if (Float.parseFloat(ratio2) <= Float.parseFloat(ratio1)) {
                            return 1;
                        } else {
                            return -1;
                        }
                    });
                    break;
            }
        }*/
        new Handler().postDelayed(() -> {
            EventBus.getDefault().postSticky(new RxHomeworkDetailRatioType(questionScoreBeanList));
        }, 300);
        new Handler().postDelayed(() -> {
            EventBus.getDefault().postSticky(new RxHomeworkDetailDurationType(bean.getHomeworkDurat()));
        }, 300);
    }

    private void classifyData() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getHomeworkDetailSuccess(HomeworkDetailResultBean bean) {
        mHomeworkDetailResultBean = bean;
        mToolbar.setToolbarTitle(mRowsHomeworkBean.getName());
        mToolbar.setToolbarMaxEms(10);
        mTvTitleHead.setText(mRowsHomeworkBean.getName());
        mTvTitleHead.setTypeface(CustomFontStyleUtil.getNewRomenType());
        mTvTitleHead.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTitleHead.setHorizontallyScrolling(true);
        setData(bean, TAG_SORT_QUESTION_NUM,"");
    }

    @Override
    public void getHomeworkDetailFail(String msg) {
        mDetailAdapter.clearData();
        mRcvData.removeAllViews();
        mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));

    }

    @Override
    public void getVideosSuccess(VideoResultBean result) {
        if (result == null) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        if (result.getQuestionVideo() == null && AwDataUtil.isEmpty(result.getCataVideos())) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        toClass(FamousTeacherLectureActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, result);
    }

    @Override
    public void getVideoFail(String msg) {
        showMsg(getString(R.string.hint_no_famouse_teacher_video));
    }

    @Override
    public void getHomeworkStudentScoreWithQuestionSingleSuccess(String rightResult, List<HomeworkStudentAnswerWithSingleQuestionResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            mStudentAnswerAdapter.clearData();
            mRcvDataStudentAnswer.removeAllViews();
            mStudentAnswerAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mStudentAnswerAdapter.addAllData(rightResult, list);
            mStudentAnswerAdapter.loadMoreComplete();
            mStudentAnswerAdapter.setEnableLoadMore(false);
            mStudentAnswerAdapter.disableLoadMoreIfNotFullPage(mRcvDataStudentAnswer);
            mRcvDataStudentAnswer.scrollToPosition(0);
        }
    }

    @Override
    public void getHomeworkStudentScoreWithQuestionSingleFail(String msg) {
        showDialog(msg);
    }

    @Override
    public void getHomeworkStudentScoreListSuccess(String model) {

    }

    @Override
    public void getHomeworkStudentScoreListFail(String msg) {

    }

    @Override
    public void getQuestionSpecialSuccess(List<QuestionSpecialResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            showMsg("暂无典型题");
            return;
        }
        toClass(SeeSpecialActivity.class, false, Extras.KEY_BEAN_SPECIAL_LIST, list);
    }

    @Override
    public void getQuestionSpecialFail(String msg) {
        showMsg("获取典型题失败");
    }

    @Override
    public void getVideoPointListSuccess(List<VideoPointResultBean> list) {
        mVideoPointResultBeanList = list;
        if (AwDataUtil.isEmpty(list)) {
            // AwEffectiveRequestViewUtil.setButtonEnableBlue(mActivity, mBtnVideoPoint, false);
            showView(mBtnVideoPoint, false);
        }
    }

    @Override
    public void getVideoPointListFail(String msg) {
        // AwEffectiveRequestViewUtil.setButtonEnableBlue(mActivity, mBtnVideoPoint, false);
        showView(mBtnVideoPoint, false);

    }

    @Override
    public void getVideoPointListNewSuccess(List<VideoPointResultBean> list) {
        mVideoPointResultBeanList = list;
        if (AwDataUtil.isEmpty(list)) {
            //  AwEffectiveRequestViewUtil.setButtonEnableBlue(mActivity, mBtnVideoPoint, false);
            showView(mBtnVideoPoint, false);

        }
    }

    @Override
    public void getVideoPointListNewFail(String msg) {
        // AwEffectiveRequestViewUtil.setButtonEnableBlue(mActivity, mBtnVideoPoint, false);
        showView(mBtnVideoPoint, false);

    }

    @Override
    public void getExplainClassesSuccess(List<ClassesResponseBean> data) {
        mClassesResponseBeanList = data;
    }


    @Override
    public void getExplainClassesFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getExplainStudentSuccess(List<ExplainStudentBean> data) {
        StudentListDialogFrament studentListDialogFrament = new StudentListDialogFrament();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Extras.KEY_STUDENT_LIST, (Serializable) data);
        studentListDialogFrament.setArguments(bundle);
        studentListDialogFrament.show(getSupportFragmentManager(), "");
    }


    @Override
    public void getExplainStudentFail(String msg) {
        showMsg(msg);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (touchEventInView(mHeaderView, event.getX(), event.getY()) || touchEventInView(mTvTitle, event.getX(), event.getY())) {
            return super.dispatchTouchEvent(event);
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if (x1 - x2 > 100 && Math.abs(y2 - y1) < 40) {
                if (mExtraPro < mRowsHomeworkBeans.size() - 1) {
                    mExtraPro++;
                    refreshData();
                } else {
                    showMsg("已是最后一份作业");
                }
                Log.e(TAG, "左滑");
            } else if (x2 - x1 > 100 && Math.abs(y2 - y1) < 40) {
                if (mExtraPro > 0) {
                    mExtraPro--;
                    refreshData();
                } else {
                    showMsg("已是第一份作业");
                }
                AwLog.e(TAG, "右滑");
            }
        }
        AwLog.e(TAG, x1 + "---" + x2 + "---" + y1 + "---" + y2 + "---");
        return super.dispatchTouchEvent(event);
    }

    /**
     * 该方法检测一个点击事件是否落入在一个View内，换句话说，检测这个点击事件是否发生在该View上。
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean touchEventInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];

        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }

        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
