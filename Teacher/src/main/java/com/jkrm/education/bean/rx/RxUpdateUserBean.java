package com.jkrm.education.bean.rx;

/**
 * Created by hzw on 2019/7/22.
 */

public class RxUpdateUserBean {

    private String mobile;
    private String avatar;
    private String nickName;
    private String realName;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public RxUpdateUserBean(String mobile, String avatar, String nickName) {
        this.mobile = mobile;
        this.avatar = avatar;
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
