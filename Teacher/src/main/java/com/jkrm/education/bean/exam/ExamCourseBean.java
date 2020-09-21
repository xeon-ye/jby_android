package com.jkrm.education.bean.exam;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/9/21 17:07
 */

public class ExamCourseBean implements Serializable {


    /**
     * code : 200
     * data : [{"courseId":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文","id":"","totalScore":""},{"courseId":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语","id":"","totalScore":""},{"courseId":"cbbf06bb966eca984018ba4cb434afa2","courseName":"物理","id":"","totalScore":""},{"courseId":"eea0aa1958f69dd3490eb37eca400444","courseName":"化学","id":"","totalScore":""},{"courseId":"861fb3717ebd9889022980b5d99022b0","courseName":"数学(文)","id":"","totalScore":""}]
     * msg :
     */


        /**
         * courseId : fab6c26483dd26d2dbf4995ece9acdb3
         * courseName : 语文
         * id :
         * totalScore :
         */

        private String courseId;
        private String courseName;
        private String id;
        private String totalScore;
        private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

        public String getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(String totalScore) {
            this.totalScore = totalScore;
        }
}
