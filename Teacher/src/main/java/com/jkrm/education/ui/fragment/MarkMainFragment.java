package com.jkrm.education.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPickerTimeUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkChoiceClassesAdapter;
import com.jkrm.education.adapter.mark.MarkChoiceCourseAdapter;
import com.jkrm.education.adapter.mark.MarkChoiceKindsAdapter;
import com.jkrm.education.adapter.mark.MarkChoicePhaseAdapter;
import com.jkrm.education.adapter.mark.MarkListAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.result.TeacherClassCouresResultBean;
import com.jkrm.education.bean.rx.RxRefreshHomeworkDetailType;
import com.jkrm.education.bean.rx.RxRefreshHomeworkListType;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.bean.test.TestMarkPhaseBean;
import com.jkrm.education.bean.type.TypeClassBean;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.ScoreMainPresent;
import com.jkrm.education.mvp.views.ScoreMainFragmentView;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.ui.activity.homework.HomeworkDetailActivity;
import com.jkrm.education.ui.activity.login.ChoiceClassesActivity;
import com.jkrm.education.ui.activity.mark.MarkDetailActivity;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.jkrm.education.bean.result.TeacherClassCouresResultBean.ClassListBean;
import com.jkrm.education.bean.result.TeacherClassCouresResultBean.ClassListBean.CourseListBean;
import butterknife.BindView;

/**
 * 批阅 ---to 作业20190529
 * Created by hzw on 2019/5/5.
 */

