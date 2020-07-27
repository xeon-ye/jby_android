package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.PracticeMainFragmentView;
import com.jkrm.education.mvp.views.QuestionBasketFragmentView;

import okhttp3.RequestBody;
import rx.Observable;

public class QuestionBasketPresent extends AwCommonPresenter implements QuestionBasketFragmentView.Presenter {

    private QuestionBasketFragmentView.View mView;

    public QuestionBasketPresent(QuestionBasketFragmentView.View view) {
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

    @Override
    public void delSomeOneQuestionBasket(String questionId, String studentId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .delSomeOneQuestionBasket(questionId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.delSomeOneQuestionBasketSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    delSomeOneQuestionBasket(questionId, studentId);
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

    @Override
    public void delAllQuestionBasket(String studentId) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .delAllQuestionBasket(studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.delAllQuestionBasketSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("清空题篮失败");
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    delAllQuestionBasket(studentId);
                } else {
                    mView.showMsg("清空题篮失败");
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void exportQuestionBasket(RequestBody requestBody) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .exportQuestionBasket(requestBody);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {

            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("导出题篮失败");
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    exportQuestionBasket(requestBody);
                } else {
                    mView.showMsg("导出题篮失败");
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
