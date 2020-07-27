package com.jkrm.education.bean.type;

/**
 * Created by hzw on 2019/6/24.
 */

public class TypeCourseBean {

    private String courseId;
    private String courseName;
    private boolean isSelect;

    public TypeCourseBean(){

    }

    public TypeCourseBean(String courseId, String courseName, boolean isSelect) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.isSelect = isSelect;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
