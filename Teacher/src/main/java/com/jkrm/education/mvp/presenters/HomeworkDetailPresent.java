package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AnswerSheetDataDetailResultBean;
import com.jkrm.education.bean.result.ExplainStudentBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.HomeworkStudentAnswerWithSingleQuestionResultBean;
import com.jkrm.education.bean.result.MaxScoreResultBean;
import com.jkrm.education.bean.result.QuestionSpecialResultBean;
import com.jkrm.education.bean.result.VideoPointResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.HomeworkDetailView;

import java.util.List;

import rx.Observable;

public class HomeworkDetailPresent extends AwCommonPresenter implements HomeworkDetailView.Presenter {

    private HomeworkDetailView.View mView;

    public HomeworkDetailPresent(HomeworkDetailView.View view) {
        this.mView = view;
    }


    @Override
    public void getHomeworkDetail(String homeworkId, String classid) {
        Observable<ResponseBean<HomeworkDetailResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .homeworkDetail(homeworkId, classid);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<HomeworkDetailResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(HomeworkDetailResultBean model) {
                mView.getHomeworkDetailSuccess(model);
                if(model != null) {
                    AwLog.d("getHomeworkDetail getGradQusetion size: " + model.getGradQusetion().size());
                    AwLog.d("getHomeworkDetail getHomeworkDurat size: " + model.getHomeworkDurat().size());
                    AwLog.d("getHomeworkDetail getQuestionScore size: " + model.getQuestionScore().size());
                } else {
                    AwLog.d("getHomeworkDetail model is null");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getHomeworkDetail(homeworkId, classid);
                } else {
                    mView.getHomeworkDetailFail(msg);
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
    public void getHomeworkStudentScoreList(String homeworkId, String classid) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getHomeworkStudentScoreList(homeworkId, classid);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.getHomeworkStudentScoreListSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getHomeworkStudentScoreList(homeworkId, classid);
                } else {
                    mView.getHomeworkStudentScoreListFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getHomeworkStudentScoreWithQuestionSingle(String rightResult, String homeworkId, String classId, String questionId) {
        Observable<ResponseBean<List<HomeworkStudentAnswerWithSingleQuestionResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getHomeworkStudentScoreWithQuestionSingle(homeworkId, classId, questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<HomeworkStudentAnswerWithSingleQuestionResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<HomeworkStudentAnswerWithSingleQuestionResultBean> model) {
                mView.getHomeworkStudentScoreWithQuestionSingleSuccess(rightResult, model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getHomeworkStudentScoreWithQuestionSingle(rightResult, homeworkId, classId, questionId);
                } else {
                    mView.getHomeworkStudentScoreWithQuestionSingleFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getQuestionSpecial(String homeworkId, String questionId) {
        Observable<ResponseBean<List<QuestionSpecialResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getQuestionSpecial(homeworkId, questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<QuestionSpecialResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<QuestionSpecialResultBean> model) {
                mView.getQuestionSpecialSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getQuestionSpecial(homeworkId, questionId);
                } else {
                    mView.getQuestionSpecialFail(msg);
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
    public void getExplainClasses(String teacherId, String homeworkId) {
        Observable<ResponseBean<List<String>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExplainClasses(teacherId,homeworkId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<String>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<String> data) {
                mView.getExplainClassesSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
               mView.getExplainClassesFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getExplainStudent(String homeworkId, String questionId) {
        Observable<ResponseBean<List<ExplainStudentBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExplainStudent(homeworkId,questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExplainStudentBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExplainStudentBean> data) {
                mView.getExplainStudentSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getExplainStudentFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
