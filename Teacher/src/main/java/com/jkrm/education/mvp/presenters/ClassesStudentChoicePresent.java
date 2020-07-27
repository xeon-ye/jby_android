package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.mvp.views.ClassesStudentChoiceView;

import java.util.List;

import rx.Observable;

public class ClassesStudentChoicePresent extends AwCommonPresenter implements ClassesStudentChoiceView.Presenter {

    private ClassesStudentChoiceView.View mView;

    public ClassesStudentChoicePresent(ClassesStudentChoiceView.View view) {
        this.mView = view;
    }

    @Override
    public void getClassesStudentList(String classIds) {
        Observable<ResponseBean<List<ClassStudentBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassesStudentList(classIds);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ClassStudentBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ClassStudentBean> model) {
                mView.getClassesStudentListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getClassesStudentListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getClassesStudentList(classIds);
                } else {
                    mView.getClassesStudentListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
