package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.TableClassBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface MultipleAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
        void getSubjectList();
        void getClassList(String teacherId);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(MultipleAchievementBean data);
        void getTableListFail(String msg);

        void getSubjectSuccess(PeriodCourseBean data);
        void getSubjectListFail(String msg);

        void getClassListSuccess(TableClassBean data);
        void getClassListFail(String msg);


    }

}