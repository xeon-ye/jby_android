package com.jkrm.education.bean;

/**
 * Created by hzw on 2019/6/18.
 */

public class QuestionOptionBean {

    public static final String[] SERIAL_NUMS = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"
    };

    private String serialNum;
    private String content;
    private boolean isSelect;

    public QuestionOptionBean(String serialNum, String content, boolean isSelect) {
        this.serialNum = serialNum;
        this.content = content;
        this.isSelect = isSelect;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
