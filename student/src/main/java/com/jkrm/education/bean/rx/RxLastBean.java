package com.jkrm.education.bean.rx;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/4 18:49
 */

public class RxLastBean {
    private boolean isLast;

    public boolean isLast() {
        return isLast;
    }

    public RxLastBean() {
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public RxLastBean(boolean isLast) {
        this.isLast = isLast;
    }
}
