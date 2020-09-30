package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.ScoreAchievementBean;
import com.jkrm.education.bean.exam.StuSectionTableBean;
import com.jkrm.education.bean.exam.StuTableTitleBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface StuSectionTableView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
        void getTableTitle(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(StuSectionTableBean data);
        void getTableListFail(String msg);
        void getTableTitleSuccess(StuTableTitleBean data);
        void getTableTitleFail(String msg);


    }

}