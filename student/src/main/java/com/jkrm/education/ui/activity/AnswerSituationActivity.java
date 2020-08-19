package com.jkrm.education.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.util.AwArraysUtil;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerSituationAdapter;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.SubQuestionsBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.rx.RxSituationType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.AnswerSituationPresent;
import com.jkrm.education.mvp.views.AnswerSituationView;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerSituationActivity extends AwMvpActivity<AnswerSituationPresent> implements AnswerSituationView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.btn_check_all)
    Button mBtnCheckAll;
    @BindView(R.id.tv_error)
    TextView mTvError;
    private AnswerSituationAdapter mAnswerSituationAdapter;
    private AnswerSheetBean mAnswerSheetBeansnswerSheetBeans;
    private String mStrTemplateId;
    List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> mList = new ArrayList<>();
    List<BatchBean> mBatBeanList = new ArrayList<>();
    AnswerSheetBean mSaveSheetBeans = new AnswerSheetBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_situation;
    }

    @Override
    protected void initView() {
        super.initView();
        setStatusBlue();

        mAnswerSheetBeansnswerSheetBeans = (AnswerSheetBean) getIntent().getSerializableExtra(Extras.KEY_ANSWERSHEET);

        Gson gson = new Gson();
        mSaveSheetBeans = gson.fromJson(gson.toJson(mAnswerSheetBeansnswerSheetBeans), AnswerSheetBean.class);
        mStrTemplateId = getIntent().getStringExtra(Extras.KEY_TEMPLATE_ID);
        setToolbar(mAnswerSheetBeansnswerSheetBeans.getTemplateName(), new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
    }

    @Override
    protected AnswerSituationPresent createPresenter() {
        return new AnswerSituationPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mAnswerSituationAdapter = new AnswerSituationAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAnswerSituationAdapter, false);
        //遍历小题
        List<AnswerSheetBean.QuestionsBean> questions = mAnswerSheetBeansnswerSheetBeans.getQuestions();
        for (int i = 0; i < questions.size(); i++) {
            List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> subQuestions = questions.get(i).getSubQuestions();
            for (int j = 0; j < subQuestions.size(); j++) {
                //有组题
                if (!AwDataUtil.isEmpty(subQuestions.get(j).getSubQuestions())) {
                    List<SubQuestionsBean> insideSubquestions = subQuestions.get(j).getSubQuestions();
                    for (int k = 0; k < insideSubquestions.size(); k++) {
                        AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestionsBean = new AnswerSheetBean.QuestionsBean.SubQuestionsBean();
                        subQuestionsBean.setId(insideSubquestions.get(k).getId());
                        subQuestionsBean.setIsOption(insideSubquestions.get(k).getIsOption());
                        subQuestionsBean.setAnswer(insideSubquestions.get(k).getAnswer());
                        subQuestionsBean.setOptions(insideSubquestions.get(k).getOptions());
                        subQuestionsBean.setQuestionNum(insideSubquestions.get(k).getQuestionNum());
                        AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer studentAnswer = new AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer();
                        studentAnswer.setAnswer(insideSubquestions.get(k).getStudentAnswer().getAnswer());
                        studentAnswer.setRawScan(insideSubquestions.get(k).getStudentAnswer().getRawScan());
                        subQuestionsBean.setStudentAnswer(studentAnswer);
                        subQuestions.add(subQuestionsBean);
                    }
                }
            }
        }
        //排除组题
        for (int i = 0; i < questions.size(); i++) {
            List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> subQuestions = questions.get(i).getSubQuestions();
            ArrayList<AnswerSheetBean.QuestionsBean.SubQuestionsBean> customDataList = new ArrayList<>();
            for (int j = 0; j < subQuestions.size(); j++) {
                if (!AwDataUtil.isEmpty(subQuestions.get(j).getId())) {
                    customDataList.add(subQuestions.get(j));
                }
            }
            questions.get(i).setSubQuestions(customDataList);
        }
        //重新排序 待修改
        for (AnswerSheetBean.QuestionsBean question : questions) {
            Collections.sort(question.getSubQuestions(), new Comparator<AnswerSheetBean.QuestionsBean.SubQuestionsBean>() {
                @Override
                public int compare(AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestionsBean, AnswerSheetBean.QuestionsBean.SubQuestionsBean t1) {
                    return Integer.parseInt(subQuestionsBean.getQuestionNum()) - Integer.parseInt(t1.getQuestionNum());
                }
            });
        }
        mAnswerSituationAdapter.addAllData(mAnswerSheetBeansnswerSheetBeans.getQuestions());
        EventBus.getDefault().post(new RxSituationType());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAnswerSituationAdapter.setOnAdapterItemClickListener(new AnswerSituationAdapter.OnAdapterItemClickListener() {

            @Override
            public void explainClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data) {
                mPresenter.explainQuestion("1", mStrTemplateId, UserUtil.getAppUser().getStudId(), data.getId());
                data.getStudentAnswer().setExplain("1");
                mAnswerSituationAdapter.notifyDataSetChanged();
            }

            @Override
            public void unExplainClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data) {
                mPresenter.explainQuestion("0", mStrTemplateId, UserUtil.getAppUser().getStudId(), data.getId());
                data.getStudentAnswer().setExplain("0");
                mAnswerSituationAdapter.notifyDataSetChanged();


            }

            @Override
            public void joinErrorClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data) {
                mPresenter.addMistake(mStrTemplateId, UserUtil.getAppUser().getStudId(), data.getId());
                data.getStudentAnswer().setMistake("1");
                mAnswerSituationAdapter.notifyDataSetChanged();

            }

            @Override
            public void deleteErrorClick(AnswerSheetBean.QuestionsBean.SubQuestionsBean data) {
                mPresenter.deleteMistake(data.getId(), UserUtil.getAppUser().getStudId());
                data.getStudentAnswer().setMistake("0");
                mAnswerSituationAdapter.notifyDataSetChanged();
            }


        });
    }

    @OnClick({R.id.tv_error, R.id.btn_check_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_error:
                toClass(ErrorQuestionNewActivity.class, false);
                break;
            case R.id.btn_check_all:
                toAnswerAnalysis();
                break;
        }
    }

    @Override
    public void explainQuestionSuccess(WatchLogBean watchLogBean) {
        showMsg("操作成功");
    }

    @Override
    public void explainQuestionFail(String msg) {
        showMsg(msg);
    }

    @Override
    public void addMistakeSuccess(String data) {
        showMsg("加入错题本成功");
    }

    @Override
    public void addMistakeFail(String msg) {
    }

    @Override
    public void deleteMistakeSuccess(String data) {
        showMsg("移出错题本成功");
    }

    @Override
    public void deleteMistakeFail(String msg) {
    }


    /**
     * 查看解析
     */
    private void toAnswerAnalysis() {
        mList.clear();
        mBatBeanList.clear();
        Gson answerSheetGson = new Gson();
        AnswerSheetBean mSplicSheetBeans = answerSheetGson.fromJson(answerSheetGson.toJson(mSaveSheetBeans), AnswerSheetBean.class);
        List<AnswerSheetBean.QuestionsBean> questions = mSplicSheetBeans.getQuestions();
        //已经拆分组题数据
        List<AnswerSheetBean.QuestionsBean> question2 = mAnswerSheetBeansnswerSheetBeans.getQuestions();
        if (AwDataUtil.isEmpty(questions)) {
            return;
        }
        for (AnswerSheetBean.QuestionsBean questionsBean : question2) {
            //已经作答
            for (AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestion : questionsBean.getSubQuestions()) {
                //将已作答答案赋值到 未拆分集合中
                for (AnswerSheetBean.QuestionsBean question : questions) {
                    for (AnswerSheetBean.QuestionsBean.SubQuestionsBean questionSubQuestion : question.getSubQuestions()) {
                        if (subQuestion.getId().equals(questionSubQuestion.getId())) {
                            AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer studentAnswer = new AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer();
                            questionSubQuestion.setStuAnswer(subQuestion.getStuAnswer());
                            if (!"2".equals(subQuestion.getIsOption())) {
                                questionSubQuestion.setStuAnswer(AwArraysUtil.listToString(subQuestion.getImageList()));
                                studentAnswer.setAnswer(AwArraysUtil.listToString(subQuestion.getImageList()));
                            } else {
                                questionSubQuestion.setStuAnswer(subQuestion.getStuAnswer());
                                studentAnswer.setAnswer(subQuestion.getStuAnswer());
                            }
                            questionSubQuestion.setStudentAnswer(studentAnswer);
                        }
                        List<com.jkrm.education.bean.SubQuestionsBean> subQuestionsBeans = questionSubQuestion.getSubQuestions();
                        if (null != subQuestionsBeans) {
                            for (SubQuestionsBean subQuestionsBean : subQuestionsBeans) {
                                if (subQuestion.getId().equals(subQuestionsBean.getId())) {
                                    SubQuestionsBean.StudentAnswer studentAnswer = new SubQuestionsBean.StudentAnswer();
                                    studentAnswer.setAnswer(subQuestion.getStuAnswer());
                                    subQuestionsBean.setStudentAnswer(studentAnswer);
                                }
                            }
                        }

                    }
                }
            }
        }
        for (int i = 0; i < questions.size(); i++) {
            List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> subQuestions = questions.get(i).getSubQuestions();
            mList.addAll(subQuestions);
        }
        //gson 转化
        for (AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestionsBean : mList) {
            Gson gson = new Gson();
            BatchBean batchBean = gson.fromJson(gson.toJson(subQuestionsBean), BatchBean.class);
            if (!AwDataUtil.isEmpty(subQuestionsBean.getStudentAnswer()) && !AwDataUtil.isEmpty(subQuestionsBean.getStudentAnswer().getAnswer())) {
                batchBean.setStudAnswer(subQuestionsBean.getStudentAnswer().getAnswer());
            }
            if (!AwDataUtil.isEmpty(subQuestionsBean.getImageList())) {
                batchBean.setStudAnswer(AwArraysUtil.splitList(subQuestionsBean.getImageList()));
            }
            if (!AwDataUtil.isEmpty(batchBean.getSubQuestions())) {
                for (BatchBean.SubQuestionsBean subQuestion : batchBean.getSubQuestions()) {
                    for (AnswerSheetBean.QuestionsBean question : question2) {
                        for (AnswerSheetBean.QuestionsBean.SubQuestionsBean questionSubQuestion : question.getSubQuestions()) {
                            if (subQuestion.getId().equals(questionSubQuestion.getId())) {
                                subQuestion.setStudAnswer(questionSubQuestion.getStudentAnswer().getAnswer());
                            }
                        }
                    }
                }
            }

            batchBean.setAnswerSituation(true);
            mBatBeanList.add(batchBean);
        }
        toClass(AnswerAnalysisActivity.class, false, Extras.EXERCISEREPORT, (Serializable) mBatBeanList, Extras.KEY_ANSWER_SITUATION, true,Extras.OUTSIDE_POS,0,Extras.INGSIDE_POS,0);
    }
}
