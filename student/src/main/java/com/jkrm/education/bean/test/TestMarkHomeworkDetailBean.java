package com.jkrm.education.bean.test;

import java.util.List;

/**
 * Created by hzw on 2019/5/18.
 */

public class TestMarkHomeworkDetailBean {

    private String classesName;
    private int submitCount;
    private int totalCount;
    private List<TestQuestionBean> questionList;

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public int getSubmitCount() {
        return submitCount;
    }

    public void setSubmitCount(int submitCount) {
        this.submitCount = submitCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<TestQuestionBean> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<TestQuestionBean> questionList) {
        this.questionList = questionList;
    }
}
