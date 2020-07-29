package com.jkrm.education.bean.test;

import java.io.Serializable;

/**
 * Created by hzw on 2019/5/22.
 */

public class TestMarkCommonUseScoreBean implements Serializable {

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
