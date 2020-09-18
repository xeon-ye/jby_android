package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ColumnDataBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;
import com.jkrm.education.bean.exam.LineDataBean;
import com.jkrm.education.bean.exam.OverViewBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface StudentAnalysisView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getOverView(RequestBody requestBody);
        void getColumnData(RequestBody requestBody);
        void getLineData(RequestBody requestBody);
    }

    interface View extends AwBaseView {

        void getOverViewSuccess(OverViewBean overViewBean);
        void getOverViewFail(String msg);

        void getColumnDataSuccess(List<ColumnDataBean> data);
        void getColumnDataFail(String msg);

        void getLineDataSuccess(List<LineDataBean> data);
        void getLineDataFail(String msg);
    }

}