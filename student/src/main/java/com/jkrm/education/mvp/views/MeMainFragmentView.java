package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.RequestClassesBean;
import com.jkrm.education.bean.result.TeachersResultBean;
import com.jkrm.education.bean.result.VersionResultBean;
import com.jkrm.education.bean.test.TestMeTeachersBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeMainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherList(String params);
        void getVersionInfo();
        void getClassesById(String stuId);
    }

    interface View extends AwBaseView {
        void getTeacherListSuccess(List<TeachersResultBean> list);
        void getTeacherListFail(String msg);

        void getVersionInfoSuccess(VersionResultBean bean);
        void getVersionFail(String msg);
        void getClassesByIdSuccess(List<RequestClassesBean> data);
        void getClassesByIdFail(String msg);
    }

}