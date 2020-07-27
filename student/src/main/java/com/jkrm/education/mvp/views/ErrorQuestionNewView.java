package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.hzw.baselib.project.student.bean.SubjectDataResultBean;
import com.jkrm.education.bean.ErrorQuestionBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.VideoResultBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ErrorQuestionNewView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getErrorQuestionList(String studentId);
    }

    interface View extends AwBaseView {

        void getErrorQuestionSuccess(List<ErrorQuestionBean> list);
        void getErrorQuestionFail(String msg);
    }

}