package com.jkrm.education.bean.type;

/**
 * Created by hzw on 2019/6/24.
 */

public class TypeClassBean {

    private String classId;
    private String className;
    private boolean isSelect;

    public TypeClassBean(){

    }

    public TypeClassBean(String classId, String className, boolean isSelect) {
        this.classId = classId;
        this.className = className;
        this.isSelect = isSelect;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
