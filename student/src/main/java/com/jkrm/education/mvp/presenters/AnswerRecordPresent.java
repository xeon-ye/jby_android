package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.BookExercisesBean;
import com.jkrm.education.mvp.views.AnswerAnalysisView;
import com.jkrm.education.mvp.views.AnswerRecordView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/1 9:58
 */

public class AnswerRecordPresent extends AwCommonPresenter implements AnswerRecordView.Presenter  {
    private AnswerRecordView.View mView;

    public AnswerRecordPresent(AnswerRecordView.View view) {
        this.mView = view;
    }

    @Override
    public void getReinforceCourseList(String studentId) {
        Observable<ResponseBean<List<ReinfoRoceCouseBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getReinforceCourseList(studentId);
        addIOSubscription(observable, new AwApiSubscriber(
                new AwApiCallback<List<ReinfoRoceCouseBean>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(List<ReinfoRoceCouseBean> data) {
                        mView.getReinforceCourseListSucces(data);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mView.getReinforceCourseListFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }



                }));
    }

    @Override
    public void getReinforceList(RequestBody requestBody) {
        Observable<ResponseBean<ReinforBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getReinforceList(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(
                new AwApiCallback<ReinforBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.showLoadingDialog();
                    }

                    @Override
                    public void onSuccess(ReinforBean data) {
                        mView.getReinforceListSuccess(data);
                    }



                    @Override
                    public void onFailure(int code, String msg) {
                        mView.getReinforceListFail(msg);
                    }

                    @Override
                    public void onCompleted() {
                        mView.dismissLoadingDialog();
                    }



                }));
    }
}
