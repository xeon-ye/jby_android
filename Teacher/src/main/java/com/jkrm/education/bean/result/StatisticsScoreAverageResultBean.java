package com.jkrm.education.bean.result;

/**
 * Created by hzw on 2019/6/26.
 */

public class StatisticsScoreAverageResultBean {


    /**
     * avgScore : 78.333336
     * classAvg : 309.18182
     * classId : 1
     * className : 初一一班
     * devScore : 230.84848
     * totalScore : 470.0
     */

    private String avgScore; //班级均分
    private String classAvg; //年级均分
    private String classId;
    private String className;
    private String devScore; //均分差
    private String totalScore; //总分
    private boolean isLeader;

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getClassAvg() {
        return classAvg;
    }

    public void setClassAvg(String classAvg) {
        this.classAvg = classAvg;
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

    public String getDevScore() {
        return devScore;
    }

    public void setDevScore(String devScore) {
        this.devScore = devScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }
}
