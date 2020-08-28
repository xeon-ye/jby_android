package com.jkrm.education.bean;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/8/28 10:19
 */

public class ExamReviewBean {
    private String id;
    private String answerId;//答题表id
    private String progress;//是否批阅0未批阅，1批阅
    private String rawScan;//题目解析图片
    private String sheetId;//sheet表Id
    private String maxScore;//题的最高分
    private String score;//题的得分
    private String questionId;//题目id
    private String questionNum;//题号
    private String sheetRawScan;//答题卡图片
    private String gradedScan;//批阅图片
    private String finishTime;//批阅完成时间
    private String missingStatus;//0缺考，1实考
    private int optStatus;
    private String scores;//步长
    private String questioType;//题的类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getRawScan() {
        return rawScan;
    }

    public void setRawScan(String rawScan) {
        this.rawScan = rawScan;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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

    public String getSheetRawScan() {
        return sheetRawScan;
    }

    public void setSheetRawScan(String sheetRawScan) {
        this.sheetRawScan = sheetRawScan;
    }

    public String getGradedScan() {
        return gradedScan;
    }

    public void setGradedScan(String gradedScan) {
        this.gradedScan = gradedScan;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getMissingStatus() {
        return missingStatus;
    }

    public void setMissingStatus(String missingStatus) {
        this.missingStatus = missingStatus;
    }

    public int getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(int optStatus) {
        this.optStatus = optStatus;
    }

    public String getScores() {
        return scores;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getQuestioType() {
        return questioType;
    }

    public void setQuestioType(String questioType) {
        this.questioType = questioType;
    }
}
