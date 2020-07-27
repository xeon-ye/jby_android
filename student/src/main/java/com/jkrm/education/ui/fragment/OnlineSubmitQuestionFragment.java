package com.jkrm.education.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hzw.baselib.base.AwMvpFragment;
import com.hzw.baselib.base.AwMvpLazyFragment;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwArraysUtil;
import com.hzw.baselib.util.AwDateUtils;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.jkrm.education.R;
import com.jkrm.education.adapter.OnlineSubmitNoGroupQuestionAdapter;
import com.jkrm.education.adapter.OnlineSubmitQuestionAdapter;
import com.jkrm.education.bean.QuestionBean;
import com.jkrm.education.bean.SaveReinforceBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.rx.RxOnlineJumpType;
import com.jkrm.education.bean.rx.RxQuestionBean;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineSubmitQuestionPresent;
import com.jkrm.education.mvp.presenters.ScoreMainPresent;
import com.jkrm.education.mvp.views.OnlineSubmitQuestionFragmentView;
import com.jkrm.education.mvp.views.ScoreMainFragmentView;
import com.jkrm.education.ui.activity.ExerciseReportActivity;
import com.jkrm.education.ui.activity.OnlineAnswerActivity;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @Description: 提交页面
 * @Author: xiangqian
 * @CreateDate: 2020/5/9 9:43
 */

