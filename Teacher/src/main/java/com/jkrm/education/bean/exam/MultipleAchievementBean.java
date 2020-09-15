package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/10 15:31
 * Description:
 */
public class MultipleAchievementBean implements Serializable {


    /**
     * code : 200
     * current : 1
     * data : null
     * msg :
     * pages : 2
     * rows : [{"classId":"8B2EF0E417B747B588FBFD3C09E9D6E9","className":"2班","createBy":"","createTime":"2020-09-08T14:38:00+0800","deleteStatus":"0","examId":"5ff202e4aa744563b631ca6569066cee","id":"0050feafe61c597348f91e28adf549f0","paperId":"a81d2ed127bc446489c9b3f580027803","reaList":[{"classRank":"1","courseId":"-1","courseName":"总分","schRank":"2","score":"198"},{"classRank":"1","courseId":"2abad4627d44c059a599dcd55b40869b","courseName":"数学","schRank":"1","score":"107"},{"classRank":"1","courseId":"cbbf06bb966eca984018ba4cb434afa2","courseName":"物理","schRank":"1","score":"28"},{"classRank":"1","courseId":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语","schRank":"2","score":"27"},{"classRank":"1","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","schRank":"3","score":"36"}],"remark":"","schId":"6a349f665db2496c9e48036d770c6e40","studCode":"90011","studExamCode":"90011","studId":"83237bcd15ba4f598d803045d076d614","studName":"学生11","sxClassRank":"1","sxSchRank":"1","sxScore":"107","totalClassRank":"1","totalSchRank":"2","totalScore":"170","updateBy":"","updateTime":"2020-09-08T14:38:00+0800","wlClassRank":"1","wlSchRank":"1","wlScore":"28","ywClassRank":"1","ywSchRank":"3","ywScore":"36","yyClassRank":"1","yySchRank":"2","yyScore":"27"},{"classId":"0BBE6B5B837242FA94DDAB792B8D2DB8","className":"1班","createBy":"","createTime":"2020-09-05T14:33:00+0800","deleteStatus":"0","examId":"5f116f8972974d29a30d5375e409fdb7","id":"0539dbce6f8beae2e5a7ea654ae4a3b2","paperId":"8a11494873414d29a46d2afd05265bde","reaList":[{"classRank":"1","courseId":"-1","courseName":"总分","schRank":"1","score":"50"},{"classRank":"1","courseId":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语","schRank":"1","score":"50"}],"remark":"","schId":"6a349f665db2496c9e48036d770c6e40","studCode":"90010","studExamCode":"90010","studId":"327f34f4eabc4da0833b6534e792c23d","studName":"高三一","totalClassRank":"1","totalSchRank":"2","totalScore":"50","updateBy":"","updateTime":"2020-09-05T14:33:00+0800","yyClassRank":"1","yySchRank":"1","yyScore":"50"},{"classId":"0BBE6B5B837242FA94DDAB792B8D2DB8","className":"1班","createBy":"","createTime":"2020-09-05T12:39:00+0800","deleteStatus":"0","examId":"da18941132a642adb80e413f4dc7378c","id":"11349e92899bb81b51749cdcc6d5b4a4","paperId":"55e37df9adc74d8da5c3c9b62c7a8271","reaList":[{"classRank":"1","courseId":"-1","courseName":"总分","schRank":"1","score":"80"},{"classRank":"1","courseId":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语","schRank":"1","score":"80"}],"remark":"","schId":"6a349f665db2496c9e48036d770c6e40","studCode":"90012","studExamCode":"90012","studId":"ebcdb42a295940c4a9dbea52d4706ad4","studName":"高三三","totalClassRank":"1","totalSchRank":"1","totalScore":"47","updateBy":"","updateTime":"2020-09-05T12:39:00+0800","yyClassRank":"1","yySchRank":"1","yyScore":"47"},{"classId":"0BBE6B5B837242FA94DDAB792B8D2DB8","className":"1班","createBy":"","createTime":"2020-09-05T14:52:00+0800","deleteStatus":"0","examId":"5c72e79e91284dca88292bd40210ce12","id":"33eebc1a7aea54abe606a218b174c8ec","paperId":"c080be2aa866404781db8e08900f4862","reaList":[{"classRank":"1","courseId":"-1","courseName":"总分","schRank":"1","score":"42"},{"classRank":"2","courseId":"861fb3717ebd9889022980b5d99022b0","courseName":"数学(文)","schRank":"2","score":"42"}],"remark":"","schId":"6a349f665db2496c9e48036d770c6e40","studCode":"90012","studExamCode":"90012","studId":"ebcdb42a295940c4a9dbea52d4706ad4","studName":"高三三","sxwClassRank":"2","sxwSchRank":"2","sxwScore":"42","totalClassRank":"2","totalSchRank":"3","totalScore":"38","updateBy":"","updateTime":"2020-09-05T14:52:00+0800"}]
     */

