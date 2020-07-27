package com.jkrm.education.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.exoplayer2.C;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPickerTimeUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.score.MarkChoiceClassesAdapter;
import com.jkrm.education.adapter.score.MarkChoiceCourseAdapter;
import com.jkrm.education.adapter.score.MarkChoiceCourseNewAdapter;
import com.jkrm.education.adapter.score.MarkChoiceKindsAdapter;
import com.jkrm.education.adapter.score.MarkChoicePhaseAdapter;
import com.jkrm.education.adapter.score.ScoreListAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.ClassBean;
import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.rx.RxAnswerSituationType;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.bean.test.TestMarkClassesBean;
import com.jkrm.education.bean.test.TestMarkCourseBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.bean.test.TestMarkPhaseBean;
import com.jkrm.education.bean.type.TypeClassBean;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.constants.TestConstant;
import com.jkrm.education.mvp.presenters.ScoreMainPresent;
import com.jkrm.education.mvp.views.ScoreMainFragmentView;
import com.jkrm.education.ui.activity.ScanQrcodeActivity;
import com.jkrm.education.ui.activity.homework.HomeworkDetailActivity;
import com.jkrm.education.util.DataUtil;
import com.jkrm.education.util.ReLoginUtil;
import com.jkrm.education.util.TestDataUtil;
import com.jkrm.education.widgets.ScreenSubjectDialogFragment;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 成绩 ---to 作业20190529
 * Created by hzw on 2019/5/5.
 */

