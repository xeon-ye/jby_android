package com.jkrm.education.ui.activity.mark;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkCommonUseScoreAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.DaoMarkCommonScoreUseBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean.GradQusetionBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.bean.rx.RxMarkImgOperateType;
import com.jkrm.education.bean.rx.RxRefreshHomeworkDetailType;
import com.jkrm.education.bean.rx.RxRefreshHomeworkListType;
import com.jkrm.education.bean.rx.RxRefreshMarkDetailType;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.db.util.DaoScoreCommonUseUtil;
import com.jkrm.education.mvp.presenters.MarkPresent;
import com.jkrm.education.mvp.presenters.MarkPresentOld;
import com.jkrm.education.mvp.views.MarkView;
import com.jkrm.education.mvp.views.MarkViewOld;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by hzw on 2019/5/14.
 * <p>
 * 入口页面数据说明
 * 1) 作业列表入口 --- 含RowsHomeworkBean, 不含studentId, 不含题目id, 直接从第一个非选择题第一个学生按题批阅
 * 2) 作业详情入口 --- 含RowsHomeworkBean
 * 按题批阅时 --- 含题目id, 不含studentId, 从该题目第一个学生按题批阅
 * 按人批阅时 --- 含题目id, 含studentId, 从该题该学生位置按人批阅
 * ===================================================================================================
 * 初始api调用
 * RowsHomeworkBean 不为空的情况下...获取题目列表  -> 获取学生列表 -> 获取某学生某题目的答题
 * 按题批阅 --- 如进入该页面时, 未选择特定题目(即questionId==null), 则获取列表的第一条非选择题. 即使有题目id, 最好也获取一次列表(方便切换到按人批阅后, 无数据还要再调用该接口获取数据)
 * 按人批阅 --- 前后题目切换
 * 1) 获取到题目数据后, 定义currentQuestionIndex,  再获取学生列表数据, 定义currentStudentIndex, 供前后切换学生/题目时判断
 * 2) 根据当前批阅模式, 分别根据学生id和题目id, 获取该学生的答题内容
 * ===================================================================================================
 * 批阅调用
 * 1) 分步批阅(分步给分) --- 已批阅 - > 判断总分 -> 判断保存本地图片路径 -> 提交批阅图片到服务器 -> 批阅调用
 * --- 未批阅 -> 直接上/下一题/学生
 * 2) 直接批阅(一键给分) --- 批阅调用
 */

public class MarkActivityOld extends AwMvpActivity<MarkPresentOld> implements MarkViewOld.View {

