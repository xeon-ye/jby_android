package com.jkrm.education.bean.rx;

import java.util.List;

/**
 * Created by hzw on 2019/8/16.
 */

public class RxRefreshQuestionBasketType {

    public static final String TAG_QUESTION_BASKET = "questionBasket";
    public static final String TAG_ERROR_QUESTION = "errorQuestion";
    public static final String TAG_HOMEWORK_DETAIL = "homeworkDetail";

    private String tag;
    private boolean isDel;
    private List<String> questionIds;

    public RxRefreshQuestionBasketType(String tag, boolean isDel, List<String> questionIds) {
        this.tag = tag;
        this.isDel = isDel;
        this.questionIds = questionIds;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }
}
