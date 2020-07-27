package com.jkrm.education.bean.user;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/30 18:18
 */

public class SubmitAnswerSheetBean {

    private String questionId;
    private String answer="";

    public SubmitAnswerSheetBean(String questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public SubmitAnswerSheetBean() {
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

    @Override
    public String toString() {
        return "SubmitAnswerSheetBean{" +
                "questionId='" + questionId + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