public class ScoreMainFragment extends AwMvpLazyFragment<ScoreMainPresent> implements ScoreMainFragmentView.View,
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
    @BindView(R.id.rcv_dataCourese)
    RecyclerView mRcvDataCourese;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private ScoreListAdapter mAdapter;
    //筛选内容
    private MarkChoiceKindsAdapter mKindsAdapter;
    private MarkChoiceCourseAdapter mCourseAdapter;
    private MarkChoiceCourseNewAdapter mMarkChoiceCourseNewAdapter;
    private MarkChoiceClassesAdapter mClassesAdapter;

    private List<RowsHomeworkBean> mRowsHomeworkBeanList = new ArrayList<>();
    private List<TypeClassBean> mTypeClassBeanList = new ArrayList<>();
    private List<TypeCourseBean> mTypeCourseBeanList = new ArrayList<>();
    private List<TestMarkKindsBean> mKindsBeanList = new ArrayList<>();

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
        return R.layout.fragment_score_main;
    }

    @Override
    protected void initView() {
        super.initView();

        setToolbarLeftImgWithRightTxt("作业", R.mipmap.icon_scan, "筛选",
                () -> {
                    AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsCamera, new IPermissionListener() {
                        @Override
                        public void granted() {
                            toClass(ScanQrcodeActivity.class, false);
                        }

                        @Override
                        public void shouldShowRequestPermissionRationale() {
                            showDialog("扫码需开启相机权限");
                        }

                        @Override
                        public void needToSetting() {
                            showDialog("扫码需开启相机权限");
                        }
                    });
                }, () -> {
                    ScreenSubjectDialogFragment  mScreenSubjectDialogFragment = new ScreenSubjectDialogFragment();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(Extras.KEY_SCREEN_COURSE,(Serializable)mTypeCourseBeanList);
                    mScreenSubjectDialogFragment.setArguments(bundle);
                    mScreenSubjectDialogFragment.show(getFragmentManager(),null);
                    mScreenSubjectDialogFragment.setOnItemClickListener(new ScreenSubjectDialogFragment.onItemClickListener() {
                        @Override
                        public void onItemClick(int pos) {
                            if(AwBaseConstant.COMMON_INVALID_VALUE.equals(mTypeCourseBeanList.get(pos).getCourseId())) {
                                courseId = "";
                            }else{
                                courseId =mTypeCourseBeanList.get(pos).getCourseId();
                            }
                            getData(true);
                        }
                    });
                   /* if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                        mDrawerLayout.closeDrawers();
                    } else {
                        mDrawerLayout.openDrawer(Gravity.RIGHT);
                    }*/
                });
        mToolbar.setRTextColor(R.color.white);
        mToolbar.setToolbarTitleColor(R.color.white);
        // 禁止手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2020/5/9 用户信息格式不一样需要重新登录
        if(AwDataUtil.isEmpty(AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO,MyConstant.SPConstant.KEY_ACC,""))){
            ReLoginUtil.reLogin(mActivity);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, true);
        mAdapter = new ScoreListAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);
        mAdapter.setEnableLoadMore(false);
        resetDate();
        getData(true);
        initChoiceData();
    }

    private void resetDate() {
        setText(mTvStartDate, "");
        setText(mTvEndDate, "");
        startDate = "";
        endDate = "";

//        startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-7)));
//        endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    private void getData(boolean needResetIndex) {
        if(needResetIndex) {
            index = 1;
            mRowsHomeworkBeanList = new ArrayList<>();
        }
        mPresenter.getAnswerSheets(MyApp.getInstance().getAppUser().getStudId(),
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

        //初始化 班级
        mClassesAdapter = new MarkChoiceClassesAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataPhase, mClassesAdapter, 4);

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
        mKindsBeanList = DataUtil.createKindsList();
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
            classId = "";
            courseId = "";
            resetDate();
        });
        mBtnConfirm.setOnClickListener(v -> {
            for(TypeClassBean tempBean : mTypeClassBeanList) {
                if(tempBean.isSelect()) {
                    if(AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getClassId())) {
                        classId = "";
                    } else {
                        classId = tempBean.getClassId();

                    }
                }
            }
            for(TypeCourseBean tempBean : mTypeCourseBeanList) {
                if(tempBean.isSelect()) {
                    if(AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getCourseId())) {
                        courseId = "";
                    } else {
                        courseId = tempBean.getCourseId();
                    }
                }
            }
            startDate = mTvStartDate.getText().toString();
            endDate = mTvEndDate.getText().toString();
            mDrawerLayout.closeDrawers();
            mAdapter.clearData();
            mRcvData.removeAllViews();
            getData(true);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RowsHomeworkBean bean = (RowsHomeworkBean) adapter.getItem(position);
            if(bean.isHandle()) {
                toClass(HomeworkDetailActivity.class, false, Extras.KEY_BEAN_ROWS_HOMEWORK, bean);
            } else {
                showMsg("处理中，请稍后查看");
            }
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
        mClassesAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TypeClassBean kindsBean = (TypeClassBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                TypeClassBean tempBean = (TypeClassBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            mClassesAdapter.notifyDataSetChanged();
        });
        mCourseAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            TypeCourseBean kindsBean = (TypeCourseBean) adapter.getItem(position);
            for(int i=0; i<adapter.getItemCount(); i++) {
                TypeCourseBean tempBean = (TypeCourseBean) adapter.getItem(i);
                tempBean.setSelect(false);
            }
            kindsBean.setSelect(true);
            mCourseAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current) {
        mSflLayout.setRefreshing(false);
        totalPages = pages;
        //组合班级列表(已组装完毕的, 再次刷新接口获取数据后不再组装.)
        if(AwDataUtil.isEmpty(mTypeClassBeanList)) {
            TypeClassBean typeClassBean = new TypeClassBean();
            typeClassBean.setClassId(AwBaseConstant.COMMON_INVALID_VALUE);
            typeClassBean.setClassName("全部");
            typeClassBean.setSelect(true);
            mTypeClassBeanList.add(0, typeClassBean);
            if(data != null && !AwDataUtil.isEmpty(data.getClassList())) {
                List<ClassBean> tempList = data.getClassList();
                for(ClassBean temp : tempList) {
                    boolean isExist = false;
                    for(TypeClassBean temp2 : mTypeClassBeanList) {
                        if(temp.getName().equals(temp2.getClassName())) {
                            isExist = true;
                        }
                    }
                    if(!isExist) {
                        mTypeClassBeanList.add(new TypeClassBean(temp.getId(), temp.getName(), false));
                    }
                }
            }
            mClassesAdapter.addAllData(mTypeClassBeanList);
            mClassesAdapter.loadMoreComplete();
            mClassesAdapter.setEnableLoadMore(false);
            mClassesAdapter.disableLoadMoreIfNotFullPage(mRcvDataPhase);
        }

        if(AwDataUtil.isEmpty(mTypeCourseBeanList)) {
            //组合学科列表
            TypeCourseBean typeCourseBean = new TypeCourseBean();
            typeCourseBean.setCourseId(AwBaseConstant.COMMON_INVALID_VALUE);
            typeCourseBean.setCourseName("全部");
            typeCourseBean.setSelect(true);
            mTypeCourseBeanList.add(0, typeCourseBean);
            if(data != null && !AwDataUtil.isEmpty(data.getCoursesList())) {
                List<CourseBean> tempList = data.getCoursesList();
                for(CourseBean temp : tempList) {
                    boolean isExist = false;
                    for(TypeCourseBean temp2 : mTypeCourseBeanList) {
                        if(temp.getCourse().equals(temp2.getCourseName())) {
                            isExist = true;
                        }
                    }
                    if(!isExist) {
                        mTypeCourseBeanList.add(new TypeCourseBean(temp.getId(), temp.getCourse(), false));
                    }
                }
            }
            getData(true);
            mCourseAdapter.addAllData(mTypeCourseBeanList);
            mMarkChoiceCourseNewAdapter.addAllData(mTypeCourseBeanList);
            mCourseAdapter.loadMoreComplete();
            mCourseAdapter.setEnableLoadMore(false);
            mCourseAdapter.disableLoadMoreIfNotFullPage(mRcvDataCourese);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RxAnswerSituationType rxAnswerSituationType){
        getData(true);
    }
}
