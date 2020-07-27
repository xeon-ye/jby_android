package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AllStudentScoreResultBean;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.QuestionResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface SeeAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getHomeworkScoreStudentAll(String homeworkId, String classId, String teacherId);
        void getQuestion(String questionId);
        void getSingleStudentQuestionAnswer(String homeworkId, String questionId, String studentId);
        void updateStudentQuestionAnswer(RequestBody body);
    }

    interface View extends AwBaseView {
        void getHomeworkScoreStudentAllSuccess(List<AllStudentScoreResultBean> model);
        void getHomeworkScoreStudentAllFail(String msg);

        void getQuestionSuccess(QuestionResultBean resultBean);
        void getQuestionFail(String msg);

        void getSingleStudentQuestionAnswerSuccess(StudentSingleQuestionAnswerResultBean bean);
        void getSingleStudentQuestionAnswerFail(String msg);

        void updateStudentQuestionAnswerSuccess();
        void updateStudentQuestionAnswerFail(String msg);
    }

}