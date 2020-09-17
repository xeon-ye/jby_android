package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwCommonTopListPopupWithIconWindow;
import com.hzw.baselib.widgets.BidirectionalSeekBar;
import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.error.ErrorClassesAdapter;
import com.jkrm.education.adapter.error.ErrorCourseAdapter;
import com.jkrm.education.adapter.error.ErrorHomeWordAdapter;
import com.jkrm.education.adapter.error.ErrorMistakeAdapter;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.bean.MistakeBean;
import com.jkrm.education.bean.result.error.ErrorClassesBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.bean.result.error.ErrorHomeWork;
import com.jkrm.education.mvp.presenters.ErrorQuestionFragmentPresent;
import com.jkrm.education.mvp.views.ErrorQuestionFragmentView;
import com.jkrm.education.ui.activity.ErrorQuestionActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description: 错题本
 * @Author: xiangqian
 * @CreateDate: 2020/7/2 18:50
 */

public class ErrorQuestionFragment extends AwMvpLazyFragment<ErrorQuestionFragmentPresent> implements ErrorQuestionFragmentView.View {
    @BindView(R.id.iv_error)
    ImageView mIvError;
    @BindView(R.id.tv_error_num)
    TextView mTvErrorNum;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;

    @BindView(R.id.ll_of_bottom)
    LinearLayout mLlOfBottom;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_change)
    TextView mTvChange;
    @BindView(R.id.tv_score)
    TextView mTvScore;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    Unbinder unbinder;
    @BindView(R.id.rcv_course)
    RecyclerView mRcvCourse;
    @BindView(R.id.rcv_classes)
    RecyclerView mRcvClasses;
    @BindView(R.id.rcv_homewok)
    RecyclerView mRcvHomewok;
    @BindView(R.id.tv_pro)
    TextView mTvPro;

    @BindView(R.id.ll_of_seekbar)
    LinearLayout mLlOfSeekbar;
    @BindView(R.id.tv_reset)
    TextView mTvReset;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    @BindView(R.id.ll_of_setting)
    LinearLayout mLlOfSetting;
    @BindView(R.id.bid_seekbar)
    BidirectionalSeekBar mBidSeekbar;
    @BindView(R.id.ll_of_sort)
    LinearLayout llOfSort;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.rsb)
    RangeSeekBar mRsb;
    @BindView(R.id.tv_normal)
    TextView mTvNormal;
    @BindView(R.id.tv_inverted)
    TextView mTvInverted;
    Unbinder unbinder1;

    private ErrorCourseAdapter mErrorCourseAdapter;
    private ErrorHomeWordAdapter mErrorHomeWordAdapter;
    private ErrorClassesAdapter mErrorClassesAdapter;
    private ErrorMistakeAdapter mErrorMistakeAdapter;
    private List<ErrorCourseBean> mErrorCourseBeanList = new ArrayList<>();
    private List<ErrorHomeWork> mErrorHomeWorkList = new ArrayList<>();
    private List<ErrorClassesBean> mErrorClassesList = new ArrayList<>();
    private List<MistakeBean> mMistakeList = new ArrayList<>();
    private double mLeftVaule = 0, mRightValue = 0.6;
    public static String mStrClassIds = "", mStrtempIds = "";
    private ArrayList<MarkBean> mMarkBeanList = new ArrayList<>();

    @Override
    protected ErrorQuestionFragmentPresent createPresenter() {
        return new ErrorQuestionFragmentPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_error_question_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        //关闭手势滑动
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mErrorCourseAdapter = new ErrorCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRcvCourse, mErrorCourseAdapter, false);
        mErrorHomeWordAdapter = new ErrorHomeWordAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRcvHomewok, mErrorHomeWordAdapter, false);
        mErrorClassesAdapter = new ErrorClassesAdapter();
        AwRecyclerViewUtil.setRecyclerViewFlowLayout(getActivity(), mRcvClasses, mErrorClassesAdapter, 4);
        mErrorMistakeAdapter = new ErrorMistakeAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(getActivity(), mRcvData, mErrorMistakeAdapter, false);
        EventBus.getDefault().register(this);
        mRsb.setProgress(0,60);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getErrorCourseList(UserUtil.getAppUser().getTeacherId());
        mPresenter.getErrorBasket(UserUtil.getTeacherId());

        mMarkBeanList.add(new MarkBean(true, "按题号排序"));
        mMarkBeanList.add(new MarkBean(false, "得分率排序"));
    }

    @Override
    protected void initListener() {
        super.initListener();
        //课目
        mErrorCourseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ErrorCourseBean> mList = adapter.getData();
                for (int i = 0; i < mList.size(); i++) {
                    if (i == position) {
                        mList.get(position).setChecked(true);
                        ErrorCourseBean errorCourseBean = mErrorCourseBeanList.get(position);
                        errorCourseBean.setChecked(true);
                        //mStrClassIds=errorCourseBean.getId();
                        // mErrorCourseAdapter.notifyDataSetChanged();
                        mPresenter.getErrorHomewordList(UserUtil.getAppUser().getTeacherId(), errorCourseBean.getCourseId());
                    } else {
                        mList.get(i).setChecked(false);
                    }
                }
                mErrorCourseAdapter.notifyDataSetChanged();
            }
        });
        //作业
        mErrorHomeWordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                List<ErrorHomeWork> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(position).setChecked(true);
                        ErrorHomeWork errorHomeWork = mErrorHomeWorkList.get(position);
                        mStrtempIds = errorHomeWork.getTemplateId();
                        mTvTitle.setText(errorHomeWork.getName());
                        errorHomeWork.setChecked(true);
                        mPresenter.getErrorClassesList(UserUtil.getAppUser().getSchool().getId(), errorHomeWork.getTemplateId());
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mErrorHomeWordAdapter.notifyDataSetChanged();
            }
        });
        mErrorHomeWordAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                List<ErrorHomeWork> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(position).setChecked(true);
                        ErrorHomeWork errorHomeWork = mErrorHomeWorkList.get(position);
                        mStrtempIds = errorHomeWork.getTemplateId();
                        mTvTitle.setText(errorHomeWork.getName());
                        errorHomeWork.setChecked(true);
                        mPresenter.getErrorClassesList(UserUtil.getAppUser().getSchool().getId(), errorHomeWork.getTemplateId());
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mErrorHomeWordAdapter.notifyDataSetChanged();
            }
        });
        mErrorClassesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ErrorClassesBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        ErrorClassesBean errorClassesBean = mErrorClassesList.get(position);
                        errorClassesBean.setChecked(!errorClassesBean.isChecked());
                    }
                }
                mStrClassIds = "";
                for (int i = 0; i < data.size(); i++) {
                    ErrorClassesBean errorClassesBean = mErrorClassesList.get(i);
                    if (errorClassesBean.isChecked()) {
                        mStrClassIds = mStrClassIds + errorClassesBean.getId() + ",";
                    }
                }
                mErrorClassesAdapter.notifyDataSetChanged();
            }
        });

        mBidSeekbar.setOnSeekBarChangeListener(new BidirectionalSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(int leftProgress, int rightProgress) {
                mLeftVaule = (int) ((int) Math.round((leftProgress + 5) / 10.0) * 10.0);
                mRightValue = (int) ((int) Math.round((rightProgress) / 10.0) * 10.0);
             /*   mLeftVaule = leftProgress;
                mRightValue = rightProgress;*/
                if (leftProgress == 0) {
                    mLeftVaule = 0;
                }
                if (rightProgress == 100) {
                    mRightValue = 100;
                }
                if (leftProgress == 0 && rightProgress == 100) {
                    mTvPro.setText("不限");
                } else {
                    mTvPro.setText(mLeftVaule + "-" + mRightValue + "%");
                }
            }
        });
        mRsb.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                mLeftVaule = (int) ((int) Math.round((leftValue + 5) / 10.0) * 10.0);
                mRightValue = (int) ((int) Math.round((rightValue) / 10.0) * 10.0);
             /*   mLeftVaule = leftProgress;
                mRightValue = rightProgress;*/
                if (leftValue == 0) {
                    mLeftVaule = 0;
                }
                if (rightValue == 100) {
                    mRightValue = 100;
                }
                if (leftValue == 0 && rightValue == 100) {
                    mTvPro.setText("不限");
                } else {
                    mTvPro.setText(mLeftVaule + "-" + mRightValue + "%");
                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
        mErrorMistakeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_join:
                        MistakeBean mistakeBean = mMistakeList.get(position);
                        if ("1".equals(mistakeBean.getIsBasket())) {
                            mPresenter.deleteErrorBasket(RequestUtil.deleteErrorBasket(UserUtil.getTeacherId(), mistakeBean.getId()));
                            mistakeBean.setIsBasket("0");
                        } else {
                            mPresenter.addErrorBasket(RequestUtil.deleteErrorBasket(UserUtil.getTeacherId(), mistakeBean.getId()));
                            mistakeBean.setIsBasket("1");
                        }

                        mErrorMistakeAdapter.notifyDataSetChanged();
                        break;
                    case R.id.tv_analyse:

                        break;
                }
            }
        });
    }


    @OnClick({R.id.tv_change, R.id.tv_score, R.id.tv_sort, R.id.tv_reset, R.id.tv_sure, R.id.btn_sure, R.id.iv_error, R.id.tv_normal, R.id.tv_inverted})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
                break;
            case R.id.tv_score:
                showView(mLlOfSetting, true);
                showView(llOfSort, false);
                break;
            case R.id.tv_sort:
                // showView(llOfSort, true);
                // showView(mLlOfSetting, false);
                AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlphaWithIcon(mActivity, mMarkBeanList, mTvSort, new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                    }
                }, new AwCommonTopListPopupWithIconWindow.OnItemClickListener() {
                    @Override
                    public void onClick(Object bean) {
                        if ("按题号排序".equals(((MarkBean) bean).getTitle())) {
                            setData(0);
                            mTvSort.setText("按题号排序");
                        } else if ("得分率排序".equals(((MarkBean) bean).getTitle())) {
                            setData(1);
                            mTvSort.setText("得分率排序");
                        }
                    }
                });
                break;
            case R.id.tv_reset:
                mLlOfSeekbar.removeView(mBidSeekbar);
                mLlOfSeekbar.addView(mBidSeekbar);
                mTvPro.setText("不限");
                mLeftVaule = 0;
                mRightValue = 100;

                break;
            case R.id.tv_sure:
                showView(mLlOfSetting, false);
                mTvScore.setText("得分率：" + mLeftVaule + "-" + mRightValue + "%");
                mPresenter.getErrorMistakeList(mStrClassIds, mStrtempIds, mRightValue / 100 + "", mLeftVaule / 100 + "");
                break;
            case R.id.btn_sure:
                mDrawerLayout.closeDrawers();
                mStrClassIds = "";
                for (int i = 0; i < mErrorClassesList.size(); i++) {
                    ErrorClassesBean errorClassesBean = mErrorClassesList.get(i);
                    if (errorClassesBean.isChecked()) {
                        mStrClassIds = mStrClassIds + errorClassesBean.getId() + ",";
                    }
                }
                mPresenter.getErrorMistakeList(mStrClassIds, mStrtempIds, mRightValue + "", mLeftVaule / 100 + "");
                break;
            case R.id.iv_error:
                toClass(ErrorQuestionActivity.class, false);
                break;
            case R.id.tv_normal:
                showView(llOfSort, false);
                setData(0);
                mTvSort.setText("按题号排序");
                break;
            case R.id.tv_inverted:
                showView(llOfSort, false);
                setData(1);
                mTvSort.setText("按得分率倒序排序");
                break;
        }
    }


    @Override
    public void getErrorCourseListSuccess(List<ErrorCourseBean> data) {
        if (!AwDataUtil.isEmpty(data)) {
            mErrorCourseBeanList = data;
            mErrorCourseAdapter.addAllData(mErrorCourseBeanList);
            // mRcvCourse.findViewHolderForAdapterPosition(0).itemView.performClick();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRcvCourse.findViewHolderForAdapterPosition(0).itemView.performClick();

                }
            }, 100);
