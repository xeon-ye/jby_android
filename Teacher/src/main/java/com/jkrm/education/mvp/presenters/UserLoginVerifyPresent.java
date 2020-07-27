package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.UserLoginVerifyResultBean;
import com.jkrm.education.mvp.views.ResetPwdView;
import com.jkrm.education.mvp.views.UserLoginVerifyView;

import okhttp3.RequestBody;
import rx.Observable;

public class UserLoginVerifyPresent extends AwCommonPresenter implements UserLoginVerifyView.Presenter {

    private UserLoginVerifyView.View mView;

    public UserLoginVerifyPresent(UserLoginVerifyView.View view) {
        this.mView = view;
    }

    @Override
    public void verifyUserLogin(RequestBody requestBody) {
        Observable<ResponseBean<UserLoginVerifyResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .verifyUserLogin(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<UserLoginVerifyResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(UserLoginVerifyResultBean model) {
                mView.verifyUserLoginSuccess();
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
