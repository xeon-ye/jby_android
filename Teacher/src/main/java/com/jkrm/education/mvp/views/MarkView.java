package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.MarkAllStudentBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface MarkView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        /**
         * 获取某作业某班级某题学生批阅状态列表
         */
        void getAllStudentListByQuestion(String homework_id, String classId, String question_id);

        /**
         * 获取某作业某班级某学生所有题目批阅状态.
         */
        void getAllQuestionListByPerson(String homework_id, String student_id);

        void getSingleStudentQuestionAnswer(String homeworkId, String questionId, String studentId);
        void uploadOss(boolean isNext, String model, String filePath);
        void addSpecial(RequestBody body);
        void deleteSpecial(String homeworkId, String questionId, String studCode);
        void markQuestion(boolean isNext, String questionAnswerId, RequestBody requestBody);



    }

    interface View extends AwBaseView {

        void getAllStudentListByQuestionSuccess(List<AnswerSheetProgressResultBean> list);
        void getAllStudentListByQuestionFail(String msg);

        void getAllQuestionListByPersonSuccess(List<HomeworkDetailResultBean.GradQusetionBean> list);
        void getAllQuestionListByPersonFail(String msg);

        void getSingleStudentQuestionAnswerSuccess(StudentSingleQuestionAnswerResultBean bean);
        void getSingleStudentQuestionAnswerFail(String msg);

        void uploadOssSuccess(boolean isNext, OssResultBean bean);
        void uploadOssFail(boolean isNext, String msg);

        void addSpecialSuccess();
        void deleteSpecialSuccess();

        void markQuestionSuccess(boolean isNext);
        void markQuestionFail(String msg);


    }

}