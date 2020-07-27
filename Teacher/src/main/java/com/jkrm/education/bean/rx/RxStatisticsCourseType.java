package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.RowsStatisticsHomeworkResultBean;

/**
 * Created by hzw on 2019/6/12.
 */

public class RxStatisticsCourseType {

    private RowsStatisticsHomeworkResultBean homeworkBean;

    public RxStatisticsCourseType() {
    }

    public RxStatisticsCourseType(RowsStatisticsHomeworkResultBean homeworkBean) {
        this.homeworkBean = homeworkBean;
    }

    public RowsStatisticsHomeworkResultBean getHomeworkBean() {
        return homeworkBean;
    }

    public void setHomeworkBean(RowsStatisticsHomeworkResultBean homeworkBean) {
        this.homeworkBean = homeworkBean;
    }
}
