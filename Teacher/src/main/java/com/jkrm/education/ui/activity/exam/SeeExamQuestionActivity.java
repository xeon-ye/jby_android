package com.jkrm.education.ui.activity.exam;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.QuestionBasketOptionsAdapter;
import com.jkrm.education.adapter.exam.SeeExamQuestionAdapter;
import com.jkrm.education.bean.QuestionOptionBean;
import com.jkrm.education.bean.exam.ExamQuestionBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.presenters.SeeTargetQuestionPresent;
import com.jkrm.education.mvp.views.SeeTargetQuestionView;
import com.jkrm.education.ui.activity.ImgActivity;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.kexanie.library.MathView;

/**
 * Created by hzw on 2019/5/19.
 */

public class SeeExamQuestionActivity extends AwMvpActivity<SeeTargetQuestionPresent> implements SeeTargetQuestionView.View {

    @BindView(R.id.iv_img)
    ImageView mIvImg;
    @BindView(R.id.mv_questionTxt)
    MathView mMvQuestionTxt;
    @BindView(R.id.iv_open)
    ImageView mIvOpen;
    @BindView(R.id.mv_answerContent)
    MathView mMvAnswerContent;
    @BindView(R.id.mv_resolveContent)
    MathView mMvResolveContent;
    @BindView(R.id.iv_shrink)
    ImageView mIvShrink;
    @BindView(R.id.ll_bottomLayout)
    LinearLayout mLlBottomLayout;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.view_noData)
    RelativeLayout mViewNoData;
    @BindView(R.id.rcv_exam_data)
    RecyclerView mRcvExamData;

    private String imgUrl = "";
    private String questionId = "";
    private SeeExamQuestionAdapter mSeeExamQuestionAdapter;

    @Override
    protected SeeTargetQuestionPresent createPresenter() {
        return new SeeTargetQuestionPresent(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_homework_see_answer;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusTxtDark();
        setToolbarWithBackImg("查看答案", null);

        //默认打开
        showView(mLlBottomLayout, true);
        showView(mIvOpen, false);
        mSeeExamQuestionAdapter=new SeeExamQuestionAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity,mRcvExamData,mSeeExamQuestionAdapter,false);
    }

    @Override
    protected void initData() {
        super.initData();
//        imgUrl = TestDataUtil.imgUris[RandomValueUtil.getNum(0, TestDataUtil.imgUris.length - 1)];
//        AwImgUtil.setImg(mActivity, mIvImg, imgUrl);

        questionId = getIntent().getStringExtra(Extras.COMMON_PARAMS);
        if (AwDataUtil.isEmpty(questionId)) {
            showDialogToFinish("题目id获取失败");
        }
        mPresenter.getExamQuestion(questionId);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvImg.setOnClickListener(v -> toClass(ImgActivity.class, false, Extras.IMG_URL, imgUrl));
        mIvOpen.setOnClickListener(v -> {
            showView(mLlBottomLayout, true);
            showView(mIvOpen, false);
        });
        mIvShrink.setOnClickListener(v -> {
            showView(mLlBottomLayout, false);
            showView(mIvOpen, true);
        });
    }

    @Override
    public void getQuestionSuccess(QuestionResultBean resultBean) {
        if (resultBean != null) {
            showView(mViewNoData, false);
            if (!AwDataUtil.isEmpty(resultBean.getContent())) {
                mMvQuestionTxt.setText(resultBean.getContent());
            } else {
                mMvQuestionTxt.setText("");
            }

            if (!AwDataUtil.isEmpty(resultBean.getAnswer())) {
                mMvAnswerContent.setText(resultBean.getAnswer());
            } else {
                mMvAnswerContent.setText("");
            }

            if (!AwDataUtil.isEmpty(resultBean.getAnswerExplanation())) {
                mMvResolveContent.setText(resultBean.getAnswerExplanation());
            } else {
                mMvResolveContent.setText("");
            }

            if (resultBean.getType() != null && resultBean.getType().isChoiceQuestion() && null != resultBean.getOptions()) {
                mRcvData.setVisibility(View.VISIBLE);
                QuestionBasketOptionsAdapter questionBasketOptionsAdapter = new QuestionBasketOptionsAdapter();
                AwRecyclerViewUtil.setRecyclerViewLinearlayout((Activity) mActivity, mRcvData, questionBasketOptionsAdapter, false);
                setChoiceListData(resultBean, questionBasketOptionsAdapter, mRcvData);
            } else {
                mRcvData.setVisibility(View.GONE);
            }
        } else {
            showView(mViewNoData, true);
        }

    }

    @Override
    public void getQuestionFail(String msg) {
        showView(mViewNoData, true);
    }

    @Override
    public void getExamQuestionSuccess(List<ExamQuestionBean> data) {
        mSeeExamQuestionAdapter.addAllData(data);
    }

    @Override
    public void getExamQuestionFail(String msg) {
        showMsg(msg);
    }

    /**
     * 选择题选项列表数据填充.
     */
    private void setChoiceListData(QuestionResultBean bean, QuestionBasketOptionsAdapter adapter, RecyclerView rcvData) {
        Map<String, String> mOptionsMap = (Map<String, String>) bean.getOptions();
        List<QuestionOptionBean> mQuestionOptionBeanList = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, String> entry : mOptionsMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (AwDataUtil.isEmpty(entry.getValue())) {
                continue;
            }
            mQuestionOptionBeanList.add(new QuestionOptionBean(QuestionOptionBean.SERIAL_NUMS[index], entry.getValue(), false));
            index++;
        }
        if (AwDataUtil.isEmpty(mQuestionOptionBeanList)) {
            adapter.clearData();
            rcvData.removeAllViews();
            adapter.setEmptyView(AwRecyclerViewUtil.getEmptyDataView(mActivity, MyConstant.ViewConstant.VIEW_EMPTY_COMMON, -1));
            mRcvData.setVisibility(View.GONE);
        } else {
            adapter.addAllData(mQuestionOptionBeanList);
            adapter.loadMoreComplete();
            adapter.setEnableLoadMore(false);
            adapter.disableLoadMoreIfNotFullPage(rcvData);
        }
    }

    @Override
    protected void onDestroy() {
        RichText.recycle();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