/*            ErrorCourseBean errorCourseBean = mErrorCourseBeanList.get(0);
            errorCourseBean.setChecked(true);
            mErrorCourseAdapter.notifyDataSetChanged();
            mPresenter.getErrorHomewordList(UserUtil.getAppUser().getTeacherId(), errorCourseBean.getCourseId());*/
        }
    }

    @Override
    public void getErrorCorrseListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getErrorHomeworkListSuccess(List<ErrorHomeWork> data) {
        if (!AwDataUtil.isEmpty(data)) {
            mErrorHomeWorkList = data;
            mErrorHomeWordAdapter.addAllData(mErrorHomeWorkList);
            //mErrorHomeWorkList.get(0).setChecked(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRcvHomewok.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }, 100);
            //mPresenter.getErrorClassesList(UserUtil.getAppUser().getSchool().getId(), data.get(0).getTemplateId());
        }
    }

    @Override
    public void getErrorHomeworkListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getErrorClassesListSuccess(List<ErrorClassesBean> data) {
        mErrorClassesList = data;
        mErrorClassesAdapter.addAllData(mErrorClassesList);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRcvClasses.findViewHolderForAdapterPosition(0).itemView.performClick();
            }
        }, 100);
        mPresenter.getErrorMistakeList(mErrorClassesList.get(0).getId(), mErrorHomeWorkList.get(0).getTemplateId(), mRightValue + "", mLeftVaule + "");
    }

    @Override
    public void getErrorClassesListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getErrorMistakeListSuccess(List<MistakeBean> data) {
        mMistakeList = data;
        mErrorMistakeAdapter.addAllData(mMistakeList);
        if (AwDataUtil.isEmpty(mMistakeList)) {
            mErrorMistakeAdapter.clearData();
            mRcvData.removeAllViews();
            setData(0);
            mErrorMistakeAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, "暂无数据", -1));
        }
    }


    @Override
    public void getErrorMistakeListFail(String mag) {
        showMsg(mag);
    }

    @Override
    public void addErrorBasketSuccess(String data) {
        showMsg("加入题篮成功");
        mPresenter.getErrorBasket(UserUtil.getTeacherId());
    }

    @Override
    public void addErrorBasketFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getErrorBasketSuccess(List<ErrorBasketBean> data) {
        mTvErrorNum.setText(data.size() + "");
    }

    @Override
    public void getErrorBasketFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void deleteErrorBasketSuccess(String data) {
        showMsg("移除题篮成功");
        mPresenter.getErrorBasket(UserUtil.getTeacherId());
    }

    @Override
    public void deleteErrorBasketFail(String msg) {
        showMsg(msg);
    }

    private void setData(int type) {
        if (type == 0) {
            Collections.sort(mMistakeList, new Comparator<MistakeBean>() {
                @Override
                public int compare(MistakeBean o1, MistakeBean o2) {
                    //大于0表示正序，小于0表示逆序
                    return Integer.parseInt(o1.getQuestionNum()) - Integer.parseInt(o2.getQuestionNum());
                }
            });
        } else if (type == 1) {
            Collections.sort(mMistakeList, new Comparator<MistakeBean>() {
                @Override
                public int compare(MistakeBean o1, MistakeBean o2) {
                    return new Double(o2.getClassRatio()).compareTo(new Double(o1.getClassRatio()));
                }
            });
        }
        mErrorMistakeAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ErrorBasketBean errorBasketBean) {
        for (int i = 0; i < mMistakeList.size(); i++) {
            if (mMistakeList.get(i).getId().equals(errorBasketBean.getId())) {
                mMistakeList.get(i).setIsBasket("0");
            }
        }
        mErrorMistakeAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.getErrorBasket(UserUtil.getTeacherId());
            }
        }, 100);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }
}
