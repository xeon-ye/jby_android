package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.OssResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface TestView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void uploadOss(String model, String filePath);
        void addSpecial(RequestBody body);
        void answerSheetProgress(String homeworkId, String classId);
        void answerSheetProgressWithStudent(String homeworkId, String studentId);
        void getQustionAnswerWithSingleStudent(String homework_id, String student_id);
        void getStatusErrorQuestionInSomeDay(String teacherId);
        void getStatusMarkBeforeDawn(String teacherId);
    }

    interface View extends AwBaseView {
    }

}