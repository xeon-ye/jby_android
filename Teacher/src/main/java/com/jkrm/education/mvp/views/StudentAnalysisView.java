package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface StudentAnalysisView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getColumnData(String examId,String studId);
        void getLineData(String examId,String studId);
    }

    interface View extends AwBaseView {
        void getColumnDataSuccess();
        void getColumnDataFail(String msg);

        void getLineDataSuccess();
        void getLineDataFail(String msg);
    }

}