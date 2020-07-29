package com.hzw.baselib.bean;

public class ClassesResponseBean {

    /**
     * code : 200
     * data : [{"classId":"4dca54f3b8ad4522a95d72f18caf1c6e","className":"七年级5班","homeId":"1595927489703pS449"}]
     * msg :
     */

        /**
         * classId : 4dca54f3b8ad4522a95d72f18caf1c6e
         * className : 七年级5班
         * homeId : 1595927489703pS449
         */

        private String classId;
        private String className;
        private String homeId;

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getHomeId() {
            return homeId;
        }

        public void setHomeId(String homeId) {
            this.homeId = homeId;
        }
}
