package com.jkrm.education.bean.rx;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/24 20:14
 */

public class RxReviewStatusType {

    private String id;
    private boolean isRight;

    public RxReviewStatusType(String id, boolean isRight) {
        this.id = id;
        this.isRight = isRight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
