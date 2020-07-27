package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.TeacherLectureView;

import rx.Observable;

public class TeacherLecturePresent extends AwCommonPresenter implements TeacherLectureView.Presenter {

    private TeacherLectureView.View mView;

    public TeacherLecturePresent(TeacherLectureView.View view) {
        this.mView = view;
    }


    @Override
    public void getVideos(String params) {
        Observable<ResponseBean<VideoResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVideos(params);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<VideoResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(VideoResultBean model) {
                mView.getVideosSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
//                mView.showMsg(msg);
                mView.getVideoFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