public class MarkMainFragment extends AwMvpLazyFragment<ScoreMainPresent> implements ScoreMainFragmentView.View,
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
    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;
    @BindView(R.id.rcv_dataPhase)
    RecyclerView mRcvDataPhase;
    @BindView(R.id.rcv_dataClassees)
    RecyclerView mRcvDataClassees;
    @BindView(R.id.rcv_dataCourese)
    RecyclerView mRcvDataCourese;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private MarkListAdapter mAdapter;
    //筛选内容
    private MarkChoiceKindsAdapter mKindsAdapter;
    private MarkChoicePhaseAdapter mPhaseAdapter;
    private MarkChoiceCourseAdapter mCourseAdapter;
    private MarkChoiceClassesAdapter mClassesAdapter;

    private List<RowsHomeworkBean> mRowsHomeworkBeanList = new ArrayList<>();
    private List<TeacherClassCouresResultBean> mTeacherClassCouresResultBeanList = new ArrayList<>();
    private List<ClassListBean> mClassListBeanList = new ArrayList<>();
    private List<ClassListBean> mClassListBeanListAll = new ArrayList<>();
    private List<CourseListBean> mCourseListBeanList = new ArrayList<>();
    private List<CourseListBean> mCourseListBeanListAll = new ArrayList<>();

    private List<TypeClassBean> mTypeClassBeanList = new ArrayList<>();
    private List<TypeCourseBean> mTypeCourseBeanList = new ArrayList<>();

    private int index = 1;
    private int totalPages = Integer.MAX_VALUE;
    private String startDate = "";
    private String endDate = "";
    private String classId = "";
    private String courseId = "";

    @Override
    protected ScoreMainPresent createPresenter() {
        return new ScoreMainPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mark_main;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarHideLeftWithRightTxt("作业", "筛选", () -> {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawers();
            } else {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        mToolbar.setToolbarTitleColor(R.color.white);
        mToolbar.setRTextColor(R.color.white);
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    protected void initData() {
        super.initData();
        resetDate();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mAdapter = new MarkListAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        mPresenter.getTeacherClassAndCourseList(UserUtil.getTeacherId());
        getData(true);
        initChoiceData();
        EventBus.getDefault().register(this);
    }

    private void resetDate() {
//        startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-7)));
//        endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));

        //20190828 修改默认全部数据, 不筛选时间了
        startDate = "";
        endDate = "";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByBus(RxRefreshHomeworkListType type) {
        if(type == null)
            return;
        getData(true);
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

    public boolean isDrawerLayoutShowing() {
        return mDrawerLayout.isDrawerOpen(Gravity.RIGHT);
    }

    public void closeDrawerLayout() {
        mDrawerLayout.closeDrawers();
    }

    private void getData(boolean needResetIndex) {
        if(needResetIndex) {
            index = 1;
            mRowsHomeworkBeanList = new ArrayList<>();
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.getAnswerSheets(MyApp.getInstance().getAppUser().getTeacherId(),
                startDate, endDate, classId, courseId, index);
    }

    @Override
    public void onLoadMoreRequested() {
        if(index < totalPages) {
            index++;
            getData(false);
        } else {
            mAdapter.loadMoreEnd(true);
        }
    }

    /**
     *  初始化筛选框控件
     */
    private void initChoiceData() {
        //初始化 分类
        mKindsAdapter = new MarkChoiceKindsAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataKinds, mKindsAdapter, 4);
        //初始化 年级学段
        mPhaseAdapter = new MarkChoicePhaseAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataPhase, mPhaseAdapter, 4);
        //初始化 班级
        mClassesAdapter = new MarkChoiceClassesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataClassees, mClassesAdapter, 4);
        //初始化 学科
        mCourseAdapter = new MarkChoiceCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataCourese, mCourseAdapter, 4);

        setChoiceData();

    }

    /**
     * 填充筛选框数据, 测试用, 实际数据要使用后台返回
     */
    private void setChoiceData() {
        //分类
        List<TestMarkKindsBean> kindsBeanList = TestDataUtil.createMarkKindsList();
        if (AwDataUtil.isEmpty(kindsBeanList)) {
            mKindsAdapter.clearData();
            mRcvDataKinds.removeAllViews();
            mKindsAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mKindsAdapter.addAllData(kindsBeanList);
            mKindsAdapter.loadMoreComplete();
            mKindsAdapter.setEnableLoadMore(false);
            mKindsAdapter.disableLoadMoreIfNotFullPage(mRcvDataKinds);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mRcvData);
        mTvStartDate.setOnClickListener(v -> AwPickerTimeUtil.showPickerTimeView(mActivity, getString(R.string.common_date_start), AwDateUtils.formatDate17, dateString -> {
            if(AwDataUtil.isEmpty(mTvEndDate.getText().toString())) {
                setText(mTvStartDate, dateString);
            } else {
                if(AwDateUtils.isAfterFirstDateParams(dateString, mTvEndDate.getText().toString())) {
                    setText(mTvStartDate, dateString);
                } else {
                    showDialog(getString(R.string.common_date_error1));
                    return;
                }
            }
        }));
        mTvEndDate.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(mTvStartDate.getText().toString())) {
                showMsg(getString(R.string.common_date_error_select_start_date));
                return;
            }
            AwPickerTimeUtil.showPickerTimeView(mActivity, getString(R.string.common_date_end), AwDateUtils.formatDate17, dateString -> {
                if(AwDateUtils.isAfterFirstDateParams(mTvStartDate.getText().toString(), dateString)) {
                    setText(mTvEndDate, dateString);
                } else {
                    showDialog(getString(R.string.common_date_error2));
                }
            });
        });
        mBtnReset.setOnClickListener(v -> {
            setChoiceData();
            if(!AwDataUtil.isEmpty(mTypeClassBeanList)) {
                for(TypeClassBean temp : mTypeClassBeanList) {
                    temp.setSelect("全部".equals(temp.getClassName()));
                }
                mClassesAdapter.notifyDataSetChanged();
            }
            if(!AwDataUtil.isEmpty(mTypeCourseBeanList)) {
                for(TypeCourseBean temp : mTypeCourseBeanList) {
                    temp.setSelect("全部".equals(temp.getCourseName()));
                }
                mCourseAdapter.notifyDataSetChanged();
            }
            setText(mTvStartDate, "");
            setText(mTvEndDate, "");
            classId = "";
            courseId = "";
            resetDate();
            resetPhase_class_course();
        });
        mBtnConfirm.setOnClickListener(v -> {
            for(ClassListBean tempBean : mClassListBeanList) {
                if(tempBean.isSelect()) {
                    if(AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getClassId())) {
                        classId = "";
                    } else {
                        classId = tempBean.getClassId();

                    }
                }
            }
            for(CourseListBean tempBean : mCourseListBeanList) {
                if(tempBean.isSelect()) {
                    if(AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getCourse())) {
                        courseId = "";
                    } else {
                        courseId = tempBean.getCourse();
                    }
                }
            }
            mDrawerLayout.closeDrawers();
            startDate = mTvStartDate.getText().toString();
            endDate = mTvEndDate.getText().toString();
            mAdapter.clearData();
            mRcvData.removeAllViews();
            getData(true);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RowsHomeworkBean bean = (RowsHomeworkBean) adapter.getItem(position);
            if(bean.isHandle()) {
                toClass(HomeworkDetailActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, bean,Extras.KEY_BEAN_ROWS_HOMEWORK_LIST,(Serializable)mRowsHomeworkBeanList,Extras.KEY_BEAN_ROWS_HOMEWORK_PRO,position);
            } else {
                showMsg("处理中，请稍后查看");
            }
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RowsHomeworkBean bean = (RowsHomeworkBean) adapter.getItem(position);
            if(bean.isHandle()) {
                //不管是回评还是批阅, 都是进入批阅详情页面.
                toClass(MarkDetailActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, bean);
            } else {
                showMsg("处理中，请稍后查看");
            }
