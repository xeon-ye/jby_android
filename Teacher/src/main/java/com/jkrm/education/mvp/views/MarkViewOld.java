package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.StudentSingleQuestionAnswerResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface MarkViewOld extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getHomeworkDetail(String homeworkId, String classid);
        void answerSheetProgress(String homeworkId, String classId);
        void getSingleStudentQuestionAnswer(String homeworkId, String questionId, String studentId);
        void uploadOss(boolean isNext, String model, String filePath);
        void addSpecial(RequestBody body);
        void deleteSpecial(String homeworkId, String questionId, String studCode);
        void markQuestion(boolean isNext, String questionAnswerId, RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getHomeworkDetailSuccess(HomeworkDetailResultBean bean);
        void getHomeworkDetailFail(String msg);


        void answerSheetProgressSuccess(List<AnswerSheetProgressResultBean> list);
        void answerSheetProgressFail(String msg);

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