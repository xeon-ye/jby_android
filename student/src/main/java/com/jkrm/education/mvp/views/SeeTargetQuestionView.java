package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.QuestionResultBean;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface SeeTargetQuestionView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getQuestion(String questionId);
    }

    interface View extends AwBaseView {
        void getQuestionSuccess(QuestionResultBean resultBean);
        void getQuestionFail(String msg);
    }

}