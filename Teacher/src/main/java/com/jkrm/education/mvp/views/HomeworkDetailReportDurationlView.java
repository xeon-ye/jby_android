package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.ReportDurationsResultBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface HomeworkDetailReportDurationlView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void report_durations(String homeworkId);
    }

    interface View extends AwBaseView {
        void report_durationsSuccess(List<ReportDurationsResultBean> result);
    }

}