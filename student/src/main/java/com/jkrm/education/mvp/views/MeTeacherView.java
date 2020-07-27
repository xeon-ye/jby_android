package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.TeachersResultBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeTeacherView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherList(String params);
    }

    interface View extends AwBaseView {
        void getTeacherListSuccess(List<TeachersResultBean> list);
        void getTeacherListFail(String msg);
    }

}