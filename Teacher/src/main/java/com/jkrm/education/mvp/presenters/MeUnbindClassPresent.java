package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.MeUnbindClassView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class MeUnbindClassPresent extends AwCommonPresenter implements MeUnbindClassView.Presenter {


    private MeUnbindClassView.View mView;

    public MeUnbindClassPresent(MeUnbindClassView.View view) {
        this.mView = view;
    }





    @Override
    public void unBindSchoolClass(String teacherId, String classId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .unbindSchoolClass(teacherId,classId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.unBindSchoolClassSuccess(model);
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
    public void getClassesById(String teacherId) {
        Observable<ResponseBean<List<RequestClassesBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassesById(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<RequestClassesBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<RequestClassesBean> data) {
                mView.getClassesByIdSuccess(data);
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
