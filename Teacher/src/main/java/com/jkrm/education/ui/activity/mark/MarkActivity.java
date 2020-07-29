package com.jkrm.education.ui.activity.mark;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwConvertUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwEffectiveRequestViewUtil;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.widgets.AwCommonBottomListPopupWindow;
import com.hzw.baselib.widgets.AwCommonListPopupWindow;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkCommonUseScoreAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.DaoMarkCommonScoreUseBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
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
import com.jkrm.education.mvp.views.MarkView;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.MainActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;
import com.jkrm.education.widget.IncommonUseDialogFrament;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

import static com.hzw.baselib.util.AwImgUtil.drawableToBitmap;

/**
 * Created by hzw on 2019/5/14.
 * <p>
 * 入口页面数据说明
 * 批阅详情入口 --- 含RowsHomeworkBean
 * 按题批阅时 --- 含题目id, 不含studentId, 获取该题所有学生批阅状态列表数据
 * --- 全部已批阅, 从该题第一个学生开始批阅
 * --- 部分已批阅, 从该题第一个未批阅的学生开始批阅
 * 按人批阅时 --- 含题目id, 含studentId, 从该题该学生位置按人批阅, 获取该学生所有题目批阅状态
 * --- 该学生所有题目已批阅, 从第一题开始批阅
 * --- 该学生部分题目已批阅. 前后题切换未批阅题目
 * ===================================================================================================
 * api调用
 * 按题批阅
 * 1) 获取所有学生该题批阅状态列表
 * --- 切换到按人批阅, 获取该学生所有题目答题状态列表, 替换掉按人批阅之前的list数据
 * 按人批阅
 * 1) 获取该学生所有题目答题状态列表
 * --- 切换到按题批阅, 获取所有学生该题批阅状态列表
 * 根据当前批阅模式, 分别根据学生id和题目id, 获取该学生的答题内容
 * ===================================================================================================
 * 批阅调用
 * 1) 分步批阅(分步给分) --- 已批阅 - > 判断总分 -> 判断保存本地图片路径 -> 提交批阅图片到服务器 -> 批阅调用
 * --- 未批阅 -> 直接上/下一题/学生
 * 2) 直接批阅(一键给分) --- 批阅调用
 */

public class MarkActivity extends AwMvpActivity<MarkPresent> implements MarkView.View {

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
    @BindView(R.id.iv_commonUse)
    ImageView mIvCommonUse;

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
     * 当前题目列表
     */
    private List<GradQusetionBean> mCurrentQuestionListByPerson = new ArrayList<>();
    private List<GradQusetionBean> mAllQusetionListByPerson = new ArrayList<>(); //按人批阅
    /**
     * 当前题目bean, 一般作业详情进入携带, 及本页面临时保存使用
     */
    private GradQusetionBean mGradQusetionBean;
    /**
     * 当前学生列表
     */
    private List<AnswerSheetProgressResultBean> mCurrentStudentListByQuestion = new ArrayList<>();
    private List<AnswerSheetProgressResultBean> mAllStudentListByQuestion = new ArrayList<>(); //按题批阅
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
    private float maxScore = 0f;
    /**
     * 总批阅数
     */
    private int totalMarkCount = 0;
    /**
     * 已批阅数
     */
    private int currentMarkCount = 0;
    /**
     * 当前上传成功的批阅图片保存路径
     */
    private String currentUploadOssFilePath = "";
    /**
     * 原始图片宽高
     */
    private Bitmap originBitmap = null;
    private int origintImgWidth = 0;
    private int origintImgHeight = 0;
    /**
     * 查看大图时加载的图片(清空后默认原图, 否则用批阅后的图片)
     */
    private String currentImgUrl = "";
    /**
     * 进入页面是否回评(回评展示所有批阅数据, 反之仅展示未批阅的学生或题目列表数据)
     */
    private boolean isSelectReMark;
    /**
     * 是否需要清空评分, 防止已批阅已绘制分数的答卷重复导致绘制分数与实际分数不符
     */
    private boolean needResetScore = true;
    private String questionName = "测试题目";
    private String questionId = "";
    private String homeworkId = "";
    private String classId = "";
    private String studentId = "";
    private MarkCommonUseScoreAdapter mCommonUseScoreAdapter;
    ArrayList<MarkBean> mMarkByQuestionList = new ArrayList<>();
    ArrayList<MarkBean> mHandleSwitchList = new ArrayList<>();
    private List<GradQusetionBean> mQuestionList = new ArrayList<>();//题目
    private List<AnswerSheetProgressResultBean> mStudentList = new ArrayList<>();//学生

