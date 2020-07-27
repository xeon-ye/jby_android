package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.MeModifyNameView;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import rx.Observable;

public class MeModifyNamePresent extends AwCommonPresenter implements MeModifyNameView.Presenter {

    private MeModifyNameView.View mView;

    public MeModifyNamePresent(MeModifyNameView.View view) {
        this.mView = view;
    }

    @Override
    public void updateNickname(@Body RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .updateUserNickname(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.updateNicknameSuccess(model);
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
    public void updateRealName(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .updateUserRealName(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.updateRealNameSuccess(model);
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
