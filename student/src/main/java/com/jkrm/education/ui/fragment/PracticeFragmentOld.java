package com.jkrm.education.ui.fragment;


import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwPickerTimeUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.PracticeAdapter;
import com.jkrm.education.adapter.score.MarkChoiceCourseAdapter;
import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean.PracticeBean;
import com.jkrm.education.bean.result.PracticeResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.rx.RxPracticeNumsType;
import com.jkrm.education.bean.type.TypeCourseBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.PracticeMainPresent;
import com.jkrm.education.mvp.views.PracticeMainFragmentView;
import com.jkrm.education.ui.activity.FamousTeacherLectureActivity;
import com.jkrm.education.ui.activity.ImgActivity;
import com.jkrm.education.ui.activity.QuestionExpandActivity;
import com.jkrm.education.ui.activity.SeeTargetQuestionActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * 练习
 * Created by hzw on 2019/5/5.
 */

public class PracticeFragmentOld extends AwMvpLazyFragment<PracticeMainPresent> implements PracticeMainFragmentView.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.sfl_layout)
    SwipeRefreshLayout mSflLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.rcv_dataQustion)
    RecyclerView mRcvDataQuestion;
    @BindView(R.id.rcv_dataCourese)
    RecyclerView mRcvDataCourese;
    @BindView(R.id.btn_reset)
    Button mBtnReset;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.tv_startDate)
    TextView mTvStartDate;
    @BindView(R.id.tv_endDate)
    TextView mTvEndDate;

    private List<PracticeResultBean> mPracticeResultBeanList = new ArrayList<>();
    private List<TypeCourseBean> mTypeCourseBeanList = new ArrayList<>();
    private PracticeAdapter mAdapter;
    //筛选内容
    private MarkChoiceCourseAdapter mCourseAdapter;

    private String startDate = "";
    private String endDate = "";
    private String courseId = "";

    @Override
    protected PracticeMainPresent createPresenter() {
        return new PracticeMainPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_practice;
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarHideLeftWithRightTxt("题篮", "筛选", () -> {
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
        //取消下拉刷新
        AwRecyclerViewUtil.setSwipeRefreshLayout(mSflLayout, false);
        mAdapter = new PracticeAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAdapter, false);

        View headerView = getLayoutInflater().inflate(R.layout.inflate_practice, null);
        mAdapter.addHeaderView(headerView);//添加了headerview, 注意position位置处理

        initChoiceData();
        resetDate();
        getData();
    }

    private void resetDate() {
        setText(mTvStartDate, "");
        setText(mTvEndDate, "");
        startDate = AwDateUtils.formatDate17.format(AwDateUtils.getOldDate(AwDateUtils.getOldDate(-7)));
        endDate = AwDateUtils.formatDate17.format(new Date(System.currentTimeMillis()));
    }

    @Override
    public void onRefresh() {
        getData();
    }

    private void getData() {
//        mPresenter.getPracticeDataList(UserUtil.getStudId(), startDate, endDate, courseId, "");
        mPresenter.getPracticeDataList("3d9326a7b87edf2a3fb45416adffd494", startDate, endDate, courseId, "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().postSticky(new RxPracticeNumsType(startDate, endDate));
            }
        }, 500);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mSflLayout.setOnRefreshListener(this);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PracticeBean bean = (PracticeBean) adapter.getItem(position);
//            showMsg(getString(R.string.common_function_is_dev));
            switch (view.getId()) {
                case R.id.iv_img:
                    toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getScanImage());
                    break;
                case R.id.btn_studentAnswer:
