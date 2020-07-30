package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.RegexUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerRecordParentsAdapter;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.AnswerRecordPresent;
import com.jkrm.education.mvp.views.AnswerRecordView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.jkrm.education.widgets.AnswerRecordFragmentDialog;
import com.jkrm.education.widgets.ProgressBar;
import com.jkrm.education.widgets.ProgressBarCoverAdapter;
import com.jkrm.education.widgets.TextWithStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 答题记录
 */
public class AnswerRecordActivity extends AwMvpActivity<AnswerRecordPresent> implements AnswerRecordView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.tv_practNum)
    TextView mTvPractNum;
    @BindView(R.id.tv_practQuestNum)
    TextView mTvPractQuestNum;
    @BindView(R.id.tv_correctRate)
    TextView mTvCorrectRate;
    @BindView(R.id.tv_practTime)
    TextView mTvPractTime;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    private String mCourseId = "";
    List<ReinfoRoceCouseBean> mRoceCouseBeanList = new ArrayList<>();
    private AnswerRecordParentsAdapter mAnswerRecordParentsAdapter = new AnswerRecordParentsAdapter();

    @Override
    protected AnswerRecordPresent createPresenter() {
        return new AnswerRecordPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_record;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImgAndRightView("答题记录", "全部科目", new AwViewCustomToolbar.OnRightClickListener() {

            @Override
            public void onRightTextClick() {
                AnswerRecordFragmentDialog answerRecordFragmentDialog = new AnswerRecordFragmentDialog();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Extras.KEY_ANS_COURSE_LIST, (Serializable) mRoceCouseBeanList);
                answerRecordFragmentDialog.setArguments(bundle);
                answerRecordFragmentDialog.show(getSupportFragmentManager(), null);
                answerRecordFragmentDialog.setOnItemClickListener(new AnswerRecordFragmentDialog.onItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        mCourseId = mRoceCouseBeanList.get(pos).getCourseId();
                        mToolbarCustom.setRightText(mRoceCouseBeanList.get(pos).getCourseName());
                        for (int i = 0; i < mRoceCouseBeanList.size(); i++) {
                            if (mRoceCouseBeanList.get(i).getCourseName().equals(mRoceCouseBeanList.get(pos).getCourseName())) {
                                mRoceCouseBeanList.get(i).setSelect(true);
                            } else {
                                mRoceCouseBeanList.get(i).setSelect(false);
                            }
                        }
                        mAnswerRecordParentsAdapter.notifyDataSetChanged();
                        getDataList();
                    }
                });

            }
        });
        mToolbarCustom.setRTextColor(R.color.colorAccent);
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAnswerRecordParentsAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getReinforceCourseList(UserUtil.getAppUser().getStudId());
        getDataList();
    }

    private void getDataList() {
        mPresenter.getReinforceList(RequestUtil.getAnswerRecordBody(UserUtil.getAppUser().getStudId(), mCourseId));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void getReinforceCourseListSucces(List<ReinfoRoceCouseBean> list) {
        list.add(0, new ReinfoRoceCouseBean("全部科目", "", "全部科目", "", true));
        mRoceCouseBeanList = list;
    }

    @Override
    public void getReinforceCourseListFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void getReinforceListSuccess(ReinforBean data) {
        if (null == data || AwDataUtil.isEmpty(data.getList())) {
            mAnswerRecordParentsAdapter.clearData();
            mRcvData.removeAllViews();
            mAnswerRecordParentsAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            return;
        } else {
            mTvPractNum.setText(data.getStatis().getPractNum() + "\n\n练习次数");
            mTvPractQuestNum.setText(data.getStatis().getPractQuestNum() + "\n\n练习题数");
            mTvCorrectRate.setText(RegexUtil.calculatePercentage(data.getStatis().getCorrectRate()) + "\n\n正确率");
            mTvPractTime.setText(AwDateUtils.getDate(Integer.parseInt(data.getStatis().getPractTime())) + "\n\n练习时长");
        }

        mAnswerRecordParentsAdapter.addAllData(data.getList());
    }


    @Override
    public void getReinforceListFail(String msg) {
        showMsg(msg);
        mAnswerRecordParentsAdapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
    }


}
