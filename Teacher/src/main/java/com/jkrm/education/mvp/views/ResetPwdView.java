package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ResetPwdView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void updateUserPwd(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void updateUserPwdSuccess();
    }

}