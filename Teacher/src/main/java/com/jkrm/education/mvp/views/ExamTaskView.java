package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.ReViewTaskBean;
import com.jkrm.education.bean.result.TeacherClassBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface ExamTaskView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getReviewTaskList(String teacherId,String examId,String paperId);
    }

    interface View extends AwBaseView {
       // void getReviewTaskListSuccess(int readWay,String readWayName,int finishRead,int totalRead,List<ReViewTaskBean.Bean> quesList);
        void getReviewTaskListSuccess(List<ReViewTaskBean> data);
        void getReviewTaskListFail(String msg);
    }

}