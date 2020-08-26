package com.jkrm.education.bean;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/26 15:51
 */

public class TaskBean {

    private List<Bean> list;

    public List<Bean> getList() {
        return list;
    }

    public void setList(List<Bean> list) {
        this.list = list;
    }

    public static class Bean{
        private String classTeacherId;
        private int compReadNum;
        private String courseId;
        private String courseName;
        private String examEndTime;
        private String examId;
        private String examName;
        private String  id;
        private String isRead;
        private String noCompReadNum;
        private String paperManageId;
        private String progress;
        private String readPattern;
        private String readTeacherId;
        private String schId;
        private String shortName;
        private String examStartTime;

        public String getExamStartTime() {
            return examStartTime;
        }

        public void setExamStartTime(String examStartTime) {
            this.examStartTime = examStartTime;
        }

        public String getClassTeacherId() {
            return classTeacherId;
        }

        public void setClassTeacherId(String classTeacherId) {
            this.classTeacherId = classTeacherId;
        }

        public int getCompReadNum() {
            return compReadNum;
        }

        public void setCompReadNum(int compReadNum) {
            this.compReadNum = compReadNum;
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

        public String getExamEndTime() {
            return examEndTime;
        }

        public void setExamEndTime(String examEndTime) {
            this.examEndTime = examEndTime;
        }

        public String getExamId() {
            return examId;
        }

        public void setExamId(String examId) {
            this.examId = examId;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getNoCompReadNum() {
            return noCompReadNum;
        }

        public void setNoCompReadNum(String noCompReadNum) {
            this.noCompReadNum = noCompReadNum;
        }

        public String getPaperManageId() {
            return paperManageId;
        }

        public void setPaperManageId(String paperManageId) {
            this.paperManageId = paperManageId;
        }

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getReadPattern() {
            return readPattern;
        }

        public void setReadPattern(String readPattern) {
            this.readPattern = readPattern;
        }

        public String getReadTeacherId() {
            return readTeacherId;
        }

        public void setReadTeacherId(String readTeacherId) {
            this.readTeacherId = readTeacherId;
        }

        public String getSchId() {
            return schId;
        }

        public void setSchId(String schId) {
            this.schId = schId;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }

}
