package com.jkrm.education.bean.result;

import java.io.Serializable;

/**
 * Created by hzw on 2019/6/12.
 */

public class TeacherClassBean implements Serializable{

    private String classId;
    private String className;
    private String courseName;
    private String course;


    //自定义开始.........................................................

    private boolean isSelect;//是否选中

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
    //自定义结束.........................................................

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "TeacherClassBean{" +
                "classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
