package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.BatchBean;
import com.jkrm.education.bean.BatchQuestionBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/28 12:05
 */

public interface ExerciseReportView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void getBatch(String batchid);
        void getBatchQuestion(String batchid);
    }

    interface View extends AwBaseView {
        void getBatchSuccess(List<BatchBean> data );
        void getBatchFail(String msg);

        void getBatchQuestionSuccess(BatchQuestionBean batchQuestionBean);
        void getBatchQuestionFail(String msg);
    }
}
