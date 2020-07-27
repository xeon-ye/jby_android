package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.views.MainView;

import rx.Observable;

public class MainPresent extends AwCommonPresenter implements MainView.Presenter {

    private MainView.View mView;

    public MainPresent(MainView.View view) {
        this.mView = view;
    }

    @Override
    public void getVersionInfo() {
        Observable<ResponseBean<VersionResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVersionInfo(AwBaseConstant.VERSION_SYSTEMTYPE_ANDROID, AwBaseConstant.VERSION_ORGTYPE_TEACHER, MyConstant.ServerConstant.getVersionEnvType());
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<VersionResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
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
                mView.dismissLoadingDialog();
            }
        }));
    }
}
