package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.VideoResultBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface ChartPracticeFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getPracticeDataTable(String student_id, String start_date, String end_date);
    }

    interface View extends AwBaseView {
        void getPracticeDataTableSuccess(List<PracticeTableResult> list);
        void getPracticeDataTableFail(String msg);
    }
}