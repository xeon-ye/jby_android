package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.RequestClassesBean;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.test.TestMeTeachersBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.views.MeMainFragmentView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import retrofit2.http.PATCH;
import rx.Observable;
import rx.Subscriber;

public class MeMainFragmentPresent extends AwCommonPresenter implements MeMainFragmentView.Presenter {

    private MeMainFragmentView.View mView;

    public MeMainFragmentPresent(MeMainFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getTeacherList(String params) {
        Observable<ResponseBean<List<TeachersResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getMyTeacherList(params);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<TeachersResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<TeachersResultBean> model) {
                mView.getTeacherListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getTeacherListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getTeacherList(params);
                } else {
                    mView.getTeacherListFail(msg);
                }
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
                .getVersionInfo(AwBaseConstant.VERSION_SYSTEMTYPE_ANDROID, AwBaseConstant.VERSION_ORGTYPE_STUDENT, MyConstant.ServerConstant.getVersionEnvType());
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
                mView.getVersionFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getClassesById(String studId) {
        Observable<ResponseBean<List<RequestClassesBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassesById(studId);
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

    @Override
    public void logout() {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .logout();
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String data) {

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
