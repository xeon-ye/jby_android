package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.result.login.LoginResult;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface FindPwdView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getVerifyCode(RequestBody requestBody);
        void verifyPhoneCode(RequestBody requestBody);
        void registerUser(RequestBody requestBody);
        void verifyCodeLogin(RequestBody requestBody);
        void userRegister(RequestBody body);

    }

    interface View extends AwBaseView {
        void getVerifyCodeSuccess(String code);
        void verifyPhoneCodeSuccess();

        void registerUserSuccess();
        void registerUserFail(String msg);

        void verifyCodeLoginSuccess(LoginResult data);
        void verifyCodeLoginFail(String msg);

        void userRegisterSuccess(RegisterBean data);
        void userRegisterFail(String msg);
    }

}