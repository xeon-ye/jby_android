package com.jkrm.education.bean.test;

import com.hzw.baselib.bean.AwPopupBaseBean;

/**
 * 科目
 * Created by hzw on 2019/5/17.
 */

public class TestMarkCourseBean extends AwPopupBaseBean {

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
}
