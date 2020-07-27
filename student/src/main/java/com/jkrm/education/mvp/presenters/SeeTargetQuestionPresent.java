package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.mvp.views.SeeTargetQuestionView;

import rx.Observable;

public class SeeTargetQuestionPresent extends AwCommonPresenter implements SeeTargetQuestionView.Presenter {

    private SeeTargetQuestionView.View mView;

    public SeeTargetQuestionPresent(SeeTargetQuestionView.View view) {
        this.mView = view;
    }


    @Override
    public void getQuestion(String questionId) {
        Observable<ResponseBean<QuestionResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getQuestion(questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<QuestionResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(QuestionResultBean model) {
                mView.getQuestionSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getQuestion(questionId);
                } else {
                    mView.getQuestionFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
