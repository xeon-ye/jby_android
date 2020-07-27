package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.InputPswView;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/28 18:45
 */

public class InputPswPresent extends AwCommonPresenter implements InputPswView.Presenter  {

    private InputPswView.View mView;

    public InputPswPresent(InputPswView.View view) {
        this.mView = view;
    }


    @Override
    public void userRegister(RequestBody body) {
        Observable<ResponseBean<RegisterBean>> observable=RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .userRegister(body);
        addIOSubscription(observable,new AwApiSubscriber(new AwApiCallback<RegisterBean>() {

            @Override
            public void onStart() {
                super.onStart();
                mView.showLoadingDialog();
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                mView.dismissLoadingDialog();
            }

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
