package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.LoginResult;
import com.jkrm.education.bean.result.UserInfoResult;
import com.jkrm.education.bean.result.VersionResultBean;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface LoginView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void login(RequestBody body);
        void getVersionInfo();
    }

    interface View extends AwBaseView {
        void loginSuccess(LoginResult loginResultBean);
        void getVersionInfoSuccess(VersionResultBean bean);

    }

}