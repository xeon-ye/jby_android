package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.exam.ColumnDataBean;
import com.jkrm.education.bean.exam.ExamCompreBean;
import com.jkrm.education.bean.exam.LineDataBean;
import com.jkrm.education.bean.exam.OverViewBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ViewStudentAnswerSheetView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getCourseList(RequestBody requestBody);
    }

    interface View extends AwBaseView {

        void getCourseListSuccess(ExamCompreBean data);
        void getCourseListFail(String msg);


    }

}