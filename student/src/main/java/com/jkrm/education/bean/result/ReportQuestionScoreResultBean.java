package com.jkrm.education.bean.result;

/**
 * 作业详情报表 --- 得分
 * Created by hzw on 2019/6/1.
 */

public class ReportQuestionScoreResultBean {


    /**
     * score : 4.0 学生分数
     * questionNum : 1
     * questionScore : 4.0 总分
     */

    private String score;
    private String questionNum;
    private String questionScore;
    private String classAvg;
    private String avgScore;
    private String subScore;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public String getClassAvg() {
        return classAvg;
    }

    public void setClassAvg(String classAvg) {
        this.classAvg = classAvg;
    }

    public String getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(String avgScore) {
        this.avgScore = avgScore;
    }

    public String getSubScore() {
        return subScore;
    }

    public void setSubScore(String subScore) {
        this.subScore = subScore;
    }
}
