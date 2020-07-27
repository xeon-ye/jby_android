package com.jkrm.education.bean.test;

import java.io.Serializable;

/**
 * 班级联系人
 * Created by hzw on 2019/5/16.
 */

public class TestMeClassesContactBean implements Serializable {

    private String name;
    private String studentId;
    private String mobile;

    public TestMeClassesContactBean() {
    }

    public TestMeClassesContactBean(String name, String studentId, String mobile) {
        this.name = name;
        this.studentId = studentId;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
