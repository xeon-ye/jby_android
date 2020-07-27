package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.ClassStudentBean;

import java.util.List;

/**
 * Created by hzw on 2019/6/12.
 */

public class RxStatisticsStudentType {

    private List<ClassStudentBean> studentList;
    private int tag;

    public RxStatisticsStudentType(List<ClassStudentBean> teacherList, int tag) {
        this.studentList = teacherList;
        this.tag = tag;
    }

    public List<ClassStudentBean> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<ClassStudentBean> studentList) {
        this.studentList = studentList;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
