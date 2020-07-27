package com.jkrm.education.ui.activity.homework;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDisplayUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.HomeworkDetailViewPagerAdapter;
import com.jkrm.education.adapter.MarkHomeworkDetailAdapter;
import com.jkrm.education.bean.QuestionBasketAddRequestBean;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.MaxScoreResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.rx.RxHomeworkDetailType;
import com.jkrm.education.bean.rx.RxRefreshQuestionBasketType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.HomeworkDetailPresent;
import com.jkrm.education.mvp.views.HomeworkDetailView;
import com.jkrm.education.mvp.views.OnlineAnswerChoiceView;
import com.jkrm.education.ui.activity.FamousTeacherLectureActivity;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
import com.jkrm.education.ui.activity.OnlineGroupObjectiveQuestionsActivity;
import com.jkrm.education.ui.activity.OnlineMultipleChoiceActivity;
import com.jkrm.education.ui.activity.OnlineNonGroupSubjectiveQuestionsActivity;
import com.jkrm.education.ui.activity.OnlineNonMultipleChoiceActivity;
import com.jkrm.education.ui.activity.OnlineSubjectiveQuestionsOfGroupQuestionsActivity;
import com.jkrm.education.ui.activity.OriginPaperActivity;
import com.jkrm.education.ui.activity.QuestionExpandActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.ui.activity.VideoPointActivity;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hzw on 2019/5/24.
 */

public class HomeworkDetailActivity extends AwMvpActivity<HomeworkDetailPresent> implements HomeworkDetailView.View {

    @BindView(R.id.tv_scoreResult)
    TextView mTvScoreResult;
    @BindView(R.id.tv_videoPoint)
    TextView mTvVideoPoint;
    @BindView(R.id.view_alpha)
    View mViewAlpha;
    @BindView(R.id.tv_classeAverage)
    TextView mTvClasseAverage;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    private static final int TAG_SORT_QUESTION_NUM = 0;
    private static final int TAG_SORT_QUESTION_RATIO_INCREASE = 1;
    private static final int TAG_SORT_QUESTION_RATIO_REDUCE = 2;
    private static final int TAG_SORT_QUESTION_RATIO_AVERAGE = 3;
    @BindView(R.id.tv_exam)
    TextView mTvExam;
    private IndicatorViewPager indicatorViewPager;
    private HomeworkDetailViewPagerAdapter mViewPagerAdapter;

    private MarkHomeworkDetailAdapter mDetailAdapter;

    private RowsHomeworkBean mRowsHomeworkBean;
    private String sheetId = "";
    private String homeworkId = "";
    //得分 展示在哪个柱状图里
    public static String mStrAverageGrade="";

    private List<AnswerSheetDataDetailResultBean> mAnswerSheetDataDetailResultBeanList = new ArrayList<>();
    /**
     * 正在添加移出题篮的问题bean
     */
    private AnswerSheetDataDetailResultBean mAnswerSheetDataDetailResultBean;
    /**
     * 对点微课列表
     */
    private List<VideoPointResultBean> mVideoPointResultBeanList = new ArrayList<>();

