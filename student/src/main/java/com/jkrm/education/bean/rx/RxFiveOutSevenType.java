package com.jkrm.education.bean.rx;

/**
 * @Description: 七选五状态修改
 * @Author: xiangqian
 * @CreateDate: 2020/5/26 17:44
 */

public class RxFiveOutSevenType {
    private String id;
    private String choice;
    private int type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RxFiveOutSevenType(String id, String choice, int type) {
        this.id = id;
        this.choice = choice;
        this.type = type;
    }

    public RxFiveOutSevenType(String choice, int type) {
        this.choice = choice;
        this.type = type;
    }

    public RxFiveOutSevenType() {
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
