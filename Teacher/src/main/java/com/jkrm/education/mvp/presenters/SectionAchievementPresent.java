package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.SectionAchievementBean;
import com.jkrm.education.mvp.views.SectionAchievementView;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

public class SectionAchievementPresent extends AwCommonPresenter implements SectionAchievementView.Presenter {

    private SectionAchievementView.View mView;

    public SectionAchievementPresent(SectionAchievementView.View view) {
        this.mView = view;
    }


    @Override
    public void getTableList(RequestBody requestBody) {
        Observable<SectionAchievementBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSectionTable(requestBody);
        addIOSubscription(observable, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        });
    }


}
