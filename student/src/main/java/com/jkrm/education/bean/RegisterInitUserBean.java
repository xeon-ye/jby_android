package com.jkrm.education.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/11 20:09
 */

public class RegisterInitUserBean {


    /**
     * code : 200
     * data : {"typeInfo":"1","school":{"name":"崇文门中学","id":"100058"},"roleId":"role_student_100058","schoolId":"100058","roleName":"学生","userType":"1","menus":[{"subMenu":[{"icon":"iconzuoye","menuId":"31_1","menuName":"作业","menuType":"2","routeName":"student_homework_center"},{"icon":"iconzuoyecuotiben","menuId":"31_2","menuName":"作业错题本","menuType":"2","routeName":"student_homework_mistakes_notebook"},{"menuId":"31_4","menuName":"查看报告","menuType":"2","routeName":"exam_center_report"},{"menuId":"31_5","menuName":"考试错题本","menuType":"2","routeName":"exam_center_mistake"}],"menuId":"31","menuName":"我的作业","menuType":"2","routeName":"student_homework_center"}],"user":{"gradeName":"","nickName":"","sex":"0","className":"","avatar":"","schoolPhase":"","classId":"","schoolPhaseId":"","teacherId":"","phone":"18410179636","school":"","course":"","id":"51c511f4c58d2caf79a1ac907cb34f2c","courseId":"","studId":"","username":"18410179636","studCode":""},"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ7XCJpZFwiOlwiNTFjNTExZjRjNThkMmNhZjc5YTFhYzkwN2NiMzRmMmNcIixcInVzZXJuYW1lXCI6XCIxODQxMDE3OTYzNlwiLFwibmlja05hbWVcIjpudWxsLFwicmVhbE5hbWVcIjpcIjE4NDEwMTc5NjM2XCIsXCJwaG9uZVwiOlwiMTg0MTAxNzk2MzZcIixcInNleFwiOjAsXCJhdmF0YXJcIjpudWxsLFwicm9sZUlkXCI6bnVsbCxcInNjaG9vbFwiOntcImlkXCI6XCIxMDAwNThcIixcIm5hbWVcIjpcIuW0h-aWh-mXqOS4reWtplwifSxcInNjaG9vbFBoYXNlXCI6bnVsbCxcInNjaG9vbFBoYXNlSWRcIjpudWxsLFwiY291cnNlXCI6bnVsbCxcImNvdXJzZUlkXCI6bnVsbCxcInRlYWNoZXJJZFwiOm51bGwsXCJzdHVkSWRcIjpudWxsLFwiYXBwXCI6bnVsbCxcImNsaWVudFwiOm51bGwsXCJjbGllbnRJcFwiOlwiMTE0LjI0MC44Ny4xOThcIixcImNsaWVudFNlc3Npb25JZFwiOlwiMTE0LjI0MC44Ny4xOThcIixcIm9yaWdpblwiOm51bGwsXCJ0eXBlSW5mb1wiOjF9IiwiZXhwIjoxNTk1MjEzOTk1fQ.zmm44WOG_YPYc4uUkR8ijJvX0B0l9HPJ8mncGukJhXk"}
     * msg :
     */


        /**
         * typeInfo : 1
         * school : {"name":"崇文门中学","id":"100058"}
         * roleId : role_student_100058
         * schoolId : 100058
         * roleName : 学生
         * userType : 1
         * menus : [{"subMenu":[{"icon":"iconzuoye","menuId":"31_1","menuName":"作业","menuType":"2","routeName":"student_homework_center"},{"icon":"iconzuoyecuotiben","menuId":"31_2","menuName":"作业错题本","menuType":"2","routeName":"student_homework_mistakes_notebook"},{"menuId":"31_4","menuName":"查看报告","menuType":"2","routeName":"exam_center_report"},{"menuId":"31_5","menuName":"考试错题本","menuType":"2","routeName":"exam_center_mistake"}],"menuId":"31","menuName":"我的作业","menuType":"2","routeName":"student_homework_center"}]
         * user : {"gradeName":"","nickName":"","sex":"0","className":"","avatar":"","schoolPhase":"","classId":"","schoolPhaseId":"","teacherId":"","phone":"18410179636","school":"","course":"","id":"51c511f4c58d2caf79a1ac907cb34f2c","courseId":"","studId":"","username":"18410179636","studCode":""}
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ7XCJpZFwiOlwiNTFjNTExZjRjNThkMmNhZjc5YTFhYzkwN2NiMzRmMmNcIixcInVzZXJuYW1lXCI6XCIxODQxMDE3OTYzNlwiLFwibmlja05hbWVcIjpudWxsLFwicmVhbE5hbWVcIjpcIjE4NDEwMTc5NjM2XCIsXCJwaG9uZVwiOlwiMTg0MTAxNzk2MzZcIixcInNleFwiOjAsXCJhdmF0YXJcIjpudWxsLFwicm9sZUlkXCI6bnVsbCxcInNjaG9vbFwiOntcImlkXCI6XCIxMDAwNThcIixcIm5hbWVcIjpcIuW0h-aWh-mXqOS4reWtplwifSxcInNjaG9vbFBoYXNlXCI6bnVsbCxcInNjaG9vbFBoYXNlSWRcIjpudWxsLFwiY291cnNlXCI6bnVsbCxcImNvdXJzZUlkXCI6bnVsbCxcInRlYWNoZXJJZFwiOm51bGwsXCJzdHVkSWRcIjpudWxsLFwiYXBwXCI6bnVsbCxcImNsaWVudFwiOm51bGwsXCJjbGllbnRJcFwiOlwiMTE0LjI0MC44Ny4xOThcIixcImNsaWVudFNlc3Npb25JZFwiOlwiMTE0LjI0MC44Ny4xOThcIixcIm9yaWdpblwiOm51bGwsXCJ0eXBlSW5mb1wiOjF9IiwiZXhwIjoxNTk1MjEzOTk1fQ.zmm44WOG_YPYc4uUkR8ijJvX0B0l9HPJ8mncGukJhXk
         */