//                    if(bean.getSmDto() == null || AwDataUtil.isEmpty(bean.getSmDto().getRawScan())) {
//                        showDialog("学霸答卷图片不存在，无法展示");
//                        return;
//                    }
//                    toClass(ImgActivity.class, false, Extras.IMG_URL, bean.getSmDto().getRawScan());
                    break;
                case R.id.btn_seeAnswer:
                    toClass(SeeTargetQuestionActivity.class, false, Extras.COMMON_BOOLEAN, bean.isChoiceQuestion() ? false : true, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_questionExpand:
                    toClass(QuestionExpandActivity.class, false, Extras.COMMON_PARAMS, bean.getQuestionId());
                    break;
                case R.id.btn_famousTeacherLecture:
                    mPresenter.getVideos(bean.getQuestionId());
                    break;
            }
        });
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
            startDate = dateString;
        }));
        mTvEndDate.setOnClickListener(v -> {
            if(AwDataUtil.isEmpty(mTvStartDate.getText().toString())) {
                showMsg(getString(R.string.common_date_error_select_start_date));
                return;
            }
            AwPickerTimeUtil.showPickerTimeView(mActivity, getString(R.string.common_date_end), AwDateUtils.formatDate17, dateString -> {
                if(AwDateUtils.isAfterFirstDateParams(mTvStartDate.getText().toString(), dateString)) {
                    setText(mTvEndDate, dateString);
                    endDate = dateString;
                } else {
                    showDialog(getString(R.string.common_date_error2));
                }
            });
        });
        mBtnReset.setOnClickListener(v -> {});
        mBtnConfirm.setOnClickListener(v -> {
            for(TypeCourseBean tempBean : mTypeCourseBeanList) {
                if(tempBean.isSelect()) {
                    if(AwBaseConstant.COMMON_INVALID_VALUE.equals(tempBean.getCourseId())) {
                        courseId = "";
                    } else {
                        courseId = tempBean.getCourseId();
                    }
                }
            }
            mDrawerLayout.closeDrawers();
            getData();
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

    /**
     *  初始化筛选框控件
     */
    private void initChoiceData() {
        //初始化 学科
        mCourseAdapter = new MarkChoiceCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataCourese, mCourseAdapter, 4);

    }

    @Override
    public void getPracticeDataListSuccess(PracticeDataResultBean bean) {
        mSflLayout.setRefreshing(false);
        if(null == bean || AwDataUtil.isEmpty(bean.getList())) {
            mAdapter.clearData();
            mRcvData.removeAllViews();
            mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        }
        if(AwDataUtil.isEmpty(mTypeCourseBeanList)) {
            //组合学科列表
            TypeCourseBean typeCourseBean = new TypeCourseBean();
            typeCourseBean.setCourseId(AwBaseConstant.COMMON_INVALID_VALUE);
            typeCourseBean.setCourseName("全部");
            typeCourseBean.setSelect(true);
            mTypeCourseBeanList.add(0, typeCourseBean);
            if(bean != null && !AwDataUtil.isEmpty(bean.getCoursesList())) {
                List<CourseBean> tempList = bean.getCoursesList();
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
            mCourseAdapter.addAllData(mTypeCourseBeanList);
            mCourseAdapter.loadMoreComplete();
            mCourseAdapter.setEnableLoadMore(false);
            mCourseAdapter.disableLoadMoreIfNotFullPage(mRcvDataCourese);
        }
        List<PracticeBean> list = bean.getList();
        mAdapter.addAllData(list);
        mAdapter.loadMoreComplete();
        mAdapter.disableLoadMoreIfNotFullPage(mRcvData);
    }

    @Override
    public void getPracticeDataListFail(String msg) {
        mSflLayout.setRefreshing(false);
        mAdapter.clearData();
        mRcvData.removeAllViews();
        mAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }

    @Override
    public void getVideosSuccess(VideoResultBean result) {
        if(result == null) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        if(result.getQuestionVideo() == null && AwDataUtil.isEmpty(result.getCataVideos())) {
            showMsg(getString(R.string.hint_no_famouse_teacher_video));
            return;
        }
        toClass(FamousTeacherLectureActivity.class, false, Extras.KEY_BEAN_VIDEO_RESULT, result);
    }

    @Override
    public void getVideoFail(String msg) {
        showMsg(getString(R.string.hint_no_famouse_teacher_video));
    }
}
