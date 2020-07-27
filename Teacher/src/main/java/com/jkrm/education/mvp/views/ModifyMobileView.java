package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ModifyMobileView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getVerifyCode(RequestBody requestBody);
        void verifyPhoneCode(RequestBody requestBody);
        void modifyMobile(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getVerifyCodeSuccess(String code);
        void verifyPhoneCodeSuccess();
        void modifyMobileSuccess();
    }

}