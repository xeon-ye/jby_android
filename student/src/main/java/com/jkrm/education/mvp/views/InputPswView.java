package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.RegisterBean;
import com.jkrm.education.bean.result.LoginResult;

import okhttp3.RequestBody;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/11 16:58
 */

public interface InputPswView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void userRegister(RequestBody body);
        void updateUserPwd(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void userRegisterSuccess(RegisterBean data);
        void userRegisterFail(String msg);

        void updateUserPwdSuccess();


    }
}
