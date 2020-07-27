package com.jkrm.education.bean.result.education;

import java.util.List;

/**
 * Created by hzw on 2019/5/6.
 */

public class StudentListBean {


    private List<StudentsBean> students;

    public List<StudentsBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsBean> students) {
        this.students = students;
    }

    public static class StudentsBean {
        /**
         * id : 1
         * name : 张三
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
