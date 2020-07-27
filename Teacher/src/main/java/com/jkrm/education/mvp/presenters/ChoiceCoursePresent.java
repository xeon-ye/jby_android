package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.ChoiceCourseView;

import rx.Observable;

public class ChoiceCoursePresent extends AwCommonPresenter implements ChoiceCourseView.Presenter {

    private ChoiceCourseView.View mView;

    public ChoiceCoursePresent(ChoiceCourseView.View view) {
        this.mView = view;
    }


    @Override
    public void getPeriodCourse() {
        Observable<ResponseBean<PeriodCourseBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getPeriodCourse();
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<PeriodCourseBean>() {


            @Override
            public void onSuccess(PeriodCourseBean data) {
                mView.getPeriodCourseSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getPeriodCourseFail(msg);
            }
        }));
    }
}
