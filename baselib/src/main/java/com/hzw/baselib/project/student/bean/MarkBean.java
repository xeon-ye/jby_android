package com.hzw.baselib.project.student.bean;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/4/9 17:50
 */

public class MarkBean {

    private boolean select;
    private String title;

    public MarkBean(boolean select, String title) {
        this.select = select;
        this.title = title;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
