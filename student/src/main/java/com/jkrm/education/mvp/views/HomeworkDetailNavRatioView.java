package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ReportQuestionScoreRateResultBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface HomeworkDetailNavRatioView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void report_questionRate(String homeworkId, String studentId);
    }

    interface View extends AwBaseView {
        void report_questionRateSuccess(List<ReportQuestionScoreRateResultBean> result);
        void report_questionRateFail(String msg);
    }

}