package com.jkrm.education.bean.test;

/**
 * 分类
 * Created by hzw on 2019/5/17.
 */

public class TestMarkKindsBean {

    private boolean checked;
    private String name;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestMarkKindsBean() {
    }

    public TestMarkKindsBean(boolean checked, String name) {

        this.checked = checked;
        this.name = name;
    }
}
