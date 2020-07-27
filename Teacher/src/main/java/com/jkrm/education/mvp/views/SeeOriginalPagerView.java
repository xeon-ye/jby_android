package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.HomeworkDetailResultBean;
import com.jkrm.education.bean.result.NormalBean;
import com.jkrm.education.bean.result.OriginalPagerResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface SeeOriginalPagerView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void answerSheetProgress(String homeworkId, String classId);
        void getStudentOriginalQuestionAnswer(String homework_id, String student_id);
        void unConnect(RequestBody requestBody);
    }

    interface View extends AwBaseView {

        void answerSheetProgressSuccess(List<AnswerSheetProgressResultBean> list);

        void getStudentOriginalQuestionAnswerSuccess(OriginalPagerResultBean bean);

        void unConnectSuccess(NormalBean normalBean);
        void unConnectFail(String msg);
    }

}