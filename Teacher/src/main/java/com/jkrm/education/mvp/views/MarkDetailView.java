package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.OssResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface MarkDetailView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getHomeworkDetail(String homeworkId, String classid);
        void answerSheetProgress(String homeworkId, String classId);
        void getQustionAnswerWithSingleStudent(String homework_id, String student_id);
    }

    interface View extends AwBaseView {
        void getHomeworkDetailSuccess(HomeworkDetailResultBean bean);
        void getHomeworkDetailFail(String msg);

        void answerSheetProgressSuccess(List<AnswerSheetProgressResultBean> list);

        void getQustionAnswerWithSingleStudentSuccess(List<HomeworkDetailResultBean.GradQusetionBean> list);
        void getQustionAnswerWithSingleStudentFail(String msg);
    }

}