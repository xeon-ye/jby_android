package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ExamReviewBean;
import com.jkrm.education.bean.ScoreBean;

import java.util.List;

import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by hzw on 2018/11/13
 */
public interface CorrectionView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getExamReadHeader(String teacherId, String examId, String paperId, String readWay);

        void getExamQuestions(String teacherId, String examId, String paperId, String readWay, String questionId);

    }

    interface View extends AwBaseView {
        void  getExamReadHeaderSuccess(String data);
        void  getExamReadHeaderFail(String msg);

        void getExamQuestionsSuccess(String data);
        void getExamQuestionsFail(String msg);

    }

}