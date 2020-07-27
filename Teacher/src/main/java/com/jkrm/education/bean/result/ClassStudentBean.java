package com.jkrm.education.bean.result;

import com.hzw.baselib.util.AwDataUtil;

import java.io.Serializable;

/**
 * Created by hzw on 2019/6/12.
 */

public class ClassStudentBean implements Serializable {

    private String id;
    private String userId;
    private String studentName;
    private String studCode;
    private String enrollmentYear;
    private String grade;
    private String schId;
    private String schPhase;
    private String phone;

    //自定义开始.........................................................

    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
    //自定义结束.........................................................

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudCode() {
        return studCode;
    }

    public void setStudCode(String studCode) {
        this.studCode = studCode;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(String enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchId() {
        return schId;
    }

    public void setSchId(String schId) {
        this.schId = schId;
    }

    public String getSchPhase() {
        return schPhase;
    }

    public void setSchPhase(String schPhase) {
        this.schPhase = schPhase;
    }

    public String getPhone() {
        if(AwDataUtil.isEmpty(phone)) {
            return "暂无手机号";
        }
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