    @Override
    protected MarkPresent createPresenter() {
        return new MarkPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
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
                if (null == displayCutout) {
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

                mFlImgFlLayout.setPadding(displayCutout.getSafeInsetLeft(), 0, 0, 0);
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
        mMarkByQuestionList.add(new MarkBean(true, "按题批阅"));
        mMarkByQuestionList.add(new MarkBean(false, "按人批阅"));
        mHandleSwitchList.add(new MarkBean(true, "分步赋分"));
        mHandleSwitchList.add(new MarkBean(false, "一键赋分"));
        mQuestionList = (List<GradQusetionBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_GRADQUESTIONLIST);
        if (mQuestionList.isEmpty()) {
            showDialogToFinish("获取题目列表错误");
        } else {
            mQuestionList.remove(0);//第一题为选择题  移除
        }
        mStudentList = (List<AnswerSheetProgressResultBean>) getIntent().getSerializableExtra(Extras.KEY_BEAN_GRADQUESTION_WITH_STUDENT_LIST);
        List<AnswerSheetProgressResultBean> state = new ArrayList();
        for (int i = 0; i < mStudentList.size(); i++) {
            if ("1".equals(mStudentList.get(i).getStatus())) {
                state.add(mStudentList.get(i));
            }
        }
        mStudentList.removeAll(state);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCommonUseScoreAdapter, true);
        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {
                //获取是否按人批阅, 默认按题批阅
                isMarkByQuestion = getIntent().getBooleanExtra(Extras.BOOLEAN_MARK_IS_BY_QUESTION, true);
                mGradQusetionBean = (GradQusetionBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_GRADQUSETIONBEAN);
                mCurrentStudentBean = (AnswerSheetProgressResultBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS);
                isSelectReMark = getIntent().getBooleanExtra(Extras.KEY_IS_RE_MARK, false);
                if (mGradQusetionBean != null) {
                    //定位到哪题(批阅详情按题和按人批阅入口进入), 作业列表进入该字段为空, 获取列表后默认定位到第一个非选择题
                    questionId = mGradQusetionBean.getQuestionId();
                }
                if (mCurrentStudentBean != null) {
                    //定位到哪个学生(批阅详情按人批阅入口进入), 作业列表进入该字段为空, 获取列表后默认定位到第一个学生
                    studentId = mCurrentStudentBean.getStudentId();
                }
                mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
                if (mRowsHomeworkBean != null) {
                    homeworkId = mRowsHomeworkBean.getId();
                    classId = mRowsHomeworkBean.getClasses().getId();
                    switchMarkModel(true);
                    AwLog.d(TAG + "提交数: " + mRowsHomeworkBean.getStatistics().getSubmitted() + " ,总人数: " + mRowsHomeworkBean.getClasses().getPopulation());
//                    currentMarkCount = AwDataUtil.isEmpty(mRowsHomeworkBean.getStatistics().getSubmitted()) ? 0 : Integer.parseInt(mRowsHomeworkBean.getStatistics().getSubmitted());
//                    totalMarkCount = AwDataUtil.isEmpty(mRowsHomeworkBean.getClasses().getPopulation()) ? 0 : Integer.parseInt(mRowsHomeworkBean.getClasses().getPopulation());
//                    AwLog.d(TAG +"提交数处理后: " + mRowsHomeworkBean.getStatistics().getSubmitted() + " ,总人数处理后: " + mRowsHomeworkBean.getClasses().getPopulation());
//                    setText(mTvTotalMarkPercent, "总批阅进度：" + AwConvertUtil.double2String(Double.parseDouble(String.valueOf((float) currentMarkCount / totalMarkCount * 100)), 2) + "%");
                    //开始api调用, 获取题目列表
                    if (isMarkByQuestion) {
                        //获取该题所有学生答题批阅状态列表
                        mPresenter.getAllStudentListByQuestion(homeworkId, classId, questionId);
                        mAllQusetionListByPerson = mQuestionList;
                    } else {
                        //获取该学生所有题目批阅状态列表
                        mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
                        mAllStudentListByQuestion = mStudentList;
                    }

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
        if (type.isClick()) {
            if (!isMarked) {
                AwLog.d("onTouchEvent refreshByBus mIvQuestionImg.setOnClickListener isMarked false , 长/宽: " + origintImgHeight / origintImgWidth);
                if (mCurrentStudentSingleQuestionAnswerResultBean != null) {
                    //   toClass(ImgActivity.class, false, Extras.IMG_URL, currentImgUrl, Extras.IMG_HEIGHT, "0.9", Extras.IMG_NEED_PORTRAIT, origintImgHeight/origintImgWidth >= 2);
                }
            } else {
                AwLog.d("onTouchEvent refreshByBus mIvQuestionImg.setOnClickListener isMarked true");
                showLoadingDialog();
                try {
                    AwFileUtil.deleteFile(new File(Environment.getExternalStorageDirectory() + File.separator + "jby/temp"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AwImgUtil.imageSaveTemp(AwImgUtil.getViewBitmapMatrixReset(mIvQuestionImg, originBitmap, mIvQuestionImg.getCanvasList()), mCurrentStudentBean.getStudCode() + questionId + System.currentTimeMillis(), new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        //分步判分时上传批阅图片到后台.
                        dismissLoadingDialog();
                        toClass(ImgActivity.class, false, Extras.IMG_URL, s,
                                Extras.IMG_HEIGHT, "0.9", Extras.IMG_NEED_PORTRAIT, origintImgHeight / origintImgWidth >= 2);

                    }
                });
            }
            return;
        } else {
            AwLog.d("onTouchEvent refreshByBus mIvQuestionImg.setOnClickListener false");
        }
        isMarked = type.isMark();
        totalMarkScore = type.getTotalMarkScore();
        setQuestionInfo(false);
        AwLog.d("MarkActivity refreshByBus isMark: " + isMarked + ",totalMarkScore: " + totalMarkScore);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        originBitmap = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initListener() {
        super.initListener();
        mCommonUseScoreAdapter.setOnItemClickListener((adapter, view, position) -> {
            TestMarkCommonUseScoreBean bean = (TestMarkCommonUseScoreBean) adapter.getItem(position);
            if (isEditCommonUse) {
             /*   //常用编辑状态下才允许更换顺序
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
                        if (Float.parseFloat(o1.getScore()) >= Float.parseFloat(o2.getScore())) {
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
                        if (Float.parseFloat(o1.getScore()) >= Float.parseFloat(o2.getScore())) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                    return 0;
                });
                mCommonUseScoreAdapter.notifyDataSetChanged();*/
            } else {
                if (needResetScore) {
                    showDialog("该题已线上批阅过，再次赋分前请先清空评分");
                    return;
                }
                mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_NORMAL);
                //给题目赋值分数....
                if (isHandleSwitch) {
                    if (bean.isSelect()) {
                        bean.setSelect(false);
                        mIvQuestionImg.setScore(CanvasImageViewWithScale.DEFAULT_INVALID_SCORE, maxScore);
                    } else {
                        mIvQuestionImg.setScore(Float.parseFloat(bean.getScore()), maxScore);
                        for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                            temp.setSelect(false);
                        }
                        bean.setSelect(true);
                    }
                    mCommonUseScoreAdapter.notifyDataSetChanged();
                } else {
                    totalMarkScore = bean.getScore();
                    mIvQuestionImg.setScore(Float.parseFloat(bean.getScore()), maxScore);
                    mIvQuestionImg.drawAutoMarkResult(getCanvasImgCenterXY());
                    //这里延时下操作, 给予图片绘制时间(时间可调试更改)
                    new Handler().postDelayed(() -> toSaveImg(true, totalMarkScore), 300);
                    //直接判分, 不用上传图片
//                        mPresenter.markQuestion(true, mCurrentStudentSingleQuestionAnswerResultBean.getId(), RequestUtil.getMarkQuestionRequest("", totalMarkScore, "1"));

                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_commonUse, R.id.tv_markByQuestion, R.id.tv_handleSwitch, R.id.btn_seeOriginalQuestion, R.id.btn_resetScore, R.id.btn_addSpecial, R.id.iv_lastQuestion, R.id.iv_nextQuestion, R.id.iv_commonUse})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_back:
                if (isMarkedSomeOne) {
                    EventBus.getDefault().postSticky(new RxRefreshMarkDetailType()); //刷新批阅详情
                    EventBus.getDefault().postSticky(new RxRefreshHomeworkListType()); //刷新作业列表
                }
                finish();
                break;
            case R.id.tv_commonUse:
            case R.id.iv_commonUse:
                IncommonUseDialogFrament incommonUseDialogFrament = new IncommonUseDialogFrament();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_SCORE_LIST, (Serializable) mCommonUseScoreList);
                incommonUseDialogFrament.setArguments(bundle);
                incommonUseDialogFrament.show(getMyFragmentManager(), null);
                incommonUseDialogFrament.setOnSureChickListener(new IncommonUseDialogFrament.onSureChickListener() {
                    @Override
                    public void onSureChick() {
                        mCommonUseScoreAdapter.notifyDataSetChanged();
                        mIvQuestionImg.setEnableTouch(true);
                        isEditCommonUse = false;
                        showView(mVieAlpha, false);
                    }

                    @Override
                    public void onCancelChick() {
                        mIvQuestionImg.setEnableTouch(true);
                        isEditCommonUse = false;
                        showView(mVieAlpha, false);
                    }
                });
                incommonUseDialogFrament.setOnItemClickListener(new IncommonUseDialogFrament.OnItemClickListener() {
                    @Override
                    public void onItemChick(BaseQuickAdapter adapter, int position) {
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
                                    if (Float.parseFloat(o1.getScore()) >= Float.parseFloat(o2.getScore())) {
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
                                    if (Float.parseFloat(o1.getScore()) >= Float.parseFloat(o2.getScore())) {
                                        return 1;
                                    } else {
                                        return -1;
                                    }
                                }
                                return 0;
                            });
                            //  mCommonUseScoreAdapter.notifyDataSetChanged();
                        }
                    }
                });
                if (isEditCommonUse) {
                    mIvQuestionImg.setEnableTouch(true);
                    isEditCommonUse = false;
                    showView(mVieAlpha, false);
                    //setText(mTvCommonUse, "常用");
                    mIvCommonUse.setImageResource(R.mipmap.common_setting);

                    List<String> commonUseScoreList = new ArrayList<>();
                    //保存到数据库
                    for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                        if (temp.isHandleModify()) {
                            commonUseScoreList.add(String.valueOf(temp.getScore()));
                        }
                    }
                    List<DaoMarkCommonScoreUseBean> list = DaoScoreCommonUseUtil.getInstance().queryBeanByQueryBuilderOfName("");
                    if (!AwDataUtil.isEmpty(list)) {
                        for (DaoMarkCommonScoreUseBean temp : list) {
                            DaoScoreCommonUseUtil.getInstance().deleteBean(temp);
                        }
                    }
                    DaoScoreCommonUseUtil.getInstance().insertBean(new DaoMarkCommonScoreUseBean(questionName, "", commonUseScoreList));
                } else {
                    mIvQuestionImg.setEnableTouch(false);
                    isEditCommonUse = true;
                    showView(mVieAlpha, true);
                    // setText(mTvCommonUse, "完成");
                    // mIvCommonUse.setImageResource(R.mipmap.common_finish);

                }
                break;
            case R.id.tv_markByQuestion:
                if (isEditCommonUse) {
                    return;
                }
                AwPopupwindowUtil.showCommonPopupWindowWithParent(mActivity, mMarkByQuestionList, mTvMarkByQuestion, new AwCommonBottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onClick(Object bean) {
                        if ("按题批阅".equals(((MarkBean) bean).getTitle())) {
                            mTvMarkByQuestion.setSelected(false);
                        } else if ("按人批阅".equals(((MarkBean) bean).getTitle())) {
                            mTvMarkByQuestion.setSelected(true);
                        }
                        switchMarkModel(false);
                    }
                });
                break;
            case R.id.tv_handleSwitch:
                if (isEditCommonUse) {
                    return;
                }
                AwPopupwindowUtil.showCommonPopupWindowWithParent(mActivity, mHandleSwitchList, mTvHandleSwitch, new AwCommonBottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onClick(Object bean) {
                        if ("一键赋分".equals(bean)) {
                            mTvHandleSwitch.setSelected(true);
                        } else if ("分步赋分".equals(bean)) {
                            mTvHandleSwitch.setSelected(false);
                        }
                        if (mTvHandleSwitch.isSelected()) {
                            //切换到一键给分, 重置给分图片, 防止二次绘制, 并清空已选分数
                            resetScoreImg(false);
                            currentImgUrl = mCurrentStudentSingleQuestionAnswerResultBean.getRawScan();
                            //避免CanvasImageViewWithScale 二次测绘错误
                            //AwImgUtil.setImg(mActivity, mIvQuestionImg, currentImgUrl);
                            Glide.with(mActivity).load(currentImgUrl).into(mIvQuestionImg);
                            for (TestMarkCommonUseScoreBean temp : mCommonUseScoreList) {
                                temp.setSelect(false);
                            }
                            mCommonUseScoreAdapter.notifyDataSetChanged();
                            mIvQuestionImg.setScore(CanvasImageViewWithScale.DEFAULT_INVALID_SCORE, maxScore);

                            mTvHandleSwitch.setSelected(false);
                            isHandleSwitch = false;
//                    showView(mIvLastQuestion, false);
//                    showView(mIvNextQuestion, false);
                            setText(mTvHandleSwitch, "一键赋分");
                            // AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvHandleSwitch, true);
                        } else {
                            mTvHandleSwitch.setSelected(true);
                            isHandleSwitch = true;
                            showView(mIvLastQuestion, true);
                            showView(mIvNextQuestion, true);
                            setText(mTvHandleSwitch, "分步赋分");
                            //AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvHandleSwitch, false);
                        }
                    }
                });

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
                needResetScore = false;
                resetScoreImg(false);
                setQuestionInfo(true);
                currentImgUrl = mCurrentStudentSingleQuestionAnswerResultBean.getRawScan();
                //避免CanvasImageViewWithScale 二次测绘错误
                //AwImgUtil.setImg(mActivity, mIvQuestionImg, currentImgUrl);
                Glide.with(mActivity).load(currentImgUrl).into(mIvQuestionImg);
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
                if ("添加典型".equals(mBtnAddSpecial.getText().toString())) {
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
     * 展示总批阅进度(区分按题批阅和按人批阅)
     */
    private void setTotalMarkPercent(boolean isInit) {
        if (isMarkByQuestion) {
            //如果是按题批阅, 每成功批阅一个未批阅的学生, 更新下总批阅进度
            if (!isInit && currentMarkCount < mAllStudentListByQuestion.size() && "0".equals(mCurrentStudentSingleQuestionAnswerResultBean.getStatus())) {
                currentMarkCount++;
            }
            //setText(mTvTotalMarkPercent, "总批阅进度：" + AwConvertUtil.double2String(Double.parseDouble(String.valueOf((float) currentMarkCount / mAllStudentListByQuestion.size() * 100)), 2) + "%");
            setText(mTvTotalMarkPercent, "总批阅进度：" + currentMarkCount + "/" + mAllStudentListByQuestion.size() + "");//批阅进度由 % 显示为  8/10
        } else {
            //如果是按人批阅, 每成功批阅一道未批阅的题目, 更新下总批阅进度
            if (!isInit && currentMarkCount < mAllQusetionListByPerson.size() && "0".equals(mCurrentStudentSingleQuestionAnswerResultBean.getStatus())) {
                currentMarkCount++;
            }
            //setText(mTvTotalMarkPercent, "总批阅进度：" + AwConvertUtil.double2String(Double.parseDouble(String.valueOf((float) currentMarkCount / mAllQusetionListByPerson.size() * 100)), 2) + "%");
            setText(mTvTotalMarkPercent, "总批阅进度：" + currentMarkCount + "/" + mAllQusetionListByPerson.size() + "");//批阅进度由 % 显示为  8/10
        }
    }

    /**
     * 设置按题批阅/按人批阅按钮状态(初始时和点击后的切换)
     *
     * @param isInit
     */
    private void switchMarkModel(boolean isInit) {
        if (isInit) {
            mTvMarkByQuestion.setSelected(isMarkByQuestion);
            //AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvMarkByQuestion, !isMarkByQuestion);
            setText(mTvMarkByQuestion, isMarkByQuestion ? "按题批阅" : "按人批阅");
        } else {
            if (mTvMarkByQuestion.isSelected()) {
                if (null == mCurrentStudentBean) {
                    showMsg("当前学生用户不存在，无法按人批阅");
                    return;
                }
                mTvMarkByQuestion.setSelected(false);
                isMarkByQuestion = false;
                setText(mTvMarkByQuestion, "按人批阅");
                // AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvMarkByQuestion, true);
                //获取该学生所有题目批阅状态列表
                mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
            } else {
                if (AwDataUtil.isEmpty(questionId)) {
                    showMsg("当前题目不存在，无法按题批阅");
                    return;
                }
                mTvMarkByQuestion.setSelected(true);
                isMarkByQuestion = true;
                setText(mTvMarkByQuestion, "按题批阅");
                // AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mTvMarkByQuestion, false);
                //获取该题所有学生答题批阅状态列表
                mPresenter.getAllStudentListByQuestion(homeworkId, classId, questionId);
            }
        }
    }

    /**
     * 分步批阅图片保存本地, 保存成功后上传该张图片, 上传成功后调用批阅接口.
     *
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
        AwImgUtil.imageSave(AwImgUtil.getViewBitmapMatrixReset(mIvQuestionImg, originBitmap, mIvQuestionImg.getCanvasList()), mCurrentStudentBean.getStudCode() + questionId, new Subscriber<String>() {
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
                currentUploadOssFilePath = s;
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
                    if (Float.parseFloat(o1.getScore()) >= Float.parseFloat(o2.getScore())) {
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
                    if (Float.parseFloat(o1.getScore()) >= Float.parseFloat(o2.getScore())) {
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
     *
     * @param isNext
     * @return
     */
    private boolean continueOperate(boolean isNext) {
        if (isNext) {
            if (isMarkByQuestion) {
                if (AwDataUtil.isEmpty(mCurrentStudentListByQuestion)) {
                    showDialog("学生列表不存在，无法切换学生");
                    return false;
                }
                //如果按题批阅, 切换下一个人
                if (!isMarked) {
                    AwLog.d("MarkActivity 下一人切换学生之前 index: " + currentStudentIndex + " ,总人数: " + mCurrentStudentListByQuestion.size());
                    if (currentStudentIndex == mCurrentStudentListByQuestion.size() - 1) {
                        if (isSelectReMark) {
                            showDialogToFinish("现有题卡已全部批阅完成，请点击确定按钮返回");
                        } else {
                            if ("总批阅进度：100.00%".equals(mTvTotalMarkPercent.getText().toString())) {
                                showDialogWithCancelDismiss("未批阅数据已全部批阅完成, 是否切换到该题全部学生数据查看?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismissDialog();
                                        isSelectReMark = true;
                                        mPresenter.getAllStudentListByQuestion(homeworkId, classId, questionId);
                                    }
                                });
                            } else {

                                showDialogCustomLeftAndRight("是否切换到下一题", "取消", "切换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                        // TODO: 2020/5/22  循环
                                        //当前题目所有 学生    批完后切换下一题
                                        if (currentQuestionIndex < mQuestionList.size() - 1) {
                                            currentQuestionIndex++;//下一题
                                            mGradQusetionBean = mQuestionList.get(currentQuestionIndex);
                                            questionId = mGradQusetionBean.getQuestionId();
                                            mPresenter.getAllStudentListByQuestion(homeworkId, classId, questionId);
                                        } else {
                                            showDialog("翻阅完毕");
                                        }
                                    }
                                });

                            }
                        }
                    } else {
                        if (currentStudentIndex < mAllStudentListByQuestion.size() - 1) {
                            currentStudentIndex++;
                        }
                        AwLog.d("MarkActivity 下一人切换学生之后 index: " + currentStudentIndex + " ,总人数: " + mCurrentStudentListByQuestion.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            } else {
                //如果按人批阅, 切换下一题
                if (AwDataUtil.isEmpty(mCurrentQuestionListByPerson)) {
                    showDialog("题目列表不存在, 无法切换题目");
                    return false;
                }
                if (!isMarked) {
                    AwLog.d("MarkActivity 下一题切换题目之前 index: " + currentQuestionIndex + " ,总题数: " + mCurrentQuestionListByPerson.size());
                    if (currentQuestionIndex == mCurrentQuestionListByPerson.size() - 1) {
                        if (isSelectReMark) {
                            showDialog("没有更多题目了");
                        } else {
                            if ("总批阅进度：100.00%".equals(mTvTotalMarkPercent.getText().toString())) {
                                showDialogWithCancelDismiss("未批阅数据已全部批阅完成, 是否切换到该学生全部非选择题题目数据查看?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismissDialog();
                                        isSelectReMark = true;
                                        mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
                                    }
                                });
                            } else {
                                showDialogCustomLeftAndRight("是否切换到下一学生", "取消", "切换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                        // TODO: 2020/5/22  循环
                                        //当前学生所有题目    批完后切换下一学生
                                        if (currentStudentIndex < mStudentList.size() - 1) {
                                            currentStudentIndex++;// 下一学生
                                            mCurrentStudentBean = mStudentList.get(currentStudentIndex);
                                            studentId = mCurrentStudentBean.getId();
                                            mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
                                        } else {
                                            showDialog("翻阅完毕");
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        currentQuestionIndex++;
                        AwLog.d("MarkActivity 下一题切换题目之后 index: " + currentQuestionIndex + " ,总题数: " + mCurrentQuestionListByPerson.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            }
        } else {
            if (isMarkByQuestion) {
                //如果按题批阅, 切换上一个人
                if (!isMarked) {
                    if (AwDataUtil.isEmpty(mCurrentStudentListByQuestion)) {
                        showDialog("学生列表不存在，无法切换学生");
                        return false;
                    }
                    AwLog.d("MarkActivity 上一人切换学生之前 index: " + currentStudentIndex + " ,总人数: " + mCurrentStudentListByQuestion.size());
                    if (currentStudentIndex == 0) {
                        if (isSelectReMark) {
                            showDialogToFinish("现有题卡已全部批阅完成，请点击确定按钮返回");
                        } else {
                            if ("总批阅进度：100.00%".equals(mTvTotalMarkPercent.getText().toString())) {
                                showDialogWithCancelDismiss("未批阅数据已全部批阅完成, 是否切换到该题全部学生数据查看?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismissDialog();
                                        isSelectReMark = true;
                                        mPresenter.getAllStudentListByQuestion(homeworkId, classId, questionId);
                                    }
                                });
                            } else {
                                showDialogCustomLeftAndRight("是否切换到上一题", "取消", "切换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                        // TODO: 2020/5/22  循环
                                        if (currentQuestionIndex > 0) {
                                            currentQuestionIndex--;//上一题
                                            mGradQusetionBean = mQuestionList.get(currentQuestionIndex);
                                            questionId = mGradQusetionBean.getQuestionId();
                                            mPresenter.getAllStudentListByQuestion(homeworkId, classId, questionId);
                                        } else {
                                            showDialog("翻阅完毕");
                                        }
                                    }
                                });


                            }
                        }
                    } else {
                        currentStudentIndex--;
                        AwLog.d("MarkActivity 上一人切换学生之后 index: " + currentStudentIndex + " ,总人数: " + mCurrentStudentListByQuestion.size());
                        getSingleStudentQuestionAnswer();
                    }
                    return false;
                }
            } else {
                //如果按人批阅, 切换上一题
                if (AwDataUtil.isEmpty(mCurrentQuestionListByPerson)) {
                    showDialog("题目列表不存在, 无法切换题目");
                    return false;
                }
                if (!isMarked) {
                    AwLog.d("MarkActivity 上一题切换题目之前 index: " + currentQuestionIndex + " ,总题数: " + mCurrentQuestionListByPerson.size());
                    if (currentQuestionIndex == 0) {
                        if (isSelectReMark) {
                            showDialog("没有更多题目了");
                        } else {
                            if ("总批阅进度：100.00%".equals(mTvTotalMarkPercent.getText().toString())) {

                                showDialogWithCancelDismiss("未批阅数据已全部批阅完成, 是否切换到该学生全部非选择题题目数据查看?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dismissDialog();
                                        isSelectReMark = true;
                                        mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
                                    }
                                });
                            } else {
                              /*  // TODO: 2020/5/21 循环
                                showDialogCustomLeftAndRight("是否切换到上一个学生", "取消", "切换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //上一个学生
                                        if(currentStudentIndex>0){
                                            currentStudentIndex--;
                                        }else{
                                            //最后一个学生
                                            currentStudentIndex=mCurrentStudentListByQuestion.size()-1;
                                        }
                                        mCurrentStudentBean = mCurrentStudentListByQuestion.get(currentStudentIndex);
                                        mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
                                        dismissDialog();
                                    }
                                });*/
                                showDialogCustomLeftAndRight("是否切换到上一学生", "取消", "切换", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dismissDialog();
                                        //上一学生
                                        if (currentStudentIndex > 0) {
                                            currentStudentIndex--;// 下一学生
                                            mCurrentStudentBean = mStudentList.get(currentStudentIndex);
                                            studentId = mCurrentStudentBean.getId();
                                            mPresenter.getAllQuestionListByPerson(homeworkId, mCurrentStudentBean.getStudentId());
                                        } else {
                                            showDialog("翻阅完毕");
                                        }
                                    }
                                });

                                // showDialog("该学生前面没有更多待批阅的题目了，请往后翻阅");
                            }
                        }
                    } else {
                        currentQuestionIndex--;
                        AwLog.d("MarkActivity 上一题切换题目之后 index: " + currentQuestionIndex + " ,总题数: " + mCurrentQuestionListByPerson.size());
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
            mCurrentStudentBean = mCurrentStudentListByQuestion.get(currentStudentIndex);
            studentId = mCurrentStudentBean.getStudentId();
        } else {
            //按人批阅, 切换题目
            mGradQusetionBean = mCurrentQuestionListByPerson.get(currentQuestionIndex);
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
     *
     * @param needClearImg false 清空评分保留图片, true 切换题目或学生时, 清除图片等
     */
    private void resetScoreImg(boolean needClearImg) {
        mIvQuestionImg.setScore(-1, maxScore);
        mIvQuestionImg.clearAllScore();
        isMarked = false;
        if (needClearImg) {
            mIvQuestionImg.setImageDrawable(null);
            //重置状态
            mIvQuestionImg.setFristLoad(true);
        }


    }

    /**
     * title 中间题目信息(判分时动态展示当前总分, 清空评分或切换题/人时, 展示默认数值)
     *
     * @param isReset
     */
    private void setQuestionInfo(boolean isReset) {
        String currentShowMarkScore = "未批阅";
        if (isReset) {
            currentShowMarkScore = MyDateUtil.replace(mCurrentStudentSingleQuestionAnswerResultBean.getScore());
        } else {
            currentShowMarkScore = String.valueOf(totalMarkScore);

        }
        if (TextUtils.isEmpty(currentShowMarkScore)) {
            currentShowMarkScore = "0";
        }
        setText(mTvQuestionInfo, "【" + mCurrentStudentSingleQuestionAnswerResultBean.getQuestionNum() + "】得分：" + currentShowMarkScore + "/" + MyDateUtil.replace(mCurrentStudentSingleQuestionAnswerResultBean.getMaxScore()));
        if (!isReset) {
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
    public void getAllStudentListByQuestionSuccess(List<AnswerSheetProgressResultBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            showDialogToFinish("“现有题卡已全部批阅完成，请点击确定按钮返回");
            return;
        }
        currentMarkCount = 0;//重置已批阅的学生数
        mAllStudentListByQuestion = list;
        sortStudent(mAllStudentListByQuestion);
        mCurrentStudentListByQuestion = new ArrayList<>();
        //筛选未批阅的学生列表
        for (AnswerSheetProgressResultBean temp : mAllStudentListByQuestion) {
            if ("1".equals(temp.getStatus())) {
                //已批阅人数++
                currentMarkCount++;
            } else {
                mCurrentStudentListByQuestion.add(temp);
            }
        }
//        if(AwDataUtil.isEmpty(mCurrentStudentListByQuestion)) {
//            isSelectReMark = true;
//        }
        //如果是回评, 则展示所有待批阅的数据
        if (isSelectReMark) {
            mCurrentStudentListByQuestion = mAllStudentListByQuestion;
        }
        AwLog.d("mCurrentStudentListByQuestion sizw: " + mCurrentStudentListByQuestion.size());
        setTotalMarkPercent(true);
        if (AwDataUtil.isEmpty(studentId)) {
            //学生id为空, 直接按题批阅. 默认该学生列表第一条未批阅数据
            for (int i = 0; i < mCurrentStudentListByQuestion.size(); i++) {
                AnswerSheetProgressResultBean tempBean = mCurrentStudentListByQuestion.get(i);
                if ("0".equals(tempBean.getStatus())) {
                    currentStudentIndex = i;
                    break;//只要第一条筛选到, 直接退出循环
                }
            }
        } else {
            for (int i = 0; i < mCurrentStudentListByQuestion.size(); i++) {
                AnswerSheetProgressResultBean tempBean = mCurrentStudentListByQuestion.get(i);
                if (tempBean.getStudentId().equals(studentId)) {
                    currentStudentIndex = i;
                    break;//只要第一条筛选到, 直接退出循环
                }
            }
        }
        currentStudentIndex = 0;
        getSingleStudentQuestionAnswer();
    }

    @Override
    public void getAllStudentListByQuestionFail(String msg) {
        showDialogToFinish("“现有题卡已全部批阅完成，请点击确定按钮返回");
    }

    /**
     * 按人批阅, 获取该学生所有题目批阅结果列表
     *
     * @param list
     */
    @Override
    public void getAllQuestionListByPersonSuccess(List<GradQusetionBean> list) {
        if (AwDataUtil.isEmpty(list)) {
            showMsg("获取学生题目列表失败");
            return;
        }
        currentMarkCount = 0;//重置已批阅的题目数
        mAllQusetionListByPerson = list;
        //去除选择题, 该页面仅批阅主观题, 选择题无扫描图片.
        for (Iterator it = mAllQusetionListByPerson.iterator(); it.hasNext(); ) {
            GradQusetionBean temp = (GradQusetionBean) it.next();
            if (temp.isChoiceQuestion()) {
                it.remove();
            }
        }
        mCurrentQuestionListByPerson = new ArrayList<>();
        for (GradQusetionBean temp : mAllQusetionListByPerson) {
            if ("1".equals(temp.getStatus())) {
                currentMarkCount++;
            } else {
                mCurrentQuestionListByPerson.add(temp);
            }
        }
//        if(AwDataUtil.isEmpty(mCurrentQuestionListByPerson)) {
//            isSelectReMark = true;
//        }
        setTotalMarkPercent(true);
        //如果是回评, 则展示所有批阅数据
        if (isSelectReMark) {
            mCurrentQuestionListByPerson = mAllQusetionListByPerson;
        }
        AwLog.d("mCurrentQuestionListByPerson size: " + mCurrentQuestionListByPerson.size());
        if (AwDataUtil.isEmpty(questionId)) {
            currentQuestionIndex = 0;
            mGradQusetionBean = mCurrentQuestionListByPerson.get(currentQuestionIndex);
            questionId = mGradQusetionBean.getQuestionId();
        } else {
            boolean isExist = false;
            for (int i = 0; i < mCurrentQuestionListByPerson.size(); i++) {
                GradQusetionBean tempBean = mCurrentQuestionListByPerson.get(i);
                if (tempBean.getQuestionId().equals(mGradQusetionBean.getQuestionId())) {
                    currentQuestionIndex = i;
                    isExist = true;
                    break;//只要第一条筛选到, 直接退出循环
                }
            }
            if (!isExist) {
                mCurrentQuestionListByPerson.add(mGradQusetionBean);
                Collections.sort(mCurrentQuestionListByPerson, (o1, o2) -> {
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
                for (int i = 0; i < mCurrentQuestionListByPerson.size(); i++) {
                    GradQusetionBean tempBean = mCurrentQuestionListByPerson.get(i);
                    if (tempBean.getQuestionId().equals(mGradQusetionBean.getQuestionId())) {
                        currentQuestionIndex = i;
                        break;//只要第一条筛选到, 直接退出循环
                    }
                }
            }
        }
        currentQuestionIndex = 0;
        getSingleStudentQuestionAnswer();
    }

    @Override
    public void getAllQuestionListByPersonFail(String msg) {
        showMsg("获取学生答题状态失败");
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
        if (AwDataUtil.isEmpty(bean.getTypical()) || "0".equals(bean.getTypical())) {
            setText(mBtnAddSpecial, "添加典型");
        } else {
            setText(mBtnAddSpecial, "移除典型");
        }
        AwEffectiveRequestViewUtil.setTextViewBgBlueAndWhite(mActivity, mBtnAddSpecial, "添加典型".equals(mBtnAddSpecial.getText().toString()));
        //判断是否线上批阅过, 如已线上批阅过, 则需先进行重置操作
        if (AwDataUtil.isEmpty(bean.getGradedScan())) {
            needResetScore = false;
        } else {
            needResetScore = true;
        }
      /*  Glide.with(mActivity).load(bean.getRawScan()).
                asBitmap().
                placeholder(mActivity.getResources().getDrawable(com.hzw.baselib.R.mipmap.icon_img_load_ing)).
                error(mActivity.getResources().getDrawable(com.hzw.baselib.R.mipmap.icon_img_load_error)).
                into(new SimpleTarget<Bitmap>() {
                         @Override
                         public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                             originBitmap = resource;
                             origintImgWidth = resource.getWidth();
                             origintImgHeight = resource.getHeight();
                             AwLog.d("图片上传 origintImgWidth: " + origintImgWidth + " ,origintImgHeight: " + origintImgHeight);
                         }
                     });*/
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(com.hzw.baselib.R.mipmap.icon_img_load_ing)
                .error(com.hzw.baselib.R.mipmap.icon_img_load_error);
        Glide.with(mActivity).load(bean.getRawScan()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                Bitmap bitmap = drawableToBitmap(resource);
                originBitmap = bitmap;
                origintImgWidth = bitmap.getWidth();
                origintImgHeight = bitmap.getHeight();
                AwLog.d("图片上传 origintImgWidth: " + origintImgWidth + " ,origintImgHeight: " + origintImgHeight);
            }
        });
        currentImgUrl = AwDataUtil.isEmpty(bean.getGradedScan()) ? bean.getRawScan() : bean.getGradedScan();
        //避免CanvasImageViewWithScale 二次测绘错误
        //AwImgUtil.setImg(mActivity, mIvQuestionImg, currentImgUrl);
        Glide.with(mActivity).load(currentImgUrl).into(mIvQuestionImg);
        setQuestionInfo(true);
        //设置常用分数, 最大分数不存在, 取消展示常用分数
        maxScore = Float.parseFloat(MyDateUtil.replace(AwDataUtil.isEmpty(bean.getMaxScore()) ? "0" : (bean.getMaxScore())));
        //2020/04/16 新增返回批阅分数
        if (!TextUtils.isEmpty(bean.getScores()) && !"-1".equals(bean.getScores())) {
            List<DaoMarkCommonScoreUseBean> list = DaoScoreCommonUseUtil.getInstance().queryBeanByQueryBuilderOfName("");
            if (AwDataUtil.isEmpty(list) || TextUtils.isEmpty(list.get(0).getList().get(0))) {
                AwLog.d("常用分数不存在");
                mCommonUseScoreList = TestDataUtil.createMarkCommonUseScoreBeanByList(bean.getScores(), null);
            } else {
                AwLog.d("常用分数列表" + list.get(0).toString());
                mCommonUseScoreList = TestDataUtil.createMarkCommonUseScoreBeanByList(bean.getScores(), list.get(0).getList());
            }
            setCommonUseScoreData();

        } else {
            if (maxScore != 0 && !AwDataUtil.isEmpty(questionId)) {
                List<DaoMarkCommonScoreUseBean> list = DaoScoreCommonUseUtil.getInstance().queryBeanByQueryBuilderOfName("");
                if (AwDataUtil.isEmpty(list) || TextUtils.isEmpty(list.get(0).getList().get(0))) {
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

    }

    @Override
    public void getSingleStudentQuestionAnswerFail(String msg) {
        mCurrentStudentSingleQuestionAnswerResultBean = new StudentSingleQuestionAnswerResultBean();
        isMarked = false;
        showDialog("获取答题失败，无法批阅，可尝试继续批阅其他答题");
    }

    @Override
    public void uploadOssSuccess(boolean isNext, OssResultBean bean) {
        try {
            AwFileUtil.deleteFile(new File(Environment.getExternalStorageDirectory() + File.separator + "jby/temp"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            AwFileUtil.deleteFile(new File(currentUploadOssFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        questionUrl = bean.getAccessUrl();
        mPresenter.markQuestion(isNext, mCurrentStudentSingleQuestionAnswerResultBean.getId(), RequestUtil.getMarkQuestionRequest(questionUrl, totalMarkScore, mCurrentStudentSingleQuestionAnswerResultBean.getStatus()));

    }

    @Override
    public void uploadOssFail(boolean isNext, String msg) {
        showDialog("提交评分结果失败，可继续批阅其他题目尝试", v -> {
            dismissDialog();
            continueOperate(isNext);
            try {
                AwFileUtil.deleteFile(new File(currentUploadOssFilePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        setTotalMarkPercent(false);
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
        return new float[]{x, y};
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isMarkedSomeOne) {
            EventBus.getDefault().postSticky(new RxRefreshMarkDetailType()); //刷新批阅详情
            EventBus.getDefault().postSticky(new RxRefreshHomeworkListType()); //刷新作业列表
        }
    }

    private void sortStudent(List<AnswerSheetProgressResultBean> list) {
        Collections.sort(list, new Comparator<AnswerSheetProgressResultBean>() {
            @Override
            public int compare(AnswerSheetProgressResultBean answerSheetProgressResultBean, AnswerSheetProgressResultBean t1) {
                if (answerSheetProgressResultBean.getStudCode().compareToIgnoreCase(t1.getStudCode()) > 0) {
                    return 1;
                }
                return 0;
            }
        });
    }
}
