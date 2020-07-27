package com.jkrm.education.bean;

/**
 * Created by hzw on 2019/9/2.
 */

public class ExportQuesionBasketRequestBean {

     private String answer;
     private String explain;
     private String[] ids;

    public ExportQuesionBasketRequestBean(String answer, String explain, String[] ids) {
        this.answer = answer;
        this.explain = explain;
        this.ids = ids;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
