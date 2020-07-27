package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.hzw.baselib.bean.AddressBean;
import com.hzw.baselib.bean.SchoolBean;
import com.jkrm.education.bean.result.login.LoginResult;

import java.util.List;

import okhttp3.RequestBody;


/**
 * 选择学校
 */
public interface ChoiceSchoolView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getResions(String encrypt, String t, String pcode, int type);
        void getSchoolList(String encrypt, String t, String provId, String areaId, String cityId);
        void registerInitUser(RequestBody requestBody);
    }

    interface View extends AwBaseView {
        void getResionsSuccess(List<AddressBean> data, int type);
        void getResionFail(String msg);

        void getSchoolSuccess(SchoolBean schoolBean);
        void getSchoolFail(String msg);

        void registerInitUserSuccess(LoginResult registerBean);
        void registerInitUserFail(String msg);
    }

}