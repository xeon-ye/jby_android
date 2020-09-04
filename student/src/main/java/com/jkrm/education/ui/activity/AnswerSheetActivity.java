package com.jkrm.education.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hzw.baselib.base.AwBaseActivity;
import com.hzw.baselib.base.AwMvpActivity;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.interfaces.IPermissionListener;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.hzw.baselib.util.AwRecyclerViewUtil;
import com.hzw.baselib.util.AwRxPermissionUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.hzw.baselib.util.AwSystemIntentUtil;
import com.hzw.baselib.widgets.AwViewCustomToolbar;
import com.jkrm.education.R;
import com.jkrm.education.adapter.AnswerSheetAdapter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.AnswerSheetBean;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.QuestionBean;
import com.jkrm.education.bean.SubQuestionsBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.bean.rx.RxAnswerSheetType;
import com.jkrm.education.bean.rx.RxAnswerSituationType;
import com.jkrm.education.bean.rx.RxCostomDownType;
import com.jkrm.education.constants.Extras;
import com.jkrm.education.mvp.presenters.OnlineNonGroupSubjectiveQuestionsChildFragmentPresent;
import com.jkrm.education.mvp.views.OnlineNonGroupSubjectiveQuestionsChildFragmentView;
import com.jkrm.education.util.RequestUtil;
import com.jkrm.education.util.UserUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.sobot.chat.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnswerSheetActivity extends AwMvpActivity<OnlineNonGroupSubjectiveQuestionsChildFragmentPresent> implements OnlineNonGroupSubjectiveQuestionsChildFragmentView.View {

    @BindView(R.id.toolbar_custom)
    AwViewCustomToolbar mToolbarCustom;
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.rcv_data)
    RecyclerView mRcvData;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private AnswerSheetAdapter mAnswerSheetAdapter;
    public static AnswerSheetBean mAnswerSheetBeans, mSplicSheetBeans = new AnswerSheetBean();
    private AnswerSheetBean.QuestionsBean.SubQuestionsBean mQuestionsBean;
    private String mStrTemplateId = "";
    private List<AnswerSheetBean.QuestionsBean.SubQuestionsBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_sheet;
    }

    @Override
    protected void initView() {
        super.initView();
        AwSpUtil.clearAll(Extras.KEY_ANSWER);
        setStatusTxtDark();
        checkPermission();
        mAnswerSheetAdapter = new AnswerSheetAdapter();
        AwRecyclerViewUtil.setRecyclerViewLinearlayout(mActivity, mRcvData, mAnswerSheetAdapter, false);
        mAnswerSheetBeans = (AnswerSheetBean) getIntent().getSerializableExtra(Extras.KEY_ANSWERSHEET);
        Gson gson = new Gson();
        mSplicSheetBeans = gson.fromJson(gson.toJson(mAnswerSheetBeans), AnswerSheetBean.class);
        mStrTemplateId = getIntent().getStringExtra(Extras.KEY_TEMPLATE_ID);
        //拼接数据
        splicingData();
        setToolbar(mAnswerSheetBeans.getTemplateName(), new AwViewCustomToolbar.OnLeftClickListener() {
            @Override
            public void onLeftTextClick() {
                finish();
            }
        });
    }

    @Override
    protected OnlineNonGroupSubjectiveQuestionsChildFragmentPresent createPresenter() {
        return new OnlineNonGroupSubjectiveQuestionsChildFragmentPresent(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void checkPermission() {
        AwRxPermissionUtil.getInstance().checkPermission(mActivity, AwRxPermissionUtil.permissionsStorage, new IPermissionListener() {
            @Override
            public void granted() {


            }

            @Override
            public void shouldShowRequestPermissionRationale() {
                showDialog("请允许获取存储权限才可正常进行导出操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }

            @Override
            public void needToSetting() {
                showDialog("请允许获取存储权限才可正常进行导出操作", v -> {
                    dismissDialog();
                    AwSystemIntentUtil.toApplicationDetailSetting(mActivity);
                    finish();
                });
            }
        });

    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        List<AnswerSheetBean.QuestionsBean> questions = mAnswerSheetBeans.getQuestions();
        for (AnswerSheetBean.QuestionsBean question : questions) {
            for (AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestion : question.getSubQuestions()) {
                if (AwDataUtil.isEmpty(subQuestion.getStudentAnswer())) {
                    AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer studentAnswer = new AnswerSheetBean.QuestionsBean.SubQuestionsBean.StudentAnswer();
                    studentAnswer.setAnswer(subQuestion.getStuAnswer());
                    studentAnswer.setMistake("0");
                    studentAnswer.setExplain("0");
                    subQuestion.setStudentAnswer(studentAnswer);
                }
            }
        }
        RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .submitAnswerSheet(RequestUtil.getSubmitAnswerBody(mStrTemplateId, UserUtil.getAppUser().getStudId(), mAnswerSheetBeans))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WatchLogBean>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {
                                   showMsg("提交错误");
                               }

                               @Override
                               public void onNext(WatchLogBean watchLogBean) {
                                   if ("200".equals(watchLogBean.getCode())) {
                                       toClass(AnswerSituationActivity.class, true, Extras.KEY_ANSWERSHEET, mAnswerSheetBeans, Extras.KEY_TEMPLATE_ID, mStrTemplateId);
                                       EventBus.getDefault().post(new RxAnswerSituationType());
                                   } else {
                                       showMsg(watchLogBean.getMsg());
                                   }
                               }
                           }
                );
    }

    /**
     * 组选题 主题干 无id需要去除并将内部的小题取出放置外层
     */
    private void splicingData() {
        //遍历小题
        List<AnswerSheetBean.QuestionsBean> questions = mAnswerSheetBeans.getQuestions();
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
                if (!AwDataUtil.isEmpty(subQuestions.get(j).getQuestionNum())) {
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
        mAnswerSheetAdapter.addAllData(mAnswerSheetBeans.getQuestions());
        mAnswerSheetAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                //图片单选和多选数据都是以ArrayList的字符串数组返回的。
                // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                List<LocalMedia> paths = PictureSelector.obtainMultipleResult(data);
                if (paths.get(0).isCompressed()) {
                    String compressPath = paths.get(0).getCompressPath();
                    mPresenter.uploadOss("user_server", compressPath);
                }

            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvnet(RxAnswerSheetType type) {
        for (AnswerSheetBean.QuestionsBean question : mAnswerSheetBeans.getQuestions()) {
            for (AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestion : question.getSubQuestions()) {
                if (type.getId().equals(subQuestion.getId())) {
                    mQuestionsBean = subQuestion;
                    if (type.isChoice()) {
                        subQuestion.setStuAnswer(type.getStuAnswer());
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (AnswerSheetBean.QuestionsBean question : mAnswerSheetBeans.getQuestions()) {
            for (AnswerSheetBean.QuestionsBean.SubQuestionsBean subQuestion : question.getSubQuestions()) {
                String string = AwSpUtil.getString(Extras.KEY_ANSWER, subQuestion.getId(), "");
                subQuestion.setStuAnswer(string);
            }
        }
    }



    @Override
    public void uploadOssSuccess(OssResultBean bean) {
        showMsg("上传成功");
        if (AwDataUtil.isEmpty(mQuestionsBean.getImageList())) {
            ArrayList<String> arrayList = new ArrayList<>();
            mQuestionsBean.setImageList(arrayList);
        }
        mQuestionsBean.getImageList().add(bean.getAccessUrl());
        mAnswerSheetAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadOssFail(String msg) {
        showMsg("上传失败");
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RxAnswerSheetType rxAnswerSheetType) {
        mAnswerSheetAdapter.notifyDataSetChanged();
    }*/
}
