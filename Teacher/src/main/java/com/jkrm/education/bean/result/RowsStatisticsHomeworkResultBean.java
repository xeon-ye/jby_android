package com.jkrm.education.bean.result;

import java.io.Serializable;

/**
 * Created by hzw on 2019/6/26.
 */

public class RowsStatisticsHomeworkResultBean implements Serializable {


    /**
     * hwName : 检测一(1.1～1.3)
     * id : 03012019020303030511
     */

    private String hwName;
    private String id;
    private boolean isSelect;

    public String getHwName() {
        return hwName;
    }

    public void setHwName(String hwName) {
        this.hwName = hwName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
