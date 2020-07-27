package com.jkrm.education.bean.rx;

import com.jkrm.education.db.DaoVideoBean;

import java.util.ArrayList;
import java.util.List;

public class RxCostomDownType {
    List<DaoVideoBean> daoVideoBeanArrayList;

    public RxCostomDownType(List<DaoVideoBean> daoVideoBeanArrayList) {
        this.daoVideoBeanArrayList = daoVideoBeanArrayList;
    }

    public List<DaoVideoBean> getDaoVideoBeanArrayList() {
        return daoVideoBeanArrayList;
    }

    public void setDaoVideoBeanArrayList(List<DaoVideoBean> daoVideoBeanArrayList) {
        this.daoVideoBeanArrayList = daoVideoBeanArrayList;
    }
}
