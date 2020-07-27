package com.jkrm.education.bean.result;

import com.hzw.baselib.util.MyDateUtil;

import java.util.List;

/**
 * Created by hzw on 2019/6/11.
 */

public class AllStudentScoreResultBean {


    /**
     * answerScore : C,B,B,B,A,B,4,4,1,4,8,10,4,10,12,12
     * classRank : 1.0
     * gradeRank : 86.0
     * id : sheet030120190203030303120207
     * questionId : prodquestion052,prodquestion053,prodquestion054,prodquestion055,prodquestion056,prodquestion057,prodquestion058,prodquestion059,prodquestion060,prodquestion061,prodquestion062,prodquestion063,prodquestion064,prodquestion065,prodquestion066,prodquestion067,prodquestion068
     * studCode : 20207
     * studentId : schstudent287
     * studentName : 何雨彤
     * totalScore : 73.0
     * typeName : 选择题,选择题,选择题,选择题,选择题,选择题,填空题,填空题,填空题,填空题,简答题,简答题,选择题,填空题,简答题,简答题,简答题
     */

    private String answerScore;
    private String classRank;
    private String gradeRank;
    private String id;
    private String questionId;
    private String studCode;
    private String studentId;
    private String studentName;
    private String totalScore;
    private String typeName;
    private String questionNum;
    private String isOption;

    //...........................自定义开始 .......................................

    public boolean isChoiceQuestion() {
//        return MyDateUtil.isChoiceQuestion(typeName);
        return "2".equals(isOption);
    }

    private List<String> questionIdList;
    private List<String> typeNameList;
    private List<String> questionNumList;
    private List<String> questionAnswerList;
    private List<String> optionsList;

    public List<String> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(List<String> questionIdList) {
        this.questionIdList = questionIdList;
    }

    public List<String> getTypeNameList() {
        return typeNameList;
    }

    public void setTypeNameList(List<String> typeNameList) {
        this.typeNameList = typeNameList;
    }

    public List<String> getQuestionNumList() {
        return questionNumList;
    }

    public void setQuestionNumList(List<String> questionNumList) {
        this.questionNumList = questionNumList;
    }

    public List<String> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<String> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }

    public List<String> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<String> optionsList) {
        this.optionsList = optionsList;
    }
    //...........................自定义结束........................................

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum;
    }

    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
    }

    public String getClassRank() {
        return classRank;
    }

    public void setClassRank(String classRank) {
        this.classRank = classRank;
    }

    public String getGradeRank() {
        return gradeRank;
    }

    public void setGradeRank(String gradeRank) {
        this.gradeRank = gradeRank;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getStudCode() {
        return studCode;
    }

    public void setStudCode(String studCode) {
        this.studCode = studCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
