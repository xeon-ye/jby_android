package com.jkrm.education.bean.test;

/**
 * Created by hzw on 2019/5/22.
 */

public class TestMarkCommonUseScoreBean {

    private String score;
    private boolean isHandleModify;
    private boolean isSelect;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public boolean isHandleModify() {
        return isHandleModify;
    }

    public void setHandleModify(boolean handleModify) {
        isHandleModify = handleModify;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
