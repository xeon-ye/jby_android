package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiListCallback;
import com.hzw.baselib.interfaces.AwApiListSubscriber;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.hzw.baselib.project.student.bean.SubjectDataResultBean;
import com.hzw.baselib.util.AwDataUtil;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ErrorQuestionBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.common.ResponseListBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.CoursePlayResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.mvp.views.ErrorQuestionNewView;
import com.jkrm.education.mvp.views.ErrorQuestionView;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;

public class ErrorQuestionNewPresent extends AwCommonPresenter implements ErrorQuestionNewView.Presenter {

    private ErrorQuestionNewView.View mView;

    public ErrorQuestionNewPresent(ErrorQuestionNewView.View view) {
        this.mView = view;
    }


    @Override
    public void getErrorQuestionList(String studentId) {
        Observable<ResponseBean<List<ErrorQuestionBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getErrorQuestionList(studentId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ErrorQuestionBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ErrorQuestionBean> data) {
                mView.getErrorQuestionSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                if (AwDataUtil.isEmpty(msg)) {
                    mView.getErrorQuestionFail(msg);
                    return;
                }
                if (msg.contains("Unterminated string at line") || msg.contains("Unexpected status")) {
                    getErrorQuestionList(studentId);
                } else {
                    mView.getErrorQuestionFail(msg);
                }
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
