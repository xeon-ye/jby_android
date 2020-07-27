package com.jkrm.education.bean;

/**
 * Created by hzw on 2019/9/29.
 */

public class ErrorQuestionStatisticsBean {

    private String num;
    private String type;
    private boolean isSelect;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
