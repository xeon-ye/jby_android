package com.jkrm.education.bean;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 11:07
 */

public class ErrorQuestionBean {

        /**
         * courseId : 60e06b3ccd9ff6c4bc754e265c8cd6cb
         * courseName : 历史与社会
         * dataMsg : http://dist.xinjiaoyu.com/app/course/lishiyushehui.png
         * errorNum : 0
         * templateId :
         * titleName :
         */

        private String courseId;
        private String courseName;
        private String dataMsg;
        private String errorNum;
        private String templateId;
        private String titleName;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getDataMsg() {
            return dataMsg;
        }

        public void setDataMsg(String dataMsg) {
            this.dataMsg = dataMsg;
        }

        public String getErrorNum() {
            return errorNum;
        }

        public void setErrorNum(String errorNum) {
            this.errorNum = errorNum;
        }

        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }
}
