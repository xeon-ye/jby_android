package com.jkrm.education.mvp.presenters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.ChoiceLearningSectionView;
import com.jkrm.education.mvp.views.ChoiceSchoolView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class ChoiceLearningSectionPresent extends AwCommonPresenter implements ChoiceLearningSectionView.Presenter {

    private ChoiceLearningSectionView.View mView;

    public ChoiceLearningSectionPresent(ChoiceLearningSectionView.View view) {
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
