package com.jkrm.education.bean;

/**
 * @Description: 提交答案
 * @Author: xiangqian
 * @CreateDate: 2020/5/27 11:41
 */

public class QuestionBean {
    private String questionId="";
    private String parentId="";
    private String answer="";
    private String konw="1";
    private String idx="1";


    public QuestionBean(String questionId) {
        this.questionId = questionId;
    }

    public QuestionBean(String questionId, String parentId) {
        this.questionId = questionId;
        this.parentId = parentId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getKonw() {
        return konw;
    }

    public void setKonw(String konw) {
        this.konw = konw;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "QuestionBean{" +
                "questionId='" + questionId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", answer='" + answer + '\'' +
                ", konw='" + konw + '\'' +
                ", idx='" + idx + '\'' +
                '}';
    }
}
