package com.jkrm.education.bean.result.login;

import com.jkrm.education.bean.user.UserBean;

/**
 * Created by hzw on 2019/5/13.
 */

public class UserInfoResult {

    /**
     * user : {"id":1,"account":"teacher_user","phone":"13600000000","email":"","name":"教师用户","nickName":"","sex":"男","avatar":"","school":{"id":1,"name":"济南外国语学校"},"phase":{"id":2,"name":"高中"},"course":{"id":2,"name":"语文"}}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
}
