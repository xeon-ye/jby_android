package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.request.RequestClassesBean;
import com.jkrm.education.bean.result.TeacherClassBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.test.TestMeClassesBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeMainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherClassList(String teacherId);
        void getUserInfo();
        void getVersionInfo();
        void getClassesById(String teacherId);
        void logout();

    }

    interface View extends AwBaseView {
        void getTeacherClassListSuccess(List<TeacherClassBean> list);
        void getTeacherClassListFail(String msg);
        void getVersionInfoSuccess(VersionResultBean bean);
        void getVersionFail(String msg);
        void getClassesByIdSuccess(List<RequestClassesBean> data);
        void getClassesByIdFail(String msg);
    }

}