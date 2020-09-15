package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.mvp.views.ScoreAchievementView;

import okhttp3.RequestBody;
import rx.Observable;

public class ScoreAchievementPresent extends AwCommonPresenter implements ScoreAchievementView.Presenter {

    private ScoreAchievementView.View mView;

    public ScoreAchievementPresent(ScoreAchievementView.View view) {
        this.mView = view;
    }


    @Override
    public void getTableList(RequestBody requestBody) {
        Observable<ResponseBean<ScoreAchievementBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getScoreTable(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<ScoreAchievementBean>() {
            @Override
            public void onSuccess(ScoreAchievementBean data) {
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
