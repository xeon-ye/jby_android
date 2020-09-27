package com.jkrm.education.ui.activity.exam;

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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.project.student.bean.MarkBean;
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
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.ComAdapter;
import com.jkrm.education.adapter.mark.MarkCommonUseScoreAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.DaoMarkCommonScoreUseBean;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.rx.RxMarkImgOperateType;
import com.jkrm.education.bean.rx.RxRefreshHomeworkDetailType;
import com.jkrm.education.bean.rx.RxRefreshHomeworkListType;
import com.jkrm.education.bean.rx.RxRefreshMarkDetailType;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.db.util.DaoScoreCommonUseUtil;
import com.jkrm.education.mvp.presenters.CorrectingPresent;
import com.jkrm.education.mvp.views.CorrectionView;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;
import com.jkrm.education.widget.ChoseQuestionNumberDialogFragment;
import com.jkrm.education.widget.IncommonUseDialogFrament;
import com.jkrm.education.widget.KeyBoardDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static com.hzw.baselib.util.AwImgUtil.drawableToBitmap;

/**
 * 批阅
 */
public class CorrectingActivity extends AwMvpActivity<CorrectingPresent> implements CorrectionView.View {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_studentId)
    TextView mTvStudentId;
    @BindView(R.id.tv_studentName)
    TextView mTvStudentName;
    @BindView(R.id.tv_questionInfo)
    TextView mTvQuestionInfo;
    @BindView(R.id.tv_commonUse)
    TextView mTvCommonUse;
    @BindView(R.id.iv_commonUse)
    ImageView mIvCommonUse;
    @BindView(R.id.tv_totalMarkPercent)
    TextView mTvTotalMarkPercent;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.ll_commonUse)
    LinearLayout mLlCommonUse;
    @BindView(R.id.tv_markByQuestion)
    TextView mTvMarkByQuestion;
    @BindView(R.id.tv_handleSwitch)
    TextView mTvHandleSwitch;
    @BindView(R.id.btn_seeOriginalQuestion)
    Button mBtnSeeOriginalQuestion;
    @BindView(R.id.btn_resetScore)
    Button mBtnResetScore;
    @BindView(R.id.btn_addSpecial)
    Button mBtnAddSpecial;
    @BindView(R.id.rl_layoutBottom)
    RelativeLayout mRlLayoutBottom;
    @BindView(R.id.iv_questionImg)
    CanvasImageViewWithScale mIvQuestionImg;
    @BindView(R.id.iv_lastQuestion)
    ImageView mIvLastQuestion;
    @BindView(R.id.iv_nextQuestion)
    ImageView mIvNextQuestion;
    @BindView(R.id.fl_imgFlLayout)
    FrameLayout mFlImgFlLayout;
    @BindView(R.id.rv_leftLayout)
    RelativeLayout mRvLeftLayout;
    @BindView(R.id.view_alpha)
    View mViewAlpha;
    @BindView(R.id.fl_leftLayout)
    FrameLayout mFlLeftLayout;
    @BindView(R.id.rcv_com)
    RecyclerView mRcvCom;
    @BindView(R.id.ll_of_com)
    LinearLayout mLlOfCom;
    @BindView(R.id.iv_all_right)
    ImageView mIvAllRight;
    @BindView(R.id.iv_all_wrong)
    ImageView mIvAllWrong;
    @BindView(R.id.ll_of_all)
    LinearLayout mLlOfAll;
    @BindView(R.id.tv_num_1)
    TextView mTvNum1;
    @BindView(R.id.tv_num_2)
    TextView mTvNum2;
    @BindView(R.id.tv_num_3)
    TextView mTvNum3;
    @BindView(R.id.tv_num_4)
    TextView mTvNum4;
    @BindView(R.id.tv_num_5)
    TextView mTvNum5;
    @BindView(R.id.tv_num_6)
    TextView mTvNum6;
    @BindView(R.id.tv_num_7)
    TextView mTvNum7;
    @BindView(R.id.tv_num_8)
    TextView mTvNum8;
    @BindView(R.id.tv_num_9)
    TextView mTvNum9;
    @BindView(R.id.tv_num_05)
    TextView mTvNum05;
    @BindView(R.id.tv_num_0)
    TextView mTvNum0;
    @BindView(R.id.iv_delete)
    ImageView mIvDelete;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    @BindView(R.id.ll_of_keyboard)
    LinearLayout mLlOfKeyboard;
    private ReViewTaskBean.Bean mBean;
    private ExamQuestionsBean mExamQuestionsBean;
    private List<ExamQuestionsBean.reaListBean> mReaList;
    private ExamQuestionsBean.reaListBean mReaBean;
    /**
     * 是否已批阅
     */
    private boolean isMarked = false;
    /**
     * 是否正在编辑常用分数
     */
    private boolean isEditCommonUse = false;
    MarkCommonUseScoreAdapter mCommonUseScoreAdapter;
    private List<ExamReadHeaderBean> mQuestionNumList = new ArrayList<>();
    //批阅分值
    private List<TestMarkCommonUseScoreBean> mCommonUseScoreList = new ArrayList<>();
    /**
     * 当前题目默认总分
     */
    private float maxScore = 0f;
    private String questionId = "";
    /**
     * 当前上传成功的批阅图片保存路径
     */
    private String currentUploadOssFilePath = "";
    /**
     * 进入页面是否回评(回评展示所有批阅数据, 反之仅展示未批阅的学生或题目列表数据)
     */
    private boolean isSelectReMark;
    /**
     * 是否需要清空评分, 防止已批阅已绘制分数的答卷重复导致绘制分数与实际分数不符
     */
    private boolean needResetScore = true;
    /**
     * 批阅人给的批阅总分
     */
    private String totalMarkScore = "";

    /**
     * 是否自动翻页
     */
    private boolean isHandleSwitch = true;
    /**
     * 原始图片宽高
     */
    private Bitmap originBitmap = null;
    /**
     * 是否已批阅某一条(只要调用过批阅接口都算批阅, 区分上一字段是否已批阅字段, 用于批阅详情刷新页面)
     */
    private boolean isMarkedSomeOne = false;
    private String questionName = "测试题目";
    ArrayList<MarkBean> mHandleSwitchList = new ArrayList<>();
    /**
     * 查看大图时加载的图片(清空后默认原图, 否则用批阅后的图片)
     */
    private String currentImgUrl = "";

    private String homeworkId = "";
    private String classId = "";
    /**
     * 批改的图片地址, 已分步批阅后上传使用, 非该模式且未分步不上传
     */
    private String questionUrl = "";
    private int origintImgWidth = 0;
    private int origintImgHeight = 0;


    /**
     * 当前题目位置
     */
    private int mCurrentQuestion = 0;
    private List<ExamQuestionsBean> mExamQuestionsBeansxamList;
    private int mMarkPos;
    private String KEY_READ_NUM, KEY_TO_BE_READ, READ_NUM;
    private int mReadNum, mToBeRead;
    private String mCurrentShowMarkScore;
    private ComAdapter mComAdapter;
    private int comIndex;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_correcting;
    }

    @Override
    protected CorrectingPresent createPresenter() {
        return new CorrectingPresent(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mBean = (ReViewTaskBean.Bean) getIntent().getExtras().getSerializable(Extras.REVIEW_TASK_BEAN);
        //回评
        isSelectReMark = getIntent().getExtras().getBoolean(Extras.KEY_IS_RE_MARK, false);
        mMarkPos = getIntent().getExtras().getInt(Extras.KEY_MACK_POS);
        mMarkPos = 0;
        //mTvQuestionInfo.setText(mBean.getQuestionNum() + "");
        setStatusTxtDark();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getNotchParams();
        }
        mIvQuestionImg.setEnableTouch(true);
        //默认分步给分
        mTvHandleSwitch.setSelected(isHandleSwitch);
        mHandleSwitchList.add(new MarkBean(true, "分步赋分"));
        mHandleSwitchList.add(new MarkBean(false, "一键赋分"));
        if (isSelectReMark) {
            mPresenter.getExamReviewHeader(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay());
            mPresenter.getExamReviewQuestions(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay(), mBean.getQuestionId());
        } else {
            mPresenter.getExamReadHeader(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay());
            mPresenter.getExamQuestions(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay(), mBean.getQuestionId());

        }

        KEY_READ_NUM = getIntent().getExtras().getString(Extras.KEY_READ_NUM);

        READ_NUM = KEY_READ_NUM;
        if (!AwDataUtil.isEmpty(KEY_READ_NUM)) {
            KEY_READ_NUM = (Integer.parseInt(KEY_READ_NUM) + 1) + "";
        }
        KEY_TO_BE_READ = getIntent().getExtras().getString(Extras.KEY_TO_BE_READ);
        //填空题小题
        mComAdapter = new ComAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvCom, mComAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mCommonUseScoreAdapter = new MarkCommonUseScoreAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCommonUseScoreAdapter, true);

        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {


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
                    mPresenter.examMark(true, RequestUtil.getExamMarkRequest(mExamQuestionsBean.getId(), mExamQuestionsBean.getAnswerId(), totalMarkScore, questionUrl, mExamQuestionsBean.getOptStatus() + "", mBean.getReadWay(), mExamQuestionsBean.getQuestionId()));

                }
            }
        });
        mIvAllRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ExamQuestionsBean.reaListBean reaListBean : mReaList) {
                    reaListBean.setRemarkScore(reaListBean.getMaxScore());
                }
                mComAdapter.notifyDataSetChanged();
            }
        });
        mIvAllWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (ExamQuestionsBean.reaListBean reaListBean : mReaList) {
                    reaListBean.setRemarkScore("0");
                }
                mComAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getExamReadHeaderSuccess(List<ExamReadHeaderBean> data) {
        mQuestionNumList = data;
        mTvStudentName.setText("第（" + mBean.getQuestionNum() + ")题");


    }

    @Override
    public void getExamReadHeaderFail(String msg) {
        showMsg(msg);

    }

    @Override
    public void getExamQuestionsSuccess(List<ExamQuestionsBean> data) {
        if (AwDataUtil.isEmpty(data)) {
            showDialogToFinish("所有学生已经批阅完成，点击返回");
            return;
        }
        mExamQuestionsBeansxamList = data;
        for (int i = 0; i < data.size(); i++) {
            /*if (mBean.getQuestionId().equals(data.get(i).getQuestionId())) {
                mExamQuestionsBean = mExamQuestionsBeansxamList.get(i);
                questionId = mExamQuestionsBean.getId();
                mCurrentQuestion = i;
            }*/
            mCurrentQuestion = mMarkPos;
            mExamQuestionsBean = mExamQuestionsBeansxamList.get(mMarkPos);
            questionId = mExamQuestionsBean.getQuestionId();
            if (AwDataUtil.isEmpty(mExamQuestionsBean.getGradedScan())) {
                needResetScore = false;
            } else {
                needResetScore = true;
            }
        }
        setExamPaperAndNum();

    }

    private void setExamPaperAndNum() {
        resetScoreImg(true);
        String replace = mExamQuestionsBean.getRawScan().replace("\\", "/");
        if (isSelectReMark) {
            replace = mExamQuestionsBean.getGradedScan().replace("\\", "/");
        }
        //填空题
        if (null != mExamQuestionsBean.getReaList() && mExamQuestionsBean.getReaList().size() > 0) {
            mRcvCom.setVisibility(View.VISIBLE);
            mLlOfAll.setVisibility(View.VISIBLE);
            mTvHandleSwitch.setVisibility(View.GONE);
            setComList();
        } else {
            //非填空题
            mRcvCom.setVisibility(View.GONE);
            mLlOfAll.setVisibility(View.GONE);
            mTvHandleSwitch.setVisibility(View.VISIBLE);
        }
        questionUrl = replace;
        //设置常用分数, 最大分数不存在, 取消展示常用分数
        maxScore = Float.parseFloat(MyDateUtil.replace(AwDataUtil.isEmpty(mExamQuestionsBean.getMaxScore()) ? "0" : (mExamQuestionsBean.getMaxScore())));
        Glide.with(mActivity).load(replace).into(mIvQuestionImg);
        Glide.with(mActivity).load(replace).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                Bitmap bitmap = drawableToBitmap(resource);
                originBitmap = bitmap;
                origintImgWidth = bitmap.getWidth();
                origintImgHeight = bitmap.getHeight();
                AwLog.d("图片上传 origintImgWidth: " + origintImgWidth + " ,origintImgHeight: " + origintImgHeight);
            }
        });
        setQuestionInfo(true);
        if (!TextUtils.isEmpty(mExamQuestionsBean.getScores()) && !"-1".equals(mExamQuestionsBean.getScores())) {
            List<DaoMarkCommonScoreUseBean> list = DaoScoreCommonUseUtil.getInstance().queryBeanByQueryBuilderOfName("");
            if (AwDataUtil.isEmpty(list) || TextUtils.isEmpty(list.get(0).getList().get(0))) {
                AwLog.d("常用分数不存在");
                mCommonUseScoreList = TestDataUtil.createMarkCommonUseScoreBeanByList(mExamQuestionsBean.getScores(), null);
            } else {
                AwLog.d("常用分数列表" + list.get(0).toString());
                mCommonUseScoreList = TestDataUtil.createMarkCommonUseScoreBeanByList(mExamQuestionsBean.getScores(), list.get(0).getList());
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
        setCommonUseScoreData();
        if (isSelectReMark) {
            showView(mTvTotalMarkPercent, false);
        }
        if (!AwDataUtil.isEmpty(KEY_TO_BE_READ) && !AwDataUtil.isEmpty(READ_NUM)) {
            mTvTotalMarkPercent.setText("总批阅进度：" + (KEY_READ_NUM) + "/" + (Integer.parseInt(KEY_TO_BE_READ) + Integer.parseInt(READ_NUM)));
        }
    }

    private void setComList() {
        mComAdapter.addAllData(mExamQuestionsBean.getReaList());
        mReaList = mExamQuestionsBean.getReaList();
        mComAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ExamQuestionsBean.reaListBean> mList = adapter.getData();
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(i).setSelect(true);
                        mReaBean = mList.get(i);
                    } else {
                        mList.get(i).setSelect(false);
                    }
                    showView(mLlOfKeyboard, true);
                   /* KeyBoardDialogFragment keyBoardDialogFragment = new KeyBoardDialogFragment();
                    keyBoardDialogFragment.show(getSupportFragmentManager(), "");
                    keyBoardDialogFragment.setOnItemClickListener(new KeyBoardDialogFragment.OnItemClickListener() {

                        @Override
                        public void onNum_1_Chick() {
                            if (1 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("1");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_2_Chick() {
                            if (2 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("2");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_3_Chick() {
                            if (3 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("3");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_4_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("4");
                            if (4 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("4");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_5_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("5");
                            if (5 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("5");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_6_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("6");
                            if (6 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("6");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_7_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("7");
                            if (7 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("7");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_8_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("8");
                            if (8 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("8");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_9_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("9");
                            if (9 > Double.parseDouble(mReaBean.getMaxScore())) {
                                showMsg("超过批阅最大值");
                                return;
                            }
                            mReaBean.setRemarkScore("9");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_05_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            if (AwDataUtil.isEmpty(remarkScore)) {
                                remarkScore = "0";
                            }
                            double v = Double.parseDouble(remarkScore);
                            v += 0.5;
                            mReaBean.setRemarkScore(v + "");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onNum_0_Chick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(remarkScore);
                            stringBuilder.append("0");
                            mReaBean.setRemarkScore("0");
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onDeleteChick() {
                            String remarkScore = mReaBean.getRemarkScore();
                            if (remarkScore.contains(".")) {
                                String[] split = remarkScore.split(".");
                                mReaBean.setRemarkScore(split[0]);
                            } else {
                                if (remarkScore.length() > 0) {
                                    mReaBean.setRemarkScore(remarkScore.substring(0, remarkScore.length() - 1));
                                }
                            }
                            mComAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelChick() {
                            keyBoardDialogFragment.dismiss();
                        }

                        @Override
                        public void onSureChick() {
                            for (int i1 = 0; i1 < mReaList.size(); i1++) {
                                if (mReaList.get(i1).isSelect()) {
                                    comIndex = i1;
                                }
                            }
                            if (comIndex < mReaList.size() - 1) {
                                comIndex++;
                                for (int i1 = 0; i1 < mReaList.size(); i1++) {
                                    if (comIndex == i1) {
                                        mReaList.get(i1).setSelect(true);
                                        mReaBean = mReaList.get(i1);
                                        mRcvCom.scrollToPosition(comIndex);
                                    } else {
                                        mReaList.get(i1).setSelect(false);
                                    }
                                }
                                mComAdapter.notifyDataSetChanged();
                            } else {
                                keyBoardDialogFragment.dismiss();
                            }
                        }
                    });*/
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void getExamQuestionsFail(String msg) {
        showMsg(msg);

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
        // 填空题
        if (null != mExamQuestionsBean.getReaList() && mExamQuestionsBean.getReaList().size() > 0) {
            StringBuffer score = new StringBuffer();
            StringBuffer ansId = new StringBuffer();
            StringBuffer id = new StringBuffer();
            for (ExamQuestionsBean.reaListBean reaListBean : mReaList) {
                score.append(reaListBean.getRemarkScore() + ",");
                ansId.append(reaListBean.getAnswerId() + ",");
                id.append(reaListBean.getId() + ",");
                if(AwDataUtil.isEmpty(reaListBean.getRemarkScore())){
                    showMsg("请批阅");
                    return;
                }
            }
            mPresenter.examMark(isNext, RequestUtil.getExamMarkRequest(id.toString(), ansId.toString(), score.toString(), questionUrl, mExamQuestionsBean.getOptStatus() + "", mBean.getReadWay(), mExamQuestionsBean.getQuestionId()));
        } else {
            mPresenter.examMark(isNext, RequestUtil.getExamMarkRequest(mExamQuestionsBean.getId(), mExamQuestionsBean.getAnswerId(), totalMarkScore, questionUrl, mExamQuestionsBean.getOptStatus() + "", mBean.getReadWay(), mExamQuestionsBean.getQuestionId()));
        }


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

    }

    @Override
    public void markQuestionFail(String msg) {

    }

    @Override
    public void examMarkSuccess(boolean isNext) {
        // setTotalMarkPercent(false);
        totalMarkScore = "";
        isMarked = false;
        isMarkedSomeOne = true;
        showMsg("提交成功");
        continueOperate(isNext);
    }

    @Override
    public void examMarkFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getExamReviewHeaderSuccess(List<ExamReadHeaderBean> data) {
        mQuestionNumList = data;
        mTvStudentName.setText(mQuestionNumList.get(0).getQuestionNum());
    }

    @Override
    public void getExamReviewHeaderFail(String msg) {

    }

    @Override
    public void getExamReviewQuestionsSuccess(List<ExamQuestionsBean> data) {
        mExamQuestionsBeansxamList = data;
        for (int i = 0; i < data.size(); i++) {
            /*if (mBean.getQuestionId().equals(data.get(i).getQuestionId())) {
                mExamQuestionsBean = mExamQuestionsBeansxamList.get(i);
                questionId = mExamQuestionsBean.getId();
                mCurrentQuestion = i;
            }*/
            mCurrentQuestion = mMarkPos;
            mExamQuestionsBean = mExamQuestionsBeansxamList.get(mMarkPos);
            questionId = mExamQuestionsBean.getId();
            if (AwDataUtil.isEmpty(mExamQuestionsBean.getGradedScan())) {
                needResetScore = false;
            } else {
                needResetScore = true;
            }
        }
        setExamPaperAndNum();
    }

    @Override
    public void getExamReviewQuestionsFail(String msg) {

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


    @OnClick({R.id.tv_studentId, R.id.tv_studentName, R.id.iv_questionImg, R.id.iv_lastQuestion, R.id.iv_nextQuestion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_studentId:
                break;
            case R.id.tv_studentName:
                ChoseQuestionNumberDialogFragment choseQuestionNumberDialogFragment = new ChoseQuestionNumberDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_QUESTION_NUM_LIST, (Serializable) mQuestionNumList);
                choseQuestionNumberDialogFragment.setArguments(bundle);
                choseQuestionNumberDialogFragment.show(getSupportFragmentManager(), "");
                choseQuestionNumberDialogFragment.setOnItemClickListener(new ChoseQuestionNumberDialogFragment.onItemClickListener() {
                    @Override
                    public void onItemChickListener(ExamReadHeaderBean bean) {
                        mTvStudentName.setText("第（" + bean.getQuestionNum() + ")题");
                        if (isSelectReMark) {
                            mPresenter.getExamReviewQuestions(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay(), bean.getQuestionId());
                        } else {
                            mPresenter.getExamQuestions(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay(), bean.getQuestionId());
                        }
                        setText(mTvQuestionInfo, "【" + mExamQuestionsBean.getQuestionNum() + "】得分：" + 0 + "/" + MyDateUtil.replace(mExamQuestionsBean.getMaxScore()));

                    }
                });
                break;

        }
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

    private float[] getCanvasImgCenterXY() {
        int x = mIvQuestionImg.getWidth() / 2;
        int y = mIvQuestionImg.getHeight() / 2;
        return new float[]{x, y};
    }

    /**
     * 分步批阅图片保存本地, 保存成功后上传该张图片, 上传成功后调用批阅接口.
     *
     * @param isNext
     * @param score
     */
    private void toSaveImg(boolean isNext, String score) {
        if (null==mExamQuestionsBean.getReaList()&&AwDataUtil.isEmpty(score)) {
            showDialog("请先判分后才可保存");
            return;
        }
      /*  mCurrentShowMarkScore = "0";
        mCurrentShowMarkScore = MyDateUtil.replace(mExamQuestionsBean.getScore());
        score = mCurrentShowMarkScore;
        if (TextUtils.isEmpty(mCurrentShowMarkScore)) {
            mCurrentShowMarkScore = "0";
        }
        totalMarkScore = score;*/
        AwLog.d("toSaveImg totalScore: " + totalMarkScore);
        /*if (AwDataUtil.isEmpty(questionId) || mCurrentStudentBean == null || AwDataUtil.isEmpty(mCurrentStudentBean.getStudCode())) {
            showDialog("数据不完整, 无法保存到服务器, 请继续尝试" + (isNext ? "上" : "下") + "一个批阅", v -> continueOperate(isNext));
            return;
        }*/

        showLoadingDialog();
        //这里会重新绘制, 获取已经绘制的分数. 需状态处理
        mIvQuestionImg.setStuatus(CanvasImageViewWithScale.STUATUS_ONRESUME);
        AwImgUtil.imageSave(AwImgUtil.getViewBitmapMatrixReset(mIvQuestionImg, originBitmap, mIvQuestionImg.getCanvasList()), mExamQuestionsBean.getId() + questionId, new Subscriber<String>() {
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

    @OnClick({R.id.iv_back, R.id.tv_commonUse, R.id.tv_markByQuestion, R.id.tv_handleSwitch, R.id.btn_seeOriginalQuestion, R.id.btn_resetScore, R.id.btn_addSpecial, R.id.iv_lastQuestion, R.id.iv_nextQuestion, R.id.iv_commonUse, R.id.tv_cancel, R.id.tv_num_1, R.id.tv_num_2, R.id.tv_num_3, R.id.tv_num_4, R.id.tv_num_5, R.id.tv_num_6, R.id.tv_num_7, R.id.tv_num_8, R.id.tv_num_9, R.id.tv_num_05, R.id.tv_num_0, R.id.iv_delete, R.id.tv_sure})
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
                isEditCommonUse = true;
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
                        if (isEditCommonUse) {
                            mIvQuestionImg.setEnableTouch(true);
                            isEditCommonUse = false;
                            // showView(mVieAlpha, false);
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
                            //showView(mVieAlpha, true);
                            // setText(mTvCommonUse, "完成");
                            // mIvCommonUse.setImageResource(R.mipmap.common_finish);

                        }
                        isEditCommonUse = false;
                        // showView(mVieAlpha, false);
                    }

                    @Override
                    public void onCancelChick() {
                        mIvQuestionImg.setEnableTouch(true);
                        isEditCommonUse = false;
                        //showView(mVieAlpha, false);
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

                break;
            case R.id.tv_handleSwitch:
                if (isEditCommonUse) {
                    return;
                }
                AwPopupwindowUtil.showCommonPopupWindowWithParent(mActivity, mHandleSwitchList, mTvHandleSwitch, new AwCommonBottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onClick(Object bean) {
                        if ("一键赋分".equals(((MarkBean) bean).getTitle())) {
                            mTvHandleSwitch.setSelected(true);
                        } else if ("分步赋分".equals(((MarkBean) bean).getTitle())) {
                            mTvHandleSwitch.setSelected(false);
                        }
                        if (mTvHandleSwitch.isSelected()) {
                            //切换到一键给分, 重置给分图片, 防止二次绘制, 并清空已选分数
                            resetScoreImg(false);
                            currentImgUrl = mExamQuestionsBean.getRawScan().replace("\\", "/");

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
                if (isEditCommonUse || mCommonUseScoreList == null) {
                    return;
                }
                needResetScore = false;
                resetScoreImg(false);
                setQuestionInfo(true);
                currentImgUrl = mExamQuestionsBean.getRawScan().replace("\\", "/");
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
                    mPresenter.addSpecial(RequestUtil.getAddSpecialRequest(mExamQuestionsBean.getHomeworkId(), mExamQuestionsBean.getQuestionId(), mExamQuestionsBean.getClassId(), MyApp.getInstance().getAppUser().getTeacherId(), "",
                            questionUrl, mExamQuestionsBean.getStudCode()));
                } else {
                    mPresenter.deleteSpecial(mExamQuestionsBean.getHomeworkId(), mExamQuestionsBean.getQuestionId(), mExamQuestionsBean.getStudCode());
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
                toSaveImg(false, totalMarkScore);
                break;
            case R.id.iv_nextQuestion:
                if (isEditCommonUse) {
                    return;
                }
              /*  if (!continueOperate(true)) {
                    //不执行保存上传判分操作
                    return;
                }*/
                toSaveImg(true, totalMarkScore);
                break;
            case R.id.tv_cancel:
                mLlOfKeyboard.setVisibility(View.GONE);
                break;
            case R.id.tv_num_1:
                if (1 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("1");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_2:
                if (2 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("2");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_3:
                if (3 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("3");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_4:
                if (4 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("4");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_5:
                if (5 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("5");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_6:
                if (6 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("6");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_7:
                if (7 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("7");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_8:
                if (8 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("8");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_9:
                if (9 > Double.parseDouble(mReaBean.getMaxScore())) {
                    showMsg("超过批阅最大值");
                    return;
                }
                mReaBean.setRemarkScore("9");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_05:
                String remarkScore = mReaBean.getRemarkScore();
                if (AwDataUtil.isEmpty(remarkScore)) {
                    remarkScore = "0";
                }
                double v1 = Double.parseDouble(remarkScore);
                v1 += 0.5;
                mReaBean.setRemarkScore(v1 + "");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_num_0:
                mReaBean.setRemarkScore("0");
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_delete:
                String score = mReaBean.getRemarkScore();
                if (score.contains(".")&&score.length()>0) {
                    String[] split = score.split("\\.");
                    if(split.length>0){
                        mReaBean.setRemarkScore(split[0]);
                    }
                } else {
                    if (score.length() > 0) {
                        mReaBean.setRemarkScore(score.substring(0, score.length() - 1));
                    }
                }
                mComAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_sure:
                for (int i1 = 0; i1 < mReaList.size(); i1++) {
                    if (mReaList.get(i1).isSelect()) {
                        comIndex = i1;
                    }
                }
                if (comIndex < mReaList.size() - 1) {
                    comIndex++;
                    for (int i1 = 0; i1 < mReaList.size(); i1++) {
                        if (comIndex == i1) {
                            mReaList.get(i1).setSelect(true);
                            mReaBean = mReaList.get(i1);
                            mRcvCom.scrollToPosition(comIndex);
                        } else {
                            mReaList.get(i1).setSelect(false);
                        }
                    }
                    mComAdapter.notifyDataSetChanged();
                } else {
                    mLlOfKeyboard.setVisibility(View.GONE);
                }
                break;
            default:
                break;
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

    private boolean continueOperate(boolean isNext) {

        if (isNext) {
            if (mCurrentQuestion < mExamQuestionsBeansxamList.size() - 1) {
                mCurrentQuestion++;
                mExamQuestionsBean = mExamQuestionsBeansxamList.get(mCurrentQuestion);
                questionId = mExamQuestionsBean.getQuestionId();
                mToBeRead++;
                if (!AwDataUtil.isEmpty(KEY_READ_NUM)) {
                    KEY_READ_NUM = (Integer.parseInt(KEY_READ_NUM) + 1) + "";
                }
                setExamPaperAndNum();
            } else {
                showDialogToFinish("所有学生已经批阅完成，点击返回");
            }
            return true;
        } else {
            if (mCurrentQuestion > 0) {
                mCurrentQuestion--;
                mExamQuestionsBean = mExamQuestionsBeansxamList.get(mCurrentQuestion);
                questionId = mExamQuestionsBean.getQuestionId();
                mToBeRead--;
                if (!AwDataUtil.isEmpty(KEY_READ_NUM)) {
                    KEY_READ_NUM = (Integer.parseInt(KEY_READ_NUM) - 1) + "";

                }
                setExamPaperAndNum();
            } else {
                showDialogToFinish("所有学生已经批阅完成，点击返回");

            }
            return true;
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
            currentShowMarkScore = MyDateUtil.replace(mExamQuestionsBean.getScore());
        } else {
            currentShowMarkScore = String.valueOf(totalMarkScore);

        }
        if (TextUtils.isEmpty(currentShowMarkScore)) {
            currentShowMarkScore = "0";
        }
        setText(mTvQuestionInfo, "【" + mExamQuestionsBean.getQuestionNum() + "】得分：" + currentShowMarkScore + "/" + MyDateUtil.replace(mExamQuestionsBean.getMaxScore()));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxMarkImgOperateType type) {
        if (type == null)
            return;
        if (type.isClick()) {
            if (!isMarked) {
                AwLog.d("onTouchEvent refreshByBus mIvQuestionImg.setOnClickListener isMarked false , 长/宽: " + origintImgHeight / origintImgWidth);
                if (mExamQuestionsBean != null) {
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
                AwImgUtil.imageSaveTemp(AwImgUtil.getViewBitmapMatrixReset(mIvQuestionImg, originBitmap, mIvQuestionImg.getCanvasList()), mExamQuestionsBean.getStudCode() + questionId + System.currentTimeMillis(), new Subscriber<String>() {
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
    protected void reLogin() {
        ReLoginUtil.reLogin(mActivity);
    }


}
