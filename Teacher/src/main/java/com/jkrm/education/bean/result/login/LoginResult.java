package com.jkrm.education.bean.result.login;

import com.jkrm.education.bean.user.UserBean;

import java.util.List;

/**
 * Created by hzw on 2019/5/6.
 */

public class LoginResult {


    /**
     * roleId : role_student
     * roleName : 学生
     * userType : 1
     * menus : [{"subMenu":[],"menuId":"30","menuName":"首页","routeName":"student_dashboard"},{"subMenu":[{"icon":"iconzuoye","menuId":"31_1","menuName":"作业","routeName":"student_homework_center"},{"icon":"iconzuoyecuotiben","menuId":"31_2","menuName":"作业错题本","routeName":"student_homework_mistakes_notebook"}],"menuId":"31","menuName":"我的作业","routeName":"student_homework_center"},{"subMenu":[],"menuId":"32","menuName":"个人中心","routeName":"student_user_center"}]
     * user : {"address":"","areaId":"","avatar":"","createBy":"","createTime":"2019-05-28T16:01:10+0800","deleteStatus":"0","email":"","id":"user_001","idCard":"","namePinyin":"","nickName":"赵依娜","phaseBasicsId":"","phaseBasicsName":"","phone":"","position":"","qq":"","realName":"赵依娜","remark":"","schoolId":"","schoolName":"","staffNo":"","subjectBasicsId":"","subjectBasicsName":"","updateBy":"","userType":"1","username":"stu_001","weiBo":"","weiXin":""}
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTkwMzc2NzAsInVzZXJfbmFtZSI6InN0dV8wMDEiLCJqdGkiOiJiNzU4ZWNhZC1mMWY1LTRmYzctOTQ5Ni1kNmI3ZjNlZmI2NGQiLCJjbGllbnRfaWQiOiJ3ZWIiLCJzY29wZSI6WyJyZWFkIl19.OwYt_pH_nUZR9sV2OSd9gHuILCMLXH85gOaYBLpZ-d0
     */

    private String roleId;
    private String roleName;
    private String userType;
    private UserBean user;
    private String token;
    private List<MenusBean> menus;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenusBean> menus) {
        this.menus = menus;
    }


    public static class MenusBean {
        /**
         * subMenu : []
         * menuId : 30
         * menuName : 首页
         * routeName : student_dashboard
         */

        private String menuId;
        private String menuName;
        private String routeName;
        private List<?> subMenu;

        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getRouteName() {
            return routeName;
        }

        public void setRouteName(String routeName) {
            this.routeName = routeName;
        }

        public List<?> getSubMenu() {
            return subMenu;
        }

        public void setSubMenu(List<?> subMenu) {
            this.subMenu = subMenu;
        }
    }
}
