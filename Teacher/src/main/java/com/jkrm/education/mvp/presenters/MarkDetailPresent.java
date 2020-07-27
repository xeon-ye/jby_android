package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwLog;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.mvp.views.MarkDetailView;

import java.util.List;

import rx.Observable;

public class MarkDetailPresent extends AwCommonPresenter implements MarkDetailView.Presenter {

    private MarkDetailView.View mView;

    public MarkDetailPresent(MarkDetailView.View view) {
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
    public void answerSheetProgress(String homeworkId, String classId) {
        Observable<ResponseBean<List<AnswerSheetProgressResultBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .answerSheetProgress(homeworkId, classId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<AnswerSheetProgressResultBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<AnswerSheetProgressResultBean> model) {
                mView.answerSheetProgressSuccess(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    answerSheetProgress(homeworkId, classId);
                } else {
                    //                    mView.getAnswerSheetsFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getQustionAnswerWithSingleStudent(String homework_id, String student_id) {
        Observable<ResponseBean<List<HomeworkDetailResultBean.GradQusetionBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getQustionAnswerWithSingleStudent(homework_id, student_id);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<HomeworkDetailResultBean.GradQusetionBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<HomeworkDetailResultBean.GradQusetionBean> model) {
                if(model != null) {
                    AwLog.d("getAllQuestionListByPerson list size: " + model.size());
                    mView.getQustionAnswerWithSingleStudentSuccess(model);
                } else {
                    AwLog.d("getAllQuestionListByPerson list is null");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if(AwDataUtil.isEmpty(msg)) {
                    mView.getQustionAnswerWithSingleStudentFail(msg);
                    return;
                }
                if(msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getQustionAnswerWithSingleStudent(homework_id,student_id);
                } else {
                    mView.getQustionAnswerWithSingleStudentFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
