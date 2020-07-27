package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.VersionResultBean;


/**
 * Created by hzw on 2018/11/13
 */
public interface MainView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getVersionInfo();
    }

    interface View extends AwBaseView {
        void getVersionInfoSuccess(VersionResultBean bean);
    }

}