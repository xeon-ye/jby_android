package com.jkrm.education.util;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.AwSpUtil;
import com.jkrm.education.base.MyApp;
import com.jkrm.education.bean.rx.RxUpdateUserBean;
import com.jkrm.education.bean.user.UserBean;
import com.jkrm.education.constants.MyConstant;

/**
 * Created by hzw on 2019/8/12.
 */

public class UserUtil {

    public static UserBean getAppUser() {
        return MyApp.getInstance().getAppUser();
    }

    public static void updateUserInfo(RxUpdateUserBean type) {
        if(type == null)
            return;
        //更新本地存储的用户信息
        UserBean userBean = MyApp.getInstance().getAppUser();
        if(userBean == null) {
            return;
        }
        if(!AwDataUtil.isEmpty(type.getMobile())) {
            userBean.setPhone(type.getMobile());
        }
        if(!AwDataUtil.isEmpty(type.getAvatar())) {
            userBean.setAvatar(type.getAvatar());
        }
        if(!AwDataUtil.isEmpty(type.getNickName())) {
            userBean.setNickName(type.getNickName());
        }
        if(!AwDataUtil.isEmpty(type.getRealName())){
            userBean.setRealName(type.getRealName());
        }
        MyApp.getInstance().saveLoginUser(userBean);
    }

    public static String getStudId() {
        if(getAppUser() != null) {
            return getAppUser().getStudId();
        }
        return "";
    }

    public static String getStudCode() {
        if(getAppUser() != null) {
            return getAppUser().getStudCode();
        }
        return "";
    }

    public static String getUserId() {
        if(getAppUser() != null) {
            return getAppUser().getId();
        }
        return "";
    }
    public static String getRoleld(){
       return AwSpUtil.getString(MyConstant.SPConstant.XML_USER_INFO, MyConstant.SPConstant.ROLE_ID, "");
    }

    public static String getClassId() {
        if(getAppUser() != null) {
            return getAppUser().getClassId();
        }
        return "";
    }

    public static String getUsername() {
        if(getAppUser() != null) {
            return getAppUser().getUsername();
        }
        return "";
    }

    public static String getNickname() {
        if(getAppUser() != null) {
            return getAppUser().getNickName();
        }
        return "";
    }

    public static String getPhone() {
        if(getAppUser() != null) {
            return getAppUser().getPhone();
        }
        return "";
    }

    public static String getAvatar() {
        if(getAppUser() != null) {
            return getAppUser().getAvatar();
        }
        return "";
    }

    public static String getStudClassId() {
        if(getAppUser() != null) {
            return getAppUser().getClassId();
        }
        return "";
    }
}
