package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AllStudentScoreResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;
import com.jkrm.education.mvp.views.SeeAchievementView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class SeeAchievementPresent extends AwCommonPresenter implements SeeAchievementView.Presenter {

    private SeeAchievementView.View mView;
    private int retryCount = 0;

    public SeeAchievementPresent(SeeAchievementView.View view) {
        this.mView = view;
    }

    @Override
    public void getHomeworkScoreStudentAll(String homeworkId, String classId, String teacherId) {

        Observable<ResponseBean<List<AllStudentScoreResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getHomeworkScoreStudentAll(homeworkId, classId, teacherId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AllStudentScoreResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AllStudentScoreResultBean> model) {
                mView.getHomeworkScoreStudentAllSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getHomeworkScoreStudentAllFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    if(retryCount > 3) {
                        mView.getHomeworkScoreStudentAllFail(msg);
                        return;
                    }
                    getHomeworkScoreStudentAll(homeworkId, classId, teacherId);
                    retryCount++;
                } else {
                    mView.getHomeworkScoreStudentAllFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }


    @Override
    public void getQuestion(String questionId) {
        Observable<ResponseBean<QuestionResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getQuestion(questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<QuestionResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(QuestionResultBean model) {
                mView.getQuestionSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getQuestionFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getQuestion(questionId);
                } else {
                    mView.getQuestionFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getSingleStudentQuestionAnswer(String homeworkId, String questionId, String studentId) {
        Observable<ResponseBean<StudentSingleQuestionAnswerResultBean>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getSingleStudentQuestionAnswer(homeworkId, questionId, studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<StudentSingleQuestionAnswerResultBean>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(StudentSingleQuestionAnswerResultBean model) {
                mView.getSingleStudentQuestionAnswerSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getSingleStudentQuestionAnswerFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getSingleStudentQuestionAnswer(homeworkId, questionId, studentId);
                } else {
                    mView.getSingleStudentQuestionAnswerFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void updateStudentQuestionAnswer(RequestBody body) {
        Observable<ResponseBean<String>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .updateStudentQuestionAnswer(body);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<String>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(String model) {
                mView.updateStudentQuestionAnswerSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.updateStudentQuestionAnswerFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    updateStudentQuestionAnswer(body);
                } else {
                    mView.updateStudentQuestionAnswerFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
