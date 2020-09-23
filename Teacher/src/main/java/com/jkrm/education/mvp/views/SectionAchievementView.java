package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.common.ResponseBean;
import com.jkrm.education.bean.exam.SectionAchievementBean;
import com.jkrm.education.bean.exam.SectionScoreBean;

import okhttp3.RequestBody;


/**
 *
 */
public interface SectionAchievementView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTableList(RequestBody requestBody);
        void getScore(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getTableListSuccess(SectionAchievementBean data);
        void getTableListFail(String msg);

        void getSectionScoreSuccess(SectionScoreBean data);
        void getSectionScoreFail(String msg);


    }

}