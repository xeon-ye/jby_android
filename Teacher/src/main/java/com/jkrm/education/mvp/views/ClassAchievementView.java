package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.ClassAchievementBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface ClassAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(ClassAchievementBean data);
        void getTableListFail(String msg);


    }

}