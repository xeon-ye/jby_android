package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.ScoreAchievementBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface ScoreAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(ScoreAchievementBean data);
        void getTableListFail(String msg);


    }

}