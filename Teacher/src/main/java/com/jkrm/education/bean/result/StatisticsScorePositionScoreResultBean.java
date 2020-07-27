package com.jkrm.education.bean.result;

/**
 * 分数段
 * Created by hzw on 2019/6/26.
 */

public class StatisticsScorePositionScoreResultBean {


    /**
     * classId : 1
     * className : 初一(1)班
     * nowCount :
     * nowProp :
     * population : 59
     * scale : 1-10
     * teacherName : 张老师
     * totalCount : 30
     * totalProp : 0.1829
     */

    private String classId;
    private String className;
    private String nowCount;
    private String nowProp;
    private String population;
    private String scale;
    private String teacherName;
    private String totalCount;
    private String totalProp;

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

    public String getNowCount() {
        return nowCount;
    }

    public void setNowCount(String nowCount) {
        this.nowCount = nowCount;
    }

    public String getNowProp() {
        return nowProp;
    }

    public void setNowProp(String nowProp) {
        this.nowProp = nowProp;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalProp() {
        return totalProp;
    }

    public void setTotalProp(String totalProp) {
        this.totalProp = totalProp;
    }
}
