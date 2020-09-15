package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.SectionAchievementBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface SectionAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(SectionAchievementBean data);
        void getTableListFail(String msg);


    }

}