    private List<MarkBean> mMarkBeanList=new ArrayList<>();

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
        mMarkBeanList.add(new MarkBean(true,"按题号排序"));
        mMarkBeanList.add(new MarkBean(false,"得分率排序"));
        setToolbarWithBackImgAndRightView("作业详情", "题号", () -> {
            showView(mViewAlpha, true);
            AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlphaWithIcon(mActivity,mMarkBeanList, mToolbar,
                    () -> HomeworkDetailActivity.this.showView(mViewAlpha, false)
                    , bean -> {
                        showView(mViewAlpha, false);
                        if (!AwDataUtil.isEmpty(mToolbar.getRightText()) && mToolbar.getRightText().equals(bean)
                                || AwDataUtil.isEmpty(mAnswerSheetDataDetailResultBeanList)) {
                            return;
                        }
                        if ("按题号排序".equals(((MarkBean) bean).getTitle())) {
                            mToolbar.setRightTextWithImg("题号");
                            setData(TAG_SORT_QUESTION_NUM);
                        } else if ("得分率排序".equals(((MarkBean) bean).getTitle())) {
                            mToolbar.setRightTextWithImg("得分率");
                            setData(TAG_SORT_QUESTION_RATIO_INCREASE);
                        }
                        //                        else if("得分率降序".equals(bean)) {
                        //                            setData(mHomeworkDetailResultBean, TAG_SORT_QUESTION_RATIO_REDUCE);
                        //                        }
                    });

        });
        mToolbar.setRTextColor(R.color.blue);
        mToolbar.setRightImgWithTxt(R.mipmap.icon_sanjiao);
        TextView textView = mToolbar.findViewById(R.id.toolbar_title);
        textView.setTypeface(CustomFontStyleUtil.getNewRomenType());
    }

    @Override
    protected void initData() {
        super.initData();
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        if (mRowsHomeworkBean == null) {
            showDialogToFinish("获取作业数据失败");
            return;
        }
        if("2".equals(mRowsHomeworkBean.getOrigin())){
            showView(mTvExam,false);
        }

        mToolbar.setToolbarMaxEms(10);
        mToolbar.setToolbarTitle(mRowsHomeworkBean.getHomeworkName());
        sheetId = mRowsHomeworkBean.getId();
        homeworkId = mRowsHomeworkBean.getHomeworkId();
        setText(mTvScoreResult, "得分" + MyDateUtil.replace(mRowsHomeworkBean.getScore()) + "分");
        mStrAverageGrade=mRowsHomeworkBean.getScore();
        setText(mTvClasseAverage, "，班级均分" + AwConvertUtil.double2String(Double.parseDouble(mRowsHomeworkBean.getAverageGrade()), 2)  + "分");
        setScoreStyle();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AwLog.d("homeworkId 发送订阅, homeworkid是: " + homeworkId);
                EventBus.getDefault().postSticky(new RxHomeworkDetailType(homeworkId));
            }
        }, 800);


        mDetailAdapter = new MarkHomeworkDetailAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mDetailAdapter, false);

        View headerView = getLayoutInflater().inflate(R.layout.inflate_homework_detail, null);
        FixedIndicatorView mScrollIndicator = headerView.findViewById(R.id.scroll_indicator);
        SViewPager mScrollViewPager = headerView.findViewById(R.id.scroll_viewPager);
        initScoreViewPager(mScrollIndicator, mScrollViewPager);
        mDetailAdapter.addHeaderView(headerView);//添加了headerview, 注意position位置处理

        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshQuestionBasketType type) {
        if (type != null && !type.getTag().equals(RxRefreshQuestionBasketType.TAG_HOMEWORK_DETAIL)) {
            if (AwDataUtil.isEmpty(mAnswerSheetDataDetailResultBeanList)) {
                return;
            }
            boolean needRefresh = false;
            List<String> questionIds = type.getQuestionIds();
            for (AnswerSheetDataDetailResultBean temp : mAnswerSheetDataDetailResultBeanList) {
                for (String tempId : questionIds) {
                    if (temp.getQuestionId().equals(tempId)) {
                        if (type.isDel()) {
                            temp.setPractice("0");
                        } else {
                            temp.setPractice("1");
                        }
                        needRefresh = true;
                    }
                }
            }
            if (needRefresh) {
                mDetailAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvVideoPoint.setOnClickListener(v -> {
            if (!AwDataUtil.isEmpty(mVideoPointResultBeanList)) {
                //不传递templateId到下一个页面
                toClass(VideoPointActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, mVideoPointResultBeanList, Extras.COMMON_PARAMS, mRowsHomeworkBean.getHomeworkName());
//                toClass(VideoPointActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, mVideoPointResultBeanList, Extras.COMMON_PARAMS, mRowsHomeworkBean.getHomeworkName(),
//                        Extras.KEY_TEMPLATE_ID, mRowsHomeworkBean.getTemplateId());
            } else {
                showDialog("暂无微课视频");
            }
        });
        mTvExam.setOnClickListener(view -> {
            toClass(OriginPaperActivity.class,false,Extras.KEY_BEAN_ROWS_HOMEWORK,mRowsHomeworkBean);//查看原卷
        });

        mDetailAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            AnswerSheetDataDetailResultBean bean = (AnswerSheetDataDetailResultBean) adapter.getItem(position);
            AwLog.d("点击的题目是: " + bean.getQuestionNum() + " ,类型: " + bean.getTypeName());
            switch (view.getId()) {
                case R.id.iv_img:
                    toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getRawScan());
                    break;
                case R.id.btn_studentAnswer:
//                    showMsg(getString(R.string.common_function_is_dev));
                    if (bean.getSmDto() == null || AwDataUtil.isEmpty(bean.getSmDto().getRawScan())) {
                        showDialog("学霸答卷图片不存在，无法展示");
                        return;
                    }
                    toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getSmDto().getRawScan());
                    break;
                case R.id.rl_questionTitle:
                    toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_BOOLEAN, bean.isChoiceQuestion() ? false : true, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_questionExpand:
                    //进入类题加连后区分数据
                    toClass(OnlineAnswerActivity.class,false ,Extras.COMMON_PARAMS, bean.getQuestionId(),Extras.COURSE_ID,mRowsHomeworkBean.getCourseId());
               /*     // *  非组题 主观题
                    toClass(OnlineNonGroupSubjectiveQuestionsActivity.class,false,Extras.COMMON_PARAMS,bean.getQuestionId());
                    // 组题 客观题
                      toClass(OnlineGroupObjectiveQuestionsActivity.class,false,Extras.COMMON_PARAMS,bean.getQuestionId());
                    //组题 主观题
                      toClass(OnlineSubjectiveQuestionsOfGroupQuestionsActivity.class,false,Extras.COMMON_PARAMS,bean.getQuestionId());
                    if("2".equals(bean.getGroupQuestion())){
                        //组题
                        toClass(OnlineMultipleChoiceActivity.class,false, Extras.COMMON_PARAMS, bean.getQuestionId());
                    }else if(bean.isChoiceQuestion()){
                        //单选 选择题
                        toClass(OnlineAnswerActivity.class,false ,Extras.COMMON_PARAMS, bean.getQuestionId());
                    }else{
                        //非选择题
                        toClass(OnlineNonMultipleChoiceActivity.class,false, Extras.COMMON_PARAMS, bean.getQuestionId());
                    }*/
                   // toClass(QuestionExpandActivity.class, false, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_famousTeacherLecture:
                    mPresenter.getVideos(bean.getQuestionId());
                    break;
                case R.id.btn_addQuestionBasket:
                    mAnswerSheetDataDetailResultBean = bean;
                    if ("1".equals(bean.getPractice())) {
                        showDialogWithCancelDismiss("确认要将该题从题篮移出吗？", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismissDialog();
                                mPresenter.delSomeOneQuestionBasket(bean.getQuestionId(), UserUtil.getStudId());
                            }
                        });
                    } else {
                        QuestionBasketAddRequestBean requestBean = new QuestionBasketAddRequestBean();
                        requestBean.setStudentId(UserUtil.getStudId());
                        requestBean.setClassId(UserUtil.getClassId());
                        requestBean.setCourseId(mRowsHomeworkBean.getCourseId());
                        requestBean.setAnswer(bean.isChoiceQuestion() ? bean.getAnswer() : bean.getScore());
                        requestBean.setHomeworkId(homeworkId);
                        requestBean.setQuestionId(bean.getQuestionId());
                        requestBean.setQuestionNum(bean.getQuestionNum());
                        requestBean.setStudCode(UserUtil.getStudCode());
                        requestBean.setQuestionTypeId(bean.getTypeId());
                        requestBean.setScanImage(bean.getRawScan());
                        mPresenter.addQuestionBasket(RequestUtil.addQuestionBasketRequest(requestBean));
                    }
                    break;
            }
        });
    }

    /**
     * 设置顶部分数颜色样式
     */
    private void setScoreStyle() {
        String scoreResult = mTvScoreResult.getText().toString();
        if ("满分/得分获取失败".equals(scoreResult)) {
            return;
        }
        SpannableString spannableString = new SpannableString(scoreResult);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF5564"));
        spannableString.setSpan(colorSpan, scoreResult.indexOf("得分") + 2, scoreResult.lastIndexOf("分"), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvScoreResult.setText(spannableString);
    }

    private void initScoreViewPager(FixedIndicatorView mScrollIndicator, SViewPager mScrollViewPager) {
        mScrollIndicator.setScrollBar(new ColorBar(mActivity, getResources().getColor(R.color.color_0093FF), AwDisplayUtil.dipToPix(mActivity, 2)));
        int selectColor = getResources().getColor(R.color.color_0093FF);
        int unSelectColor = getResources().getColor(R.color.black);
        mScrollIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor));
        mScrollViewPager.setCanScroll(false);
        mScrollViewPager.setOffscreenPageLimit(2);

        indicatorViewPager = new IndicatorViewPager(mScrollIndicator, mScrollViewPager);
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面, 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        mViewPagerAdapter = new HomeworkDetailViewPagerAdapter(getSupportFragmentManager(), mActivity);
        indicatorViewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public void answerSheetsQuestionSuccess(List<AnswerSheetDataDetailResultBean> result) {
        mAnswerSheetDataDetailResultBeanList = result;
        setData(TAG_SORT_QUESTION_NUM);
    }

    /**
     * 接口有问题, 出现指定错误重新获取, 其他错误直接返回错误信息
     *
     * @param msg
     */
    @Override
    public void answerSheetsQuestionFail(String msg) {
        mDetailAdapter.clearData();
        mRcvData.removeAllViews();
        mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    @Override
    public void getClassScoreMaxScoreSuccess(MaxScoreResultBean result) {
        if (result != null) {
            String classAvg = "0";
            if (!AwDataUtil.isEmpty(result.getClassAvg())) {
                classAvg = AwConvertUtil.double2String(Double.parseDouble(result.getClassAvg()), 2);
            }
            setText(mTvClasseAverage, "，班级均分" + classAvg + "分");
            // setText(mTvScoreResult, "满分" + MyDateUtil.replace(result.getMaxScore()) + "分，得分" + MyDateUtil.replace(result.getScore()) + "分");
            //2020.04.07 去掉满分**分
            setText(mTvScoreResult, "得分" + MyDateUtil.replace(result.getScore()) + "分");
            setScoreStyle();
        } else {
            setText(mTvClasseAverage, "，班级均分获取失败");
            setText(mTvScoreResult, "满分/得分获取失败");
        }
    }

    @Override
    public void getClassScoreMaxScoreFail(String msg) {
        setText(mTvClasseAverage, "，班级均分获取失败");
        setText(mTvScoreResult, "满分/得分获取失败");
        setScoreStyle();
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
    public void getVideoPointListSuccess(List<VideoPointResultBean> list) {
        mVideoPointResultBeanList = list;
        if (AwDataUtil.isEmpty(list)) {
            setText(mTvVideoPoint, "暂无对点微课");
            showView(mTvVideoPoint,false);
           // AwEffectiveRequestViewUtil.setTextViewEnableBlue(mActivity, mTvVideoPoint, false);
        }
    }

    @Override
    public void getVideoPointListFail(String msg) {
        setText(mTvVideoPoint, "暂无对点微课");
        showView(mTvVideoPoint,false);
       // AwEffectiveRequestViewUtil.setTextViewEnableBlue(mActivity, mTvVideoPoint, false);
    }

    @Override
    public void getVideoPointListNewSuccess(List<VideoPointResultBean> list) {
        mVideoPointResultBeanList = list;
        if (AwDataUtil.isEmpty(list)) {
            setText(mTvVideoPoint, "暂无对点微课");
            showView(mTvVideoPoint,false);

            //AwEffectiveRequestViewUtil.setTextViewEnableBlue(mActivity, mTvVideoPoint, false);
        }else{
            showView(mTvVideoPoint,true);
        }
    }

    @Override
    public void getVideoPointListNewFail(String msg) {
        setText(mTvVideoPoint, "暂无对点微课");
        showView(mTvVideoPoint,false);
        //AwEffectiveRequestViewUtil.setTextViewEnableBlue(mActivity, mTvVideoPoint, false);
    }

    @Override
    public void addQuestionBasketSuccess(String msg) {
        showMsg("添加题篮成功");
        for (AnswerSheetDataDetailResultBean temp : mAnswerSheetDataDetailResultBeanList) {
            if (temp.getQuestionId().equals(mAnswerSheetDataDetailResultBean.getQuestionId())) {
                temp.setPractice("1");
            }
        }
        mDetailAdapter.notifyDataSetChanged();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(mAnswerSheetDataDetailResultBean.getQuestionId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_HOMEWORK_DETAIL, false, questionIds));
    }

    @Override
    public void delSomeOneQuestionBasketSuccess(String msg) {
        showMsg("移出题篮成功");
        for (AnswerSheetDataDetailResultBean temp : mAnswerSheetDataDetailResultBeanList) {
            if (temp.getQuestionId().equals(mAnswerSheetDataDetailResultBean.getQuestionId())) {
                temp.setPractice("0");
            }
        }
        mDetailAdapter.notifyDataSetChanged();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(mAnswerSheetDataDetailResultBean.getQuestionId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_HOMEWORK_DETAIL, true, questionIds));
    }

    private void getData() {
        mPresenter.answerSheetsQuestion(sheetId);
       // mPresenter.getClassScoreMaxScore(sheetId);
//        mPresenter.getVideoPointList(homeworkId);
        mPresenter.getVideoPointListNew(homeworkId);
//        mPresenter.getVideoPointList("0301201902030303021890258a699734c48bd21a3c40c4c1c3d");
    }

    private void setData(int sortTag) {
        if (AwDataUtil.isEmpty(mAnswerSheetDataDetailResultBeanList)) {
            mDetailAdapter.clearData();
            mRcvData.removeAllViews();
            mDetailAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            switch (sortTag) {
                case TAG_SORT_QUESTION_NUM:
                    Collections.sort(mAnswerSheetDataDetailResultBeanList, (o1, o2) -> {
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
                    break;
         /*       case TAG_SORT_QUESTION_RATIO_INCREASE:
                    Collections.sort(mAnswerSheetDataDetailResultBeanList, (o1, o2) -> {
                        String ratio1 = o1.getGradeRatio();
                        String ratio2 = o2.getGradeRatio();
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
                    break;*/
                case TAG_SORT_QUESTION_RATIO_REDUCE:
//                    Collections.sort(mAnswerSheetDataDetailResultBeanList, (o1, o2) -> {
//                        String ratio1 = o1.getGradeRatio();
//                        String ratio2 = o2.getGradeRatio();
//                        if(AwDataUtil.isEmpty(ratio1)) {
//                            ratio1 = AwBaseConstant.COMMON_INVALID_VALUE;
//                        }
//                        if(AwDataUtil.isEmpty(ratio2)) {
//                            ratio2 = AwBaseConstant.COMMON_INVALID_VALUE;
//                        }
//                        if (Float.parseFloat(ratio2) >= Float.parseFloat(ratio1)) {
//                            return 1;
//                        } else {
//                            return -1;
//                        }
//                    });
                    break;
                // TODO: 2020/4/26 得分率由班级得分率分改为个人得分率
                case TAG_SORT_QUESTION_RATIO_INCREASE:

                    Collections.sort(mAnswerSheetDataDetailResultBeanList, (o1, o2) -> {
                        if(AwDataUtil.isEmpty(o1.getScore())||AwDataUtil.isEmpty(o1.getMaxScore())||AwDataUtil.isEmpty(o2.getScore())||AwDataUtil.isEmpty(o2.getMaxScore())){
                            return 1;
                        }
                        float ratio1 = Float.parseFloat(o1.getScore())/Float.parseFloat(o1.getMaxScore());
                        float ratio2 = Float.parseFloat(o2.getScore())/Float.parseFloat(o2.getMaxScore());
                        if (ratio1 >= ratio2) {
                            return 1;
                        } else {
                            return -1;
                        }
                    });
                    break;
            }
            mDetailAdapter.addAllData(mAnswerSheetDataDetailResultBeanList);
            mDetailAdapter.loadMoreComplete();
            mDetailAdapter.setEnableLoadMore(false);
            mDetailAdapter.disableLoadMoreIfNotFullPage(mRcvData);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}