public class OnlineSubmitQuestionFragment extends AwMvpFragment<OnlineSubmitQuestionPresent> implements OnlineSubmitQuestionFragmentView.View {
    List<SimilarResultBean> mList, mSimilarResultBeans = new ArrayList<>();
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    OnlineSubmitQuestionAdapter mOnlineSubmitQuestionAdapter;
    OnlineSubmitNoGroupQuestionAdapter mOnlineSubmitNoGroupQuestionAdapter;
    private boolean isGruopQuestion = false;//是否包含组题
    private List<QuestionBean> mQuestionBeanList = new ArrayList<>();
    private boolean isExit;
    private String mCourseId = "";


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_onlinesubmit_question_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        mList = (List<SimilarResultBean>) getArguments().getSerializable(Extras.KEY_BEAN_QUESTION_SIMILAR_LIST);
        //最后一条为 填充  展位  数据  需要 去除
        for (int i = 0; i < mList.size(); i++) {
            if (i != mList.size() - 1) {
                mSimilarResultBeans.add(mList.get(i));
                if (mList.get(i).isGroupQuestion()) {
                    isGruopQuestion = true;

                }
            }
        }
        //组合答案
        for (SimilarResultBean similarResultBean : mSimilarResultBeans) {
            if (null != similarResultBean.getSubQuestions()) {
                for (SimilarResultBean.SubQuestionsBean subQuestion : similarResultBean.getSubQuestions()) {
                    mQuestionBeanList.add(new QuestionBean(subQuestion.getId(), subQuestion.getParentId()));
                }
            } else {
                mQuestionBeanList.add(new QuestionBean(similarResultBean.getId(), similarResultBean.getParentId()));
            }

        }
        mOnlineSubmitQuestionAdapter = new OnlineSubmitQuestionAdapter(getActivity());
        mOnlineSubmitQuestionAdapter.addAllData(mSimilarResultBeans);
        //无组题
        mOnlineSubmitNoGroupQuestionAdapter = new OnlineSubmitNoGroupQuestionAdapter(getActivity());
        mOnlineSubmitNoGroupQuestionAdapter.addAllData(mSimilarResultBeans);
        //有组题
        if (isGruopQuestion) {
            AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mOnlineSubmitQuestionAdapter, false);
        } else {
            AwRecyclerViewUtil.setRecyclerViewGridlayout(mActivity, mRcvData, mOnlineSubmitNoGroupQuestionAdapter, 5);
        }


    }

    @Override
    protected void initListener() {
        super.initListener();
        mOnlineSubmitQuestionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转至当前题目
                EventBus.getDefault().post(new RxOnlineJumpType(position, 0));
            }
        });
        mOnlineSubmitNoGroupQuestionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转至当前题目
                EventBus.getDefault().post(new RxOnlineJumpType(position, 0));
            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
       /* for (SimilarResultBean similarResultBean : mSimilarResultBeans) {
            if(!similarResultBean.isAnswer()&&!similarResultBean.isGroupQuestion()&&!TextUtils.isEmpty(similarResultBean.getAnswer())){
                QuestionBean questionBean = new QuestionBean(similarResultBean.getId(), "");
                questionBean.setAnswer("");
                mQuestionBeanList.add(questionBean);
            }
            if(null!=similarResultBean.getSubQuestions()){
                for (SimilarResultBean.SubQuestionsBean subQuestion : similarResultBean.getSubQuestions()) {
                    if(!subQuestion.isAnswer()){
                        QuestionBean questionBean = new QuestionBean(subQuestion.getId(), subQuestion.getParentId());
                        questionBean.setAnswer("");
                        mQuestionBeanList.add(questionBean);
                    }

                }
            }
        }*/
        AwLog.e(TAG, mQuestionBeanList.toString());
        OnlineAnswerActivity activity = (OnlineAnswerActivity) getActivity();
        mCourseId = activity.getCourseId();
        long l = activity.stopTimer()/1000;
        mPresenter.saveReinforce(RequestUtil.getSaveReinforceBody(UserUtil.getAppUser().getStudId(), mCourseId, activity.getmStrType(), mQuestionBeanList, l+""));
    }


    @Override
    protected OnlineSubmitQuestionPresent createPresenter() {
        return new OnlineSubmitQuestionPresent(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshByQuestionID(RxQuestionBean bean) {
        if (null != bean) {
            //是否作答
            for (SimilarResultBean similarResultBean : mSimilarResultBeans) {
                if (similarResultBean.getId().equals(bean.getId())) {
                    if (null != bean.getAnswer()) {
                        similarResultBean.setAnswer(bean.isAnswer());
                    }
                }
                if (null != similarResultBean.getSubQuestions()) {
                    for (SimilarResultBean.SubQuestionsBean subQuestion : similarResultBean.getSubQuestions()) {
                        if (subQuestion.getId().equals(bean.getId())) {
                            if (null != bean.getAnswer()) {
                                subQuestion.setAnswer(bean.isAnswer());
                            }
                        }
                    }
                }

            }
            //赋值答案
            for (QuestionBean questionBean : mQuestionBeanList) {
                if (questionBean.getQuestionId().equals(bean.getId())) {
                    if (null != bean.getAnswer()) {
                        questionBean.setAnswer(bean.getAnswer());
                    }
                }
            }
            /*//删除图片
            if(bean.isPhotos()){
                if(bean.isDelete()){
                    for (QuestionBean questionBean : mQuestionBeanList) {
                        if(bean.getAnswer().equals(questionBean.getAnswer())){
                            mQuestionBeanList.remove(questionBean);
                        }
                    }
                }else{

                      //新增图片 可重复
                    QuestionBean questionBean = new QuestionBean(bean.getId(), bean.getPid());
                    questionBean.setAnswer(bean.getAnswer());
                    mQuestionBeanList.add(questionBean);
                }

            }else{
                //赋值答案
                if(bean.isAnswer()){
                    QuestionBean questionBean = new QuestionBean(bean.getId(), bean.getPid());
                    questionBean.setAnswer(bean.getAnswer());
                    mQuestionBeanList.add(questionBean);
                }
            }*/

            /*//赋值答案
            for (QuestionBean questionBean : mQuestionBeanList) {
                if(questionBean.getQuestionId().equals(bean.getId())){
                    if(null!=bean.getAnswer()){
                        //多图模式
                        if(bean.isPhotos()){
                            List<String> strings = AwArraysUtil.stringToList(bean.getAnswer());
                            for (String string : strings) {
                                if(!TextUtils.isEmpty(questionBean.getAnswer())){
                                    QuestionBean questionBean1 = new QuestionBean(questionBean.getQuestionId(), questionBean.getParentId());
                                    questionBean1.setAnswer(string);
                                    mQuestionBeanList.add(questionBean1);
                                }else{
                                    questionBean.setAnswer(string);
                                }
                            }
                            showDialog(strings.toString());
                            //答案不为空

                        }else {
                            questionBean.setAnswer(bean.getAnswer());

                        }
                    }
                }
            }*/
            mOnlineSubmitQuestionAdapter.notifyDataSetChanged();
            mOnlineSubmitNoGroupQuestionAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void saveReinforceSuccess(SaveReinforceBean model) {
        toClass(ExerciseReportActivity.class, true, Extras.BATCHID, model.getBatchId());
    }

    @Override
    public void saveReinforceFail(String msg) {
        String s=msg;
        showMsg("提交失败");
    }
}
