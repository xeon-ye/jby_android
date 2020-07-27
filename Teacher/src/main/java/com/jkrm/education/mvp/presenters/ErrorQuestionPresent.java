package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.ErrorBasketBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.ErrorQuestionView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class ErrorQuestionPresent extends AwCommonPresenter implements ErrorQuestionView.Presenter {


    private ErrorQuestionView.View mView;

    public ErrorQuestionPresent(ErrorQuestionView.View view) {
        this.mView = view;
    }


    @Override
    public void getErrorBasket(String teacherId) {
        Observable<ResponseBean<List<ErrorBasketBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorBasket(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorBasketBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorBasketBean> data) {
                mView.getErrorBasketSuccess(data);

            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getErrorBasketFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void deleteErrorBasket(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .deleteErrorBasket(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data) {
                mView.deleteErrorBasketSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.deleteErrorBasketFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void clearErrorBasket(String teacherId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .clearErrorBasket(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data) {
                mView.clearErrorBasketSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.clearErrorBasketFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
