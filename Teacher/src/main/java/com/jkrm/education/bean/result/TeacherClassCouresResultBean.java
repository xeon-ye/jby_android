package com.jkrm.education.bean.result;

import java.util.List;

/**
 * Created by hzw on 2019/8/28.
 */

public class TeacherClassCouresResultBean {


    /**
     * classList : [{"classId":"ea01ec3230c34eee81253fc2a168dc28","className":"七年级1班","courseList":[{"course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"course":"a15c1d2ced38de900e5158753b111aa8","courseName":"科学"},{"course":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文"},{"course":"e533d90cddd06a2d280aa20027d1c15a","courseName":"道德与法治"},{"course":"60e06b3ccd9ff6c4bc754e265c8cd6cb","courseName":"历史与社会"},{"course":"2abad4627d44c059a599dcd55b40869b","courseName":"数学"}]}]
     * gradeId : 6d0005e737f6890a434ac68dd7ae9a51
     * gradeName : 七年级
     */

    private String gradeId;
    private String gradeName;
    private List<ClassListBean> classList;
    private boolean isSelect;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public List<ClassListBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassListBean> classList) {
        this.classList = classList;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public static class ClassListBean {
        /**
         * classId : ea01ec3230c34eee81253fc2a168dc28
         * className : 七年级1班
         * courseList : [{"course":"eb96a394615f679a562ef6acdf104dbe","courseName":"英语"},{"course":"a15c1d2ced38de900e5158753b111aa8","courseName":"科学"},{"course":"fab6c26483dd26d2dbf4995ece9acdb3","courseName":"语文"},{"course":"e533d90cddd06a2d280aa20027d1c15a","courseName":"道德与法治"},{"course":"60e06b3ccd9ff6c4bc754e265c8cd6cb","courseName":"历史与社会"},{"course":"2abad4627d44c059a599dcd55b40869b","courseName":"数学"}]
         */

        private String classId;
        private String className;
        private List<CourseListBean> courseList;
        private boolean isSelect;

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

        public List<CourseListBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseListBean> courseList) {
            this.courseList = courseList;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public static class CourseListBean {
            /**
             * course : eb96a394615f679a562ef6acdf104dbe
             * courseName : 英语
             */

            private String course;
            private String courseName;
            private boolean isSelect;

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

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }
        }
    }
}
