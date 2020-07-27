package com.jkrm.education.bean.test;

/**
 * Created by hzw on 2019/5/20.
 */

public class TestMarkDetailBean {

    private int type;
    private String questionNum;
    private int markedCount;
    private int percent;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public int getMarkedCount() {
        return markedCount;
    }

    public void setMarkedCount(int markedCount) {
        this.markedCount = markedCount;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "TestMarkDetailBean{" +
                "type=" + type +
                ", questionNum='" + questionNum + '\'' +
                ", markedCount=" + markedCount +
                ", percent=" + percent +
                '}';
    }
}
