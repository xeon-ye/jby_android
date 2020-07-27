package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface UserLoginVerifyView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void verifyUserLogin(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void verifyUserLoginSuccess();
    }

}