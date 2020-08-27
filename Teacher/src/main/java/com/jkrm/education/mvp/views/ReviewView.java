package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReViewTaskBean;

import java.util.List;

import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by hzw on 2018/11/13
 */
public interface ReviewView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getExamReviewScore(String teacherId, String examId, String paperId, String readWay, String questionId);

        void getExamReviewList(String teacherId, String examId, String paperId, String readWay, String questionId);
    }

    interface View extends AwBaseView {
        void getExamReviewScoreSuccess(String data);
        void getExamReviewScoreFail(String msg);

        void getExamReviewListSuccess(String data);
        void getExamReviewListFail(String msg);
    }

}