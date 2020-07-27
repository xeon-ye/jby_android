package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.HomeworkDetailResultBean;

import java.util.List;

/**
 * Created by hzw on 2019/6/6.
 */

public class RxHomeworkDetailRatioType {

    private List<HomeworkDetailResultBean.QuestionScoreBean> list;

    public RxHomeworkDetailRatioType(List<HomeworkDetailResultBean.QuestionScoreBean> list) {
        this.list = list;
    }

    public List<HomeworkDetailResultBean.QuestionScoreBean> getList() {
        return list;
    }

    public void setList(List<HomeworkDetailResultBean.QuestionScoreBean> list) {
        this.list = list;
    }
}
