package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.MultipleAchievementBean;
import com.jkrm.education.bean.exam.StuInfoTableBean;
import com.jkrm.education.bean.exam.StuTableTitleBean;
import com.jkrm.education.bean.result.error.ErrorCourseBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 *
 */
public interface StuInfoTableView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
        void getTableTitle(RequestBody requestBody);

    }

    interface View extends AwBaseView {
        void getTableListSuccess(StuInfoTableBean data);
        void getTableListFail(String msg);
        void getTableTitleSuccess(StuTableTitleBean data);
        void getTableTitleFail(String msg);
    }

}