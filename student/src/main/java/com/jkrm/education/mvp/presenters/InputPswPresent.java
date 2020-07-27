package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.mvp.views.InputPswView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

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
            public void onSuccess(RegisterBean data) {
                mView.userRegisterSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.userRegisterFail(msg);
            }
        }));
    }

    @Override
    public void updateUserPwd(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .updateUserPwd(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.updateUserPwdSuccess();
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
