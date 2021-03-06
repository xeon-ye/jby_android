package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.result.login.LoginResult;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.views.LoginView;

import okhttp3.RequestBody;
import rx.Observable;

public class LoginPresent extends AwCommonPresenter implements LoginView.Presenter {

    private LoginView.View mView;

    public LoginPresent(LoginView.View view) {
        this.mView = view;
    }


    @Override
    public void login(RequestBody body) {
        Observable<ResponseBean<LoginResult>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .login(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<LoginResult>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(LoginResult model) {
                mView.loginSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if("解析错误".equals(msg)){
                    mView.showMsg("用户名或密码错误");
                }else{
                    mView.showMsg(msg);
                }
                MyApp.getInstance().clearLoginUser();
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getVersionInfo() {
        Observable<ResponseBean<VersionResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVersionInfo(AwBaseConstant.VERSION_SYSTEMTYPE_ANDROID, AwBaseConstant.VERSION_ORGTYPE_TEACHER, MyConstant.ServerConstant.getVersionEnvType());
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<VersionResultBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(VersionResultBean model) {
                mView.getVersionInfoSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onCompleted() {
            }
        }));
    }
}
