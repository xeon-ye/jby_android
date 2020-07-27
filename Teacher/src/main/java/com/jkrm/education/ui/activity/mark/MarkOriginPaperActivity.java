package com.jkrm.education.ui.activity.mark;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwImgUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.mark.MarkDetailWithStudentSwitchAdapter;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.NormalBean;
import com.jkrm.education.bean.result.OriginalPagerResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.rx.RxUnConnectType;
import com.jkrm.education.bean.test.TestMarkDetailWithStudentBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.SeeOriginalPagerPresent;
import com.jkrm.education.mvp.views.SeeOriginalPagerView;
import com.jkrm.education.ui.activity.homework.HomeworkDetailActivity;
import com.jkrm.education.util.CustomFontStyleUtil;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.TestDataUtil;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzw on 2019/5/22.
 */

public class MarkOriginPaperActivity extends AwMvpActivity<SeeOriginalPagerPresent> implements SeeOriginalPagerView.View{

    @BindView(R.id.tv_classes)
    TextView mTvClasses;
    @BindView(R.id.tv_currentStudent)
    TextView mTvCurrentStudent;
    @BindView(R.id.drawerLayout_studentSwitch)
    DrawerLayout mDrawerLayoutStudentSwitch;
    @BindView(R.id.rcv_dataStudentSwitch)
    RecyclerView mRcvDataStudentSwitch;
    @BindView(R.id.splash_viewPager)
    ViewPager mSplashViewPager;
    @BindView(R.id.splash_indicator)
    FixedIndicatorView mSplashIndicator;

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private List<String> mOriginalPagerResultBeanList = new ArrayList<>();

    private MarkDetailWithStudentSwitchAdapter mStudentSwitchAdapter;
    private RowsHomeworkBean mRowsHomeworkBean;
    private String homeworkId = "";
    private String classId = "";
    private AnswerSheetProgressResultBean mCurrentStudentBean;
    private List<AnswerSheetProgressResultBean> mStudentList = new ArrayList<>();

    @Override
    protected SeeOriginalPagerPresent createPresenter() {
        return new SeeOriginalPagerPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mark_origin_pager;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImgAndRightView("查看原卷", "", () -> {
            if(AwDataUtil.isEmpty(mStudentList)) {
                showDialog("获取学生列表失败");
                return;
            }
            if (mDrawerLayoutStudentSwitch.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayoutStudentSwitch.closeDrawers();
            } else {
                mDrawerLayoutStudentSwitch.openDrawer(Gravity.RIGHT);
            }
        });
        mToolbar.setRTextColor(R.color.blue);
        mToolbar.setRightImgWithTxt(R.mipmap.icon_sanjiao);

        //禁止滑动
        mDrawerLayoutStudentSwitch.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mTvClasses.setTypeface(CustomFontStyleUtil.getNewRomenType());
    }

    @Override
    protected void initData() {
        super.initData();
        mRowsHomeworkBean = (RowsHomeworkBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ROWS_HOMEWORK);
        mCurrentStudentBean = (AnswerSheetProgressResultBean) getIntent().getSerializableExtra(Extras.KEY_BEAN_ANSWER_SHEET_PROGRESS);
        if(mRowsHomeworkBean != null) {
           //setText(mTvClasses, mRowsHomeworkBean.getClasses().getName());
            homeworkId = mRowsHomeworkBean.getId();
            classId = mRowsHomeworkBean.getClasses().getId();
        }
        if(AwDataUtil.isEmpty(homeworkId) || mCurrentStudentBean == null) {
            showDialogToFinish("获取数据失败, 无法查看原卷");
            return;
        }
        try {
            setText(mTvClasses, "扫描日期："+AwDateUtils.dealDate(mCurrentStudentBean.getCreateTime()) +" 识别学号:"+mCurrentStudentBean.getStudCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mToolbar.setRightText(mCurrentStudentBean.getStudentName());
        mPresenter.answerSheetProgress(homeworkId, classId);
        mPresenter.getStudentOriginalQuestionAnswer(homeworkId, mCurrentStudentBean.getStudentId());

        inflate = LayoutInflater.from(mActivity);
        indicatorViewPager = new IndicatorViewPager(mSplashIndicator, mSplashViewPager);
        indicatorViewPager.setAdapter(adapter);

        mStudentSwitchAdapter = new MarkDetailWithStudentSwitchAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvDataStudentSwitch, mStudentSwitchAdapter, false);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTvCurrentStudent.setOnClickListener(v -> {
            showDialogCustomLeftAndRight("取消挂接 \n\t若此提卡非本班级学生可取消挂接。取消后,此学号将变为未作答。", "取消", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.unConnect(RequestUtil.unConnectBody(mCurrentStudentBean.getSheetExtld()));
                    dismissDialog();
                }
            });

        });

        mStudentSwitchAdapter.setOnItemClickListener((adapter, view, position) -> {
            mDrawerLayoutStudentSwitch.closeDrawers();
            mCurrentStudentBean = (AnswerSheetProgressResultBean) adapter.getItem(position);
           // setText(mTvCurrentStudent, mCurrentStudentBean.getStudentName());
            mToolbar.setRightText(mCurrentStudentBean.getStudentName());
            mPresenter.getStudentOriginalQuestionAnswer(homeworkId, mCurrentStudentBean.getStudentId());
        });
    }

    private IndicatorViewPager.IndicatorPagerAdapter adapter = new IndicatorViewPager.IndicatorViewPagerAdapter() {

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.inflate_layout_viewpager_point_splash, container, false);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.inflate_layout_viewpager_see_original_pager, container, false);
            }
            ImageView iv_res = (ImageView) convertView.findViewById(R.id.iv_res);
            AwImgUtil.setImg(mActivity, iv_res, mOriginalPagerResultBeanList.get(position));
            //            iv_res.setBackground(getResources().getDrawable(images[position]));
            return convertView;
        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public int getCount() {
            return mOriginalPagerResultBeanList.size();
        }
    };

    @Override
    public void answerSheetProgressSuccess(List<AnswerSheetProgressResultBean> list) {
        mStudentList = list;
        if(AwDataUtil.isEmpty(list)) {
            mStudentSwitchAdapter.clearData();
            mRcvDataStudentSwitch.removeAllViews();
            mStudentSwitchAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
        } else {
            mStudentSwitchAdapter.addAllData(list);
            mStudentSwitchAdapter.loadMoreComplete();
            mStudentSwitchAdapter.setEnableLoadMore(false);
            mStudentSwitchAdapter.disableLoadMoreIfNotFullPage(mRcvDataStudentSwitch);
        }
    }

    @Override
    public void getStudentOriginalQuestionAnswerSuccess(OriginalPagerResultBean bean) {
        if(bean != null) {
            mOriginalPagerResultBeanList = bean.getRawScan();
            AwLog.d("getStudentOriginalQuestionAnswerSuccess list size: " + mOriginalPagerResultBeanList.size());
            indicatorViewPager.notifyDataSetChanged();
            mSplashViewPager.setCurrentItem(0);
        } else {
            showDialog("获取原卷失败");
        }
    }

    @Override
    public void unConnectSuccess(NormalBean normalBean) {
        NormalBean normalBean1=normalBean;
        showMsg("取消挂接成功");
        EventBus.getDefault().post(new RxUnConnectType());
        finish();
    }

    @Override
    public void unConnectFail(String msg) {
        showMsg("取消挂接失败");

    }
}
