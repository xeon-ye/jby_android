package com.jkrm.education.mvp.presenters;

import com.hzw.baselib.interfaces.AwApiCallback;
import com.hzw.baselib.interfaces.AwApiSubscriber;
import com.hzw.baselib.presenters.AwCommonPresenter;
import com.jkrm.education.api.APIService;
import com.jkrm.education.api.RetrofitClient;
import com.jkrm.education.bean.ExamReviewBean;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.ScoreBean;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.mvp.views.ExamTaskView;
import com.jkrm.education.mvp.views.ReviewView;

import java.util.List;

import rx.Observable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/10 15:06
 */

public class ReviewPresent extends AwCommonPresenter implements ReviewView.Presenter {


    private ReviewView.View mView;

    public ReviewPresent(ReviewView.View view) {
        this.mView = view;
    }



    @Override
    public void getExamReviewScore(String teacherId, String examId, String paperId, String readWay, String questionId) {
        Observable<ResponseBean<List<ScoreBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamReviewScore(teacherId,examId,paperId,readWay,questionId);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ScoreBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(List<ScoreBean> data) {
                mView.getExamReviewScoreSuccess(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.getExamReviewScoreFail(msg);
            }

            @Override
            public void onCompleted() {
            }
        }));
    }

    @Override
    public void getExamReviewList(String teacherId, String examId, String paperId, String readWay, String questionId,String orderType) {
        Observable<ResponseBean<List<ExamReviewBean>>> observable = RetrofitClient.builderRetrofit()
                .create(APIService.class)
                .getExamReviewList(teacherId,examId,paperId,readWay,questionId,orderType);
        addIOSubscription(observable, new AwApiSubscriber(new AwApiCallback<List<ExamReviewBean>>() {
            @Override
            public void onStart() {
                mView.showLoadingDialog();
            }

            @Override
            public void onSuccess(List<ExamReviewBean> data) {
                mView.getExamReviewListSuccess(data);
            }


            @Override
            public void onFailure(int code, String msg) {
                mView.getExamReviewListFail(msg);
            }

            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
            }
        }));
    }
}
