package com.jkrm.education.bean.result;

/**
 * 作业详情报表 --- 得分率
 * Created by hzw on 2019/6/1.
 */

public class ReportQuestionScoreRateResultBean {


    /**
     * maxScore : 4.0
     * questionNum : 1
     * questionScore : 4.0
     * ratio : 1.0
     */

    private String maxScore;
    private String questionNum;
    private String questionScore;
    private String ratio;
    private String avgScore;
    private String subScore;
    private String questSubScore;

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
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

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
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

    public String getQuestSubScore() {
        return questSubScore;
    }

    public void setQuestSubScore(String questSubScore) {
        this.questSubScore = questSubScore;
    }

    @Override
    public String toString() {
        return "ReportQuestionScoreRateResultBean{" +
                "maxScore='" + maxScore + '\'' +
                ", questionNum='" + questionNum + '\'' +
                ", questionScore='" + questionScore + '\'' +
                ", ratio='" + ratio + '\'' +
                ", avgScore='" + avgScore + '\'' +
                ", subScore='" + subScore + '\'' +
                ", questSubScore='" + questSubScore + '\'' +
                '}';
    }
}
