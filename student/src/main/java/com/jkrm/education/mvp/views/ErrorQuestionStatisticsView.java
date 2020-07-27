package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.LoginResult;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ErrorQuestionStatisticsView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getErrorQuestionStatisticsList(String templateId, String studentId);
        void addErrorQuestion(RequestBody body);
        void delErrorQuestion(String questionId, String studentId);
    }

    interface View extends AwBaseView {
        void getErrorQuestionStatisticsListSuccess(List<ErrorQuestionResultBean> result);
        void getErrorQuestionStatisticsListFail(String msg);

        void addErrorQuestionSuccess();
        void delErrorQuestionSuccess();
    }

}