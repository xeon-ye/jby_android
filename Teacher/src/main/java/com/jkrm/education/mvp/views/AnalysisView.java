package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.TaskBean;
import com.jkrm.education.bean.exam.ClassBean;
import com.jkrm.education.bean.exam.ExamSearchBean;
import com.jkrm.education.bean.exam.GradeBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface AnalysisView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getAnalysisList(RequestBody requestBody);

        void getGradeList(RequestBody requestBody);

        void getClassList(String teacherId);
    }

    interface View extends AwBaseView {
        void getAnalysisListSuccess(ExamSearchBean data);
        void getAnalysisListFail(String msg);

        void getGradeListSuccess(List<GradeBean> data);
        void getGradeListFail(String msg);

        void getClassListSuccess(List<ClassBean> data);
        void getClassListFail(String msg);
    }

}