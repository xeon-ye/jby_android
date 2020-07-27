package com.jkrm.education.bean.result;

/**
 * Created by hzw on 2019/6/11.
 */

public class StatusErrorQuestionResultBean {


    /**
     * allCnt : 491
     * catalogName : 正负数在实际生活中的应用
     * errorCnt : 287
     * rate : 0.58
     * studentCnt : 101
     */

    private String allCnt;
    private String catalogName;
    private String errorCnt;
    private String rate;
    private String studentCnt;
    private String studentScore;
    private String catalogScore;
    private String scoreRate;

    public String getAllCnt() {
        return allCnt;
    }

    public void setAllCnt(String allCnt) {
        this.allCnt = allCnt;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(String errorCnt) {
        this.errorCnt = errorCnt;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getStudentCnt() {
        return studentCnt;
    }

    public void setStudentCnt(String studentCnt) {
        this.studentCnt = studentCnt;
    }

    public String getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(String studentScore) {
        this.studentScore = studentScore;
    }

    public String getCatalogScore() {
        return catalogScore;
    }

    public void setCatalogScore(String catalogScore) {
        this.catalogScore = catalogScore;
    }

    public String getScoreRate() {
        return scoreRate;
    }

    public void setScoreRate(String scoreRate) {
        this.scoreRate = scoreRate;
    }
}
