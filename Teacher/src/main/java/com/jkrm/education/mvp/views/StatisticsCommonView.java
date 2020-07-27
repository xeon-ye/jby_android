package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetProgressResultBean;
import com.jkrm.education.bean.result.ClassStudentBean;
import com.jkrm.education.bean.result.OssResultBean;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Path;


/**
 * Created by hzw on 2018/11/13
 */
public interface StatisticsCommonView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherClassList(String teacherId);
        void getClassesStudentList(String classIds);
    }

    interface View extends AwBaseView {
        void getTeacherClassListSuccess(List<TeacherClassBean> list);
        void getTeacherClassListFail(String msg);

        void getClassesStudentListSuccess(List<ClassStudentBean> list);
        void getClassesStudentListFail(String msg);

    }

}