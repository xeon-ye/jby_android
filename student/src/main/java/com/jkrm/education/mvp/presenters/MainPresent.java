package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.views.LoginView;
import com.jkrm.education.mvp.views.MainView;

import okhttp3.RequestBody;
import rx.Observable;

public class MainPresent extends AwCommonPresenter implements MainView.Presenter {

    private MainView.View mView;

    public MainPresent(MainView.View view) {
        this.mView = view;
    }


    @Override
    public void getUserJudge() {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getUserJudge(MyApp.getInstance().getAppUser().getUsername());
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(String model) {
                AwLog.d("获取用户信息成功");
            }

            @Override
            public void onFailure(int code, String msg) {
                AwLog.d("获取用户信息: " + code + " ,msg: " + msg);
                if(4 == code) {
                    mView.needReLogin();
                } else {
                    if(AwDataUtil.isEmpty(msg)) {
                        return;
                    }
                    if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                        getUserJudge();
                    } else {
//                        mView.showMsg(msg);
                    }
                }

            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void getVersionInfo() {
        Observable<ResponseBean<VersionResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVersionInfo(AwBaseConstant.VERSION_SYSTEMTYPE_ANDROID, AwBaseConstant.VERSION_ORGTYPE_STUDENT, MyConstant.ServerConstant.getVersionEnvType());
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
