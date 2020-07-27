package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.HomeworkDetailResultBean;

import java.util.List;

/**
 * Created by hzw on 2019/6/6.
 */

public class RxHomeworkDetailDurationType {

    private List<HomeworkDetailResultBean.HomeworkDuratBean> list;

    public RxHomeworkDetailDurationType(List<HomeworkDetailResultBean.HomeworkDuratBean> list) {
        this.list = list;
    }

    public List<HomeworkDetailResultBean.HomeworkDuratBean> getList() {
        return list;
    }

    public void setList(List<HomeworkDetailResultBean.HomeworkDuratBean> list) {
        this.list = list;
    }
}
