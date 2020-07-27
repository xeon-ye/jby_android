package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.login.LoginResult;
import com.jkrm.education.mvp.views.FindPwdView;
import com.jkrm.education.mvp.views.TestView;
import com.jkrm.education.ui.activity.login.FindPwdActivity;

import okhttp3.RequestBody;
import rx.Observable;

public class FindPwdPresent extends AwCommonPresenter implements FindPwdView.Presenter {

    private FindPwdView.View mView;

    public FindPwdPresent(FindPwdView.View view) {
        this.mView = view;
    }

    @Override
    public void getVerifyCode(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getPhoneCode(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.getVerifyCodeSuccess(model);
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
    public void verifyPhoneCode(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .verifyPhoneCode(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.verifyPhoneCodeSuccess();
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
    public void registerUser(RequestBody requestBody) {
        Observable<ResponseBean<RegisterBean>> observable=RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .userRegister(requestBody);
        addIOSubscription(observable,new AwApiSubscriber(new AwApiCallback<RegisterBean>() {

            @Override
            public void onSuccess(RegisterBean data) {
                mView.registerUserSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.registerUserFail(msg);
            }
        }));
    }


    @Override
    public void verifyCodeLogin(RequestBody requestBody) {
        Observable<ResponseBean<LoginResult>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .vercodeLogin(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<LoginResult>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(LoginResult data) {
                mView.verifyCodeLoginSuccess(data);
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
    public void userRegister(RequestBody body) {
        Observable<ResponseBean<RegisterBean>> observable=RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .userRegister(body);
        addIOSubscription(observable,new AwApiSubscriber(new AwApiCallback<RegisterBean>() {


            @Override
            public void onSuccess(RegisterBean data) {
                mView.userRegisterSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.userRegisterFail(msg);
            }
        }));
    }
}
