package com.jkrm.education.bean.rx;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/5 10:39
 */

public class RxEditType {
    private boolean isEdit;
    private boolean isChoseAll;
    private boolean isClassify;

    public boolean isClassify() {
        return isClassify;
    }

    public void setClassify(boolean classify) {
        isClassify = classify;
    }


    public RxEditType(boolean isEdit, boolean isChoseAll) {
        this.isEdit = isEdit;
        this.isChoseAll = isChoseAll;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public boolean isChoseAll() {
        return isChoseAll;
    }

    public void setChoseAll(boolean choseAll) {
        isChoseAll = choseAll;
    }
}
