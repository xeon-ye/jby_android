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

public class SectionAchievementPresent extends AwCommonPresenter implements SectionAchievementView.Presenter {

    private SectionAchievementView.View mView;

    public SectionAchievementPresent(SectionAchievementView.View view) {
        this.mView = view;
    }


    @Override
    public void getTableList(RequestBody requestBody) {
        Observable<ResponseBean<SectionAchievementBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSectionTable(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<SectionAchievementBean>() {
            @Override
            public void onSuccess(SectionAchievementBean data) {
                mView.getTableListSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getTableListFail(msg);
            }

//            @Override
//            public void onStart() {
//                mView.showLoadingDialog();
//            }
//
//            @Override
//            public void onCompleted() {
//                mView.dismissLoadingDialog();
//            }

        }));
    }


}
