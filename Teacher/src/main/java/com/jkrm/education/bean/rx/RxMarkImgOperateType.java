package com.jkrm.education.bean.rx;

/**
 * Created by hzw on 2019/6/13.
 */

public class RxMarkImgOperateType {

    private boolean isMark;
    private String totalMarkScore;
    private boolean isClick;

    public RxMarkImgOperateType(boolean isMark, String totalMarkScore, boolean isClick) {
        this.isMark = isMark;
        this.totalMarkScore = totalMarkScore;
        this.isClick = isClick;
    }

    public boolean isMark() {
        return isMark;
    }

    public void setMark(boolean mark) {
        isMark = mark;
    }

    public String getTotalMarkScore() {
        return totalMarkScore;
    }

    public void setTotalMarkScore(String totalMarkScore) {
        this.totalMarkScore = totalMarkScore;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
