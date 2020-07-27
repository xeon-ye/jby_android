package com.jkrm.education.ui.activity.login;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.ChoseSchoolDialogFragment;
import com.jkrm.education.R;
import com.jkrm.education.adapter.ChooseASchoolAdapter;
import com.jkrm.education.adapter.ChoosePeriodAdapter;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.test.TestMarkKindsBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ChoiceLearningSectionPresent;
import com.jkrm.education.mvp.presenters.ChoseSchoolPresent;
import com.jkrm.education.mvp.views.ChoiceLearningSectionView;
import com.jkrm.education.mvp.views.ChoiceSchoolView;
import com.jkrm.education.util.DataUtil;
import com.umeng.commonsdk.debug.I;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择就读年级
 */
public class ChoiceLearningSectionActivity extends AwMvpActivity<ChoiceLearningSectionPresent> implements ChoiceLearningSectionView.View {

    @BindView(R.id.tv_skip)
    TextView mTvSkip;
    @BindView(R.id.rcv_data_learing_section)
    RecyclerView mRcvDataLearingSection;
    @BindView(R.id.rcv_year)
    RecyclerView mRcvYear;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    private ChooseASchoolAdapter mLearingAdpter,mYearAdapter;
    private List<TestMarkKindsBean> mLearingSectionList,mLearingyearList;
    private String mStrSection,mStrYear;
    private ChoosePeriodAdapter mChoosePeriodAdapter;
    private String mRegisterID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_learning_section;
    }

    @Override
    protected void initView() {
        super.initView();
        mLearingAdpter=new ChooseASchoolAdapter();
        mYearAdapter=new ChooseASchoolAdapter();
        mChoosePeriodAdapter=new ChoosePeriodAdapter();
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity,mRcvDataLearingSection,mChoosePeriodAdapter,3);
        AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity,mRcvYear,mYearAdapter,3);
    }

    @Override
    protected void initData() {
        super.initData();
        mLearingSectionList= DataUtil.createLearnSection();
        mLearingAdpter.addAllData(mLearingSectionList);
        mLearingyearList=DataUtil.createLearnYear();
        mYearAdapter.addAllData(mLearingyearList);
        mRegisterID = getIntent().getStringExtra(Extras.KEY_REGISTER_ID);
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
                    if(i==position){
                        data.get(i).setChecked(true);
                    }else{
                        data.get(i).setChecked(false);
                    }
                }
                mLearingAdpter.notifyDataSetChanged();
                mStrSection=data.get(position).getName();
                changeBtnState();
            }
        });

        mYearAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<TestMarkKindsBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if(i==position){
                        data.get(i).setChecked(true);
                    }else{
                        data.get(i).setChecked(false);
                    }
                }
                mYearAdapter.notifyDataSetChanged();
                mStrYear=data.get(position).getName();
                changeBtnState();
            }
        });
        mChoosePeriodAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<PeriodCourseBean.ValuesBean.FirstBean> data = adapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if(i==position){
                        data.get(i).setChecked(true);
                    }else{
                        data.get(i).setChecked(false);
                    }
                }
                mChoosePeriodAdapter.notifyDataSetChanged();
                mStrSection=data.get(position).getValueId();
                changeBtnState();
            }
        });
    }

    @OnClick({R.id.tv_skip, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                toClass(ChoiceSchoolActivity.class,false);
                break;
            case R.id.btn_next:
                MyApp.mStrSection=mStrSection;
                MyApp.mStrYear=mStrYear;
                toClass(ChoiceSchoolActivity.class,false,Extras.KEY_REGISTER_ID,mRegisterID);
                break;
        }
    }

    private void changeBtnState(){
        mBtnNext.setEnabled(!AwDataUtil.isEmpty(mStrSection)&&!AwDataUtil.isEmpty(mStrYear));
    }

    @Override
    protected ChoiceLearningSectionPresent createPresenter() {
        return new ChoiceLearningSectionPresent(this);
    }

    @Override
    public void getPeriodCourseSuccess(PeriodCourseBean data) {
        List<PeriodCourseBean.ValuesBean.FirstBean> first = data.getValues().getFirst();
        if(null!=first&&first.size()>0){
            first.get(0).setChecked(true);
            mStrSection=first.get(0).getValueId();
        }
        mChoosePeriodAdapter.addAllData(first);
    }

    @Override
    public void getPeriodCourseFail(String msg) {
        showMsg(msg);
    }
}
