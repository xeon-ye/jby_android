package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.BatchQuestionBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.ErrorQuestionStatisticsView;
import com.jkrm.education.mvp.views.ExerciseReportView;

import java.util.List;

import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/28 12:06
 */

public class ExerciseReportPresent extends AwCommonPresenter implements ExerciseReportView.Presenter {

    private ExerciseReportView.View mView;

    public ExerciseReportPresent(ExerciseReportView.View view) {
        this.mView = view;
    }

    @Override
    public void getBatch(String batchid) {
        Observable<ResponseBean<List<BatchBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getBatch(batchid);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<BatchBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<BatchBean> data) {
                mView.getBatchSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.showMsg(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getBatchQuestion(String batchid) {
        Observable<ResponseBean<BatchQuestionBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getBatchQuestion(batchid);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<BatchQuestionBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(BatchQuestionBean model) {
                mView.getBatchQuestionSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.showMsg(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
