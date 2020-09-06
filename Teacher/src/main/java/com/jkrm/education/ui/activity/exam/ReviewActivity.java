package com.jkrm.education.ui.activity.exam;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.data.BarEntry;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.project.student.bean.MarkBean;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwPopupwindowUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwCommonTopListPopupWithIconWindow;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.exam.ReviewAdapter;
import com.jkrm.education.bean.ExamReviewBean;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.ScoreBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.ReviewPresent;
import com.jkrm.education.mvp.views.ReviewView;
import com.jkrm.education.util.UserUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 回评检测
 */
public class ReviewActivity extends AwMvpActivity<ReviewPresent> implements ReviewView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_score_sort)
    TextView mTvScoreSort;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    private String EXAM_ID, PAPER_ID, READ_WAY, QUESTION_ID;
    private ReViewTaskBean.Bean mBean;
    private List<MarkBean> mScoreSortList = new ArrayList<>();
    private List<MarkBean> mSortList = new ArrayList<>();
    private ReviewAdapter mReviewAdapter;
    private List<ExamReviewBean> mList;
    private String ORDER_TYPE = "1";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_review;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();
        setToolbarWithBackImg("回评检查", new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
        EXAM_ID = (String) getIntent().getExtras().get(Extras.EXAM_ID);
        PAPER_ID = (String) getIntent().getExtras().get(Extras.PAPER_ID);
        READ_WAY = (String) getIntent().getExtras().get(Extras.READ_WAY);
        QUESTION_ID = (String) getIntent().getExtras().get(Extras.QUESTION_ID);
        mBean = (ReViewTaskBean.Bean) getIntent().getSerializableExtra(Extras.REVIEW_TASK_BEAN);
        mReviewAdapter = new ReviewAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mReviewAdapter, false);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getExamReviewScore(UserUtil.getAppUser().getTeacherId(), EXAM_ID, PAPER_ID, READ_WAY, QUESTION_ID);
        getData();
        mTvTitle.setText(mBean.getQuestionType() + ":" + mBean.getQuestionNum() + "题(" + mBean.getMaxScore() + ")分");
        if ("1".equals(mBean.getReadWay())) {
            mTvType.setText("单评题号");
        } else if ("2".equals(mBean.getReadWay())) {
            mTvType.setText("双评题号");
        } else if ("3".equals(mBean.getReadWay())) {
            mTvType.setText("终评题号");
        }
        mScoreSortList.add(new MarkBean(true, "全部"));
        mSortList.add(new MarkBean(false, "时间降序"));
        mSortList.add(new MarkBean(false, "时间升序"));
        mSortList.add(new MarkBean(false, "得分降序"));
        mSortList.add(new MarkBean(false, "得分升序"));
    }

    private void getData() {
        mPresenter.getExamReviewList(UserUtil.getAppUser().getTeacherId(), EXAM_ID, PAPER_ID, READ_WAY, QUESTION_ID, ORDER_TYPE);
    }

    @Override
    protected ReviewPresent createPresenter() {
        return new ReviewPresent(this);
    }


    @Override
    public void getExamReviewScoreSuccess(List<ScoreBean> data) {

        for (ScoreBean datum : data) {
            if (null != datum && null != datum.getScore()) {
                mScoreSortList.add(new MarkBean(false, datum.getScore()));

            }
        }
    }

    @Override
    public void getExamReviewScoreFail(String msg) {
        showMsg(msg);

    }

    @Override
    public void getExamReviewListSuccess(List<ExamReviewBean> data) {
        mList = data;
        mReviewAdapter.addAllData(mList);
    }


    @Override
    public void getExamReviewListFail(String msg) {
        showMsg(msg);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mReviewAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img:
                        toClass(CorrectingActivity.class, false, Extras.REVIEW_TASK_BEAN, mBean, Extras.KEY_IS_RE_MARK, true, Extras.KEY_MACK_POS, position);
                        break;
                }
            }
        });
    }

    @OnClick({R.id.tv_score_sort, R.id.tv_sort})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_score_sort:
                AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlphaWithIcon(mActivity, mScoreSortList, mTvScoreSort, new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dismissDialog();
                    }
                }, new AwCommonTopListPopupWithIconWindow.OnItemClickListener() {
                    @Override
                    public void onClick(Object bean) {
                        mTvScoreSort.setText(((MarkBean) bean).getTitle());
                        ArrayList<ExamReviewBean> fitter = new ArrayList<>();
                        if ("全部".equals(((MarkBean) bean).getTitle())) {
                            for (ExamReviewBean examReviewBean : mList) {
                                fitter.add(examReviewBean);
                            }
                        } else {
                            for (ExamReviewBean examReviewBean : mList) {
                                if (examReviewBean.getScore().equals(((MarkBean) bean).getTitle())) {
                                    fitter.add(examReviewBean);
                                }
                            }
                        }


                        mReviewAdapter.addAllData(fitter);
                    }
                });
                break;
            case R.id.tv_sort:
                AwPopupwindowUtil.showCommonTopListPopupWindowWithParentAndDismissNoAlphaWithIcon(mActivity, mSortList, mTvSort, new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        dismissDialog();
                    }
                }, new AwCommonTopListPopupWithIconWindow.OnItemClickListener() {
                    @Override
                    public void onClick(Object bean) {
                        mTvSort.setText(((MarkBean) bean).getTitle());
                        //排序常用分数
                        if ("时间降序".equals(((MarkBean) bean).getTitle())) {
                            ORDER_TYPE = "1";

                        } else if ("时间升序".equals(((MarkBean) bean).getTitle())) {
                            ORDER_TYPE = "2";
                        } else if ("得分降序".equals(((MarkBean) bean).getTitle())) {
                            ORDER_TYPE = "3";
                        } else if ("得分升序".equals(((MarkBean) bean).getTitle())) {
                            ORDER_TYPE = "4";
                        }
                        getData();

                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
