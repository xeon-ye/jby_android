package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ClassAchievementBean;
import com.jkrm.education.mvp.views.ClassAchievementView;

import okhttp3.RequestBody;
import rx.Observable;

public class ClassAchievementPresent extends AwCommonPresenter implements ClassAchievementView.Presenter {

    private ClassAchievementView.View mView;

    public ClassAchievementPresent(ClassAchievementView.View view) {
        this.mView = view;
    }


    @Override
    public void getTableList(RequestBody requestBody) {
        Observable<ResponseBean<ClassAchievementBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassTable(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<ClassAchievementBean>() {
            @Override
            public void onSuccess(ClassAchievementBean data) {
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
