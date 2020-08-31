package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ClassessBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.mvp.views.ChoiceClassesView;
import com.jkrm.education.mvp.views.CorrectionView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class CorrectingPresent extends AwCommonPresenter implements CorrectionView.Presenter {


    private CorrectionView.View mView;

    public CorrectingPresent(CorrectionView.View view) {
        this.mView = view;
    }

    @Override
    public void getExamReadHeader(String teacherId, String examId, String paperId, String readWay) {
        Observable<ResponseBean<List<ExamReadHeaderBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamReadHeader(teacherId,examId,paperId,readWay);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamReadHeaderBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamReadHeaderBean> data) {
                mView.getExamReadHeaderSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getExamReadHeaderFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

    @Override
    public void getExamQuestions(String teacherId, String examId, String paperId, String readWay, String questionId) {
        Observable<ResponseBean<List<ExamQuestionsBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamQuestions(teacherId,examId,paperId,readWay,questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamQuestionsBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamQuestionsBean> data) {
                mView.getExamQuestionsSuccess(data);
            }



            @Override
            public void onFailure(int code, String msg) {
                mView.getExamQuestionsFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }

}
