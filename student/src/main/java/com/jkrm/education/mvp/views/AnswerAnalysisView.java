package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.SimilarResultBean;
import com.jkrm.education.bean.result.WatchLogBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/5/28 18:43
 */

public interface AnswerAnalysisView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void collectQuestion(RequestBody requestBody);
        void removeCollectQuestion(RequestBody requestBody);
        void readOverQuestion(RequestBody requestBody);
        void getSimilar(String params);
    }

    interface View extends AwBaseView {
        void collectQuestionSuccess(WatchLogBean request);
        void collectQuestionFail(String msg);

        void removeCollectQuestionSuccess(WatchLogBean request);
        void removeCollectQuestionFail(String msg);

        void readOverQuestionSuccess(WatchLogBean request);
        void readOverQuestionFail(String msg);

        void getSimilarSuccess(List<SimilarResultBean> result);
        void getSimilarFail(String msg);
    }

}
