package com.jkrm.education.bean.result.error;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/7/23 11:13
 */

public class ErrorCourseBean {


    /**
     * code : 200
     * data : [{"course":"","courseId":"2abad4627d44c059a599dcd55b40869b","courseName":"数学","id":""}]
     * msg :
     */


    /**
     * course :
     * courseId : 2abad4627d44c059a599dcd55b40869b
     * courseName : 数学
     * id :
     */

    private String course;
    private String courseId;
    private String courseName;
    private String id;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
