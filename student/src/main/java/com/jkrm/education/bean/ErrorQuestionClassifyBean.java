package com.jkrm.education.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/3 11:58
 */

public class ErrorQuestionClassifyBean implements Serializable {

        /**
         * monthy : 11
         * reaList : [{"courseId":"","courseName":"","dataMsg":"","errorNum":"5","templateId":"mxzy-201909010013040134","titleName":"配餐34　共点力的平衡(一)"}]
         */

        private String monthy;
        private List<ReaListBean> reaList;

        public String getMonthy() {
            return monthy;
        }

        public void setMonthy(String monthy) {
            this.monthy = monthy;
        }

        public List<ReaListBean> getReaList() {
            return reaList;
        }

        public void setReaList(List<ReaListBean> reaList) {
            this.reaList = reaList;
        }

        public static class ReaListBean implements Serializable{
            /**
             * courseId :
             * courseName :
             * dataMsg :
             * errorNum : 5
             * templateId : mxzy-201909010013040134
             * titleName : 配餐34　共点力的平衡(一)
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
}
