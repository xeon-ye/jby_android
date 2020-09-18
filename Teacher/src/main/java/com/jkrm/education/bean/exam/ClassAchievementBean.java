package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/10 16:17
 * Description:
 */
public class ClassAchievementBean implements Serializable {


    /**
     * code : 200
     * data : [{"avgRank":"1","avgScore":"169.0","classId":"67574d7db09c46088ed05fa5da312eb8","className":"3班","courseId":"","courseName":"","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","goodNum":"0","goodRank":"0","goodRate":"0.0","lowNum":"0","lowRank":"0","lowRate":"0.0","maxScore":"171","minScore":"167","missing":"1","paperId":"","passNum":"0","passRank":"0","passRate":"0.0","perfectNum":"0","perfectRank":"0","perfectRate":"0.0","realNum":"7"},{"avgRank":"3","avgScore":"142.0","classId":"bf0b8650db11434195780f6f09946d71","className":"2班","courseId":"","courseName":"","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","goodNum":"0","goodRank":"0","goodRate":"0.0","lowNum":"0","lowRank":"0","lowRate":"0.0","maxScore":"146","minScore":"138","missing":"0","paperId":"","passNum":"0","passRank":"0","passRate":"0.0","perfectNum":"0","perfectRank":"0","perfectRate":"0.0","realNum":"8"},{"avgRank":"2","avgScore":"144.0","classId":"806fdcda96bf4ca69ea55882e907a327","className":"1班","courseId":"","courseName":"","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","goodNum":"0","goodRank":"0","goodRate":"0.0","lowNum":"0","lowRank":"0","lowRate":"0.0","maxScore":"180","minScore":"108","missing":"0","paperId":"","passNum":"0","passRank":"0","passRate":"0.0","perfectNum":"0","perfectRank":"0","perfectRate":"0.0","realNum":"8"}]
     * msg :
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * avgRank : 1
         * avgScore : 169.0
         * classId : 67574d7db09c46088ed05fa5da312eb8
         * className : 3班
         * courseId :
         * courseName :
         * examId : 6bfe14f69ba949bb944cdb2c3e4d63be
         * goodNum : 0
         * goodRank : 0
         * goodRate : 0.0
         * lowNum : 0
         * lowRank : 0
         * lowRate : 0.0
         * maxScore : 171
         * minScore : 167
         * missing : 1
         * paperId :
         * passNum : 0
         * passRank : 0
         * passRate : 0.0
         * perfectNum : 0
         * perfectRank : 0
         * perfectRate : 0.0
         * realNum : 7
         */

        private String avgRank;
        private String avgScore;
        private String classId;
        private String className;
        private String courseId;
        private String courseName;
        private String examId;
        private String goodNum;
        private String goodRank;
        private String goodRate;
        private String lowNum;
        private String lowRank;
        private String lowRate;
        private String maxScore;
        private String minScore;
        private String missing;
        private String paperId;
        private String passNum;
        private String passRank;
        private String passRate;
        private String perfectNum;
        private String perfectRank;
        private String perfectRate;
        private String realNum;

        public String getAvgRank() {
            return avgRank;
        }

        public void setAvgRank(String avgRank) {
            this.avgRank = avgRank;
        }

        public String getAvgScore() {
            return avgScore;
        }

        public void setAvgScore(String avgScore) {
            this.avgScore = avgScore;
        }

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

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getGoodNum() {
            return goodNum;
        }

        public void setGoodNum(String goodNum) {
            this.goodNum = goodNum;
        }

        public String getGoodRank() {
            return goodRank;
        }

        public void setGoodRank(String goodRank) {
            this.goodRank = goodRank;
        }

        public String getGoodRate() {
            return goodRate;
        }

        public void setGoodRate(String goodRate) {
            this.goodRate = goodRate;
        }

        public String getLowNum() {
            return lowNum;
        }

        public void setLowNum(String lowNum) {
            this.lowNum = lowNum;
        }

        public String getLowRank() {
            return lowRank;
        }

        public void setLowRank(String lowRank) {
            this.lowRank = lowRank;
        }

        public String getLowRate() {
            return lowRate;
        }

        public void setLowRate(String lowRate) {
            this.lowRate = lowRate;
        }

        public String getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(String maxScore) {
            this.maxScore = maxScore;
        }

        public String getMinScore() {
            return minScore;
        }

        public void setMinScore(String minScore) {
            this.minScore = minScore;
        }

        public String getMissing() {
            return missing;
        }

        public void setMissing(String missing) {
            this.missing = missing;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getPassNum() {
            return passNum;
        }

        public void setPassNum(String passNum) {
            this.passNum = passNum;
        }

        public String getPassRank() {
            return passRank;
        }

        public void setPassRank(String passRank) {
            this.passRank = passRank;
        }

        public String getPassRate() {
            return passRate;
        }

        public void setPassRate(String passRate) {
            this.passRate = passRate;
        }

        public String getPerfectNum() {
            return perfectNum;
        }

        public void setPerfectNum(String perfectNum) {
            this.perfectNum = perfectNum;
        }

        public String getPerfectRank() {
            return perfectRank;
        }

        public void setPerfectRank(String perfectRank) {
            this.perfectRank = perfectRank;
        }

        public String getPerfectRate() {
            return perfectRate;
        }

        public void setPerfectRate(String perfectRate) {
            this.perfectRate = perfectRate;
        }

        public String getRealNum() {
            return realNum;
        }

        public void setRealNum(String realNum) {
            this.realNum = realNum;
        }
    }
}
