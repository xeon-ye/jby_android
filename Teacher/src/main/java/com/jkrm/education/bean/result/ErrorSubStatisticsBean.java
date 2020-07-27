package com.jkrm.education.bean.result;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/27 18:06
 */

public class ErrorSubStatisticsBean {

    /**
     * code : 200
     * data : [{"cnt":"3","duration":"满分","populatNum":"","sbumitNum":"","studList":[{"studentId":"ef46f9600b449b35146b97548203929c","studentName":"陈夏怡"},{"studentId":"5a4c4f4c0518a167a1f4eabffda77a72","studentName":"高奇"},{"studentId":"ac0bbae51a78fb46cd00c183a4f7dedd","studentName":"赵娅君"}]},{"cnt":"0","duration":"优","populatNum":"","sbumitNum":""},{"cnt":"0","duration":"良","populatNum":"","sbumitNum":""},{"cnt":"0","duration":"差","populatNum":"","sbumitNum":""},{"cnt":"1","duration":"零分","populatNum":"","sbumitNum":"","studList":[{"studentId":"64b91bdb744e8cddc14af28ca6e6f19f","studentName":"王明惠"}]}]
     * msg :
     */


        /**
         * cnt : 3
         * duration : 满分
         * populatNum :
         * sbumitNum :
         * studList : [{"studentId":"ef46f9600b449b35146b97548203929c","studentName":"陈夏怡"},{"studentId":"5a4c4f4c0518a167a1f4eabffda77a72","studentName":"高奇"},{"studentId":"ac0bbae51a78fb46cd00c183a4f7dedd","studentName":"赵娅君"}]
         */

        private String cnt;
        private String duration;
        private String populatNum;
        private String sbumitNum;
        private List<StudListBean> studList;

        public String getCnt() {
            return cnt;
        }

        public void setCnt(String cnt) {
            this.cnt = cnt;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPopulatNum() {
            return populatNum;
        }

        public void setPopulatNum(String populatNum) {
            this.populatNum = populatNum;
        }

        public String getSbumitNum() {
            return sbumitNum;
        }

        public void setSbumitNum(String sbumitNum) {
            this.sbumitNum = sbumitNum;
        }

        public List<StudListBean> getStudList() {
            return studList;
        }

        public void setStudList(List<StudListBean> studList) {
            this.studList = studList;
        }

        public static class StudListBean {
            /**
             * studentId : ef46f9600b449b35146b97548203929c
             * studentName : 陈夏怡
             */

            private String studentId;
            private String studentName;

            public String getStudentId() {
                return studentId;
            }

            public void setStudentId(String studentId) {
                this.studentId = studentId;
            }

            public String getStudentName() {
                return studentName;
            }

            public void setStudentName(String studentName) {
                this.studentName = studentName;
            }
        }
}
