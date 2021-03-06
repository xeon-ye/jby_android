package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/29 20:02
 * Description:
 */
public class StuSectionTableBean implements Serializable {

    /**
     * code : 200
     * current : 1
     * data : null
     * msg :
     * pages : 1
     * rows : [{"classAvgScore":"83.0","classBeatNum":"1","classId":"FF261AA764E541A1B4D89B26D68A3941","classMaxScore":"87","classMinScore":"","className":"1班","classRank":"4","courseId":"-1","courseName":"总分","examId":"941ab316cc5b40898623dec1989c67f8","gradeAvgScore":"84.8","gradeBeatNum":"2","gradeMaxScore":"98","gradeRank":"8","id":"33a9b0999e8e79b01c5e052692400ff4","jointRank":"","myScore":"80","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"97853","studExamCode":"97853","studId":"4a4fa66de9c34438ac4b1c25f66d43c0","studName":"高一3","studNum":"","subjectScore":""},{"classAvgScore":"83.0","classBeatNum":"3","classId":"FF261AA764E541A1B4D89B26D68A3941","classMaxScore":"87","classMinScore":"","className":"1班","classRank":"2","courseId":"-1","courseName":"总分","examId":"941ab316cc5b40898623dec1989c67f8","gradeAvgScore":"84.8","gradeBeatNum":"6","gradeMaxScore":"98","gradeRank":"4","id":"9fe58bc06f4f2bc40e87367903cdf6e2","jointRank":"","myScore":"86","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"97870","studExamCode":"97870","studId":"dbd54ffad9544e098e7200d9f6fda936","studName":"高一20","studNum":"","subjectScore":""},{"classAvgScore":"83.0","classBeatNum":"2","classId":"FF261AA764E541A1B4D89B26D68A3941","classMaxScore":"87","classMinScore":"","className":"1班","classRank":"3","courseId":"-1","courseName":"总分","examId":"941ab316cc5b40898623dec1989c67f8","gradeAvgScore":"84.8","gradeBeatNum":"3","gradeMaxScore":"98","gradeRank":"7","id":"bd68c0bacb52207ee8d5629c446b352d","jointRank":"","myScore":"82","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"97872","studExamCode":"97872","studId":"2f15596ee2904019bc8de46a401f1129","studName":"高一22","studNum":"","subjectScore":""},{"classAvgScore":"83.0","classBeatNum":"4","classId":"FF261AA764E541A1B4D89B26D68A3941","classMaxScore":"87","classMinScore":"","className":"1班","classRank":"1","courseId":"-1","courseName":"总分","examId":"941ab316cc5b40898623dec1989c67f8","gradeAvgScore":"84.8","gradeBeatNum":"7","gradeMaxScore":"98","gradeRank":"3","id":"e1174d86e3fd365dce0603a441db8770","jointRank":"","myScore":"87","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"97871","studExamCode":"97871","studId":"619822616ef440c2ad2e904fd669e34f","studName":"高一21","studNum":"","subjectScore":""},{"classAvgScore":"83.0","classBeatNum":"1","classId":"FF261AA764E541A1B4D89B26D68A3941","classMaxScore":"87","classMinScore":"","className":"1班","classRank":"4","courseId":"-1","courseName":"总分","examId":"941ab316cc5b40898623dec1989c67f8","gradeAvgScore":"84.8","gradeBeatNum":"2","gradeMaxScore":"98","gradeRank":"8","id":"e5de57821c889ef930cd1a27071121a9","jointRank":"","myScore":"80","objectScore":"","paperId":"-1","schId":"6a349f665db2496c9e48036d770c6e40","sheetId":"","studCode":"97873","studExamCode":"97873","studId":"9bca755507b944a7925cd461537113cf","studName":"高一23","studNum":"","subjectScore":""}]
     * size : 500
     * total : 5
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
         * classAvgScore : 83.0
         * classBeatNum : 1
         * classId : FF261AA764E541A1B4D89B26D68A3941
         * classMaxScore : 87
         * classMinScore :
         * className : 1班
         * classRank : 4
         * courseId : -1
         * courseName : 总分
         * examId : 941ab316cc5b40898623dec1989c67f8
         * gradeAvgScore : 84.8
         * gradeBeatNum : 2
         * gradeMaxScore : 98
         * gradeRank : 8
         * id : 33a9b0999e8e79b01c5e052692400ff4
         * jointRank :
         * myScore : 80
         * objectScore :
         * paperId : -1
         * schId : 6a349f665db2496c9e48036d770c6e40
         * sheetId :
         * studCode : 97853
         * studExamCode : 97853
         * studId : 4a4fa66de9c34438ac4b1c25f66d43c0
         * studName : 高一3
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
