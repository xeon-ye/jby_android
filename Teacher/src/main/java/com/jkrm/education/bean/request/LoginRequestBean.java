package com.jkrm.education.bean.request;

/**
 * Created by hzw on 2019/4/25.
 */

public class LoginRequestBean {

    private String account;
    private String password;

    public LoginRequestBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
