package com.jkrm.education.bean.rx;

/**
 * @Description: 翻页
 * @Author: xiangqian
 * @CreateDate: 2020/5/12 16:54
 */

public class RxTurnpageType {

    private boolean isOutSide;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RxTurnpageType(boolean isOutSide, String id) {
        this.isOutSide = isOutSide;
        this.id = id;
    }

    public RxTurnpageType(boolean isOutSide) {
        this.isOutSide = isOutSide;
    }

    public RxTurnpageType() {
    }

    public boolean isOutSide() {
        return isOutSide;
    }

    public void setOutSide(boolean outSide) {
        isOutSide = outSide;
    }
}
