package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReinfoRoceCouseBean;
import com.jkrm.education.bean.ReinforBean;
import com.jkrm.education.bean.result.WatchLogBean;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/1 16:50
 */

public interface AnswerSituationView extends AwBaseView {
    interface Presenter extends AwBasePresenter {
        void explainQuestion(String value,String templateId,String studentId,String questionId);
        void addMistake(String templateId,String studentId,String questionId);
        void deleteMistake(String questionId, String studentId);
    }

    interface View extends AwBaseView {
        void explainQuestionSuccess(WatchLogBean watchLogBean);
        void explainQuestionFail(String msg);

        void addMistakeSuccess(String data);
        void addMistakeFail(String msg);

        void deleteMistakeSuccess(String data);
        void deleteMistakeFail(String msg);
    }
}
