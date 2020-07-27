package com.jkrm.education.bean.test;

import java.io.Serializable;
import java.util.List;

/**
 * 我的班级
 * Created by hzw on 2019/5/16.
 */

public class TestMeClassesBean implements Serializable {

    private int id;
    private String classes;
    private String course;
    private List<TestMeClassesContactBean> contactList;

    public TestMeClassesBean() {
    }

    public TestMeClassesBean(int id, String classes, String course, List<TestMeClassesContactBean> contactList) {
        this.id = id;
        this.classes = classes;
        this.course = course;
        this.contactList = contactList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<TestMeClassesContactBean> getContactList() {
        return contactList;
    }

    public void setContactList(List<TestMeClassesContactBean> contactList) {
        this.contactList = contactList;
    }
}
