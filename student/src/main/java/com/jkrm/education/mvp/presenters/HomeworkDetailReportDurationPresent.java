package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.ReportDurationsResultBean;
import com.jkrm.education.mvp.views.HomeworkDetailReportDurationlView;

import java.util.List;

import rx.Observable;

public class HomeworkDetailReportDurationPresent extends AwCommonPresenter implements HomeworkDetailReportDurationlView.Presenter {

    private HomeworkDetailReportDurationlView.View mView;

    public HomeworkDetailReportDurationPresent(HomeworkDetailReportDurationlView.View view) {
        this.mView = view;
    }



    @Override
    public void report_durations(String homeworkId) {
        Observable<ResponseBean<List<ReportDurationsResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .report_durations(homeworkId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ReportDurationsResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ReportDurationsResultBean> model) {
                mView.report_durationsSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.report_durationsFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    report_durations(homeworkId);
                } else {
                    mView.report_durationsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
