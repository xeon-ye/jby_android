package com.jkrm.education.bean.result;

import com.hzw.baselib.util.AwDataUtil;
import com.hzw.baselib.util.MyDateUtil;

/**
 * Created by hzw on 2019/6/6.
 */

public class HomeworkStudentAnswerWithSingleQuestionResultBean {


    /**
     * answerScore : 4.0
     * studCode : 30067
     * studentId : schstudent067
     * studentName : 金慧丹
     * typeName : 填空题
     */

    private String answerScore;
    private String studCode;
    private String studentId;
    private String studentName;
    private String typeName;
    private String rowScan;
    private String isOption;

    public String getIsOption() {
        return isOption;
    }

    public void setIsOption(String isOption) {
        this.isOption = isOption;
    }

    public String getRowScan() {
        return rowScan;
    }

    public void setRowScan(String rowScan) {
        this.rowScan = rowScan;
    }

    //自定义开始.......................................................
    public boolean isChoiceQuestion() {
        return MyDateUtil.isChoiceQuestion(typeName,isOption);
    }
    public String getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(String answerScore) {
        this.answerScore = answerScore;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
