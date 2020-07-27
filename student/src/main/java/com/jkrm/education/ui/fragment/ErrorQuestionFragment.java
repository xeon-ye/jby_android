package com.jkrm.education.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.hzw.baselib.project.student.bean.SubjectDataResultBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPickerTimeUtil;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ErrorQuestionAdapter;
import com.jkrm.education.adapter.score.MarkChoiceCourseAdapter;
import com.jkrm.education.adapter.score.MarkChoiceKindsAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.QuestionBasketAddRequestBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.rx.RxRefreshErrorQuestionListType;
import com.jkrm.education.bean.rx.RxRefreshQuestionBasketType;
import com.jkrm.education.bean.test.TestMarkHomeworkDetailBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ErrorQuestionPresent;
import com.jkrm.education.mvp.views.ErrorQuestionView;
import com.jkrm.education.ui.activity.FamousTeacherLectureActivity;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
import com.jkrm.education.ui.activity.QuestionBasketActivity;
import com.jkrm.education.ui.activity.QuestionExpandActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 错题本
 * Created by hzw on 2019/5/5.
 */

public class ErrorQuestionFragment extends AwMvpLazyFragment<ErrorQuestionPresent> implements ErrorQuestionView.View,
        BaseQuickAdapter.RequestLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.rcv_dataKinds)
    RecyclerView mRcvDataKinds;
    @BindView(R.id.rcv_dataCourese)
    RecyclerView mRcvDataCourese;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.ll_scanDate)
    LinearLayout mLlScanData;
    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;
    @BindView(R.id.tv_choiceHomewokOrExam)
    TextView mTvChoiceHomewokOrExam;
    @BindView(R.id.view_alpha)
    View mViewAlpha;
    @BindView(R.id.img_question_basket)
    ImageView mImgQuestionBasket;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_question_basket)
    TextView mTvQuestionBasket;

    //排序依据时间/个人得分率/班级得分率
    private static final String ORDER_TYPE_DATE = "1";
    private static final String ORDER_TYPE_RATIO_STUDENT = "2";
    private static final String ORDER_TYPE_RATIO_CLASS = "3";
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    Unbinder unbinder;
    private TestMarkHomeworkDetailBean mDetailBean;
    private ErrorQuestionAdapter mAdapter;
    //筛选内容
    private MarkChoiceKindsAdapter mKindsAdapter;
    private MarkChoiceCourseAdapter mCourseAdapter;

    private List<ErrorQuestionResultBean> mErrorQuestionResultBeanList = new ArrayList<>();
    private List<TypeCourseBean> mTypeCourseBeanList = new ArrayList<>();
    private List<AnswerSheetAllDataResultBean> mHomeworkAllList = new ArrayList<>();
    private List<TestMarkKindsBean> mKindsBeanList = new ArrayList<>();
    private List<SubjectDataResultBean> mSubjectDataList=new ArrayList<>();
    private int index = 1;
    private int totalPages = Integer.MAX_VALUE;
    private String currentHomeworkId = "";
    private String startDate = "";
    private String endDate = "";
    private String courseId = "";
    private String orderType = ORDER_TYPE_DATE;
    /**
     * 正在添加移出题篮的问题bean
     */
    private ErrorQuestionResultBean mErrorQuestionResultBean;

    private boolean needRefresh = false;

    @Override
    protected ErrorQuestionPresent createPresenter() {
        return new ErrorQuestionPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_error_question;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarHideLeftWithRightTxt("错题本", "筛选", () -> {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        mToolbar.setToolbarTitleColor(R.color.white);
        mToolbar.setRTextColor(R.color.white);


        mToolbar.setLeftRightImgAndText(R.mipmap.icon_sanjiao_white, "时间");
        mToolbar.setLeftTextColor(R.color.white);
        mToolbar.setOnLeftClickListener(new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                //先关闭抽屉, 否则界面太乱
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawers();
                }
                showView(mViewAlpha, true);
                AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlpha(mActivity, TestDataUtil.createErrorQuestionType(), mToolbar,
                        () -> showView(mViewAlpha, false)
                        , bean -> {
                            String name = (String) bean;
                            mToolbar.setLeftRightImgAndText(R.mipmap.icon_sanjiao_white, name);
                            showView(mViewAlpha, false);
                            switch (name) {
                                case "时间":
                                    orderType = ORDER_TYPE_DATE;
                                    break;
                                case "个人得分率":
                                    orderType = ORDER_TYPE_RATIO_STUDENT;
                                    break;
                                case "班级得分率":
                                    orderType = ORDER_TYPE_RATIO_CLASS;
                                    break;
                            }
                            getData(true);
                        });
            }
        });
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mAdapter = new ErrorQuestionAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        initChoiceData();
        //获取科目TAB
        mPresenter.getSubjectList(MyApp.getInstance().getAppUser().getStudId());
        getQuestionNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshQuestionBasketType type) {
        if (type != null && !type.getTag().equals(RxRefreshQuestionBasketType.TAG_ERROR_QUESTION)) {
            if (AwDataUtil.isEmpty(mErrorQuestionResultBeanList)) {
                return;
            }
            boolean needRefresh = false;
            List<String> questionIds = type.getQuestionIds();
            for (ErrorQuestionResultBean temp : mErrorQuestionResultBeanList) {
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
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshErrorQuestionListType type) {
        if (type != null) {
            needRefresh = true;
        }
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (needRefresh) {
                getData(true);
            }
        } else {
            //TODO 不可见操作
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    @Override
    public void onLoadMoreRequested() {
        if (index < totalPages) {
            index++;
            getData(false);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    private void getData(boolean needResetIndex) {
        if (needResetIndex) {
            index = 1;
            mErrorQuestionResultBeanList = new ArrayList<>();
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.getMistakeListNew(MyApp.getInstance().getAppUser().getStudId(), String.valueOf(index), startDate, endDate, currentHomeworkId, courseId, orderType);
        mPresenter.getHomeworkListAll(MyApp.getInstance().getAppUser().getStudId(),courseId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRcvData);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            showMsg(getString(R.string.common_function_is_dev));
            ErrorQuestionResultBean bean = (ErrorQuestionResultBean) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.iv_img:
                    if(!AwDataUtil.isEmpty(bean.getGradedScan())){
                        toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getGradedScan());
                    }else{
                        toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getRawScan());
                    }
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
                    //类题加练
                case R.id.btn_questionExpand:
                    toClass(OnlineAnswerActivity.class,false,Extras.COMMON_PARAMS,bean.getQuestionId(),Extras.COURSE_ID,courseId);
                    //toClass(QuestionExpandActivity.class, false, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_famousTeacherLecture:
                    mPresenter.getVideos(bean.getQuestionId());
                    break;
                case R.id.btn_addQuestionBasket:
                    mErrorQuestionResultBean = bean;
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
                        requestBean.setCourseId(bean.getCourseId());
                        requestBean.setAnswer(bean.isChoiceQuestion() ? bean.getAnswer() : bean.getGrade().getScore());
                        requestBean.setHomeworkId(bean.getHomeworkId());
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
        mTvStartDate.setOnClickListener(v -> AwPickerTimeUtil.showPickerTimeView(mActivity, getString(R.string.common_date_start), AwDateUtils.formatDate17, dateString -> {
            if (AwDataUtil.isEmpty(mTvEndDate.getText().toString())) {
                setText(mTvStartDate, dateString);
            } else {
                if (AwDateUtils.isAfterFirstDateParams(dateString, mTvEndDate.getText().toString())) {
                    setText(mTvStartDate, dateString);
                } else {
                    showDialog(getString(R.string.common_date_error1));
                    return;
                }
            }
//            startDate = dateString;
        }));
        mTvEndDate.setOnClickListener(v -> {
            if (AwDataUtil.isEmpty(mTvStartDate.getText().toString())) {
                showMsg(getString(R.string.common_date_error_select_start_date));
                return;
            }
            AwPickerTimeUtil.showPickerTimeView(mActivity, getString(R.string.common_date_end), AwDateUtils.formatDate17, dateString -> {
                if (AwDateUtils.isAfterFirstDateParams(mTvStartDate.getText().toString(), dateString)) {
                    setText(mTvEndDate, dateString);
//                    endDate = dateString;
                } else {
                    showDialog(getString(R.string.common_date_error2));
                }
            });
        });
        mBtnReset.setOnClickListener(v -> {
            setChoiceData();
            if (!AwDataUtil.isEmpty(mTypeCourseBeanList)) {
                for (TypeCourseBean temp : mTypeCourseBeanList) {
                    temp.setSelect("全部".equals(temp.getCourseName()));
                }
                mCourseAdapter.notifyDataSetChanged();
            }
            setText(mTvStartDate, "");
            setText(mTvEndDate, "");
            currentHomeworkId = "";
            courseId = "";
            resetDate();
        });
        mBtnConfirm.setOnClickListener(v -> {
            for (TypeCourseBean tempBean : mTypeCourseBeanList) {
                if (tempBean.isSelect()) {
                    if (AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getCourseId())) {
                        courseId = "";
                    } else {
                        courseId = tempBean.getCourseId();
                    }
                    //获取TAB选中学科 否则 筛选中会出现其他学科
                    SubjectDataResultBean subjectDataResultBean = mSubjectDataList.get(mTabLayout.getSelectedTabPosition());
                    courseId=subjectDataResultBean.getId();
                }
            }
            for (TestMarkKindsBean tempBean : mKindsBeanList) {
                if (tempBean.isChecked()) {
                    String kindsName = tempBean.getName();
                    switch (kindsName) {
                        case "日日清":
                            startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-1)));
                            endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));
                            break;
                        case "周周清":
                            startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-7)));
                            endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));
                            break;
                        case "月月清":
                            startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-30)));
                            endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));
                            break;
                        case "自定义":
                            startDate = mTvStartDate.getText().toString();
                            endDate = mTvEndDate.getText().toString();
                            break;
                        default:
                            break;
                    }
                }
            }
            mDrawerLayout.closeDrawers();
            mAdapter.clearData();
            mRcvData.removeAllViews();
            getData(true);

        });
        mKindsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TestMarkKindsBean kindsBean = (TestMarkKindsBean) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                TestMarkKindsBean tempBean = (TestMarkKindsBean) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            kindsBean.setChecked(true);
            if ("自定义".equals(kindsBean.getName())) {
                showView(mLlScanData, true);
            } else {
                showView(mLlScanData, false);
            }
            mKindsAdapter.notifyDataSetChanged();
        });
        mCourseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TypeCourseBean kindsBean = (TypeCourseBean) adapter.getItem(position);
            for (int i = 0; i < adapter.getItemCount(); i++) {
                TypeCourseBean tempBean = (TypeCourseBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            mCourseAdapter.notifyDataSetChanged();
        });
        mTvChoiceHomewokOrExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AwDataUtil.isEmpty(mHomeworkAllList)) {
                    showDialog("作业列表获取失败");
                    return;
                }
                AwPopupwindowUtil.showCommonListPopupWindow(mActivity, mHomeworkAllList, mToolbar, bean -> {
                    AnswerSheetAllDataResultBean temp = (AnswerSheetAllDataResultBean) bean;
                    currentHomeworkId = temp.getHomeworkId();
                    setText(mTvChoiceHomewokOrExam, temp.getHomeworkName());
                });
            }
        });
        //
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(mSubjectDataList.size()>0){
                    SubjectDataResultBean subjectDataResultBean = mSubjectDataList.get(tab.getPosition());
                    courseId=subjectDataResultBean.getId();
                    currentHomeworkId="";
                    setText(mTvChoiceHomewokOrExam, "");
                    getData(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mImgQuestionBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClass(QuestionBasketActivity.class, false);
            }
        });
        mTvQuestionBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toClass(QuestionBasketActivity.class, false);
            }
        });
    }

    private void resetDate() {
        setText(mTvStartDate, "");
        setText(mTvEndDate, "");
        startDate = "";
        endDate = "";
//        startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-7)));
//        endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));
    }


    /**
     * 初始化筛选框控件
     */
    private void initChoiceData() {
        //初始化 分类
        mKindsAdapter = new MarkChoiceKindsAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataKinds, mKindsAdapter, 4);
        //初始化 学科
        mCourseAdapter = new MarkChoiceCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataCourese, mCourseAdapter, 4);

        setChoiceData();

    }

    /**
     * 填充筛选框数据, 测试用, 实际数据要使用后台返回
     */
    private void setChoiceData() {
        showView(mLlScanData, false);
        //分类
        mKindsBeanList = TestDataUtil.createErrorQuestionKindsList();
        if (AwDataUtil.isEmpty(mKindsBeanList)) {
            mKindsAdapter.clearData();
            mRcvDataKinds.removeAllViews();
            mKindsAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mKindsAdapter.addAllData(mKindsBeanList);
            mKindsAdapter.loadMoreComplete();
            mKindsAdapter.setEnableLoadMore(false);
            mKindsAdapter.disableLoadMoreIfNotFullPage(mRcvDataKinds);
        }
    }

    @Override
    public void getHomeworkListAllSuccess(List<AnswerSheetAllDataResultBean> model) {
        mHomeworkAllList = model;
    }

    @Override
    public void getHomeworkListAllFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getMistakeListSuccess(AnswerSheetDataResultBean data, List<ErrorQuestionResultBean> list, int total, int size, int pages, int current) {
        needRefresh = false;
        mSflLayout.setRefreshing(false);
        totalPages = pages;
        if (AwDataUtil.isEmpty(mTypeCourseBeanList)) {
            //组合学科列表
            TypeCourseBean typeCourseBean = new TypeCourseBean();
            typeCourseBean.setCourseId(AwBaseConstant.COMMON_INVALID_VALUE);
            typeCourseBean.setCourseName("全部");
            typeCourseBean.setSelect(true);
            mTypeCourseBeanList.add(0, typeCourseBean);
            if (data != null && !AwDataUtil.isEmpty(data.getCoursesList())) {
                List<CourseBean> tempList = data.getCoursesList();
                for (CourseBean temp : tempList) {
                    boolean isExist = false;
                    for (TypeCourseBean temp2 : mTypeCourseBeanList) {
                        if (temp.getCourse().equals(temp2.getCourseName())) {
                            isExist = true;
                        }
                    }
                    if (!isExist) {
                        mTypeCourseBeanList.add(new TypeCourseBean(temp.getId(), temp.getCourse(), false));
                    }
                }
                mAdapter.clearData();
                mRcvData.removeAllViews();
                mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
                return;
            }
            mCourseAdapter.addAllData(mTypeCourseBeanList);
            mCourseAdapter.loadMoreComplete();
            mCourseAdapter.setEnableLoadMore(false);
            mCourseAdapter.disableLoadMoreIfNotFullPage(mRcvDataCourese);
        }
/*        if (AwDataUtil.isEmpty(list) || AwDataUtil.isEmpty(data.getNewList())) {
            if (current == 1) {
                mAdapter.clearData();
                mRcvData.removeAllViews();
                mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            }
            mAdapter.loadMoreEnd(true);
            return;
        }*/
        if (!AwDataUtil.isEmpty(data.getNewList()) && index == 1) {
            mErrorQuestionResultBeanList.addAll(data.getNewList());
        }
        mErrorQuestionResultBeanList.addAll(list);
        mAdapter.addAllData(mErrorQuestionResultBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }


    @Override
    public void getMistakeListFail(String msg) {
        AwLog.d("getMistakeList fail");
        needRefresh = false;
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
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
    public void addQuestionBasketSuccess(String msg) {
        showMsg("添加题篮成功");
        getQuestionNum();
        for (ErrorQuestionResultBean temp : mErrorQuestionResultBeanList) {
            if (temp.getQuestionId().equals(mErrorQuestionResultBean.getQuestionId())) {
                temp.setPractice("1");
            }
        }
        mAdapter.notifyDataSetChanged();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(mErrorQuestionResultBean.getQuestionId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_ERROR_QUESTION, false, questionIds));
    }

    @Override
    public void delSomeOneQuestionBasketSuccess(String msg) {
        showMsg("移出题篮成功");
        getQuestionNum();
        for (ErrorQuestionResultBean temp : mErrorQuestionResultBeanList) {
            if (temp.getQuestionId().equals(mErrorQuestionResultBean.getQuestionId())) {
                temp.setPractice("0");
            }
        }
        mAdapter.notifyDataSetChanged();
        List<String> questionIds = new ArrayList<>();
        questionIds.add(mErrorQuestionResultBean.getQuestionId());
        EventBus.getDefault().postSticky(new RxRefreshQuestionBasketType(RxRefreshQuestionBasketType.TAG_ERROR_QUESTION, true, questionIds));
    }

    @Override
    public void getSubjectListSuccess(List<SubjectDataResultBean> dataResultBeans) {
       if(dataResultBeans!=null&&dataResultBeans.size()>0){
           mSubjectDataList=dataResultBeans;
           if(dataResultBeans.size()<=3){
               mTabLayout.setTabMode(TabLayout.MODE_FIXED);
               mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
           }
           for (int i = 0; i < dataResultBeans.size(); i++) {
               mTabLayout.addTab(mTabLayout.newTab().setText(dataResultBeans.get(i).getCourse()),false);
           }
           mTabLayout.getTabAt(0).select();//默认选中第一个
           getData(true);
       }else{
           getMistakeListFail("暂无错题");
       }
    }

    @Override
    public void getPracticeDataListSuccess(PracticeDataResultBean bean) {
        if(bean.getQuest().size()>99){
            mTvNum.setText("99");
        }else{
            mTvNum.setText(bean.getQuest().size()+"");
        }
    }


    @Override
    public void getPracticeDataListFail(String msg) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        getQuestionNum();
    }
    private void getQuestionNum() {
        mPresenter.getPracticeDataList(UserUtil.getStudId(), "", "", "", "");
    }
}
