package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.mvp.views.MeMainFragmentView;
import com.jkrm.education.mvp.views.MeTeacherView;

import java.util.List;

import rx.Observable;

public class MeTeacherPresent extends AwCommonPresenter implements MeTeacherView.Presenter {

    private MeTeacherView.View mView;

    public MeTeacherPresent(MeTeacherView.View view) {
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
}
