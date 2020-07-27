package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.PeriodCourseBean;


/**
 *
 */
public interface ChoiceCourseView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
       void getPeriodCourse();
    }

    interface View extends AwBaseView {
        void getPeriodCourseSuccess(PeriodCourseBean data);
       void getPeriodCourseFail(String msg);
    }

}