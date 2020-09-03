package com.jkrm.education.bean.exam;

import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/28 17:37
 */

public class ExamReadHeaderBean implements Serializable {
    private String examName;
    private String questionId;
    private String questionNum;
    private String progress;
    private String readNum;
    private String readNumPercen;
    private Integer isNotReview;//是否有回评:0没有，1有
    private String maxScore;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getReadNumPercen() {
        return readNumPercen;
    }

    public void setReadNumPercen(String readNumPercen) {
        this.readNumPercen = readNumPercen;
    }

    public Integer getIsNotReview() {
        return isNotReview;
    }

    public void setIsNotReview(Integer isNotReview) {
        this.isNotReview = isNotReview;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }
}
