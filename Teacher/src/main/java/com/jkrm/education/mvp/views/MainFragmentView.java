package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.StatusErrorQuestionResultBean;
import com.jkrm.education.bean.result.StatusHomeworkScanResultBean;
import com.jkrm.education.bean.result.TeacherTodoBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface MainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherTodoList(String teacherId, int index);
        void getStatusMarkBeforeDawn(String teacherId);
        void getStatusErrorQuestionInSomeDay(String teacherId);
    }

    interface View extends AwBaseView {
        void getTeacherTodoListSuccess(String data, List<TeacherTodoBean> list, int total, int size, int pages, int current);
        void getTeacherTodoListFaile(String msg);

        void getStatusMarkBeforeDawnSuccess(StatusHomeworkScanResultBean bean);
        void getStatusErrorQuestionInSomeDaySuccess(List<StatusErrorQuestionResultBean> list);
        void getStatusErrorQuestionInSomeDayFail(String msg);
    }

}