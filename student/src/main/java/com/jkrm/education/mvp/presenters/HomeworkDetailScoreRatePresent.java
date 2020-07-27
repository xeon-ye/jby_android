package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ReportQuestionScoreRateResultBean;
import com.jkrm.education.mvp.views.HomeworkDetailScoreRateView;

import java.util.List;

import rx.Observable;

public class HomeworkDetailScoreRatePresent extends AwCommonPresenter implements HomeworkDetailScoreRateView.Presenter {

    private HomeworkDetailScoreRateView.View mView;

    public HomeworkDetailScoreRatePresent(HomeworkDetailScoreRateView.View view) {
        this.mView = view;
    }

    @Override
    public void report_questionRate(String homeworkId, String studentId) {
        Observable<ResponseBean<List<ReportQuestionScoreRateResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .report_questionRate(homeworkId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ReportQuestionScoreRateResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ReportQuestionScoreRateResultBean> model) {
                mView.report_questionRateSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.report_questionRateFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    report_questionRate(homeworkId, studentId);
                } else {
                    mView.report_questionRateFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
