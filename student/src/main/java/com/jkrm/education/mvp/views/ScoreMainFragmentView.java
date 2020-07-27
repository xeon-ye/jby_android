package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.test.TestMarkBean;

import java.util.List;

import okhttp3.RequestBody;


/**
 * Created by hzw on 2018/11/13
 */
public interface ScoreMainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getAnswerSheets(String student_id, String start_date, String end_date, String class_id, String course_id, int page_);
    }

    interface View extends AwBaseView {
        void getAnswerSheetsSuccess(AnswerSheetDataResultBean data, List<RowsHomeworkBean> list, int total, int size, int pages, int current);
        void getAnswerSheetsFail(String msg);
    }

}