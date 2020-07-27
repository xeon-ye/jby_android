package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;

import okhttp3.RequestBody;
import retrofit2.http.Body;


/**
 * Created by hzw on 2018/11/13
 */
public interface MeModifyNameView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void updateNickname(@Body RequestBody requestBody);
        void updateRealName(@Body RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void updateNicknameSuccess(String code);
        void updateRealNameSuccess(String code);
    }

}