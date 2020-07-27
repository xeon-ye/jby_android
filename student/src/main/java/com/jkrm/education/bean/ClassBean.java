package com.jkrm.education.bean;

/**
 * Created by hzw on 2019/7/11.
 */

public class ClassBean {

    private String id;
    private String name;
    private boolean isSelect;

    public ClassBean(String id, String name, boolean isSelect) {
        this.id = id;
        this.name = name;
        this.isSelect = isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
