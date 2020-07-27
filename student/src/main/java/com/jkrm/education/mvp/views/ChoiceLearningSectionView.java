package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.jkrm.education.bean.PeriodCourseBean;
import com.jkrm.education.bean.RegisterBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 *
 */
public interface ChoiceLearningSectionView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
       void getPeriodCourse();
    }

    interface View extends AwBaseView {
        void getPeriodCourseSuccess(PeriodCourseBean data);
       void getPeriodCourseFail(String msg);
    }

}