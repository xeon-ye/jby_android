package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.TeacherClassCouresResultBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface ScoreMainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getTeacherClassAndCourseList(String teacher_id);
        void getAnswerSheets(String teacher_id, String start_date, String end_date, String class_ids, String course_id, int page_);
    }

    interface View extends AwBaseView {
        void getTeacherClassAndCourseListSuccess(List<TeacherClassCouresResultBean> list);
        void getTeacherClassAndCourseListFail(String msg);
        void needBingSchoolClass();

        void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current);
        void getAnswerSheetsFail(String msg);
    }

}