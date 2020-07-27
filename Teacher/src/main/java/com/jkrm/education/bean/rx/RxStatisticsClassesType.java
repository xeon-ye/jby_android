package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.List;

/**
 * Created by hzw on 2019/6/12.
 */

public class RxStatisticsClassesType {

    private List<TeacherClassBean> teacherList;
    private int tag;

    public RxStatisticsClassesType(List<TeacherClassBean> teacherList, int tag) {
        this.teacherList = teacherList;
        this.tag = tag;
    }

    public List<TeacherClassBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherClassBean> teacherList) {
        this.teacherList = teacherList;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
