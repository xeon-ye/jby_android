package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/10 16:17
 * Description:
 */
public class SectionAchievementBean implements Serializable {


    /**
     * code : 200
     * current : 1
     * data : 0,50_50,100_100,150_150,200_200,250_250,300_300,350_350,400_400,450_450,500_500,550_550,557
     * msg :
     * pages : 1
     * rows : [{"classId":"67574d7db09c46088ed05fa5da312eb8","className":"3班","courseId":"","courseName":"","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","joinNum":"8","list":[{"orderBy":"0","studNum":"0","studRate":"0.0","studScale":"0,50"},{"orderBy":"1","studNum":"0","studRate":"0.0","studScale":"50,100"},{"orderBy":"2","studNum":"0","studRate":"0.0","studScale":"100,150"},{"orderBy":"3","studNum":"2","studRate":"1.0","studScale":"150,200"},{"orderBy":"4","studNum":"0","studRate":"0.0","studScale":"200,250"},{"orderBy":"5","studNum":"0","studRate":"0.0","studScale":"250,300"},{"orderBy":"6","studNum":"0","studRate":"0.0","studScale":"300,350"},{"orderBy":"7","studNum":"0","studRate":"0.0","studScale":"350,400"},{"orderBy":"8","studNum":"0","studRate":"0.0","studScale":"400,450"},{"orderBy":"9","studNum":"0","studRate":"0.0","studScale":"450,500"},{"orderBy":"10","studNum":"0","studRate":"0.0","studScale":"500,550"},{"orderBy":"11","studNum":"0","studRate":"0.0","studScale":"550,557"}],"paperId":""},{"classId":"bf0b8650db11434195780f6f09946d71","className":"2班","courseId":"","courseName":"","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","joinNum":"8",
     * "list":[{"orderBy":"0","studNum":"0","studRate":"0.0","studScale":"0,50"},{"orderBy":"1","studNum":"0","studRate":"0.0","studScale":"50,100"},{"orderBy":"2","studNum":"2","studRate":"1.0","studScale":"100,150"},{"orderBy":"3","studNum":"0","studRate":"0.0","studScale":"150,200"},{"orderBy":"4","studNum":"0","studRate":"0.0","studScale":"200,250"},{"orderBy":"5","studNum":"0","studRate":"0.0","studScale":"250,300"},{"orderBy":"6","studNum":"0","studRate":"0.0","studScale":"300,350"},{"orderBy":"7","studNum":"0","studRate":"0.0","studScale":"350,400"},{"orderBy":"8","studNum":"0","studRate":"0.0","studScale":"400,450"},{"orderBy":"9","studNum":"0","studRate":"0.0","studScale":"450,500"},{"orderBy":"10","studNum":"0","studRate":"0.0","studScale":"500,550"},{"orderBy":"11","studNum":"0","studRate":"0.0","studScale":"550,557"}],"paperId":""},{"classId":"806fdcda96bf4ca69ea55882e907a327","className":"1班","courseId":"","courseName":"","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","joinNum":"8","list":[{"orderBy":"0","studNum":"0","studRate":"0.0","studScale":"0,50"},{"orderBy":"1","studNum":"0","studRate":"0.0","studScale":"50,100"},{"orderBy":"2","studNum":"1","studRate":"0.5","studScale":"100,150"},
     * {"orderBy":"3","studNum":"1","studRate":"0.5","studScale":"150,200"},
     * {"orderBy":"4","studNum":"0","studRate":"0.0","studScale":"200,250"},{"orderBy":"5","studNum":"0","studRate":"0.0","studScale":"250,300"},{"orderBy":"6","studNum":"0","studRate":"0.0","studScale":"300,350"},{"orderBy":"7","studNum":"0","studRate":"0.0","studScale":"350,400"},{"orderBy":"8","studNum":"0","studRate":"0.0","studScale":"400,450"},{"orderBy":"9","studNum":"0","studRate":"0.0","studScale":"450,500"},{"orderBy":"10","studNum":"0","studRate":"0.0","studScale":"500,550"},{"orderBy":"11","studNum":"0","studRate":"0.0","studScale":"550,557"}],"paperId":""}]
     * size : 500
     * total : 3
     */

    private String code;
    private String current;
    private String data;
    private String msg;
    private String pages;
    private String size;
    private String total;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * classId : 67574d7db09c46088ed05fa5da312eb8
         * className : 3班
         * courseId :
         * courseName :
         * examId : 6bfe14f69ba949bb944cdb2c3e4d63be
         * joinNum : 8
         * list : [{"orderBy":"0","studNum":"0","studRate":"0.0","studScale":"0,50"},{"orderBy":"1","studNum":"0","studRate":"0.0","studScale":"50,100"},{"orderBy":"2","studNum":"0","studRate":"0.0","studScale":"100,150"},{"orderBy":"3","studNum":"2","studRate":"1.0","studScale":"150,200"},{"orderBy":"4","studNum":"0","studRate":"0.0","studScale":"200,250"},{"orderBy":"5","studNum":"0","studRate":"0.0","studScale":"250,300"},{"orderBy":"6","studNum":"0","studRate":"0.0","studScale":"300,350"},{"orderBy":"7","studNum":"0","studRate":"0.0","studScale":"350,400"},{"orderBy":"8","studNum":"0","studRate":"0.0","studScale":"400,450"},{"orderBy":"9","studNum":"0","studRate":"0.0","studScale":"450,500"},{"orderBy":"10","studNum":"0","studRate":"0.0","studScale":"500,550"},{"orderBy":"11","studNum":"0","studRate":"0.0","studScale":"550,557"}]
         * paperId :
         */

        private String classId;
        private String className;
        private String courseId;
        private String courseName;
        private String examId;
        private String joinNum;
        private String paperId;
        private List<ListBean> list;

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

        public String getJoinNum() {
            return joinNum;
        }

        public void setJoinNum(String joinNum) {
            this.joinNum = joinNum;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * orderBy : 0
             * studNum : 0
             * studRate : 0.0
             * studScale : 0,50
             */

            private String orderBy;
            private String studNum;
            private String studRate;
            private String studScale;

            public String getOrderBy() {
                return orderBy;
            }

            public void setOrderBy(String orderBy) {
                this.orderBy = orderBy;
            }

            public String getStudNum() {
                return studNum;
            }

            public void setStudNum(String studNum) {
                this.studNum = studNum;
            }

            public String getStudRate() {
                return studRate;
            }

            public void setStudRate(String studRate) {
                this.studRate = studRate;
            }

            public String getStudScale() {
                return studScale;
            }

            public void setStudScale(String studScale) {
                this.studScale = studScale;
            }
        }
    }
}
