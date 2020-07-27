package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.todo.TodoBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface MainLoseView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getStatusErrorQuestionInSomeDay(String teacherId);
    }

    interface View extends AwBaseView {
        void getStatusErrorQuestionInSomeDaySuccess(List<StatusErrorQuestionResultBean> list);
    }

}