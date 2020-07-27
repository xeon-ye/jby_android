package com.jkrm.education.bean.result;

/**
 * Created by hzw on 2019/6/18.
 */

public class StatisticsHomeworkSubmitRatioResultBean {


    /**
     * classId : 1
     * className : 初一(1)班
     * ctime : 2019-06-17
     * population : 118
     * studentId :
     * studentName :
     * submitted : 80
     * submittedRatio : 0.68
     */

    private String classId;
    private String className;
    private String ctime;
    private String population;
    private String studentId;
    private String studentName;
    private String submitted;
    private String submittedRatio;

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

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
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

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getSubmittedRatio() {
        return submittedRatio;
    }

    public void setSubmittedRatio(String submittedRatio) {
        this.submittedRatio = submittedRatio;
    }

    @Override
    public String toString() {
        return "StatisticsHomeworkSubmitRatioResultBean{" +
                "classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", ctime='" + ctime + '\'' +
                ", population='" + population + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", submitted='" + submitted + '\'' +
                ", submittedRatio='" + submittedRatio + '\'' +
                '}';
    }
}
