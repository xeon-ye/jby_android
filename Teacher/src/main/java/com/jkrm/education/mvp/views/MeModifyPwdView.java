package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeModifyPwdView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void modifyPwd(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void modifyPwdSuccess();
    }

}