//            if(bean.isMarkFinish()) {
//                toClass(MarkDetailActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, bean);
//            } else {
//                toClass(MarkActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, bean);
//            }

        });
        mKindsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TestMarkKindsBean kindsBean = (TestMarkKindsBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                TestMarkKindsBean tempBean = (TestMarkKindsBean) adapter.getItem(i);
                tempBean.setChecked(false);
            }
            kindsBean.setChecked(true);
            mKindsAdapter.notifyDataSetChanged();
        });
        mPhaseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TeacherClassCouresResultBean kindsBean = (TeacherClassCouresResultBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                TeacherClassCouresResultBean tempBean = (TeacherClassCouresResultBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            mPhaseAdapter.notifyDataSetChanged();
            setClassAndCourseData(kindsBean);
        });
        mClassesAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ClassListBean kindsBean = (ClassListBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                ClassListBean tempBean = (ClassListBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            classId = kindsBean.getClassId();
            mClassesAdapter.notifyDataSetChanged();
            setCourseData(kindsBean);
        });
        mCourseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CourseListBean kindsBean = (CourseListBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                CourseListBean tempBean = (CourseListBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            courseId = kindsBean.getCourse();
            mCourseAdapter.notifyDataSetChanged();
        });
    }

    /**
     * 填充年级数据
     * @param list
     */
    private void setPhaseData(List<TeacherClassCouresResultBean> list) {
        mTeacherClassCouresResultBeanList = list;
        if(AwDataUtil.isEmpty(mTeacherClassCouresResultBeanList)) {
            mTeacherClassCouresResultBeanList = new ArrayList<>();
        }
        if(AwDataUtil.isEmpty(mTeacherClassCouresResultBeanList) || !"全部".equals(mTeacherClassCouresResultBeanList.get(0).getGradeName())) {
            TeacherClassCouresResultBean teacherClassCouresResultBean = new TeacherClassCouresResultBean();
            teacherClassCouresResultBean.setGradeId(AwBaseConstant.COMMON_INVALID_VALUE);
            teacherClassCouresResultBean.setGradeName("全部");
            teacherClassCouresResultBean.setSelect(true);
            teacherClassCouresResultBean.setClassList(new ArrayList<>());
            mTeacherClassCouresResultBeanList.add(0, teacherClassCouresResultBean);
        }

        //填充年级
        mPhaseAdapter.addAllData(mTeacherClassCouresResultBeanList);
        mPhaseAdapter.loadMoreComplete();
        mPhaseAdapter.setEnableLoadMore(false);
        mPhaseAdapter.disableLoadMoreIfNotFullPage(mRcvDataPhase);

        setClassAndCourseData(mTeacherClassCouresResultBeanList.get(0));
    }

    /**
     * 填充班级数据
     * @param teacherClassCouresResultBean
     */
    private void setClassAndCourseData(TeacherClassCouresResultBean teacherClassCouresResultBean) {
        if("全部".equals(teacherClassCouresResultBean.getGradeName())) {
            mClassListBeanList = mClassListBeanListAll;
        } else {
            //填充班级
            mClassListBeanList = teacherClassCouresResultBean.getClassList();
        }

        if(AwDataUtil.isEmpty(mClassListBeanList)) {
            mClassListBeanList = new ArrayList<>();
        }
        if(AwDataUtil.isEmpty(mClassListBeanList) || !"全部".equals(mClassListBeanList.get(0).getClassName())) {
            ClassListBean classListBean = new ClassListBean();
            classListBean.setClassId(AwBaseConstant.COMMON_INVALID_VALUE);
            classListBean.setClassName("全部");
            classListBean.setSelect(true);
            mClassListBeanList.add(0, classListBean);
        } else {
            for(ClassListBean temp : mClassListBeanList) {
                temp.setSelect(false);
            }
            mClassListBeanList.get(0).setSelect(true);
        }

        mClassesAdapter.addAllData(mClassListBeanList);
        mClassesAdapter.loadMoreComplete();
        mClassesAdapter.setEnableLoadMore(false);
        mClassesAdapter.disableLoadMoreIfNotFullPage(mRcvDataClassees);

        setCourseData(mClassListBeanList.get(0));
    }

    /**
     * 填充学科数据
     * @param classListBean
     */
    private void setCourseData(ClassListBean classListBean) {
        if("全部".equals(classListBean.getClassName())) {
            mCourseListBeanList = mCourseListBeanListAll;
        } else {
            //填充学科
            mCourseListBeanList = classListBean.getCourseList();
        }
        if(AwDataUtil.isEmpty(mCourseListBeanList)) {
            mCourseListBeanList = new ArrayList<>();
        }
        if(AwDataUtil.isEmpty(mCourseListBeanList) || !"全部".equals(mCourseListBeanList.get(0).getCourseName())) {
            CourseListBean courseListBean = new CourseListBean();
            courseListBean.setCourse(AwBaseConstant.COMMON_INVALID_VALUE);
            courseListBean.setCourseName("全部");
            courseListBean.setSelect(true);
            mCourseListBeanList.add(0, courseListBean);
        } else {
            for(CourseListBean temp : mCourseListBeanList) {
                temp.setSelect(false);
            }
            mCourseListBeanList.get(0).setSelect(true);
        }

        mCourseAdapter.addAllData(mCourseListBeanList);
        mCourseAdapter.loadMoreComplete();
        mCourseAdapter.setEnableLoadMore(false);
        mCourseAdapter.disableLoadMoreIfNotFullPage(mRcvDataCourese);
    }

    /**
     * 重置年级班级学科数据
     */
    private void resetPhase_class_course() {
        if(AwDataUtil.isEmpty(mTeacherClassCouresResultBeanList)) {
            return;
        }
        for(TeacherClassCouresResultBean temp : mTeacherClassCouresResultBeanList) {
            temp.setSelect(false);
        }
        mTeacherClassCouresResultBeanList.get(0).setSelect(true);
        mPhaseAdapter.notifyDataSetChanged();
        setClassAndCourseData(mTeacherClassCouresResultBeanList.get(0));
    }

    @Override
    public void getTeacherClassAndCourseListSuccess(List<TeacherClassCouresResultBean> list) {
        mClassListBeanListAll = new ArrayList<>();
        mCourseListBeanListAll = new ArrayList<>();
        for(TeacherClassCouresResultBean temp : list) {
            mClassListBeanListAll.addAll(temp.getClassList());
        }
        for(ClassListBean temp : mClassListBeanListAll) {
            for(CourseListBean tempCourse : temp.getCourseList()) {
                if(!mCourseListBeanListAll.contains(tempCourse)) {
                    mCourseListBeanListAll.add(tempCourse);
                }
            }
        }
        // TODO: 2020/5/7   过滤学科重复数据
        HashMap<String,ClassListBean.CourseListBean> map=new HashMap<>();
        for (ClassListBean.CourseListBean classListBean : mCourseListBeanListAll) {
            String classId = classListBean.getCourse();
            map.put(classId,classListBean);
        }
        mCourseListBeanListAll.clear();
        mCourseListBeanListAll.addAll(map.values());
        setPhaseData(list);
    }

    @Override
    public void getTeacherClassAndCourseListFail(String msg) {
       setPhaseData(new ArrayList<>());
    }

    @Override
    public void needBingSchoolClass() {
        showMsg("请先绑定班级");
        toClass(ChoiceClassesActivity.class,false);
    }

    @Override
    public void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {
        mSflLayout.setRefreshing(false);
        totalPages = pages;
        if(AwDataUtil.isEmpty(mTypeClassBeanList) || AwDataUtil.isEmpty(mTypeCourseBeanList) ) {
            //组装班级和学科信息, 已组装过的刷新接口数据后不再重新组装
            if(data != null && !AwDataUtil.isEmpty(data.getClassList())) {
                MyApp.mTeacherClassHomeworkBeanList = data.getClassList();
            } else {
                MyApp.mTeacherClassHomeworkBeanList = new ArrayList<>();
            }
//            List<TeacherClassBean> teacherClassBeanList = new ArrayList<>();
//            if(data != null && !AwDataUtil.isEmpty(data.getTeachingClasses())) {
//                teacherClassBeanList = data.getTeachingClasses();
//            }
//            TeacherClassBean teacherClassBean = new TeacherClassBean();
//            teacherClassBean.setSelect(true);
//            teacherClassBean.setClassId(AwBaseConstant.COMMON_INVALID_VALUE);
//            teacherClassBean.setClassName("全部");
//            teacherClassBean.setCourse(AwBaseConstant.COMMON_INVALID_VALUE);
//            teacherClassBean.setCourseName("全部");
//            teacherClassBeanList.add(0, teacherClassBean);
//
//            mTypeClassBeanList = new ArrayList<>();
//            mTypeCourseBeanList = new ArrayList<>();
//
//            for(TeacherClassBean temp : teacherClassBeanList) {
//                if(AwDataUtil.isEmpty(classId)) {
//                    mTypeClassBeanList.add(new TypeClassBean(temp.getClassId(), temp.getClassName(), "全部".equals(temp.getClassName())));
//                } else {
//                    mTypeClassBeanList.add(new TypeClassBean(temp.getClassId(), temp.getClassName(), classId.equals(temp.getClassId())));
//                }
//            }
//
//            for(TeacherClassBean temp : teacherClassBeanList) {
//                boolean isExist = false;
//                for(TypeCourseBean temp2 : mTypeCourseBeanList) {
//                    if(temp.getCourseName().equals(temp2.getCourseName())) {
//                        isExist = true;
//                    }
//                }
//                if(!isExist) {
//                    if(AwDataUtil.isEmpty(courseId)) {
//                        mTypeCourseBeanList.add(new TypeCourseBean(temp.getCourse(), temp.getCourseName(), "全部".equals(temp.getCourseName())));
//                    } else {
//                        mTypeCourseBeanList.add(new TypeCourseBean(temp.getCourse(), temp.getCourseName(), courseId.equals(temp.getCourse())));
//                    }
//                }
//            }
//            //班级列表
//            if (AwDataUtil.isEmpty(mTypeClassBeanList)) {
//                mClassesAdapter.clearData();
//                mRcvDataClassees.removeAllViews();
//                mClassesAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
//            } else {
//                mClassesAdapter.addAllData(mTypeClassBeanList);
//                mClassesAdapter.loadMoreComplete();
//                mClassesAdapter.setEnableLoadMore(false);
//                mClassesAdapter.disableLoadMoreIfNotFullPage(mRcvDataClassees);
//            }
//            //学科列表
//            if (AwDataUtil.isEmpty(mTypeCourseBeanList)) {
//                mCourseAdapter.clearData();
//                mRcvDataCourese.removeAllViews();
//                mCourseAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
//            } else {
//                mCourseAdapter.addAllData(mTypeCourseBeanList);
//                mCourseAdapter.loadMoreComplete();
//                mCourseAdapter.setEnableLoadMore(false);
//                mCourseAdapter.disableLoadMoreIfNotFullPage(mRcvDataCourese);
//            }
        }

        //填充列表数据
        if (AwDataUtil.isEmpty(list)) {
            if(current == 1) {
                mAdapter.clearData();
                mRcvData.removeAllViews();
                mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据，可筛选更换查询条件", -1));
            }
            mAdapter.loadMoreEnd(true);
            return;
        }
        mRowsHomeworkBeanList.addAll(list);
        mAdapter.addAllData(mRowsHomeworkBeanList);
        mAdapter.loadMoreComplete();
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getAnswerSheetsFail(String msg) {
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据，可筛选更换查询条件", -1));
    }
}
