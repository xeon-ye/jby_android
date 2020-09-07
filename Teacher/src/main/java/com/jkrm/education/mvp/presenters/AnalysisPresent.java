package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.TaskBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.AnalysisView;
import com.jkrm.education.mvp.views.TaskView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class AnalysisPresent extends AwCommonPresenter implements AnalysisView.Presenter {


    private AnalysisView.View mView;

    public AnalysisPresent(AnalysisView.View view) {
        this.mView = view;
    }


    @Override
    public void getAnalysisList(RequestBody requestBody) {
        Observable<ResponseBean<List<String>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getAnalysisList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<String>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<String> data) {
                mView.getAnalysisListSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getAnalysisListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
