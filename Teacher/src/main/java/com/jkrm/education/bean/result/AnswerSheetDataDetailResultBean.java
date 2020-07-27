package com.jkrm.education.bean.result;

import com.hzw.baselib.util.MyDateUtil;
import com.hzw.baselib.util.RandomValueUtil;

/**
 * Created by hzw on 2019/5/30.
 */

public class AnswerSheetDataDetailResultBean {

    /**
     * answer : 学生答案(仅限选择题)
     * answerSheetId : 71238ff17644633fbedd775c93bab854 答题卡id
     * correctness : 0
     * createBy :
     * createTime : 2019-05-30T17:08:37+0800
     * deleteStatus : 0
     * gradedScan :
     * graderId : teacher_001
     * id : 003483e521993ae70c4b063baff5511e
     * maxScore : 8.0 原题总分
     * prodAnswer : (1)2,3.6,15，　(2)－8，－31/2，－51/4，－1/6，　(3)2，－8,0,15　(4)－31/2，3.6，－51/4，－1/6 原题答案
     * questionId : prodquestion011 原题id
     * questionNum : 11
     * rawScan : http://jbyweb.oss-cn-beijing.aliyuncs.com/app/output/155920718751010728/write/4/1.jpg
     * remark :
     * score : 4.0
     * status : 1
     * studentId : stu_092
     * updateBy :
     * "typeName":"填空题",
     * updateTime : 2019-05-31T06:08:36+0800
     */

    private String answer;
    private String answerSheetId;
    private String correctness;
    private String createBy;
    private String createTime;
    private String deleteStatus;
    private String gradedScan;
    private String graderId;
    private String id;
    private String maxScore;
    private String prodAnswer;
    private String questionId;
    private String questionNum;
    private String rawScan;
    private String remark;
    private String score;
    private String status;
    private String studentId;
    private String updateBy;
    private String updateTime;
    private String typeName;

    //------------------------------------------自定义start-------------------------------------------------

    private String rate;//得分率

    public String getRate() {
        return RandomValueUtil.getNum(0, 100) + "%";
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    /**
     * 是否是选择题
     * @return
     */
    public boolean isChoiceQuestion() {
        return MyDateUtil.isChoiceQuestion(typeName,"");
    }

    //------------------------------------------自定义end-------------------------------------------------

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerSheetId() {
        return answerSheetId;
    }

    public void setAnswerSheetId(String answerSheetId) {
        this.answerSheetId = answerSheetId;
    }

    public String getCorrectness() {
        return correctness;
    }

    public void setCorrectness(String correctness) {
        this.correctness = correctness;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getGradedScan() {
        return gradedScan;
    }

    public void setGradedScan(String gradedScan) {
        this.gradedScan = gradedScan;
    }

    public String getGraderId() {
        return graderId;
    }

    public void setGraderId(String graderId) {
        this.graderId = graderId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
    }

    public String getProdAnswer() {
        return prodAnswer;
    }

    public void setProdAnswer(String prodAnswer) {
        this.prodAnswer = prodAnswer;
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

    public String getRawScan() {
        return rawScan;
    }

    public void setRawScan(String rawScan) {
        this.rawScan = rawScan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
