package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.mvp.views.ErrorQuestionStatisticsView;
import com.jkrm.education.mvp.views.LoginView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class ErrorQuestionStatisticsPresent extends AwCommonPresenter implements ErrorQuestionStatisticsView.Presenter {

    private ErrorQuestionStatisticsView.View mView;

    public ErrorQuestionStatisticsPresent(ErrorQuestionStatisticsView.View view) {
        this.mView = view;
    }


    @Override
    public void getErrorQuestionStatisticsList(String templateId, String studentId) {
        Observable<ResponseBean<List<ErrorQuestionResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorQuestionStatisticsList(templateId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorQuestionResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorQuestionResultBean> model) {
                mView.getErrorQuestionStatisticsListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
//                    mView.showMsg("获取错题统计列表数据失败， 错误码： " + code);
                    mView.getErrorQuestionStatisticsListFail("获取错题统计列表数据失败， 错误码： " + code);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getErrorQuestionStatisticsList(templateId, studentId);
                } else {
                    mView.getErrorQuestionStatisticsListFail(msg);
//                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void addErrorQuestion(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addErrorQuestion(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.addErrorQuestionSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("加入错题本失败， 错误码： " + code);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    addErrorQuestion(body);
                } else {
                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void delErrorQuestion(String questionId, String studentId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .delErrorQuestion(questionId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.delErrorQuestionSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("移出错题本失败， 错误码： " + code);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    delErrorQuestion(questionId, studentId);
                } else {
                    mView.showMsg(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
