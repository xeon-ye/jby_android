package com.jkrm.education.bean;

/**
 * Created by hzw on 2019/8/15.
 */

public class QuestionBasketAddRequestBean {

    //作业ID
    private String homeworkId;
    // 题目ID
    private String questionId;
    // 题目ID
    private String questionNum;
    //班级ID
    private String classId;
    //学科
    private String courseId;
    // 题类型
    private String questionTypeId;
    // 学生id
    private String studentId;
    // 学生学号
    private String studCode;
    //学生答案
    private String answer;
    //学生答案图片路径
    private String scanImage;

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudCode() {
        return studCode;
    }

    public void setStudCode(String studCode) {
        this.studCode = studCode;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getScanImage() {
        return scanImage;
    }

    public void setScanImage(String scanImage) {
        this.scanImage = scanImage;
    }
}