    private String code;
    private String current;
    private Object data;
    private String msg;
    private String pages;
    private List<RowsBean> rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * classId : 8B2EF0E417B747B588FBFD3C09E9D6E9
         * className : 2班
         * createBy :
         * createTime : 2020-09-08T14:38:00+0800
         * deleteStatus : 0
         * examId : 5ff202e4aa744563b631ca6569066cee
         * id : 0050feafe61c597348f91e28adf549f0
         * paperId : a81d2ed127bc446489c9b3f580027803
         * reaList : [{"classRank":"1","courseId":"-1","courseName":"总分","schRank":"2","score":"198"},{"classRank":"1","courseId":"2abad4627d44c059a599dcd55b40869b","courseName":"数学","schRank":"1","score":"107"},{"classRank":"1","courseId":"cbbf06bb966eca984018ba4cb434afa2","courseName":"物理","schRank":"1","score":"28"},{"classRank":"1","courseId":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语","schRank":"2","score":"27"},{"classRank":"1","courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","schRank":"3","score":"36"}]
         * remark :
         * schId : 6a349f665db2496c9e48036d770c6e40
         * studCode : 90011
         * studExamCode : 90011
         * studId : 83237bcd15ba4f598d803045d076d614
         * studName : 学生11
         * sxClassRank : 1
         * sxSchRank : 1
         * sxScore : 107
         * totalClassRank : 1
         * totalSchRank : 2
         * totalScore : 170
         * updateBy :
         * updateTime : 2020-09-08T14:38:00+0800
         * wlClassRank : 1
         * wlSchRank : 1
         * wlScore : 28
         * ywClassRank : 1
         * ywSchRank : 3
         * ywScore : 36
         * yyClassRank : 1
         * yySchRank : 2
         * yyScore : 27
         * sxwClassRank : 2
         * sxwSchRank : 2
         * sxwScore : 42
         */

        private String classId;
        private String className;
        private String createBy;
        private String createTime;
        private String deleteStatus;
        private String examId;
        private String id;
        private String paperId;
        private String remark;
        private String schId;
        private String studCode;
        private String studExamCode;
        private String studId;
        private String studName;
        private String sxClassRank;
        private String sxSchRank;
        private String sxScore;
        private String totalClassRank;
        private String totalSchRank;
        private String totalScore;
        private String updateBy;
        private String updateTime;
        private String wlClassRank;
        private String wlSchRank;
        private String wlScore;
        private String ywClassRank;
        private String ywSchRank;
        private String ywScore;
        private String yyClassRank;
        private String yySchRank;
        private String yyScore;
        private String sxwClassRank;
        private String sxwSchRank;
        private String sxwScore;
        private List<ReaListBean> reaList;

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

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSchId() {
            return schId;
        }

        public void setSchId(String schId) {
            this.schId = schId;
        }

        public String getStudCode() {
            return studCode;
        }

        public void setStudCode(String studCode) {
            this.studCode = studCode;
        }

        public String getStudExamCode() {
            return studExamCode;
        }

        public void setStudExamCode(String studExamCode) {
            this.studExamCode = studExamCode;
        }

        public String getStudId() {
            return studId;
        }

        public void setStudId(String studId) {
            this.studId = studId;
        }

        public String getStudName() {
            return studName;
        }

        public void setStudName(String studName) {
            this.studName = studName;
        }

        public String getSxClassRank() {
            return sxClassRank;
        }

        public void setSxClassRank(String sxClassRank) {
            this.sxClassRank = sxClassRank;
        }

        public String getSxSchRank() {
            return sxSchRank;
        }

        public void setSxSchRank(String sxSchRank) {
            this.sxSchRank = sxSchRank;
        }

        public String getSxScore() {
            return sxScore;
        }

        public void setSxScore(String sxScore) {
            this.sxScore = sxScore;
        }

        public String getTotalClassRank() {
            return totalClassRank;
        }

        public void setTotalClassRank(String totalClassRank) {
            this.totalClassRank = totalClassRank;
        }

        public String getTotalSchRank() {
            return totalSchRank;
        }

        public void setTotalSchRank(String totalSchRank) {
            this.totalSchRank = totalSchRank;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getWlClassRank() {
            return wlClassRank;
        }

        public void setWlClassRank(String wlClassRank) {
            this.wlClassRank = wlClassRank;
        }

        public String getWlSchRank() {
            return wlSchRank;
        }

        public void setWlSchRank(String wlSchRank) {
            this.wlSchRank = wlSchRank;
        }

        public String getWlScore() {
            return wlScore;
        }

        public void setWlScore(String wlScore) {
            this.wlScore = wlScore;
        }

        public String getYwClassRank() {
            return ywClassRank;
        }

        public void setYwClassRank(String ywClassRank) {
            this.ywClassRank = ywClassRank;
        }

        public String getYwSchRank() {
            return ywSchRank;
        }

        public void setYwSchRank(String ywSchRank) {
            this.ywSchRank = ywSchRank;
        }

        public String getYwScore() {
            return ywScore;
        }

        public void setYwScore(String ywScore) {
            this.ywScore = ywScore;
        }

        public String getYyClassRank() {
            return yyClassRank;
        }

        public void setYyClassRank(String yyClassRank) {
            this.yyClassRank = yyClassRank;
        }

        public String getYySchRank() {
            return yySchRank;
        }

        public void setYySchRank(String yySchRank) {
            this.yySchRank = yySchRank;
        }

        public String getYyScore() {
            return yyScore;
        }

        public void setYyScore(String yyScore) {
            this.yyScore = yyScore;
        }

        public String getSxwClassRank() {
            return sxwClassRank;
        }

        public void setSxwClassRank(String sxwClassRank) {
            this.sxwClassRank = sxwClassRank;
        }

        public String getSxwSchRank() {
            return sxwSchRank;
        }

        public void setSxwSchRank(String sxwSchRank) {
            this.sxwSchRank = sxwSchRank;
        }

        public String getSxwScore() {
            return sxwScore;
        }

        public void setSxwScore(String sxwScore) {
            this.sxwScore = sxwScore;
        }

        public List<ReaListBean> getReaList() {
            return reaList;
        }

        public void setReaList(List<ReaListBean> reaList) {
            this.reaList = reaList;
        }

        public static class ReaListBean {
            /**
             * classRank : 1
             * courseId : -1
             * courseName : 总分
             * schRank : 2
             * score : 198
             */

            private String classRank;
            private String courseId;
            private String courseName;
            private String schRank;
            private String score;

            public String getClassRank() {
                return classRank;
            }

            public void setClassRank(String classRank) {
                this.classRank = classRank;
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

            public String getSchRank() {
                return schRank;
            }

            public void setSchRank(String schRank) {
                this.schRank = schRank;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }
        }
    }
}
