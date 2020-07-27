package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.MeModifyPwdView;

import okhttp3.RequestBody;
import rx.Observable;

public class ModifyPwdPresent extends AwCommonPresenter implements MeModifyPwdView.Presenter {

    private MeModifyPwdView.View mView;

    public ModifyPwdPresent(MeModifyPwdView.View view) {
        this.mView = view;
    }

    @Override
    public void modifyPwd(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .modifyUserPwd(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.modifyPwdSuccess();
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
