package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.constants.AwBaseConstant;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.result.login.LoginResult;
import com.jkrm.education.bean.test.TestMeClassesBean;
import com.jkrm.education.constants.MyConstant;
import com.jkrm.education.mvp.views.MeMainFragmentView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class MeMainFragmentPresent extends AwCommonPresenter implements MeMainFragmentView.Presenter {

    private MeMainFragmentView.View mView;

    public MeMainFragmentPresent(MeMainFragmentView.View view) {
        this.mView = view;
    }


    @Override
    public void getTeacherClassList(String teacherId) {
        Observable<ResponseBean<List<TeacherClassBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getTeacherClassList(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<TeacherClassBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<TeacherClassBean> model) {
                mView.getTeacherClassListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getTeacherClassListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getTeacherClassList(teacherId);
                } else {
                    mView.getTeacherClassListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getUserInfo() {
        Observable<ResponseBean<LoginResult>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getUserInfo(MyApp.getInstance().getAppUser().getUsername());
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<LoginResult>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(LoginResult model) {
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
                mView.getVersionFail(msg);
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
