package com.jkrm.education.bean.test;

/**
 * Created by hzw on 2019/5/18.
 */

public class TestQuestionBean {

    private String questionNum;
    private int totalScore;
    private String rightAnswer;
    private double rate;
    private int type;//题目类型 1选择题 2填空题 3解答题 4阅读题 5作文题....
    private String questionImgUrl;

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestionImgUrl() {
        return questionImgUrl;
    }

    public void setQuestionImgUrl(String questionImgUrl) {
        this.questionImgUrl = questionImgUrl;
    }
}
