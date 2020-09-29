package com.jkrm.education.bean.exam;

import java.io.Serializable;

/**
 * @Author: Zhoujing
 * Createdate: 2020/9/29 15:48
 * Description: 构造需要的bean
 */
public class ScoreQuestionBean implements Serializable {

    private String studCode;
    private String studExamCode;
    private String studId;
    private String studName;
    private String myScore;
    private String subjectScore;
    private String objectScore;
    private String examCategory; //判断是否联考，1为有
    private String gradeRank;
    private String classRank;
    private String jointRank;
    private String isOption;
    private String studAnswer;
    private String className;
    private String classId;
    private String courseId;
    private String examId;
    private String answer;
    private String maxScore;
    private String questionId;
    private String questionNum;
    private String score;
    private String noSpanAnswer;

    public String getStudCode() {
        return studCode;
    }

    public void setStudCode(String studCode) {
        this.studCode = studCode;
    }

    public String getStudExamCode() {
        return studExamCode;
    }

    public void setStudExamCode(String studExamCode) {
        this.studExamCode = studExamCode;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getMyScore() {
        return myScore;
    }

    public void setMyScore(String myScore) {
        this.myScore = myScore;
    }

    public String getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(String subjectScore) {
        this.subjectScore = subjectScore;
    }

    public String getObjectScore() {
        return objectScore;
    }

    public void setObjectScore(String objectScore) {
        this.objectScore = objectScore;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public String getGradeRank() {
        return gradeRank;
    }

    public void setGradeRank(String gradeRank) {
        this.gradeRank = gradeRank;
    }

    public String getClassRank() {
        return classRank;
    }

    public void setClassRank(String classRank) {
        this.classRank = classRank;
    }

    public String getJointRank() {
        return jointRank;
    }

    public void setJointRank(String jointRank) {
        this.jointRank = jointRank;
    }

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }

    public String getStudAnswer() {
        return studAnswer;
    }

    public void setStudAnswer(String studAnswer) {
        this.studAnswer = studAnswer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(String maxScore) {
        this.maxScore = maxScore;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getNoSpanAnswer() {
        return noSpanAnswer;
    }

    public void setNoSpanAnswer(String noSpanAnswer) {
        this.noSpanAnswer = noSpanAnswer;
    }

    @Override
    public String toString() {
        return "ScoreQuestionBean{" +
                "studCode='" + studCode + '\'' +
                ", studExamCode='" + studExamCode + '\'' +
                ", studId='" + studId + '\'' +
                ", studName='" + studName + '\'' +
                ", myScore='" + myScore + '\'' +
                ", subjectScore='" + subjectScore + '\'' +
                ", objectScore='" + objectScore + '\'' +
                ", examCategory='" + examCategory + '\'' +
                ", gradeRank='" + gradeRank + '\'' +
                ", classRank='" + classRank + '\'' +
                ", jointRank='" + jointRank + '\'' +
                ", isOption='" + isOption + '\'' +
                ", studAnswer='" + studAnswer + '\'' +
                ", className='" + className + '\'' +
                ", classId='" + classId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", examId='" + examId + '\'' +
                ", answer='" + answer + '\'' +
                ", maxScore='" + maxScore + '\'' +
                ", questionId='" + questionId + '\'' +
                ", questionNum='" + questionNum + '\'' +
                ", score='" + score + '\'' +
                ", noSpanAnswer='" + noSpanAnswer + '\'' +
                '}';
    }
}
