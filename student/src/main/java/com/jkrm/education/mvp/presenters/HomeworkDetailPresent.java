package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.MaxScoreResultBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.HomeworkDetailView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class HomeworkDetailPresent extends AwCommonPresenter implements HomeworkDetailView.Presenter {

    private HomeworkDetailView.View mView;

    public HomeworkDetailPresent(HomeworkDetailView.View view) {
        this.mView = view;
    }


    @Override
    public void answerSheetsQuestion(String answer_sheet_id) {
        Observable<ResponseBean<List<AnswerSheetDataDetailResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answerSheetsQuestion(answer_sheet_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AnswerSheetDataDetailResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AnswerSheetDataDetailResultBean> model) {
                mView.answerSheetsQuestionSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    answerSheetsQuestion(answer_sheet_id);
                } else {
                    mView.answerSheetsQuestionFail(msg);
                }

            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getClassScoreMaxScore(String answer_sheet_id) {
        Observable<ResponseBean<MaxScoreResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getClassScoreMaxScore(answer_sheet_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<MaxScoreResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(MaxScoreResultBean model) {
                mView.getClassScoreMaxScoreSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getClassScoreMaxScore(answer_sheet_id);
                } else {
                    mView.getClassScoreMaxScoreFail(msg);
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
    public void getVideoPointList(String homework_id) {
        Observable<ResponseBean<List<VideoPointResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVideoPointList(homework_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<VideoPointResultBean>>() {
            @Override
            public void onStart() {
                //这里不展示加载框, 因为与作业详情同时调用接口
            }

            @Override
            public void onSuccess(List<VideoPointResultBean> model) {
                mView.getVideoPointListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getVideoPointListFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getVideoPointList(homework_id);
                } else {
                    mView.getVideoPointListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
            }
        }));
    }


    @Override
    public void getVideoPointListNew(String homework_id) {
        Observable<ResponseBean<List<VideoPointResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getVideoPointListNew(homework_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<VideoPointResultBean>>() {
            @Override
            public void onStart() {
                //这里不展示加载框, 因为与作业详情同时调用接口
            }

            @Override
            public void onSuccess(List<VideoPointResultBean> model) {
                mView.getVideoPointListNewSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getVideoPointListNewFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getVideoPointListNew(homework_id);
                } else {
                    mView.getVideoPointListNewFail(msg);
                }
            }

            @Override
            public void onCompleted() {
            }
        }));
    }


    @Override
    public void addQuestionBasket(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .addQuestionBasket(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.addQuestionBasketSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.showMsg("添加题篮失败");
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    addQuestionBasket(body);
                } else {
                    mView.showMsg("添加题篮失败");
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


}
