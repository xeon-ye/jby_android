package com.jkrm.education.bean.rx;

import com.jkrm.education.bean.result.SimilarResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: xiangqian
 * @CreateDate: 2020/6/16 19:05
 */

public class RxErrorQuestion {

    private ArrayList<SimilarResultBean> mList;

    public RxErrorQuestion(ArrayList<SimilarResultBean> list) {
        mList = list;
    }

    public ArrayList<SimilarResultBean> getList() {
        return mList;
    }

    public void setList(ArrayList<SimilarResultBean> list) {
        mList = list;
    }
}
