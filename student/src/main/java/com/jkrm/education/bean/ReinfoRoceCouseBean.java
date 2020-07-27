package com.jkrm.education.bean;

import android.text.BoringLayout;

import java.io.Serializable;

public class ReinfoRoceCouseBean implements Serializable {


        /**
         * course : 语文
         * courseId : fab6c26483dd26d2dbf4995ece9acdb3
         * courseName : 语文
         * id : fab6c26483dd26d2dbf4995ece9acdb3
         */

        private String course;
        private String courseId;
        private String courseName;
        private String id;
        private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public ReinfoRoceCouseBean(String course, String courseId, String courseName, String id, boolean select) {
        this.course = course;
        this.courseId = courseId;
        this.courseName = courseName;
        this.id = id;
        this.select = select;
    }

    public ReinfoRoceCouseBean(String course, String courseId, String courseName, String id) {
        this.course = course;
        this.courseId = courseId;
        this.courseName = courseName;
        this.id = id;
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
