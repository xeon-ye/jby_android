package com.jkrm.education.ui.activity.exam;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.DisplayCutout;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwFileUtil;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.widgets.AwCommonBottomListPopupWindow;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkCommonUseScoreAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.DaoMarkCommonScoreUseBean;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.rx.RxRefreshHomeworkListType;
import com.jkrm.education.bean.rx.RxRefreshMarkDetailType;
import com.jkrm.education.bean.test.TestMarkCommonUseScoreBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.db.util.DaoScoreCommonUseUtil;
import com.jkrm.education.mvp.presenters.CorrectingPresent;
import com.jkrm.education.mvp.views.CorrectionView;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widget.CanvasImageViewWithScale;
import com.jkrm.education.widget.ChoseQuestionNumberDialogFragment;
import com.jkrm.education.widget.IncommonUseDialogFrament;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

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
    private ReViewTaskBean.Bean mBean;
    private ExamQuestionsBean mExamQuestionsBean;
    /**
     * 是否已批阅
     */
    private boolean isMarked = false;
    /**
     * 是否正在编辑常用分数
     */
    private boolean isEditCommonUse = false;
    MarkCommonUseScoreAdapter mCommonUseScoreAdapter;
    private List<ExamReadHeaderBean> mQuestionNumList=new ArrayList<>();
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

    /**
     * 当前题目位置
     */
    private int mCurrentQuestion=0;

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
        setStatusTxtDark();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getNotchParams();
        }
        mIvQuestionImg.setEnableTouch(true);
        mHandleSwitchList.add(new MarkBean(true, "分步赋分"));
        mHandleSwitchList.add(new MarkBean(false, "一键赋分"));
        mPresenter.getExamReadHeader(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay());
        mPresenter.getExamQuestions(UserUtil.getAppUser().getTeacherId(), mBean.getExamId(), mBean.getPaperId(), mBean.getReadWay(), mBean.getQuestionId());
    }

    @Override
    protected void initData() {
        super.initData();
        mCommonUseScoreAdapter = new MarkCommonUseScoreAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mCommonUseScoreAdapter, true);
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

                }
            }
        });
    }

    @Override
    public void getExamReadHeaderSuccess(List<ExamReadHeaderBean> data) {
        mQuestionNumList = data;
    }

    @Override
    public void getExamReadHeaderFail(String msg) {
        showMsg(msg);

    }

    @Override
    public void getExamQuestionsSuccess(List<ExamQuestionsBean> data) {
        if(AwDataUtil.isEmpty(data)){
            showDialogToFinish("所有学生已经批阅完成，点击返回");
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            if(mBean.getQuestionId().equals(data.get(i).getQuestionId())){
                mExamQuestionsBean=data.get(i);
                questionId=mExamQuestionsBean.getQuestionId();
                mCurrentQuestion=i;
            }
        }
        String replace = mExamQuestionsBean.getRawScan().replace("\\", "/");
        //设置常用分数, 最大分数不存在, 取消展示常用分数
        maxScore = Float.parseFloat(MyDateUtil.replace(AwDataUtil.isEmpty(mExamQuestionsBean.getMaxScore()) ? "0" : (mExamQuestionsBean.getMaxScore())));
        Glide.with(mActivity).load(replace).into(mIvQuestionImg);
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
      //  mPresenter.examMark(isNext, RequestUtil.getExamMarkRequest(questionId));


    }

    @Override
    public void uploadOssFail(boolean isNext, String msg) {

    }

    @Override
    public void addSpecialSuccess() {

    }

    @Override
    public void deleteSpecialSuccess() {

    }

    @Override
    public void markQuestionSuccess(boolean isNext) {

    }

    @Override
    public void markQuestionFail(String msg) {

    }

    @Override
    public void examMarkSuccess(boolean isNext) {

    }

    @Override
    public void examMarkFail(String msg) {

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
                ChoseQuestionNumberDialogFragment choseQuestionNumberDialogFragment=new ChoseQuestionNumberDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Extras.KEY_QUESTION_NUM_LIST, (Serializable) mQuestionNumList);
                choseQuestionNumberDialogFragment.setArguments(bundle);
                choseQuestionNumberDialogFragment.show(getSupportFragmentManager(),"");
                choseQuestionNumberDialogFragment.setOnItemClickListener(new ChoseQuestionNumberDialogFragment.onItemClickListener() {
                    @Override
                    public void onItemChickListener(ExamReadHeaderBean bean) {
                        mTvStudentName.setText(bean.getQuestionNum());
                    }
                });
                break;
            case R.id.iv_questionImg:
                break;
            case R.id.iv_lastQuestion:
                break;
            case R.id.iv_nextQuestion:
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
        if (AwDataUtil.isEmpty(score)) {
            showDialog("请先判分后才可保存");
            return;
        }
        totalMarkScore = score;
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
                            currentImgUrl = mExamQuestionsBean.getRawScan().replace("\\","/");

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
                //setQuestionInfo(true);
                currentImgUrl = mExamQuestionsBean.getRawScan().replace("\\","/");
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
           /*     if ("添加典型".equals(mBtnAddSpecial.getText().toString())) {
                    mPresenter.addSpecial(RequestUtil.getAddSpecialRequest(homeworkId, questionId, classId, MyApp.getInstance().getAppUser().getTeacherId(), "",
                            questionUrl, mExamQuestionsBean.getStudCode()));
                } else {
                    mPresenter.deleteSpecial(homeworkId, questionId, mCurrentStudentBean.getStudCode());
                }*/
                break;
            case R.id.iv_lastQuestion:
                if (isEditCommonUse) {
                    return;
                }
            /*    if (!continueOperate(false)) {
                    //不执行保存上传判分操作
                    return;
                }*/
                toSaveImg(false, mIvQuestionImg.getTotalScore());
                break;
            case R.id.iv_nextQuestion:
                if (isEditCommonUse) {
                    return;
                }
               /* if (!continueOperate(true)) {
                    //不执行保存上传判分操作
                    return;
                }*/
                toSaveImg(true, mIvQuestionImg.getTotalScore());
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

}
