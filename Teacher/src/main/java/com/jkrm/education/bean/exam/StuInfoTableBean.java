package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/22 10:29
 * Description:
 */
public class StuInfoTableBean implements Serializable {


    /**
     * code : 200
     * current : 1
     * data : null
     * msg :
     * pages : 1
     * rows : [{"classAvgScore":"166.0","classBeatNum":"1","classId":"bf0b8650db11434195780f6f09946d71","classMaxScore":"170","classMinScore":"","className":"2班","classRank":"1","courseId":"-1","courseName":"总分","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"172.333","gradeBeatNum":"5","gradeMaxScore":"204","gradeRank":"1","id":"154f0dbfe90346e31c18261b8591b892","jointRank":"","myScore":"170","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"99952","studExamCode":"99952","studId":"5914144896ff4ac5934c400f3969c146","studName":"学生52","studNum":"","subjectScore":""},
     * {"classAvgScore":"181.0","classBeatNum":"1","classId":"67574d7db09c46088ed05fa5da312eb8","classMaxScore":"191","classMinScore":"","className":"3班","classRank":"1","courseId":"-1","courseName":"总分","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"172.333","gradeBeatNum":"4","gradeMaxScore":"204","gradeRank":"2","id":"2b315206a32fed17ebf9eba238abc796","jointRank":"","myScore":"191","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"99003","studExamCode":"99003","studId":"e6df6b59a14e468ca6049f57e08a6836","studName":"学生3","studNum":"","subjectScore":""},
     * {"classAvgScore":"166.0","classBeatNum":"0","classId":"bf0b8650db11434195780f6f09946d71","classMaxScore":"170","classMinScore":"","className":"2班","classRank":"2","courseId":"-1","courseName":"总分","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"172.333","gradeBeatNum":"0","gradeMaxScore":"204","gradeRank":"6","id":"cf71f0b69dd7abb40f53f74b46a17f47","jointRank":"","myScore":"162","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"99002","studExamCode":"99002","studId":"790c2d764b9c40a48ff73b8176110661","studName":"学生2","studNum":"","subjectScore":""},
     * {"classAvgScore":"181.0","classBeatNum":"0","classId":"67574d7db09c46088ed05fa5da312eb8","classMaxScore":"191","classMinScore":"","className":"3班","classRank":"2","courseId":"-1","courseName":"总分","examId":"6bfe14f69ba949bb944cdb2c3e4d63be","gradeAvgScore":"172.333","gradeBeatNum":"3","gradeMaxScore":"204","gradeRank":"3","id":"f06cca6e781403e081f4d98a38ccf0b0","jointRank":"","myScore":"171","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"99953","studExamCode":"99953","studId":"a83a7e4ab8db402b882c1dfe65134925","studName":"学生53","studNum":"","subjectScore":""}]
     * size : 500
     * total : 4
     */

    private String code;
    private String current;
    private Object data;
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
         * classAvgScore : 166.0
         * classBeatNum : 1
         * classId : bf0b8650db11434195780f6f09946d71
         * classMaxScore : 170
         * classMinScore :
         * className : 2班
         * classRank : 1
         * courseId : -1
         * courseName : 总分
         * examId : 6bfe14f69ba949bb944cdb2c3e4d63be
         * gradeAvgScore : 172.333
         * gradeBeatNum : 5
         * gradeMaxScore : 204
         * gradeRank : 1
         * id : 154f0dbfe90346e31c18261b8591b892
         * jointRank :
         * myScore : 170
         * objectScore :
         * paperId : -1
         * schId : 6a349f665db2496c9e48036d770c6e40
         * sheetId :
         * studCode : 99952
         * studExamCode : 99952
         * studId : 5914144896ff4ac5934c400f3969c146
         * studName : 学生52
         * studNum :
         * subjectScore :
         */

        private String classAvgScore;
        private String classBeatNum;
        private String classId;
        private String classMaxScore;
        private String classMinScore;
        private String className;
        private String classRank;
        private String courseId;
        private String courseName;
        private String examId;
        private String gradeAvgScore;
        private String gradeBeatNum;
        private String gradeMaxScore;
        private String gradeRank;
        private String id;
        private String jointRank;
        private String myScore;
        private String objectScore;
        private String paperId;
        private String schId;
        private String sheetId;
        private String studCode;
        private String studExamCode;
        private String studId;
        private String studName;
        private String studNum;
        private String subjectScore;

        public String getClassAvgScore() {
            return classAvgScore;
        }

        public void setClassAvgScore(String classAvgScore) {
            this.classAvgScore = classAvgScore;
        }

        public String getClassBeatNum() {
            return classBeatNum;
        }

        public void setClassBeatNum(String classBeatNum) {
            this.classBeatNum = classBeatNum;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassMaxScore() {
            return classMaxScore;
        }

        public void setClassMaxScore(String classMaxScore) {
            this.classMaxScore = classMaxScore;
        }

        public String getClassMinScore() {
            return classMinScore;
        }

        public void setClassMinScore(String classMinScore) {
            this.classMinScore = classMinScore;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

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

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getGradeAvgScore() {
            return gradeAvgScore;
        }

        public void setGradeAvgScore(String gradeAvgScore) {
            this.gradeAvgScore = gradeAvgScore;
        }

        public String getGradeBeatNum() {
            return gradeBeatNum;
        }

        public void setGradeBeatNum(String gradeBeatNum) {
            this.gradeBeatNum = gradeBeatNum;
        }

        public String getGradeMaxScore() {
            return gradeMaxScore;
        }

        public void setGradeMaxScore(String gradeMaxScore) {
            this.gradeMaxScore = gradeMaxScore;
        }

        public String getGradeRank() {
            return gradeRank;
        }

        public void setGradeRank(String gradeRank) {
            this.gradeRank = gradeRank;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJointRank() {
            return jointRank;
        }

        public void setJointRank(String jointRank) {
            this.jointRank = jointRank;
        }

        public String getMyScore() {
            return myScore;
        }

        public void setMyScore(String myScore) {
            this.myScore = myScore;
        }

        public String getObjectScore() {
            return objectScore;
        }

        public void setObjectScore(String objectScore) {
            this.objectScore = objectScore;
        }

        public String getPaperId() {
            return paperId;
        }

        public void setPaperId(String paperId) {
            this.paperId = paperId;
        }

        public String getSchId() {
            return schId;
        }

        public void setSchId(String schId) {
            this.schId = schId;
        }

        public String getSheetId() {
            return sheetId;
        }

        public void setSheetId(String sheetId) {
            this.sheetId = sheetId;
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

        public String getStudNum() {
            return studNum;
        }

        public void setStudNum(String studNum) {
            this.studNum = studNum;
        }

        public String getSubjectScore() {
            return subjectScore;
        }

        public void setSubjectScore(String subjectScore) {
            this.subjectScore = subjectScore;
        }
    }
}
