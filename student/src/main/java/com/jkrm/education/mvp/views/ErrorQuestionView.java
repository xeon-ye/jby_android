package com.jkrm.education.mvp.views;

import com.hzw.baselib.base.AwBasePresenter;
import com.hzw.baselib.base.AwBaseView;
import com.hzw.baselib.project.student.bean.AnswerSheetAllDataResultBean;
import com.hzw.baselib.project.student.bean.SubjectDataResultBean;
import com.jkrm.education.bean.result.AnswerSheetDataResultBean;
import com.jkrm.education.bean.result.ErrorQuestionResultBean;
import com.jkrm.education.bean.result.PracticeDataResultBean;
import com.jkrm.education.bean.result.RowsHomeworkBean;
import com.jkrm.education.bean.result.VideoResultBean;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Path;


/**
 * Created by hzw on 2018/11/13
 */
public interface ErrorQuestionView extends AwBaseView {

    interface Presenter extends AwBasePresenter {
        void getHomeworkListAll(String studentId,String course_id);
        void getMistakeListNew(String student_id, String current, String start_date, String end_date, String homework_id, String course_id, String order_type);
        void getVideos(String params);
        void addQuestionBasket(RequestBody body);
        void delSomeOneQuestionBasket(String questionId, String studentId);
        void getSubjectList(String studentId);
        void getPracticeDataList(String student_id, String start_date, String end_date, String course_id, String questionTypeId);
    }

    interface View extends AwBaseView {
        void getHomeworkListAllSuccess(List<AnswerSheetAllDataResultBean> model);
        void getHomeworkListAllFail(String msg);

        void getMistakeListSuccess(AnswerSheetDataResultBean data, List<ErrorQuestionResultBean> list, int total, int size, int pages, int current);
        void getMistakeListFail(String msg);

        void getVideosSuccess(VideoResultBean result);
        void getVideoFail(String msg);

        void addQuestionBasketSuccess(String msg);
        void delSomeOneQuestionBasketSuccess(String msg);

        void getSubjectListSuccess(List<SubjectDataResultBean> dataResultBeans);

        void getPracticeDataListSuccess(PracticeDataResultBean bean);
        void getPracticeDataListFail(String msg);

    }

}