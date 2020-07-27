package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.todo.TodoBean;
import com.jkrm.education.mvp.views.MainFragmentView;
import com.jkrm.education.mvp.views.MainLoseView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class MainLosePresent extends AwCommonPresenter implements MainLoseView.Presenter {

    private MainLoseView.View mView;

    public MainLosePresent(MainLoseView.View view) {
        this.mView = view;
    }

    @Override
    public void getStatusErrorQuestionInSomeDay(String teacherId) {
        Observable<ResponseBean<List<StatusErrorQuestionResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getStatusErrorQuestionInSomeDay(teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<StatusErrorQuestionResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<StatusErrorQuestionResultBean> model) {
                mView.getStatusErrorQuestionInSomeDaySuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getStatusErrorQuestionInSomeDay(teacherId);
                } else {
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