    @BindView(R.id.fl_imgFlLayout)
    FrameLayout mFlImgFlLayout;
    @BindView(R.id.tv_markByQuestion)
    TextView mTvMarkByQuestion;
    @BindView(R.id.tv_handleSwitch)
    TextView mTvHandleSwitch;
    @BindView(R.id.iv_questionImg)
    CanvasImageViewWithScale mIvQuestionImg;
    @BindView(R.id.view_alpha)
    View mVieAlpha;
    @BindView(R.id.rv_leftLayout)
    RelativeLayout mRlLeftLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_commonUse)
    TextView mTvCommonUse;
    @BindView(R.id.tv_studentId)
    TextView mTvStudentId;
    @BindView(R.id.tv_studentName)
    TextView mTvStudentName;
    @BindView(R.id.tv_questionInfo)
    TextView mTvQuestionInfo;
    @BindView(R.id.tv_totalMarkPercent)
    TextView mTvTotalMarkPercent;
    @BindView(R.id.fl_leftLayout)
    FrameLayout mFlLeftLayout;
    @BindView(R.id.iv_lastQuestion)
    ImageView mIvLastQuestion;
    @BindView(R.id.iv_nextQuestion)
    ImageView mIvNextQuestion;
    @BindView(R.id.btn_addSpecial)
    Button mBtnAddSpecial;

    private static final String TAG = MainActivity.class.getName();
    /**
     * 作业列表返回行数据
     */
    private RowsHomeworkBean mRowsHomeworkBean;
    /**
     * 查询某学生的某答案bean
     */
    private StudentSingleQuestionAnswerResultBean mCurrentStudentSingleQuestionAnswerResultBean;
    /**
     * 常用分数列表
     */
    private List<TestMarkCommonUseScoreBean> mCommonUseScoreList = new ArrayList<>();
    /**
     * 全部题目列表(全部批阅完成后使用)
     */
    private List<GradQusetionBean> mGradQusetionBeanListAll = new ArrayList<>();
    /**
     * 当前未批阅题目列表
     */
    private List<GradQusetionBean> mGradQusetionBeanList = new ArrayList<>();
    /**
     * 当前题目bean, 一般作业详情进入携带, 及本页面临时保存使用
     */
    private GradQusetionBean mGradQusetionBean;
    /**
     * 全部学生列表
     */
    private List<AnswerSheetProgressResultBean> mAnswerSheetProgressResultBeanListAll = new ArrayList<>();
    /**
     * 当前未批阅学生列表
     */
    private List<AnswerSheetProgressResultBean> mAnswerSheetProgressResultBeanList = new ArrayList<>();
    /**
     * 当前当前学生bean
     */
    private AnswerSheetProgressResultBean mCurrentStudentBean;
    /**
     * 是否正在编辑常用分数
     */
    private boolean isEditCommonUse = false;
    /**
     * 当前学生在列表中的位置
     */
    private int currentStudentIndex = 0;
    /**
     * 当前题目在列表中的位置
     */
    private int currentQuestionIndex = 0;
    /**
     * 是否按题批阅
     */
    private boolean isMarkByQuestion = true;
    /**
     * 是否自动翻页
     */
    private boolean isHandleSwitch = true;
    /**
     * 是否已批阅
     */
    private boolean isMarked = false;
    /**
     * 是否已批阅某一条(只要调用过批阅接口都算批阅, 区分上一字段是否已批阅字段, 用于批阅详情刷新页面)
     */
    private boolean isMarkedSomeOne = false;
    /**
     * 批改的图片地址, 已分步批阅后上传使用, 非该模式且未分步不上传
     */
    private String questionUrl = "";
    /**
     * 批阅人给的批阅总分
     */
    private String totalMarkScore = "";
    /**
     * 当前题目默认总分
     */
    private int maxScore = 0;
    /**
     * 总批阅数
     */
    private int totalMarkCount = 0;
    /**
     * 已批阅数
     */
    private int currentMarkCount = 0;
    private String questionName = "测试题目";
    private String questionId = "";
    private String homeworkId = "";
    private String classId = "";
    private String studentId = "";
    private MarkCommonUseScoreAdapter mCommonUseScoreAdapter;

    @Override
    protected MarkPresentOld createPresenter() {
        return new MarkPresentOld(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getNotchParams();
        }
        mIvQuestionImg.setEnableTouch(true);
        //默认分步给分
        mTvHandleSwitch.setSelected(isHandleSwitch);
    }

    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();

        decorView.post(new Runnable() {
            @Override
            public void run() {
                DisplayCutout displayCutout = decorView.getRootWindowInsets().getDisplayCutout();
                if(null == displayCutout) {
                    return;
                }
                AwLog.d("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                AwLog.d("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                AwLog.d("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                AwLog.d("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());

                List<Rect> rects = displayCutout.getBoundingRects();
                if (rects == null || rects.size() == 0) {
                    AwLog.d("TAG", "不是刘海屏");
                } else {
                    AwLog.d("TAG", "刘海屏数量:" + rects.size());
                    for (Rect rect : rects) {
                        AwLog.d("TAG", "刘海屏区域：" + rect);
                    }
                }

                mFlImgFlLayout.setPadding(displayCutout.getSafeInsetLeft(),0,0,0);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(displayCutout.getSafeInsetLeft(),0,0,0);//4个参数按顺序分别是左上右下
//                mFlImgFlLayout.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mCommonUseScoreAdapter = new MarkCommonUseScoreAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCommonUseScoreAdapter, true);
        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {
                //获取是否按人批阅, 默认按题批阅
                isMarkByQuestion = getIntent().getBooleanExtra(Extras.BOOLEAN_MARK_IS_BY_QUESTION, true);
                mGradQusetionBean = (GradQusetionBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_GRADQUSETIONBEAN);
                mCurrentStudentBean = (AnswerSheetProgressResultBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS);
                if (mGradQusetionBean != null) {
                    //定位到哪题(批阅详情按题和按人批阅入口进入), 作业列表进入该字段为空, 获取列表后默认定位到第一个非选择题
                    questionId = mGradQusetionBean.getQuestionId();
                }
                if(mCurrentStudentBean != null) {
                    //定位到哪个学生(批阅详情按人批阅入口进入), 作业列表进入该字段为空, 获取列表后默认定位到第一个学生
                    studentId = mCurrentStudentBean.getStudentId();
                }
                mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
                if (mRowsHomeworkBean != null) {
                    homeworkId = mRowsHomeworkBean.getId();
                    classId = mRowsHomeworkBean.getClasses().getId();
                    switchMarkModel(true);
                    AwLog.d(TAG + "提交数: " + mRowsHomeworkBean.getStatistics().getSubmitted() + " ,总人数: " + mRowsHomeworkBean.getClasses().getPopulation());
                    currentMarkCount = AwDataUtil.isEmpty(mRowsHomeworkBean.getStatistics().getSubmitted()) ? 0 : Integer.parseInt(mRowsHomeworkBean.getStatistics().getSubmitted());
                    totalMarkCount = AwDataUtil.isEmpty(mRowsHomeworkBean.getClasses().getPopulation()) ? 0 : Integer.parseInt(mRowsHomeworkBean.getClasses().getPopulation());
                    AwLog.d(TAG +"提交数处理后: " + mRowsHomeworkBean.getStatistics().getSubmitted() + " ,总人数处理后: " + mRowsHomeworkBean.getClasses().getPopulation());
                    setText(mTvTotalMarkPercent, "总批阅进度：" + AwConvertUtil.double2String(Double.parseDouble(String.valueOf((float) currentMarkCount / totalMarkCount * 100)), 2) + "%");
                    //开始api调用, 获取题目列表
                    mPresenter.getHomeworkDetail(homeworkId, classId);
                } else {
                    showDialogToFinish("作业信息不完整，无法获取作业详情，暂时无法批阅");
                }
            }

            @Override
            public void shouldShowRequestPermissionRationale() {
                showDialog("请允许获取存储权限才可正常进行批阅操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }

            @Override
            public void needToSetting() {
                showDialog("请允许获取存储权限才可正常进行批阅操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxMarkImgOperateType type) {
        if (type == null)
            return;
        isMarked = type.isMark();
        totalMarkScore = type.getTotalMarkScore();
        setQuestionInfo(false);
        AwLog.d("MarkActivity refreshByBus isMark: " + isMarked +  ",totalMarkScore: " + totalMarkScore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AwLog.d("drawScore... onPause");
        mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_ONPAUSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AwLog.d("drawScore... onResume");
        mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_ONRESUME);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initListener() {
        super.initListener();
        mCommonUseScoreAdapter.setOnItemClickListener((adapter, view, position) -> {
            TestMarkCommonUseScoreBean bean = (TestMarkCommonUseScoreBean) adapter.getItem(position);
            if (isEditCommonUse) {
                //常用编辑状态下才允许更换顺序
                mCommonUseScoreList.remove(bean);
                if (bean.isHandleModify()) {
                    bean.setHandleModify(false);
                    mCommonUseScoreList.add(mCommonUseScoreList.size() - 1, bean);
                } else {
                    bean.setHandleModify(true);
                    mCommonUseScoreList.add(0, bean);
                }

                //排序常用分数
                Collections.sort(mCommonUseScoreList, (o1, o2) -> {
                    if (o1.isHandleModify() && o2.isHandleModify()) {
                        if (Integer.parseInt(o1.getScore()) >= Integer.parseInt(o2.getScore())) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    return 0;
                });

                //排序常规分数
                Collections.sort(mCommonUseScoreList, (o1, o2) -> {
                    if (!o1.isHandleModify() && !o2.isHandleModify()) {
                        if (Integer.parseInt(o1.getScore()) >= Integer.parseInt(o2.getScore())) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    return 0;
                });
                mCommonUseScoreAdapter.notifyDataSetChanged();
            } else {
                mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_NORMAL);
                //给题目赋值分数....
                if (isHandleSwitch) {
                    if(bean.isSelect()) {
                        bean.setSelect(false);
                        mIvQuestionImg.setScore(CanvasImageViewWithScale.DEFAULT_INVALID_SCORE, maxScore);
                    } else {
                        mIvQuestionImg.setScore(Integer.parseInt(bean.getScore()), maxScore);
                        for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                            temp.setSelect(false);
                        }
                        bean.setSelect(true);
                    }
                    mCommonUseScoreAdapter.notifyDataSetChanged();
                } else {
                    totalMarkScore = bean.getScore();
                    mIvQuestionImg.setScore(Integer.parseInt(bean.getScore()), maxScore);
                    mIvQuestionImg.drawAutoMarkResult(getCanvasImgCenterXY());
                    //这里延时下操作, 给予图片绘制时间(时间可调试更改)
                    new Handler().postDelayed(() -> toSaveImg(true, totalMarkScore), 300);
                        //直接判分, 不用上传图片
//                        mPresenter.markQuestion(true, mCurrentStudentSingleQuestionAnswerResultBean.getId(), RequestUtil.getMarkQuestionRequest("", totalMarkScore, "1"));

                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_commonUse, R.id.tv_markByQuestion, R.id.tv_handleSwitch, R.id.btn_seeOriginalQuestion, R.id.btn_resetScore, R.id.btn_addSpecial, R.id.iv_lastQuestion, R.id.iv_nextQuestion})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                if(isMarkedSomeOne) {
                    EventBus.getDefault().postSticky(new RxRefreshMarkDetailType()); //刷新批阅详情
                    EventBus.getDefault().postSticky(new RxRefreshHomeworkListType()); //刷新作业列表
                }
                finish();
                break;
            case R.id.tv_commonUse:
                if (isEditCommonUse) {
                    mIvQuestionImg.setEnableTouch(true);
                    isEditCommonUse = false;
                    showView(mVieAlpha, false);
                    setText(mTvCommonUse, "常用");

                    List<String> commonUseScoreList = new ArrayList<>();
                    //保存到数据库
                    for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                        if (temp.isHandleModify()) {
                            commonUseScoreList.add(String.valueOf(temp.getScore()));
                        }
                    }
                    DaoScoreCommonUseUtil.getInstance().insertBean(new DaoMarkCommonScoreUseBean(questionName, questionId, commonUseScoreList));
                } else {
                    mIvQuestionImg.setEnableTouch(false);
                    isEditCommonUse = true;
                    showView(mVieAlpha, true);
                    setText(mTvCommonUse, "完成");
                }
                break;
            case R.id.tv_markByQuestion:
                if (isEditCommonUse) {
                    return;
                }
                switchMarkModel(false);
                break;
            case R.id.tv_handleSwitch:
                if (isEditCommonUse) {
                    return;
                }
                if (mTvHandleSwitch.isSelected()) {
                    //切换到一键给分, 重置给分图片, 防止二次绘制, 并清空已选分数
                    resetScoreImg(false);
                    AwImgUtil.setImg(mActivity, mIvQuestionImg, mCurrentStudentSingleQuestionAnswerResultBean.getRawScan());
                    for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                        temp.setSelect(false);
                    }
                    mCommonUseScoreAdapter.notifyDataSetChanged();
                    mIvQuestionImg.setScore(CanvasImageViewWithScale.DEFAULT_INVALID_SCORE, maxScore);

                    mTvHandleSwitch.setSelected(false);
                    isHandleSwitch = false;
                    showView(mIvLastQuestion, false);
                    showView(mIvNextQuestion, false);
                    setText(mTvHandleSwitch, "一键赋分");
                    AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvHandleSwitch, true);
                } else {
                    mTvHandleSwitch.setSelected(true);
                    isHandleSwitch = true;
                    showView(mIvLastQuestion, true);
                    showView(mIvNextQuestion, true);
                    setText(mTvHandleSwitch, "分步赋分");
                    AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvHandleSwitch, false);
                }
                break;
            case R.id.btn_seeOriginalQuestion:
                if (isEditCommonUse) {
                    return;
                }
                toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_PARAMS, questionId);
                break;
            case R.id.btn_resetScore:
                if (isEditCommonUse || mCurrentStudentSingleQuestionAnswerResultBean == null) {
                    return;
                }
                resetScoreImg(false);
                setQuestionInfo(true);
                AwImgUtil.setImg(mActivity, mIvQuestionImg, mCurrentStudentSingleQuestionAnswerResultBean.getRawScan());
                for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                    temp.setSelect(false);
                }
                mCommonUseScoreAdapter.notifyDataSetChanged();
                mIvQuestionImg.setScore(CanvasImageViewWithScale.DEFAULT_INVALID_SCORE, maxScore);
                break;
            case R.id.btn_addSpecial:
                if (isEditCommonUse) {
                    return;
                }
                if("添加典型".equals(mBtnAddSpecial.getText().toString())) {
                    mPresenter.addSpecial(RequestUtil.getAddSpecialRequest(homeworkId, questionId, classId, MyApp.getInstance().getAppUser().getTeacherId(), "",
                            questionUrl, mCurrentStudentBean.getStudCode()));
                } else {
                    mPresenter.deleteSpecial(homeworkId, questionId, mCurrentStudentBean.getStudCode());
                }
                break;
            case R.id.iv_lastQuestion:
                if (isEditCommonUse) {
                    return;
                }
                if (!continueOperate(false)) {
                    //不执行保存上传判分操作
                    return;
                }
                toSaveImg(false, mIvQuestionImg.getTotalScore());
                break;
            case R.id.iv_nextQuestion:
                if (isEditCommonUse) {
                    return;
                }
                if (!continueOperate(true)) {
                    //不执行保存上传判分操作
                    return;
                }
                toSaveImg(true, mIvQuestionImg.getTotalScore());
                break;
            default:
                break;
        }
    }

    /**
     * 设置按题批阅/按人批阅按钮状态(初始时和点击后的切换)
     * @param isInit
     */
    private void switchMarkModel(boolean isInit) {
        if (isInit) {
            mTvMarkByQuestion.setSelected(isMarkByQuestion);
            AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvMarkByQuestion, !isMarkByQuestion);
            setText(mTvMarkByQuestion, isMarkByQuestion ? "按题批阅" : "按人批阅");
        } else {
            if (mTvMarkByQuestion.isSelected()) {
                mTvMarkByQuestion.setSelected(false);
                isMarkByQuestion = false;
                setText(mTvMarkByQuestion, "按人批阅");
                AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvMarkByQuestion, true);
            } else {
                mTvMarkByQuestion.setSelected(true);
                isMarkByQuestion = true;
                setText(mTvMarkByQuestion, "按题批阅");
                AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvMarkByQuestion, false);
            }
        }
    }

    /**
     * 分步批阅图片保存本地, 保存成功后上传该张图片, 上传成功后调用批阅接口.
     * @param isNext
     * @param score
     */
    private void toSaveImg(boolean isNext, String score) {
        if (AwDataUtil.isEmpty(score)) {
            showDialog("请先判分后才可保存");
            return;
        }
        totalMarkScore = score;
        AwLog.d("toSaveImg totalScore: " + totalMarkScore);
        if (AwDataUtil.isEmpty(questionId) || mCurrentStudentBean == null || AwDataUtil.isEmpty(mCurrentStudentBean.getStudCode())) {
            showDialog("数据不完整, 无法保存到服务器, 请继续尝试" + (isNext ? "上" : "下") + "一个批阅", v -> continueOperate(isNext));
            return;
        }
        showLoadingDialog();
        //这里会重新绘制, 获取已经绘制的分数. 需状态处理
        mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_ONRESUME);
        AwImgUtil.imageSave(AwImgUtil.getViewBitmap(mIvQuestionImg), mCurrentStudentBean.getStudCode() + questionId, new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                uploadOssFail(isNext, "");
            }

            @Override
            public void onNext(String s) {
                //分步判分时上传批阅图片到后台.
                dismissLoadingDialog();
                AwLog.d("imageSave onNext path: " + s);
                mPresenter.uploadOss(isNext, "mark_server", s);
            }
        });
    }

    /**
     * 设置通用分数并分别排序常用分数和普通分数
     */
    private void setCommonUseScoreData() {
        //分类
        if (AwDataUtil.isEmpty(mCommonUseScoreList)) {
            mCommonUseScoreAdapter.clearData();
            mRcvData.removeAllViews();
            mCommonUseScoreAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            //排序常用分数
            Collections.sort(mCommonUseScoreList, (o1, o2) -> {
                if (o1.isHandleModify() && o2.isHandleModify()) {
                    if (Integer.parseInt(o1.getScore()) >= Integer.parseInt(o2.getScore())) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 0;
            });

            //排序常规分数
            Collections.sort(mCommonUseScoreList, (o1, o2) -> {
                if (!o1.isHandleModify() && !o2.isHandleModify()) {
                    if (Integer.parseInt(o1.getScore()) >= Integer.parseInt(o2.getScore())) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                return 0;
            });
            mCommonUseScoreAdapter.addAllData(mCommonUseScoreList);
            mCommonUseScoreAdapter.loadMoreComplete();
            mCommonUseScoreAdapter.setEnableLoadMore(false);
            mCommonUseScoreAdapter.disableLoadMoreIfNotFullPage(mRcvData);
        }
    }

    /**
     * 未批阅或批阅后. 无论成功与否进行下一步的操作判断
     * @param isNext
     * @return
     */
    private boolean continueOperate(boolean isNext) {
        if (isNext) {
            if (isMarkByQuestion) {
                if (AwDataUtil.isEmpty(mAnswerSheetProgressResultBeanList)) {
                    showMsg("学生列表不存在，无法切换学生");
                    return false;
                }
                //如果按题批阅, 切换下一个人
                if (!isMarked) {
                    AwLog.d("MarkActivity 下一人切换学生之前 index: " + currentStudentIndex + " ,总人数: " + mAnswerSheetProgressResultBeanList.size());
                    if (currentStudentIndex == mAnswerSheetProgressResultBeanList.size() - 1) {
                        showMsg("没有更多学生了");
                    } else {
                        currentStudentIndex++;
                        AwLog.d("MarkActivity 下一人切换学生之后 index: " + currentStudentIndex + " ,总人数: " + mAnswerSheetProgressResultBeanList.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            } else {
                //如果按人批阅, 切换下一题
                if (AwDataUtil.isEmpty(mGradQusetionBeanList)) {
                    showMsg("题目列表不存在, 无法切换题目");
                    return false;
                }
                if (!isMarked) {
                    AwLog.d("MarkActivity 下一题切换题目之前 index: " + currentQuestionIndex + " ,总题数: " + mGradQusetionBeanList.size());
                    if (currentQuestionIndex == mGradQusetionBeanList.size() - 1) {
                        showMsg("没有更多题目了");
                    } else {
                        currentQuestionIndex++;
                        AwLog.d("MarkActivity 下一题切换题目之后 index: " + currentQuestionIndex + " ,总题数: " + mGradQusetionBeanList.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            }
        } else {
            if (isMarkByQuestion) {
                //如果按题批阅, 切换上一个人
                if (!isMarked) {
                    if (AwDataUtil.isEmpty(mAnswerSheetProgressResultBeanList)) {
                        showMsg("学生列表不存在，无法切换学生");
                        return false;
                    }
                    AwLog.d("MarkActivity 上一人切换学生之前 index: " + currentStudentIndex + " ,总人数: " + mAnswerSheetProgressResultBeanList.size());
                    if (currentStudentIndex == 0) {
                        showMsg("没有更多学生了");
                    } else {
                        currentStudentIndex--;
                        AwLog.d("MarkActivity 上一人切换学生之后 index: " + currentStudentIndex + " ,总人数: " + mAnswerSheetProgressResultBeanList.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            } else {
                //如果按人批阅, 切换上一题
                if (AwDataUtil.isEmpty(mGradQusetionBeanList)) {
                    showMsg("题目列表不存在, 无法切换题目");
                    return false;
                }
                if (!isMarked) {
                    AwLog.d("MarkActivity 上一题切换题目之前 index: " + currentQuestionIndex + " ,总题数: " + mGradQusetionBeanList.size());
                    if (currentQuestionIndex == 0) {
                        showMsg("没有更多题目了");
                    } else {
                        currentQuestionIndex--;
                        AwLog.d("MarkActivity 上一题切换题目之后 index: " + currentQuestionIndex + " ,总题数: " + mGradQusetionBeanList.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private void getSingleStudentQuestionAnswer() {
        resetScoreImg(true);//重置批阅状态.
        if (isMarkByQuestion) {
            //按题批阅, 切换学生
            mCurrentStudentBean = mAnswerSheetProgressResultBeanList.get(currentStudentIndex);
            studentId = mCurrentStudentBean.getStudentId();
        } else {
            //按人批阅, 切换题目
            mGradQusetionBean = mGradQusetionBeanList.get(currentQuestionIndex);
            questionId = mGradQusetionBean.getQuestionId();
        }
        setText(mTvStudentId, mCurrentStudentBean.getStudCode());
        setText(mTvStudentName, mCurrentStudentBean.getStudentName());
        //根据学生id, 问题id获取该题详细.
        if (!AwDataUtil.isEmpty(questionId)) {
            mPresenter.getSingleStudentQuestionAnswer(homeworkId, questionId, mCurrentStudentBean.getStudentId());
        }
    }

    /**
     * 重置批阅状态
     * @param needClearImg false 清空评分保留图片, true 切换题目或学生时, 清除图片等
     */
    private void resetScoreImg(boolean needClearImg) {
        mIvQuestionImg.setScore(-1, maxScore);
        mIvQuestionImg.clearAllScore();
        isMarked = false;
        if(needClearImg) {
            mIvQuestionImg.setImageDrawable(null);
        }

    }

    /**
     * title 中间题目信息(判分时动态展示当前总分, 清空评分或切换题/人时, 展示默认数值)
     * @param isReset
     */
    private void setQuestionInfo(boolean isReset) {
        String currentShowMarkScore = "0";
        if(isReset) {
            currentShowMarkScore = MyDateUtil.replace(mCurrentStudentSingleQuestionAnswerResultBean.getScore());
        } else {
            currentShowMarkScore = String.valueOf(totalMarkScore);

        }
        setText(mTvQuestionInfo, "【" + mCurrentStudentSingleQuestionAnswerResultBean.getQuestionNum() + "】得分：" + currentShowMarkScore + "/" + MyDateUtil.replace(mCurrentStudentSingleQuestionAnswerResultBean.getMaxScore()));
        if(!isReset) {
            setScoreStyle();
        }
    }

    /**
     * 设置顶部分数颜色样式
     */
    private void setScoreStyle() {
        String scoreResult = mTvQuestionInfo.getText().toString();
        SpannableString spannableString = new SpannableString(scoreResult);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF5564"));
        spannableString.setSpan(colorSpan, scoreResult.indexOf("：") + 1, scoreResult.lastIndexOf("/"), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mTvQuestionInfo.setText(spannableString);
    }


    //.....................................................................API数据返回....................................................................

    @Override
    public void getHomeworkDetailSuccess(HomeworkDetailResultBean bean) {
        if (bean == null || AwDataUtil.isEmpty(bean.getGradQusetion())) {
            showMsg("获取题目列表失败");
            return;
        }
        mGradQusetionBeanList = bean.getGradQusetion();
        //去除选择题, 该页面仅批阅主观题, 选择题无扫描图片.
        for (Iterator it = mGradQusetionBeanList.iterator(); it.hasNext(); ) {
            GradQusetionBean temp = (GradQusetionBean) it.next();
            if (temp.isChoiceQuestion()) {
                it.remove();
            }
        }
        if (AwDataUtil.isEmpty(questionId)) {
            currentQuestionIndex = 0;
            mGradQusetionBean = mGradQusetionBeanList.get(currentQuestionIndex);
            questionId = mGradQusetionBean.getQuestionId();
        } else {
            for (int i = 0; i < mGradQusetionBeanList.size(); i++) {
                GradQusetionBean tempBean = mGradQusetionBeanList.get(i);
                if (tempBean.getQuestionId().equals(questionId)) {
                    currentQuestionIndex = i;
                }
            }
        }

        //获取学生列表
        mPresenter.answerSheetProgress(homeworkId, classId);
    }

    @Override
    public void getHomeworkDetailFail(String msg) {
        showMsg("获取题目列表失败");
    }

    @Override
    public void answerSheetProgressSuccess(List<AnswerSheetProgressResultBean> list) {
        mAnswerSheetProgressResultBeanList = list;
        if (AwDataUtil.isEmpty(mAnswerSheetProgressResultBeanList)) {
            AwLog.d("MarkActivity answerSheetProgressSuccess list is null");
        } else {
            AwLog.d("MarkActivity answerSheetProgressSuccess list size: " + list.size());
            if (AwDataUtil.isEmpty(studentId)) {
                //学生id为空, 直接按题批阅. 默认该学生列表第一条数据
                currentStudentIndex = 0;
            } else {
                for (int i = 0; i < list.size(); i++) {
                    AnswerSheetProgressResultBean tempBean = list.get(i);
                    if (tempBean.getStudentId().equals(studentId)) {
                        currentStudentIndex = i;
                    }
                }
            }
            getSingleStudentQuestionAnswer();
        }
    }

    @Override
    public void answerSheetProgressFail(String msg) {
        showMsg("获取学生列表失败");
    }

    @Override
    public void getSingleStudentQuestionAnswerSuccess(StudentSingleQuestionAnswerResultBean bean) {
        mCurrentStudentSingleQuestionAnswerResultBean = bean;
        if (bean == null) {
            showDialog("答题文件不存在，无法批阅，可尝试批阅其他答题");
            isMarked = false;
            return;
        }
        questionUrl = AwDataUtil.isEmpty(bean.getGradedScan()) ? bean.getRawScan() : bean.getGradedScan();
        if(AwDataUtil.isEmpty(bean.getTypical()) || "0".equals(bean.getTypical())) {
            setText(mBtnAddSpecial, "添加典型");
        } else {
            setText(mBtnAddSpecial, "移除典型");
        }
        AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mBtnAddSpecial, "添加典型".equals(mBtnAddSpecial.getText().toString()));
        AwImgUtil.setImg(mActivity, mIvQuestionImg, AwDataUtil.isEmpty(bean.getGradedScan()) ? bean.getRawScan() : bean.getGradedScan());
        setQuestionInfo(true);
        //设置常用分数, 最大分数不存在, 取消展示常用分数
        maxScore = Integer.parseInt(MyDateUtil.replace(AwDataUtil.isEmpty(bean.getMaxScore()) ? "0" : (bean.getMaxScore())));
        if (maxScore != 0 && !AwDataUtil.isEmpty(questionId)) {
            List<DaoMarkCommonScoreUseBean> list = DaoScoreCommonUseUtil.getInstance().queryBeanByQueryBuilderOfName(questionId);
            if (AwDataUtil.isEmpty(list)) {
                AwLog.d("常用分数不存在");
                mCommonUseScoreList = TestDataUtil.createkMarkCommonUseScoreBeanList(maxScore, null);
            } else {
                AwLog.d("常用分数列表" + list.get(0).toString());
                mCommonUseScoreList = TestDataUtil.createkMarkCommonUseScoreBeanList(maxScore, list.get(0).getList());
            }
            setCommonUseScoreData();
        } else {
            mCommonUseScoreAdapter.clearData();
            mRcvData.removeAllViews();
            mCommonUseScoreAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        }
    }

    @Override
    public void getSingleStudentQuestionAnswerFail(String msg) {
        mCurrentStudentSingleQuestionAnswerResultBean = new StudentSingleQuestionAnswerResultBean();
        isMarked = false;
        showDialog("获取答题失败，无法批阅，可尝试继续批阅其他答题");
    }

    @Override
    public void uploadOssSuccess(boolean isNext, OssResultBean bean) {
        questionUrl = bean.getAccessUrl();
        mPresenter.markQuestion(isNext, mCurrentStudentSingleQuestionAnswerResultBean.getId(), RequestUtil.getMarkQuestionRequest(questionUrl, totalMarkScore, "1"));

    }

    @Override
    public void uploadOssFail(boolean isNext, String msg) {
        showDialog("提交评分结果失败，可继续批阅其他题目尝试", v -> {
            dismissDialog();
            continueOperate(isNext);
        });
    }

    @Override
    public void addSpecialSuccess() {
        showMsg("添加典型成功");
        setText(mBtnAddSpecial, "移除典型");
        AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mBtnAddSpecial, false); //刷新作业详情
        EventBus.getDefault().postSticky(new RxRefreshHomeworkDetailType());
    }

    @Override
    public void deleteSpecialSuccess() {
        showMsg("删除典型成功");
        setText(mBtnAddSpecial, "添加典型");
        EventBus.getDefault().postSticky(new RxRefreshHomeworkDetailType());
        AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mBtnAddSpecial, true); //刷新作业详情
    }

    @Override
    public void markQuestionSuccess(boolean isNext) {
        if(!mCurrentStudentBean.isMarked()) {
            //当前答题在本次批阅成功之前是未批阅状态才更新百分比.
            currentMarkCount++;
            setText(mTvTotalMarkPercent, "总批阅进度：" + AwConvertUtil.double2String(Double.parseDouble(String.valueOf((float) currentMarkCount / totalMarkCount * 100)), 2) + "%");
        }
        totalMarkScore = "";
        isMarked = false;
        isMarkedSomeOne = true;
        showMsg("提交成功");
        continueOperate(isNext);
    }

    @Override
    public void markQuestionFail(String msg) {
        totalMarkScore = "";
        showDialogWithCancelDismiss("提交失败, 是否继续批阅其他？", v -> {
            dismissDialog();
            continueOperate(true);
        });
    }

    private void setImgSize() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,
                400);//两个400分别为添加图片的大小
        mIvQuestionImg.setLayoutParams(params);
    }

    private float[] getCanvasImgCenterXY() {
        int x = mIvQuestionImg.getWidth() / 2;
        int y = mIvQuestionImg.getHeight() / 2;
        return new float[] {x, y};
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(isMarkedSomeOne) {
            EventBus.getDefault().postSticky(new RxRefreshMarkDetailType()); //刷新批阅详情
            EventBus.getDefault().postSticky(new RxRefreshHomeworkListType()); //刷新作业列表
        }
    }
}
