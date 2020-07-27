package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.RegisterInitUserBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.WatchLogBean;
import com.jkrm.education.mvp.views.ChoiceSchoolView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class ChoseSchoolPresent extends AwCommonPresenter implements ChoiceSchoolView.Presenter {

    private ChoiceSchoolView.View mView;

    public ChoseSchoolPresent(ChoiceSchoolView.View view) {
        this.mView = view;
    }


    @Override
    public void getResions(String encrypt, String t, String pcode, int type) {
        Observable<ResponseBean<List<AddressBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getResions(encrypt, t, pcode);
        addIOSubscription(observable, new AwApiSubscriber<>(new AwApiCallback<List<AddressBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AddressBean> data) {
                mView.getResionsSuccess(data, type);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getResionFail(msg);
            }


            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getSchoolList(String encrypt, String t, String provId, String cityId, String areaId) {
        Observable<SchoolBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSchoolList(encrypt, t, provId, cityId, areaId);
        addIOSubscription(observable, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.getSchoolFail(e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setPrettyPrinting();
                Gson gson = gsonBuilder.create();
                String s = gson.toJson(o);
                SchoolBean data = new Gson().fromJson(s, SchoolBean.class);
                if ("200".equals(data.getCode())) {
                    mView.getSchoolSuccess(data);
                } else {
                    mView.getSchoolFail(data.getMsg());
                }

            }


        });
    }

    @Override
    public void registerInitUser(RequestBody requestBody) {
        Observable<ResponseBean<LoginResult>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .registerInitUser(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<LoginResult>() {

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
            public void onSuccess(LoginResult data) {
                mView.registerInitUserSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.registerInitUserFail(msg);
            }
        }));
    }


}
