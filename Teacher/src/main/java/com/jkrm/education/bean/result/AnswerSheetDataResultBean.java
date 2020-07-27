package com.jkrm.education.bean.result;

import java.util.List;

/**
 * Created by hzw on 2019/5/30.
 */

public class AnswerSheetDataResultBean {

    private List<TeacherClassBean> classList;
    private List<TeacherClassBean> teachingClasses;

    public List<TeacherClassBean> getClassList() {
        return classList;
    }

    public void setClassList(List<TeacherClassBean> classList) {
        this.classList = classList;
    }

    public List<TeacherClassBean> getTeachingClasses() {
        return teachingClasses;
    }

    public void setTeachingClasses(List<TeacherClassBean> teachingClasses) {
        this.teachingClasses = teachingClasses;
    }
}
