package com.jkrm.education.bean.result.login;

import java.util.List;

/**
 * Created by hzw on 2019/5/6.
 */

public class UserMenuBean {


    private List<MenusBean> menus;

    public List<MenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenusBean> menus) {
        this.menus = menus;
    }

    public static class MenusBean {
        /**
         * name : 首页
         * router_name : teacher_dashboard
         * subMenu : []
         */

        private String name;
        private String router_name;
        private List<?> subMenu;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRouter_name() {
            return router_name;
        }

        public void setRouter_name(String router_name) {
            this.router_name = router_name;
        }

        public List<?> getSubMenu() {
            return subMenu;
        }

        public void setSubMenu(List<?> subMenu) {
            this.subMenu = subMenu;
        }
    }
}
