package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.ChartPracticeFragmentView;
import com.jkrm.education.mvp.views.PracticeMainFragmentView;

import java.util.List;

import rx.Observable;

public class ChartPracticeFragmentPresent extends AwCommonPresenter implements ChartPracticeFragmentView.Presenter {

    private ChartPracticeFragmentView.View mView;

    public ChartPracticeFragmentPresent(ChartPracticeFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getPracticeDataTable(String student_id, String start_date, String end_date) {
        Observable<ResponseBean<List<PracticeTableResult>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getPracticeDataTable(student_id, start_date, end_date);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<PracticeTableResult>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<PracticeTableResult> model) {
                mView.getPracticeDataTableSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getPracticeDataTableFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getPracticeDataTable(student_id, start_date, end_date);
                } else {
                    mView.getPracticeDataTableFail(msg);
                }
            }

            @Override
            public void onCompleted() {
            }
        }));
    }
}
