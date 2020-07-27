package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.PracticeResultBean;
import com.jkrm.education.bean.result.PracticeTableResult;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.VideoResultBean;
import com.jkrm.education.bean.test.TestMarkBean;

import java.util.List;


/**
 * Created by hzw on 2018/11/13
 */
public interface PracticeMainFragmentView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getPracticeDataList(String student_id, String start_date, String end_date, String course_id, String questionTypeId);
//        void getPracticeDataTable(String student_id, String start_date, String end_date);
        void getVideos(String params);
    }

    interface View extends AwBaseView {
        void getPracticeDataListSuccess(PracticeDataResultBean bean);
        void getPracticeDataListFail(String msg);

//        void getPracticeDataTableSuccess(List<PracticeTableResult> list);
//        void getPracticeDataTableFail(String msg);

        void getVideosSuccess(VideoResultBean result);
        void getVideoFail(String msg);

    }
}