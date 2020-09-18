package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.TableClassBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 *
 */
public interface MultipleAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
        void getSubjectList(String teacherId);

    }

    interface View extends AwBaseView {
        void getTableListSuccess(MultipleAchievementBean data);
        void getTableListFail(String msg);

        void getSubjectSuccess(List<ErrorCourseBean> data);
        void getSubjectListFail(String msg);

    }

}