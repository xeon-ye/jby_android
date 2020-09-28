package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ClassAchievementBean;
import com.jkrm.education.bean.exam.SectionAchievementBean;
import com.jkrm.education.bean.exam.SectionScoreBean;
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
                if (o != null) {
                    SectionAchievementBean data = (SectionAchievementBean) o;
                    if (data.getCode().equals("200"))
                        mView.getTableListSuccess(data);
                    else
                        mView.getTableListFail(data.getMsg());
                } else
                    mView.getTableListFail("数据异常！！");
            }
        });
    }

    @Override
    public void getScore(RequestBody requestBody) {
        Observable<SectionScoreBean> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSectionScore(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<SectionScoreBean>() {
            @Override
            public void onSuccess(SectionScoreBean data) {
                mView.getSectionScoreSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getSectionScoreFail(msg);
            }
        }));
    }


}
