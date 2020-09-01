package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ExamReviewBean;
import com.jkrm.education.bean.ScoreBean;
import com.jkrm.education.bean.exam.ExamQuestionsBean;
import com.jkrm.education.bean.exam.ExamReadHeaderBean;
import com.jkrm.education.bean.result.OssResultBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by hzw on 2018/11/13
 */
public interface CorrectionView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getExamReadHeader(String teacherId, String examId, String paperId, String readWay);

        void getExamQuestions(String teacherId, String examId, String paperId, String readWay, String questionId);

        void uploadOss(boolean isNext, String model, String filePath);
        void addSpecial(RequestBody body);
        void deleteSpecial(String homeworkId, String questionId, String studCode);
        void markQuestion(boolean isNext, String questionAnswerId, RequestBody requestBody);
        void examMark(boolean isNext,RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void  getExamReadHeaderSuccess(List<ExamReadHeaderBean> data);
        void  getExamReadHeaderFail(String msg);

        void getExamQuestionsSuccess(List<ExamQuestionsBean> data);
        void getExamQuestionsFail(String msg);

        void uploadOssSuccess(boolean isNext, OssResultBean bean);
        void uploadOssFail(boolean isNext, String msg);

        void addSpecialSuccess();
        void deleteSpecialSuccess();

        void markQuestionSuccess(boolean isNext);
        void markQuestionFail(String msg);

        void examMarkSuccess(boolean isNext);
        void examMarkFail(String msg);
    }

}