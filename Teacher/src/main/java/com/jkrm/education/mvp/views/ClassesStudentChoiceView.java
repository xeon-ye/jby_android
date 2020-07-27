package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface ClassesStudentChoiceView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getClassesStudentList(String classIds);
    }

    interface View extends AwBaseView {
        void getClassesStudentListSuccess(List<ClassStudentBean> list);
        void getClassesStudentListFail(String msg);

    }

}