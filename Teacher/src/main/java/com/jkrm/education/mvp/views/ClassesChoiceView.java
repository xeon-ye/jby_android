package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface ClassesChoiceView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherClassList(String teacherId);
    }

    interface View extends AwBaseView {
        void getTeacherClassListSuccess(List<TeacherClassBean> list);
        void getTeacherClassListFail(String msg);
    }

}