package com.jkrm.education.bean.result;

/**
 * Created by hzw on 2019/6/19.
 */

public class StatisticsErrorQuestionRatioResultBean {


    /**
     * classId : 1
     * className : 初一(1)班
     * correctNum : 1356
     * ctime : 2019-06-18
     * mistakeRatio : 0.98
     * studentId :
     * studentName :
     */

    private String classId;
    private String className;
    private String correctNum;
    private String ctime;
    private String mistakeRatio;
    private String studentId;
    private String studentName;

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

    public String getCorrectNum() {
        return correctNum;
    }

    public void setCorrectNum(String correctNum) {
        this.correctNum = correctNum;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getMistakeRatio() {
        return mistakeRatio;
    }

    public void setMistakeRatio(String mistakeRatio) {
        this.mistakeRatio = mistakeRatio;
    }

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
