package com.jkrm.education.bean.rx;

/**
 * Created by hzw on 2019/7/18.
 */

public class RxPracticeNumsType {

    private String startDate;
    private String endDate;

    public RxPracticeNumsType(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
