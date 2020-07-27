package com.jkrm.education.bean.todo;

/**
 * 待办事项
 * Created by hzw on 2019/5/9.
 */

public class TodoBean {

    private int id; //序号
    private int idBg; //序号背景色
    private String title; //标题
    private int status; //操作状态
    private String date; //时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBg() {
        return idBg;
    }

    public void setIdBg(int idBg) {
        this.idBg = idBg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isAllowOperate() {
        return 0 == status;
    }
}
