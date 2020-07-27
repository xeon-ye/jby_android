package com.jkrm.education.ui.activity.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ChooseASchoolAdapter;
import com.jkrm.education.adapter.ChooseCourseAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ChoiceCoursePresent;
import com.jkrm.education.mvp.views.ChoiceCourseView;
import com.jkrm.education.util.DataUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChoiceCourseActivity extends AwMvpActivity<ChoiceCoursePresent> implements ChoiceCourseView.View {

    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.rcv_data_learing_section)
    RecyclerView mRcvDataLearingSection;
    @BindView(R.id.rcv_year)
    RecyclerView mRcvYear;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    private ChooseASchoolAdapter mLearingAdpter, mYearAdapter;
    private ChooseCourseAdapter mChoosePeriodAdapter, mChooseCouseAdapter;
    private List<TestMarkKindsBean> mLearingSectionList, mLearingyearList;
    private String mStrSection, mStrYear, mStrCourse, mStrPeriod;
    private PeriodCourseBean periodCourseBean;
    private String mRegisterID;
    private String mStrcouseId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_course;
    }


    @Override
    protected void initView() {
        super.initView();
        mLearingAdpter = new ChooseASchoolAdapter();
        mYearAdapter = new ChooseASchoolAdapter();
        mChoosePeriodAdapter = new ChooseCourseAdapter();
        mChooseCouseAdapter = new ChooseCourseAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvDataLearingSection, mChoosePeriodAdapter, 3);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvYear, mChooseCouseAdapter, 3);
    }

    @Override
    protected void initData() {
        super.initData();
        mRegisterID = getIntent().getStringExtra(Extras.KEY_REGISTER_ID);
        mLearingSectionList = DataUtil.createLearnSection();
        mLearingAdpter.addAllData(mLearingSectionList);
        //mLearingyearList=DataUtil.createJuniorHighSchool();
        // mYearAdapter.addAllData(mLearingyearList);
        mPresenter.getPeriodCourse();

    }

    @Override
    protected void initListener() {
        super.initListener();
        mLearingAdpter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<TestMarkKindsBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setChecked(true);
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                if (position == 0) {
                    mLearingyearList = DataUtil.createHighSchool();

                } else {
                    mLearingyearList = DataUtil.createJuniorHighSchool();
                }
                mYearAdapter.addAllData(mLearingyearList);
                mLearingAdpter.notifyDataSetChanged();
                mStrSection = data.get(position).getName();
                changeBtnState();
            }
        });
        mYearAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<TestMarkKindsBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setChecked(true);
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mYearAdapter.notifyDataSetChanged();
                mStrYear = data.get(position).getName();
                changeBtnState();
            }
        });
        mChooseCouseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<PeriodCourseBean.ValuesBean.FirstBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setChecked(true);
                        mStrCourse = data.get(i).getValueName();
                        mStrcouseId = data.get(i).getValueId();
                        mStrSection = data.get(position).getValueId();
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mChooseCouseAdapter.notifyDataSetChanged();
                changeBtnState();
            }
        });
        //学段
        mChoosePeriodAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<PeriodCourseBean.ValuesBean.FirstBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (i == position) {
                        data.get(i).setChecked(true);
                        String cascade = data.get(i).getCascade();
                        mStrPeriod = data.get(i).getValueId();
                        //选择学段 更新科目
                        mChooseCouseAdapter.addAllData(periodCourseBean.getValues().get(cascade));
                    } else {
                        data.get(i).setChecked(false);
                    }
                }
                mChoosePeriodAdapter.notifyDataSetChanged();
                mStrYear = data.get(position).getName();
                mStrCourse = "";
                changeBtnState();
            }
        });

    }

    @OnClick({R.id.tv_skip, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                toClass(ChoiceSchoolActivity.class, false);
                break;
            case R.id.btn_next:
                MyApp.mStrSection = mStrPeriod;
                MyApp.mStrYear = mStrYear;
                toClass(ChoiceSchoolActivity.class, false, Extras.KEY_REGISTER_ID, mRegisterID, Extras.KEY_COURSE_ID, mStrcouseId);
                break;
        }
    }

    private void changeBtnState() {
        mBtnNext.setEnabled(!AwDataUtil.isEmpty(mStrPeriod) && !AwDataUtil.isEmpty(mStrCourse));
    }

    @Override
    protected ChoiceCoursePresent createPresenter() {
        return new ChoiceCoursePresent(this);
    }

    @Override
    public void getPeriodCourseSuccess(PeriodCourseBean data) {
        periodCourseBean = data;
        List<PeriodCourseBean.ValuesBean.FirstBean> first = data.getValues().get("first");
        if (null != first && first.size() > 0) {
            first.get(0).setChecked(true);
            mStrPeriod = first.get(0).getValueId();
            //选择学段 更新科目
            mChooseCouseAdapter.addAllData(periodCourseBean.getValues().get(first.get(0).getCascade()   ));
        }
        mChoosePeriodAdapter.addAllData(first);
        mRcvDataLearingSection.getLayoutManager().findViewByPosition(0).performClick();

    }

    @Override
    public void getPeriodCourseFail(String msg) {

    }
}
