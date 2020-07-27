package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.common.ResponseListBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.test.TestMarkBean;
import com.jkrm.education.mvp.views.PracticeMainFragmentView;
import com.jkrm.education.mvp.views.ScoreMainFragmentView;
import com.jkrm.education.util.TestDataUtil;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class PracticeMainPresent extends AwCommonPresenter implements PracticeMainFragmentView.Presenter {

    private PracticeMainFragmentView.View mView;

    public PracticeMainPresent(PracticeMainFragmentView.View view) {
        this.mView = view;
    }

    @Override
    public void getPracticeDataList(String student_id, String start_date, String end_date, String course_id, String questionTypeId) {
        Observable<ResponseBean<PracticeDataResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getPracticeDataList(student_id, start_date, end_date, course_id, questionTypeId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<PracticeDataResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(PracticeDataResultBean model) {
                mView.getPracticeDataListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getPracticeDataListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getPracticeDataList(student_id, start_date, end_date, course_id, questionTypeId);
                } else {
                    mView.getPracticeDataListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
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
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getVideos(params);
                } else {
                    mView.getVideoFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
