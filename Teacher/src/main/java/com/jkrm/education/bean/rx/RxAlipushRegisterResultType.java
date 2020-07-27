package com.jkrm.education.bean.rx;

/**
 * Created by hzw on 2019/6/16.
 */

public class RxAlipushRegisterResultType {

    private boolean isRegisterSuccess;

    public RxAlipushRegisterResultType(boolean isRegisterSuccess) {
        this.isRegisterSuccess = isRegisterSuccess;
    }

    public boolean isRegisterSuccess() {
        return isRegisterSuccess;
    }

    public void setRegisterSuccess(boolean registerSuccess) {
        isRegisterSuccess = registerSuccess;
    }
}
