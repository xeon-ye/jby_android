package com.jkrm.education.bean.result;

import com.jkrm.education.bean.ClassBean;
import com.jkrm.education.bean.CourseBean;
import com.jkrm.education.bean.type.TypeClassBean;

import java.util.List;

/**
 * Created by hzw on 2019/5/30.
 */

public class AnswerSheetDataResultBean {

    private List<ClassBean> classList;
    private List<CourseBean> coursesList;
    private List<ErrorQuestionResultBean> newList;

    public List<ClassBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ClassBean> classList) {
        this.classList = classList;
    }

    public List<CourseBean> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(List<CourseBean> coursesList) {
        this.coursesList = coursesList;
    }

    public List<ErrorQuestionResultBean> getNewList() {
        return newList;
    }

    public void setNewList(List<ErrorQuestionResultBean> newList) {
        this.newList = newList;
    }
}
