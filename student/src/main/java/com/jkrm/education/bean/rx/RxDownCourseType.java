package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.CoursePlayResultBean;

import java.util.List;

public class RxDownCourseType {
    private List<CoursePlayResultBean.VideoListBean> list;
    private List<String> downUrlList;

    public void setList(List<CoursePlayResultBean.VideoListBean> list) {
        this.list = list;
    }

    public List<CoursePlayResultBean.VideoListBean> getList() {
        return list;
    }

    public RxDownCourseType(List<String> downUrlList) {
        this.downUrlList = downUrlList;
    }

    public List<String> getDownUrlList() {
        return downUrlList;
    }

    public void setDownUrlList(List<String> downUrlList) {
        this.downUrlList = downUrlList;
    }
}