        private String typeInfo;
        private SchoolBean school;
        private String roleId;
        private String schoolId;
        private String roleName;
        private String userType;
        private UserBean user;
        private String token;
        private List<MenusBean> menus;

        public String getTypeInfo() {
            return typeInfo;
        }

        public void setTypeInfo(String typeInfo) {
            this.typeInfo = typeInfo;
        }

        public SchoolBean getSchool() {
            return school;
        }

        public void setSchool(SchoolBean school) {
            this.school = school;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
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

        public static class SchoolBean {
            /**
             * name : 崇文门中学
             * id : 100058
             */

            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class UserBean {
            /**
             * gradeName :
             * nickName :
             * sex : 0
             * className :
             * avatar :
             * schoolPhase :
             * classId :
             * schoolPhaseId :
             * teacherId :
             * phone : 18410179636
             * school :
             * course :
             * id : 51c511f4c58d2caf79a1ac907cb34f2c
             * courseId :
             * studId :
             * username : 18410179636
             * studCode :
             */

            private String gradeName;
            private String nickName;
            private String sex;
            private String className;
            private String avatar;
            private String schoolPhase;
            private String classId;
            private String schoolPhaseId;
            private String teacherId;
            private String phone;
            private String school;
            private String course;
            private String id;
            private String courseId;
            private String studId;
            private String username;
            private String studCode;

            public String getGradeName() {
                return gradeName;
            }

            public void setGradeName(String gradeName) {
                this.gradeName = gradeName;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSchoolPhase() {
                return schoolPhase;
            }

            public void setSchoolPhase(String schoolPhase) {
                this.schoolPhase = schoolPhase;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getSchoolPhaseId() {
                return schoolPhaseId;
            }

            public void setSchoolPhaseId(String schoolPhaseId) {
                this.schoolPhaseId = schoolPhaseId;
            }

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSchool() {
                return school;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public String getCourse() {
                return course;
            }

            public void setCourse(String course) {
                this.course = course;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getStudId() {
                return studId;
            }

            public void setStudId(String studId) {
                this.studId = studId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getStudCode() {
                return studCode;
            }

            public void setStudCode(String studCode) {
                this.studCode = studCode;
            }
        }

        public static class MenusBean {
            /**
             * subMenu : [{"icon":"iconzuoye","menuId":"31_1","menuName":"作业","menuType":"2","routeName":"student_homework_center"},{"icon":"iconzuoyecuotiben","menuId":"31_2","menuName":"作业错题本","menuType":"2","routeName":"student_homework_mistakes_notebook"},{"menuId":"31_4","menuName":"查看报告","menuType":"2","routeName":"exam_center_report"},{"menuId":"31_5","menuName":"考试错题本","menuType":"2","routeName":"exam_center_mistake"}]
             * menuId : 31
             * menuName : 我的作业
             * menuType : 2
             * routeName : student_homework_center
             */

            private String menuId;
            private String menuName;
            private String menuType;
            private String routeName;
            private List<SubMenuBean> subMenu;

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

            public String getMenuType() {
                return menuType;
            }

            public void setMenuType(String menuType) {
                this.menuType = menuType;
            }

            public String getRouteName() {
                return routeName;
            }

            public void setRouteName(String routeName) {
                this.routeName = routeName;
            }

            public List<SubMenuBean> getSubMenu() {
                return subMenu;
            }

            public void setSubMenu(List<SubMenuBean> subMenu) {
                this.subMenu = subMenu;
            }

            public static class SubMenuBean {
                /**
                 * icon : iconzuoye
                 * menuId : 31_1
                 * menuName : 作业
                 * menuType : 2
                 * routeName : student_homework_center
                 */

                private String icon;
                private String menuId;
                private String menuName;
                private String menuType;
                private String routeName;

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

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

                public String getMenuType() {
                    return menuType;
                }

                public void setMenuType(String menuType) {
                    this.menuType = menuType;
                }

                public String getRouteName() {
                    return routeName;
                }

                public void setRouteName(String routeName) {
                    this.routeName = routeName;
                }
            }
        }
}
