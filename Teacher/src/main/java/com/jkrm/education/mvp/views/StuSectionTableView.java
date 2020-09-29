package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.StuSectionTableBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface StuSectionTableView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(StuSectionTableBean data);
        void getTableListFail(String msg);


    }

}